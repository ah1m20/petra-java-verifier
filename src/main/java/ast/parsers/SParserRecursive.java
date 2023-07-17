package ast.parsers;

import ast.terms.statements.s.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;

import java.util.List;

import static ast.interp.util.ParserUtils.invalidZ;

public final class SParserRecursive {

    public S parseS(List<Statement> statements){
        Statement statement = statements.get(0);
        if (statements.size()==1 && statement.isEmptyStmt()){
            return new Skip();
        } else {
            try {
                return parseZ(statements);
            } catch (NodeException e){
                return invalidZ(e.getNode(),"not valid statement.");
            }
        }
    }
    public Z parseZ(List<Statement> statements){
        Statement statement = statements.get(0);
        if (!(statement.isExpressionStmt() && statement.asExpressionStmt().getExpression().isMethodCallExpr())){
            throw new NodeException(statement);
        }
        if (statements.size()==1 &&
                (statement.asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("par") ||
                        statement.asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("seq") ||
                            statement.asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString().equals("auto")) &&
                statement.asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size()>1){
            Z left = parseZ(statement.asExpressionStmt().getExpression().asMethodCallExpr().getArguments().get(0));
            return new ZBinary(left,parse(statement.asExpressionStmt().getExpression().asMethodCallExpr().getArguments().subList(1,statement.asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size())));
        } else {
            return parseQ(statements);
        }
    }

    private Z parseZ(Expression arg){
        if (arg.isMethodReferenceExpr()){
            // x.M
            return new Am(
                    arg.asMethodReferenceExpr().getScope().asTypeExpr().getTypeAsString(),
                    arg.asMethodReferenceExpr().getIdentifier());
        } else if (arg.isLambdaExpr() && arg.asLambdaExpr().getBody().isExpressionStmt() && arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().isMethodCallExpr()){
            // x.M
            return new Am(
                    arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(),
                    arg.asLambdaExpr().getBody().asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString());
        } else if (arg.isLambdaExpr()){
            // ()->{...}
            return parseQ(arg.asLambdaExpr().getBody().asBlockStmt().getStatements());
        }
        throw new NodeException(arg);
    }

    public Z parse(List<Expression> args){
        Expression arg = args.get(0);
        if (!(arg.isLambdaExpr() || arg.isMethodReferenceExpr())){
            throw new NodeException(arg);
        }
        if (args.size()==1){
            return parseZ(arg);
        } else {
            Z first = parseZ(arg);
            return new ZBinary(first,parse(args.subList(1,args.size())));
        }
    }

    public Q parseQ(List<Statement> statements){
        Statement statement = statements.get(0);
        if (!(statement.isExpressionStmt() &&
                statement.asExpressionStmt().getExpression().isMethodCallExpr() &&
                statement.asExpressionStmt().getExpression().asMethodCallExpr().getArguments().size()==0 &&
                statement.asExpressionStmt().getExpression().asMethodCallExpr().getScope().isPresent() &&
                statement.asExpressionStmt().getExpression().asMethodCallExpr().getScope().get().isNameExpr())){
            throw new NodeException(statement);
        }
        if (statements.size()==1){
            return new Am(statement.asExpressionStmt().getExpression().asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(),
                    statement.asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString());
        } else {
            Q left = new Am(statement.asExpressionStmt().getExpression().asMethodCallExpr().getScope().get().asNameExpr().getNameAsString(),
                    statement.asExpressionStmt().getExpression().asMethodCallExpr().getNameAsString());
            return new QBinary(left,parseQ(statements.subList(1,statements.size())));
        }
    }

}
