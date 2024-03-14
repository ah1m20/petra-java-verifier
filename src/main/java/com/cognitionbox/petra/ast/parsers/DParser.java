package com.cognitionbox.petra.ast.parsers;

import com.cognitionbox.petra.ast.terms.expressions.d.D;
import com.cognitionbox.petra.ast.terms.expressions.d.DBinary;
import com.cognitionbox.petra.ast.terms.expressions.d.P;
import com.github.javaparser.ast.expr.Expression;

public final class DParser {

    public D parse(Expression expression){
        if (expression.isMethodCallExpr()){
            if (expression.asMethodCallExpr().getArguments().size()==0 &&
                    !expression.asMethodCallExpr().getScope().isPresent()){
                return new P(expression.asMethodCallExpr().getNameAsString());
            } else {
                throw new IllegalArgumentException("invalid syntax, not the required format: p()");
            }
        } else if (expression.isBinaryExpr()){
            return new DBinary(
                        parse(expression.asBinaryExpr().getLeft()),
                        parse(expression.asBinaryExpr().getRight()));
        } else if (expression.isBooleanLiteralExpr() && expression.asBooleanLiteralExpr().getValue()) {
            return new P(Boolean.toString(expression.asBooleanLiteralExpr().getValue()));
        } else {
            throw new IllegalArgumentException("must be call to single boolean method, a boolean literal true value, or a disjunction of boolean method calls.");
        }
    }
}
