package ast.interp.util.math;

import java.util.Collection;
import java.util.function.Predicate;

public final class Logic {

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().allMatch(toHold);
    }

    public static <T> boolean exists(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().anyMatch(toHold);
    }

}
