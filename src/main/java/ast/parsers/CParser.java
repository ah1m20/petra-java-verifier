package ast.parsers;

import ast.terms.expressions.PrePost;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;
import static ast.interp.util.Collections.list;

public final class CParser {
    private final DParser dParser = new DParser();
    private final SParser sParser = new SParser();
    public List<C> parse(MethodDeclaration declaration, boolean isPrimitive){
        if (declaration.getBody().isPresent() && declaration.getBody().get().getStatements().size()==1){
            return list(ifElseStatements(declaration.getBody().get().getStatements().get(0).asIfStmt()), statement->parse(statement,isPrimitive));
        } else {
            throw new IllegalArgumentException("expected body of one if statement or an if-else chain.");
        }
    }

    private C parse(Statement statement, boolean isPrimitive){
        if (statement.isIfStmt() &&
                statement.asIfStmt().getThenStmt().isBlockStmt()) {
            int size = statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().size();
            if (size > 1 && statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).isAssertStmt()) {
                PrePost pre = dParser.parse(statement.asIfStmt().getCondition());
                S s = !isPrimitive?sParser.parse(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().subList(0,size - 1)):null;
                PrePost post = dParser.parse(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).asAssertStmt().getCheck().asEnclosedExpr().getInner());
                return new C(pre, s, post);
            }
        }
        throw new IllegalArgumentException("not valid if statement.");
    }

    private List<IfStmt> ifElseStatements(IfStmt ifStmt){
        List<IfStmt> ifStmts = new ArrayList<>();
        ifElseStatementsImpl(ifStmts,ifStmt);
        return ifStmts;
    }

    private void ifElseStatementsImpl(List<IfStmt> ifStmts, IfStmt ifStmt){
        ifStmts.add(new IfStmt(ifStmt.getCondition(),  ifStmt.getThenStmt(),null));
        if (ifStmt.getElseStmt().isPresent() && ifStmt.getElseStmt().get().isIfStmt()){
            ifElseStatementsImpl(ifStmts,ifStmt.getElseStmt().get().asIfStmt());
        }
    }

    public static void main(String[] args){
        IfStmt e = new IfStmt(new BooleanLiteralExpr(true),  new EmptyStmt(),null);
        IfStmt d = new IfStmt(new BooleanLiteralExpr(true), new EmptyStmt(), e);
        IfStmt c = new IfStmt(new BooleanLiteralExpr(true), new EmptyStmt(), d);
        IfStmt b = new IfStmt(new BooleanLiteralExpr(true), new EmptyStmt(), c);
        IfStmt a = new IfStmt(new BooleanLiteralExpr(true), new EmptyStmt(), b);

        System.out.println(new CParser().ifElseStatements(a));
    }
}
