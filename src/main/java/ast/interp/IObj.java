package ast.interp;

import ast.terms.expressions.e.E;

import java.util.*;

public class IObj {
    private Set<List<String>> Omega;
    private Map<String, E> predicates;
    private Map<String, Optional<Func<String>>> methods;

    public IObj(Set<List<String>> Omega, Map<String, E> predicates, Map<String, Optional<Func<String>>> methods) {
        this.Omega = Omega;
        this.predicates = predicates;
        this.methods = methods;
    }

    public Set<List<String>> getOmega() {
        return Omega;
    }

    public Map<String, E> getPredicates() {
        return predicates;
    }

    public Map<String, Optional<Func<String>>> getMethods() {
        return methods;
    }
}
