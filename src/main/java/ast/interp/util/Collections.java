package ast.interp.util;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Collections {

    public static <T> Set<T> singleton(T e){
        return set(e);
    }

    public static <T> Set<T> set(Collection<T> e){
        return new Set<>(e);
    }
    public static <T> Set<T> set(T... e){
        Set<T> set = new Set<>();
        for (T t : set){
            set.add(t);
        }
        return set;
    }

    public static <T> List<T> list(T... e){
        List<T> list = new ArrayList<>();
        for (T t : list){
            list.add(t);
        }
        return list;
    }

    public static <T> Optional<T> find(Collection<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).findAny();
    }

    public static <T> Set<T> filter(Set<T> set, Predicate<T> predicate){
        return set.stream().filter(predicate).collect(Collectors.toCollection(()->set()));
    }

    public static <T,R> Set<R> set(Set<T> set, Function<T,R> mapper){
        return set.stream().map(mapper).collect(Collectors.toCollection(()->set()));
    }

    public static <T,R> List<R> list(List<T> list, Function<T,R> mapper){
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T,R> List<R> list(T[] list, Function<T,R> mapper){
        return Arrays.asList(list).stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> void forEach(Set<T> set, Consumer<T> consumer) {
        set.stream().forEach(consumer);
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        list.stream().forEach(consumer);
    }

    public static <T> void forEach(T[] array, Consumer<T> consumer) {
        for (T t : array){
            consumer.accept(t);
        }
    }

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().allMatch(toHold);
    }

    public static <T> boolean exists(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().anyMatch(toHold);
    }
}
