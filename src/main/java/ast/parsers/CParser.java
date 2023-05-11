package ast.parsers;

import ast.terms.expressions.PrePost;
import ast.terms.expressions.e.Ap;
import ast.terms.expressions.e.BinaryOperator;
import ast.terms.expressions.e.E;
import ast.terms.expressions.e.EBinary;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;
import ast.terms.statements.s.Seq;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

public final class CParser {
    private final DParser dParser = new DParser();
    private final SParser sParser = new SParser();
    public List<C> parse(MethodDeclaration declaration){
        if (declaration.getBody().isPresent() &&
                declaration.getBody().get().getStatements().size()>1){
            List<C> overline_c = new ArrayList<>();
            for (Statement statement : declaration.getBody().get().getStatements()){
                overline_c.add(parse(statement));
            }
            return overline_c;
        } else {
            throw new IllegalArgumentException("expected body with 1 statement.");
        }
    }

    private C parse(Statement statement){
        if (statement.isIfStmt() &&
                statement.asIfStmt().getThenStmt().isBlockStmt()) {
            int size = statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().size();
            if (size > 1 && statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).isAssertStmt()) {
                PrePost pre = dParser.parse(statement.asIfStmt().getCondition());
                Seq s = new Seq(sParser.parse(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().subList(0,size - 1)));
                PrePost post = dParser.parse(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).asAssertStmt().getCheck().asEnclosedExpr().getInner());
                return new C(pre, s, post);
            }
        }
        throw new IllegalArgumentException("not valid if statement.");
    }
}
