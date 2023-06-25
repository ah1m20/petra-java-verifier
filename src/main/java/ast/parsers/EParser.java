package ast.parsers;

import ast.terms.expressions.e.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;

import static ast.interp.util.ParserUtils.invalidE;
import static com.github.javaparser.ast.expr.UnaryExpr.Operator.LOGICAL_COMPLEMENT;

public final class EParser {
    public E parse(MethodDeclaration declaration){
        if (declaration.getBody().isPresent() &&
                declaration.getBody().get().getStatements().size()==1 &&
                    declaration.getBody().get().getStatements().get(0).isReturnStmt() &&
                        declaration.getBody().get().getStatements().get(0).asReturnStmt().getExpression().isPresent()){
            ReturnStmt returnStmt = declaration.getBody().get().getStatements().get(0).asReturnStmt();
            Expression expression = returnStmt.getExpression().get();
            return parse(expression);
        } else {
            return invalidE(declaration,"expected body with 1 statement.");
        }
    }

    private E parse(Expression expression){
        if (expression.isMethodCallExpr()) {
            if (expression.asMethodCallExpr().getArguments().size() == 0 &&
                    expression.asMethodCallExpr().getScope().isPresent() &&
                    expression.asMethodCallExpr().getScope().get().isNameExpr()) {
                return new Ap(expression.asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(), expression.asMethodCallExpr().getNameAsString());
            } else {
                return invalidE(expression,"invalid syntax, not the required format: a.p()");
            }
        } else if (expression.isBooleanLiteralExpr()){
            if (expression.asBooleanLiteralExpr().getValue()){
                return new True();
            } else {
                return invalidE(expression,"invalid syntax, only true is support.");
            }
        } else if (expression.isEnclosedExpr()){
            return new EUnary(parse(expression.asEnclosedExpr().getInner()),UnaryOperator.PAREN);
        } else if (expression.isUnaryExpr() && expression.asUnaryExpr().getOperator()==LOGICAL_COMPLEMENT){
            return new EUnary(parse(expression.asUnaryExpr().getExpression()),UnaryOperator.NOT);
        } else if (expression.isBinaryExpr()){
            BinaryOperator binaryOperator = translateBinaryOp(expression.asBinaryExpr().getOperator().asString());
            if (binaryOperator==BinaryOperator.UNKOWN){
                return invalidE(expression,"binary operator not expected, must be one of &&, ||, ^");
            } else {
                return new EBinary(
                        parse(expression.asBinaryExpr().getLeft()),
                        binaryOperator,
                        parse(expression.asBinaryExpr().getRight()));
            }
        } else {
            return invalidE(expression,"must be call to single boolean method or expression of boolean method calls.");
        }
    }

    private BinaryOperator translateBinaryOp(String in){
        switch (in){
            case "&&": return BinaryOperator.AND;
            case "||": return BinaryOperator.OR;
            case "^": return BinaryOperator.XOR;
            default: return BinaryOperator.UNKOWN;
        }
    }
}
