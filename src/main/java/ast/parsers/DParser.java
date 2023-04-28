package ast.parsers;

import ast.terms.expressions.d.D;
import ast.terms.expressions.d.DBinary;
import ast.terms.expressions.d.P;
import ast.terms.expressions.e.Ap;
import ast.terms.expressions.e.BinaryOperator;
import ast.terms.expressions.e.E;
import ast.terms.expressions.e.EBinary;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;

public final class DParser {

    public D parse(Expression expression){
        if (expression.isMethodCallExpr()){
            if (expression.asMethodCallExpr().getArguments().size()==0 &&
                    !expression.asMethodCallExpr().getScope().isPresent()){
                return new P(expression.asMethodCallExpr().getNameAsString());
            } else {
                throw new IllegalArgumentException("invalid syntax, not the required format: p()");
            }
        } else if (expression.isBinaryExpr()){
            return new DBinary(
                        parse(expression.asBinaryExpr().getLeft()),
                        parse(expression.asBinaryExpr().getRight()));
        } else {
            throw new IllegalArgumentException("must be call to single boolean method or expression of boolean method calls.");
        }
    }
}
