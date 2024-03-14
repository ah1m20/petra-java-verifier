package com.cognitionbox.petra.ast.interp;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.cognitionbox.petra.ast.interp.util.Ops.*;

public final class Func<T> implements Function<T,T> {
    private final Set<T> domain;
    private final Set<T> range;
    private Map<T,T> def = new HashMap<>();

    public Func(Set<T> domain, Set<T> range, Set<Map.Entry<T,T>> mappings) {
        this.domain = domain;
        this.range = range;
        mappings.stream().forEach(m->def.put(m.getKey(),m.getValue()));
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

    public Set<Map.Entry<T,T>> def() {
        return def.entrySet();
    }

    public T apply(T in){
        if (def.containsKey(in)){
            return def.get(in);
        } else {
            throw new IllegalArgumentException("no mapping exists for this input.");
        }
    }

    private T composeHelper(T in, Map<T,T> after){
        if (in.equals("true") && !after.containsKey("true")){
            throw new IllegalArgumentException("does not compose.");
        } else if (in.equals("true") && after.containsKey("true")){
            return (T) "true";
        } else if (!in.equals("true") && after.containsKey("true")){
            return in;
        } else if (!in.equals("true") && !after.containsKey("true")){
            return after.get(in);
        } else {
            throw new IllegalArgumentException("does not compose.");
        }
    }
    public Func<T> compose(Func<T> f){
        Map<T,T> comp = new HashMap<>();
        for (T k : f.def.keySet()){
            T v = f.def.get(k);
            T r = composeHelper(v,this.def);
            comp.put(k,r);
        }
        return new Func<>(f.dom(),this.range(),comp.entrySet());
    }

    public Func<T> restrict(Set<T> dom){
        Set<Map.Entry<T,T>> restricted = filter(def(), map->dom.contains(map.getKey()));
        Set<T> range = set(restricted, map->map.getValue());
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
