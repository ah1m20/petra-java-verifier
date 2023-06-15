package ast.parsers;

import ast.terms.statements.s.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;
import java.util.List;

public final class SParser {

    public S parse(List<Statement> statements){
        if (statements.size()==0 || (statements.size()==1 && statements.get(0).asExpressionStmt().isEmptyStmt())) {
            return new Skip();
        } else if (statements.size()==1 && statements.get(0).asExpressionStmt().getExpression().isMethodCallExpr() &&
                statements.get(0).asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("par") &&
                statements.get(0).asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size() > 1) {
            Par r = new Par();
            for (Expression arg : statements.get(0).asExpressionStmt().getExpression().asMethodCallExpr().getArguments()) {
                if (arg.isLambdaExpr() &&
                        arg.asLambdaExpr().getBody().isExpressionStmt() &&
                        arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr()) {
                    r.add(new Am(
                            arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(),
                            arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString())
                    );
                }
            }
            return r;
        } else {
            Seq q = new Seq();
            for (Statement statement : statements){
                if (statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr()){
                    q.add(parse(statement.asExpressionStmt().getExpression().asMethodCallExpr()));
                }
            }
            return q;
        }
    }

    private Am parse(MethodCallExpr methodCallExpr){
        if (methodCallExpr.getArguments().size()==0 &&
                methodCallExpr.getScope().isPresent() &&
                    methodCallExpr.getScope().get().isNameExpr()){
            return new Am(methodCallExpr.getScope().get().asNameExpr().getNameAsString(),methodCallExpr.getNameAsString());
        }
        throw new IllegalArgumentException("");
    }
}
