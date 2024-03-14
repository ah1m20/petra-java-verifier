package com.cognitionbox.petra.ast.terms;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Prog extends Term {
    private final boolean reactive;
    private final String m;
    private final String Aepsilon;
    private final List<Obj> objs;
    public Prog(boolean reactive, String aepsilon, List<Obj> objs) {
        this.reactive = reactive;
        this.Aepsilon = aepsilon;
        this.objs = objs;
        this.m = getEntryPoint(objs).orElseThrow(()->new IllegalArgumentException("Invalid entry points: There needs to be exactly one class marked @Entry and at least one method in this class marked @Entry."));
    }

    public Prog(String aepsilon, List<Obj> objs) {
        this(false,aepsilon,objs);
    }

    private Optional<String> getEntryPoint(List<Obj> objs){
        List<Obj> entryObjs = objs.stream().filter(o->o.isEntry()).collect(Collectors.toList());
        if (entryObjs.size()==1){
            List<Delta> entryPoints = entryObjs.get(0).getOverlineDelta().stream().filter(d->d.isEntry()).collect(Collectors.toList());
            if (entryPoints.size()>=1){
                return Optional.of(entryPoints.get(0).getM());
            }
        }
        return Optional.empty();
    }

    public boolean isReactive() {
        return reactive;
    }
    public String getM() {
        return m;
    }

    public String getAepsilon() {
        return Aepsilon;
    }

    public List<Obj> getObjs() {
        return objs;
    }

    @Override
    public String toString() {
        return "<"+ m + ',' + Aepsilon + ',' + objs + ">";
    }
}
