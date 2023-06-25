package ast.parsers;

import ast.terms.statements.s.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

import java.util.List;

public final class SParserRecursive {

    public S parseS(List<Statement> statements){
        Statement statement = statements.get(0);
        if (statements.size()==1 && statement.isEmptyStmt()){
            return new Skip();
        } else {
            return parseZ(statements);
        }
    }
    public Z parseZ(List<Statement> statements){
        Statement statement = statements.get(0);
        if (statements.size()==1 && statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr()){
           return parseZ(statement.asExpressionStmt().getExpression().asMethodCallExpr());
        } else {
            if (statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr()){
                Z left = parseZ(statement.asExpressionStmt().getExpression().asMethodCallExpr());
                return new ZBinary(left,StatementOperator.SEQ,parseZ(statements.subList(1,statements.size())));
            }
        }
        throw new IllegalArgumentException("");
    }

    private Z parseZ(MethodCallExpr methodCallExpr){
        if (methodCallExpr.getArguments().size()==0 &&
                methodCallExpr.getScope().isPresent() &&
                    methodCallExpr.getScope().get().isNameExpr()){
            return new Am(methodCallExpr.getScope().get().asNameExpr().getNameAsString(),methodCallExpr.getNameAsString());
        } else if (methodCallExpr.getNameAsString().equals("par") &&
                    methodCallExpr.getArguments().size()>1){
            Z left = parseZ(methodCallExpr.getArguments().get(0));
            return new ZBinary(left,StatementOperator.PAR,parse(methodCallExpr.getArguments().subList(1,methodCallExpr.getArguments().size())));
        }
        throw new IllegalArgumentException("");
    }

    private Z parseZ(Expression arg){
        if (arg.isLambdaExpr() && arg.asLambdaExpr().getBody().isExpressionStmt() && arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr()){
            return parseZ(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
        } else if (arg.isLambdaExpr()){
            return parseZ(arg.asLambdaExpr().getBody().asBlockStmt().getStatements());
        }
        throw new IllegalArgumentException("");
    }
    public Z parse(List<Expression> args){
        Expression arg = args.get(0);
        if (args.size()==1){
            if (arg.asLambdaExpr().getBody().isExpressionStmt() &&
                    arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr()){
                return parseZ(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
            } else if(arg.asLambdaExpr().getBody().isBlockStmt() && arg.asLambdaExpr().getBody().asBlockStmt().getStatements().size()>0){
                return parseZ(arg.asLambdaExpr().getBody().asBlockStmt().getStatements());
            }
        } else {
            Z first = null;
            if (arg.isLambdaExpr() &&
                    arg.asLambdaExpr().getBody().isExpressionStmt() &&
                        arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr() &&
                            arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("par") &&
                                arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size()>1){
                first = parseZ(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
                return new ZBinary(first,StatementOperator.PAR,parse(args.subList(1,args.size())));
            } else if (
                    arg.isLambdaExpr() &&
                        arg.asLambdaExpr().getBody().isExpressionStmt() &&
                            arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr() &&
                                arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size()==0){
                first = parseZ(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
                return new ZBinary(first,StatementOperator.SEQ,parse(args.subList(1,args.size())));
            } else if(arg.asLambdaExpr().getBody().isBlockStmt() && arg.asLambdaExpr().getBody().asBlockStmt().getStatements().size()>0){
                return parseZ(arg.asLambdaExpr().getBody().asBlockStmt().getStatements());
            }
        }
        throw new IllegalArgumentException("");
    }
}
