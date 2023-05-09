package ast.interp.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Collections {

    public static <T,R> Set<R> set(List<T> list, Function<T,R> mapper){
        return list.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <T,R> List<R> list(List<T> list, Function<T,R> mapper){
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> void forEach(Set<T> set, Consumer<T> consumer) {
        set.stream().forEach(consumer);
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        list.stream().forEach(consumer);
    }

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().allMatch(toHold);
    }

    public static <T> boolean exists(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().anyMatch(toHold);
    }
}
