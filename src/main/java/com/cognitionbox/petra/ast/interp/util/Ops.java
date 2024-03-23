package com.cognitionbox.petra.ast.interp.util;

import com.cognitionbox.petra.ast.interp.Func;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Ops {

    public static <T> Set<T> singleton(T e){
        return set(e);
    }

    public static <T> Set<T> set(Collection<T> e){
        return new LinkedHashSet<>(e);
    }
    public static <T> Set<T> set(T... e){
        return set(list(e));
    }

    public static <T> List<T> list(T... e){
        List<T> list = new ArrayList<>();
        Arrays.stream(e).forEach(v->list.add(v));
        return list;
    }

    public static <K,V> Map<K,V> map(){
        return new LinkedHashMap<>();
    }

    public static <T> Optional<T> find(Collection<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).findAny();
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).collect(Collectors.toCollection(()->list()));
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

    public static <T> boolean forall(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().allMatch(toHold);
    }

    public static <T> boolean exists(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().anyMatch(toHold);
    }

    public static <T> boolean existsOne(Collection<T> collection, Predicate<T> toHold){
        return collection.stream().filter(toHold).count()==1;
    }

    public static <T> Func<T> func(Set<T> dom, Set<T> codom, Set<Map.Entry<T,T>> def){
        return new Func<>(dom,codom,def);
    }

    public static <T> boolean subseteq(Set<T> a, Set<T> b){
        return b.containsAll(a);
    }
    public static <T> Set<T> bigUnion(Collection<Set<T>> sets){
        Set<T> set = new LinkedHashSet<>();
        sets.forEach(s->set.addAll(s));
        return set;
    }
    public static <T> Set<T> union(Set<T> a, Set<T> b){
        Set<T> set = new LinkedHashSet<>();
        set.addAll(a);
        set.addAll(b);
        return set;
    }

    public static <T> Set<T> intersect(Set<T> a, Set<T> b){
        Set<T> set = new LinkedHashSet<>();
        set.addAll(a);
        set.retainAll(b);
        return set;
    }

    public static <T> Set<T> difference(Set<T> a, Set<T> b){
        Set<T> set = new LinkedHashSet<>(a);
        set.removeAll(b);
        return set;
    }

    public static <T> Set<List<T>> product(List<Set<T>> sets){
        return com.google.common.collect.Sets.cartesianProduct(sets);
    }

    public static <T> Map.Entry<T,T> mapsto(T from, T too){
        return new Map.Entry() {
            @Override
            public Object getKey() {
                return from;
            }

            @Override
            public Object getValue() {
                return too;
            }

            @Override
            public Object setValue(Object value) {
                return value;
            }
        };
    }

    public static <T> Func<T> functionUnion(Func<T> a, Func<T> b){
        return new Func<T>(union(a.dom(),b.dom()), union(a.range(),b.range()), union(a.def(),b.def()));
    }

    public static <T> Func<T> functionUnion(List<Func<T>> funcs){
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        List<Set<Map.Entry<T,T>>> defs = funcs.stream().map(f->f.def()).collect(Collectors.toList());
        return new Func<T>(bigUnion(doms), bigUnion(ranges), bigUnion(defs));
    }

    public static <T> Func<List<T>> functionProduct(List<Func<T>> funcs){
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        Set<List<T>> productDom = product(doms);
        Set<List<T>> productRange = product(ranges);
        Set<Map.Entry<List<T>,List<T>>> productDef = set();
        for (List<T> in : productDom){
            List<T> out = new ArrayList<>();
            for (int i=0;i<in.size();i++){
                out.add(funcs.get(i).apply(in.get(i)));
            }
            productDef.add(mapsto(in,out));
        }
        return new Func<>(productDom, productRange, productDef);
    }

    public static <T> Func<List<T>> relationProduct(List<Func<T>> funcs){
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        Set<List<T>> productDom = product(doms);
        Set<List<T>> productRange = product(ranges);
        Set<Map.Entry<List<T>,List<T>>> productDef = set();
        Set<List<Map.Entry<T,T>>> input = product(list(funcs, f->f.def()));
        for (List<Map.Entry<T, T>> in : input){
            List<T> x = new ArrayList<>();
            List<T> y = new ArrayList<>();
            for (int i=0;i<in.size();i++){
                x.add(in.get(i).getKey());
                y.add(in.get(i).getValue());
            }
            productDef.add(mapsto(x,y));
        }
        return new Func<>(productDom, productRange, productDef);
    }

    public static <T> Func<T> id(Set<T> set){
        Set<Map.Entry<T,T>> def = set(set, e->mapsto(e,e));
        return new Func<>(set,set,def);
    }
}
