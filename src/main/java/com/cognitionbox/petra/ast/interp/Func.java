package com.cognitionbox.petra.ast.interp;

import com.cognitionbox.petra.ast.interp.util.Set;
import com.cognitionbox.petra.ast.interp.util.Collections;
import com.cognitionbox.petra.ast.interp.util.Ops;

import static com.cognitionbox.petra.ast.interp.util.Collections.filter;
import static com.cognitionbox.petra.ast.interp.util.Collections.set;

public final class Func<T> {
    private final Set<T> domain;
    private final Set<T> range;
    private final Set<Mapsto<T,T>> def;

    public Func(Set<T> domain, Set<T> range, Set<Mapsto<T,T>> def) {
        this.domain = domain;
        this.range = range;
        this.def = def;
    }

    public String name() {
        return String.valueOf(this.hashCode());
    }

    public Set<T> dom() {
        return domain;
    }

    public Set<T> range() {
        return range;
    }

    public Set<Mapsto<T,T>> def() {
        return def;
    }

    public T apply(T in){
        for (Mapsto<T,T> mapping : def){
            if (mapping.getFrom().equals(in)){
                return mapping.getToo();
            }
        }
        throw new IllegalArgumentException("no mapping exists for this input.");
    }

    public Func<T> compose(Func<T> f){
        Set<Mapsto<T,T>> comp = Collections.set();
        for (Mapsto<T,T> left : f.def()){
            for (Mapsto<T,T> right : this.def()){
                if (left.getToo().equals(right.getFrom())){
                    comp.add(Ops.mapsto(left.getFrom(),right.getToo()));
                }
            }
        }
        return new Func<>(f.dom(),this.range(),comp);
    }

    public Func<T> restrict(Set<T> dom){
        Set<Mapsto<T,T>> restricted = filter(def(), map->dom.contains(map.getFrom()));
        Set<T> range = Collections.set(restricted, map->map.getToo());
        return new Func<>(dom,range,restricted);
    }

    public Set<T> image(Set<T> in){
        return this.restrict(in).range();
    }

    @Override
    public String toString() {
        String signature = domain + "\\rightarrow" + range;
        return name() + ":" + signature + "\\\\" + name() + " = " + def;
    }
}
