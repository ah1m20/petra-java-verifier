package com.cognitionbox.petra.ast.parsers;

import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.s.G;
import com.cognitionbox.petra.ast.terms.statements.s.S;
import com.cognitionbox.petra.ast.interp.util.Collections;
import com.cognitionbox.petra.ast.interp.util.ParserUtils;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class CParser {
    private final DParser dParser = new DParser();
    private final SParserRecursive sParser = new SParserRecursive();
    public List<C> parse(MethodDeclaration declaration, boolean isPrimitive){
        AtomicInteger id = new AtomicInteger();
        if (declaration.getBody().isPresent() && declaration.getBody().get().getStatements().size()==1){
            return Collections.list(ifElseStatements(declaration.getBody().get().getStatements().get(0).asIfStmt()), statement->parse(id.getAndIncrement(),statement,isPrimitive));
        } else {
            return Collections.list();
        }
    }

    private C parse(int id, Statement statement, boolean isPrimitive){
        if (statement.isIfStmt() &&
                statement.asIfStmt().getThenStmt().isBlockStmt()) {
            int size = statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().size();
            if (size > 1 && statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).isAssertStmt()) {
                PrePost pre = dParser.parse(statement.asIfStmt().getCondition());
                S s = !isPrimitive?sParser.parseS(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().subList(0,size - 1)):new G("assumed");
                PrePost post = dParser.parse(statement.asIfStmt().getThenStmt().asBlockStmt().getStatements().get(size - 1).asAssertStmt().getCheck().asEnclosedExpr().getInner());
                if (s==null || s.isValid()){
                    return new C(id,pre, s, post);
                } else {
                    return ParserUtils.invalidC(id,statement,"not valid if statement.");
                }
            }
        }
        return ParserUtils.invalidC(id,statement,"not valid if statement.");
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
