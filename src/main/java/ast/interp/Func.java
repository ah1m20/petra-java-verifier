package ast.interp;

import java.util.Set;
import java.util.SortedSet;

public final class Func<T> {
    private final Set<T> domain;
    private final Set<T> range;
    private final SortedSet<Mapsto<T,T>> def;

    public Func(Set<T> domain, Set<T> range, SortedSet<Mapsto<T,T>> def) {
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

    @Override
    public String toString() {
        String signature = domain + "\\rightarrow" + range;
        return name() + ":" + signature + "\n" + name() + " = " + def;
    }
}
