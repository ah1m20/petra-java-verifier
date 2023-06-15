package ast.parsers;

import ast.terms.expressions.PrePost;
import ast.terms.statements.c.C;
import ast.terms.statements.s.S;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import java.util.List;
import static ast.interp.util.Collections.list;

public final class CParser {
    private final DParser dParser = new DParser();
    private final SParser sParser = new SParser();
    public List<C> parse(MethodDeclaration declaration, boolean isPrimitive){
        if (declaration.getBody().isPresent()){
            return list(declaration.getBody().get().getStatements(),statement->parse(statement,isPrimitive));
        } else {
            throw new IllegalArgumentException("expected body.");
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
}
