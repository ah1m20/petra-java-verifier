package ast.interp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ast.interp.util.Set;
import ast.terms.expressions.e.E;

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
