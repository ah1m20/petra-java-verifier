package ast.parsers;

import ast.terms.statements.s.S;
import ast.terms.statements.s.SBinary;
import ast.terms.statements.s.r.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SParserRecursive {

    public S parseS(List<Statement> statements){
        Statement statement = statements.get(0);
        if (statements.size()==1){
            R r = null;
            if (statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr()){
                r = parseR(statement.asExpressionStmt().getExpression().asMethodCallExpr());
            } else if (statement.isEmptyStmt()){
                r = new Skip();
            }
            return r;
        } else {
            R left = null;
            if (statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr()){
                left = parseR(statement.asExpressionStmt().getExpression().asMethodCallExpr());
            } else if (statement.isEmptyStmt()){
                left = new Skip();
            }
            return new SBinary(left,parseS(statements.subList(1,statements.size())));
        }
    }

    private R parseR(MethodCallExpr methodCallExpr){
        if (methodCallExpr.getArguments().size()==0 &&
                methodCallExpr.getScope().isPresent() &&
                    methodCallExpr.getScope().get().isNameExpr()){
            return new Am(methodCallExpr.getScope().get().asNameExpr().getNameAsString(),methodCallExpr.getNameAsString());
        } else if (methodCallExpr.getNameAsString().equals("par") &&
                    methodCallExpr.getArguments().size()>1){
            return parse(methodCallExpr.getArguments());
        }
        throw new IllegalArgumentException("");
    }

    public R parse(List<Expression> args){
        Expression arg = args.get(0);
        if (args.size()==1){
            return parseR(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
        } else {
            R first = null;
            if (arg.isLambdaExpr() &&
                    arg.asLambdaExpr().getBody().isExpressionStmt() &&
                    arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr()){
                first = parseR(arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr());
            }
            return new RBinary(first,parse(args.subList(1,args.size())));
        }
    }
}
