package com.cognitionbox.petra.ast.interp.util;

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
        return set(Arrays.asList(e));
    }

    public static <T> List<T> list(T... e){
        return Arrays.asList(e);
    }

    public static <T> Optional<T> find(Collection<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).findAny();
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).collect(Collectors.toCollection(()->new ArrayList<>()));
    }

    public static <T> Set<T> filter(Set<T> set, Predicate<T> predicate){
        return set.stream().filter(predicate).collect(Collectors.toCollection(()->set()));
    }

    public static <T,R> Set<R> set(Set<T> set, Function<T,R> mapper){
        return set.stream().map(mapper).collect(Collectors.toCollection(()->set()));
    }

    public static <T,R> Set<R> set(List<T> list, Function<T,R> mapper){
        return list.stream().map(mapper).collect(Collectors.toCollection(()->set()));
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

    public static <T> boolean existsOne(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().filter(toHold).count()==1;
    }

    public static <T,R> Set<R> builder(Function<T,R> mapper, Set<T> set){
        return builder(mapper,set,t->true);
    }

    public static <T> Set<T> builder(Set<T> set, Predicate<T> predicate){
        return builder(x->x,set,predicate);
    }

    public static <T,R> Set<R> builder(Function<T,R> mapper, Set<T> set, Predicate<T> predicate){
        return set.stream().filter(predicate).map(mapper).collect(Collectors.toCollection(()->set()));
    }

    public static <T,R> List<R> builder(Function<T,R> mapper, List<T> list){
        return builder(mapper,list,t->true);
    }

    public static <T,R> List<R> builder(Function<T,R> mapper, List<T> list, Predicate<T> predicate){
        return builder(mapper, new ArrayList<>(list), predicate);
    }

    public static <T,R> List<R> builder(Function<T,R> mapper, T[] list){
        return builder(mapper,list,t->true);
    }

    public static <T,R> List<R> builder(Function<T,R> mapper, T[] list, Predicate<T> predicate){
        return builder(mapper, Arrays.asList(list), predicate);
    }
}
