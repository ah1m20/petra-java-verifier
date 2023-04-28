package ast.parsers;

import ast.terms.expressions.e.Ap;
import ast.terms.expressions.e.BinaryOperator;
import ast.terms.expressions.e.E;
import ast.terms.expressions.e.EBinary;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;

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
            throw new IllegalArgumentException("expected body with 1 statement.");
        }
    }

    private E parse(Expression expression){
        if (expression.isMethodCallExpr()){
            if (expression.asMethodCallExpr().getArguments().size()==0 &&
                    expression.asMethodCallExpr().getScope().isPresent() &&
                    expression.asMethodCallExpr().getScope().get().isNameExpr()){
                return new Ap(expression.asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(),expression.asMethodCallExpr().getNameAsString());
            } else {
                throw new IllegalArgumentException("invalid syntax, not the required format: a.p()");
            }
        } else if (expression.isEnclosedExpr()){
            return parse(expression.asEnclosedExpr().getInner());
        } else if (expression.isBinaryExpr()){
            return new EBinary(
                    parse(expression.asBinaryExpr().getLeft()),
                    translateBinaryOp(expression.asBinaryExpr().getOperator().asString()),
                    parse(expression.asBinaryExpr().getRight()));
        } else {
            throw new IllegalArgumentException("must be call to single boolean method or expression of boolean method calls.");
        }
    }

    private BinaryOperator translateBinaryOp(String in){
        switch (in){
            case "&&": return BinaryOperator.AND;
            case "||": return BinaryOperator.OR;
            case "^": return BinaryOperator.XOR;
            default: throw new IllegalArgumentException("binary operator not expected, must be one of &&, ||, ^");
        }
    }
}
