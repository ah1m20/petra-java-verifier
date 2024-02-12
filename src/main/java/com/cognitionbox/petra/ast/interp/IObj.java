package com.cognitionbox.petra.ast.interp;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class IObj {
    private Set<List<String>> Omega;
    private Map<String, Set<List<String>>> predicates;
    private Map<String, Optional<Func<String>>> methods;

    public IObj(Set<List<String>> Omega, Map<String,  Set<List<String>>> predicates, Map<String, Optional<Func<String>>> methods) {
        this.Omega = Omega;
        this.predicates = predicates;
        this.methods = methods;
    }

    public Set<List<String>> getOmega() {
        return Omega;
    }

    public Map<String, Set<List<String>>> getPredicates() {
        return predicates;
    }

    public Map<String, Optional<Func<String>>> getMethods() {
        return methods;
    }
}
