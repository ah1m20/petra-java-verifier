package ast.parsers;

import ast.terms.expressions.d.DBinary;
import ast.terms.expressions.d.P;
import ast.terms.statements.s.r.Am;
import ast.terms.statements.s.r.Par;
import ast.terms.statements.s.r.R;
import ast.terms.statements.s.r.RTerminal;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public final class SParser {

    public List<R> parse(List<Statement> statements){
        List<R> rs = new ArrayList<>();
        for (Statement statement : statements){
            if (statement.isExpressionStmt() &&
                    statement.asExpressionStmt().getExpression().isMethodCallExpr()){
                rs.add(parse(statement.asExpressionStmt().getExpression().asMethodCallExpr()));
            }
        }
        return rs;
    }

    private R parse(MethodCallExpr methodCallExpr){
        if (methodCallExpr.getArguments().size()==0 &&
                methodCallExpr.getScope().isPresent() &&
                    methodCallExpr.getScope().get().isNameExpr()){
            return new Am(methodCallExpr.getScope().get().asNameExpr().getNameAsString(),methodCallExpr.getNameAsString());
        } else if (methodCallExpr.getNameAsString().equals("par") &&
                    methodCallExpr.getArguments().size()>1){
            Par par = new Par();
            for (Expression arg : methodCallExpr.getArguments()){
                if (arg.isMethodCallExpr()){
                    par.addR((RTerminal) parse(arg.asMethodCallExpr()));
                }
            }
            return par;
        }
        throw new IllegalArgumentException("");
    }
}
