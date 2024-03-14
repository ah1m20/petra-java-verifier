package com.cognitionbox.petra.ast.terms;

import java.util.ArrayList;
import java.util.List;

public final class Obj extends Term {
    private final String fullyQualifiedClassName;
    private final String A;
    private final List<Beta> overlineBeta = new ArrayList<>();
    private final List<Phi> overlinePhi = new ArrayList<>();
    private final List<Delta> overlineDelta = new ArrayList<>();

    private final ObjType objType;

    private final boolean entry;

    public Obj(boolean entry, String fullyQualifiedClassName, String A, ObjType objType) {
        this.entry = entry;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.A = A;
        this.objType = objType;
    }

    public Obj(boolean entry, String fullyQualifiedClassName, boolean valid, int lineError, String errorMessage, String a, ObjType objType) {
        super(valid, lineError, errorMessage);
        this.entry = entry;
        this.fullyQualifiedClassName = fullyQualifiedClassName;
        this.A = a;
        this.objType = objType;
    }

    public String getFullyQualifiedClassName() {
        return fullyQualifiedClassName;
    }

    public void addBeta(Beta b){
        this.overlineBeta.add(b);
    }

    public void addPhi(Phi p){
        this.overlinePhi.add(p);
    }

    public void addDelta(Delta d){
        this.overlineDelta.add(d);
    }

    public String getA() {
        return A;
    }

    public List<Beta> getOverlineBeta() {
        return overlineBeta;
    }

    public List<Phi> getOverlinePhi() {
        return overlinePhi;
    }

    public List<Delta> getOverlineDelta() {
        return overlineDelta;
    }

    public boolean isPrimitive() {
        return objType==ObjType.BASE;
    }

    public boolean isExternal() {
        return objType==ObjType.EXTERNAL;
    }

    public boolean isEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return this.getA();
    }
}
