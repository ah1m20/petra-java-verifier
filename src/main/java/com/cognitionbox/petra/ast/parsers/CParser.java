package com.cognitionbox.petra.ast.parsers;

import com.cognitionbox.petra.ast.terms.expressions.PrePost;
import com.cognitionbox.petra.ast.terms.expressions.d.P;
import com.cognitionbox.petra.ast.terms.statements.c.C;
import com.cognitionbox.petra.ast.terms.statements.c.CUnary;
import com.cognitionbox.petra.ast.terms.statements.c.CBinary;
import com.cognitionbox.petra.ast.terms.statements.s.G;
import com.cognitionbox.petra.ast.terms.statements.s.S;
import com.cognitionbox.petra.ast.interp.util.ParserUtils;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.IfStmt;

import java.util.ArrayList;
import java.util.List;

public final class CParser {
    private final DParser dParser = new DParser();
    private final SParserRecursive sParser = new SParserRecursive();
    public C parse(MethodDeclaration declaration, boolean isPrimitive){
        if (declaration.getBody().isPresent() && declaration.getBody().get().getStatements().size()==1){
            return parse(0,declaration.getBody().get().getStatements().get(0).asIfStmt(),isPrimitive);
        } else {
            throw new IllegalArgumentException("not valid method.");
        }
    }

    private C parse(int id, IfStmt ifStmt, boolean isPrimitive){
        int size = ifStmt.getThenStmt().asBlockStmt().getStatements().size();
        if (size > 0) { // && ifStmt.getThenStmt().asBlockStmt().getStatements().get(size - 1).isAssertStmt()
            PrePost pre = dParser.parse(ifStmt.getCondition());
            final PrePost post;
            final S s;
            if (ifStmt.getThenStmt().asBlockStmt().getStatements().get(size-1).isAssertStmt()){
                s = !isPrimitive?sParser.parseS(ifStmt.getThenStmt().asBlockStmt().getStatements().subList(0,size-1)):new G("assumed");
                post = dParser.parse(ifStmt.getThenStmt().asBlockStmt().getStatements().get(size - 1).asAssertStmt().getCheck().asEnclosedExpr().getInner());
            } else {
                s = !isPrimitive?sParser.parseS(ifStmt.getThenStmt().asBlockStmt().getStatements().subList(0,size)):new G("assumed");
                post = new P("true");
            }
            CUnary cbase = new CUnary(id,pre, s, post);
            if (s==null || s.isValid()){
                if (!ifStmt.getElseStmt().isPresent()) {
                    if (s==null || s.isValid()){
                        return new CUnary(id,pre, s, post);
                    } else {
                        return ParserUtils.invalidC(id,ifStmt,"not valid if statement.");
                    }
                } else if (ifStmt.getElseStmt().isPresent() && ifStmt.getElseStmt().get().isIfStmt()){
                    return new CBinary(id,cbase,parse(id+1,ifStmt.getElseStmt().get().asIfStmt(),isPrimitive));
                }
            }
        }
        return ParserUtils.invalidC(id,ifStmt,"not valid if statement.");
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
