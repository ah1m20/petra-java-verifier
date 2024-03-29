package com.cognitionbox.petra.ast.interp.util;

import com.cognitionbox.petra.ast.interp.Func;
import com.cognitionbox.petra.ast.interp.Mapsto;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cognitionbox.petra.ast.interp.util.Collections.list;
import static com.cognitionbox.petra.ast.interp.util.Collections.set;

public final class Ops {

    public static void main(String[] args){
        Set<String> a = new Set<>();
        a.add("x");
        a.add("y");
        Set<String> b = new Set<>();
        b.add("a");
        b.add("b");
        System.out.println(product(a,b));
    }

    public static <T> boolean subseteq(Set<T> a, Set<T> b){
        return b.containsAll(a);
    }
    public static <T> Set<T> union(Set<T>... sets){
        Set<T> newSet = new Set<>();
        for (Set<T> s : sets){
            newSet.addAll(s);
        }
        return newSet;
    }

    public static <T> Set<T> union(List<Set<T>> sets){
        Set<T> newSet = new Set<>();
        for (Set<T> s : sets){
            newSet.addAll(s);
        }
        return newSet;
    }

    public static <T> Set<T> union(Set<Set<T>> sets){
        Set<T> newSet = new Set<>();
        for (Set<T> s : sets){
            newSet.addAll(s);
        }
        return newSet;
    }

    public static <T> Set<T> intersect(Set<T> a, Set<T> b){
        Set<T> newSet = new Set<>();
        newSet.addAll(a);
        newSet.retainAll(b);
        return newSet;
    }

    public static <T> Set<T> toSet(java.util.Set<T> s){
        return new Set<>(s);
    }

    public static <T> java.util.Set<T> toJavaSet(Set<T> s){
        return s;
    }
    public static <T> Set<List<T>> product(Set<T>... sets){
        List<java.util.Set<T>> javaSets = Collections.list(sets, s->toJavaSet(s));
        return toSet(Sets.cartesianProduct(javaSets));
    }

    public static <T> Set<List<T>> product(List<Set<T>> sets){
        List<java.util.Set<T>> javaSets = Collections.list(sets, s->toJavaSet(s));
        return toSet(Sets.cartesianProduct(javaSets));
    }

    public static <T> Mapsto<T,T> mapsto(T from, T too){
        return new Mapsto<>(from,too);
    }

    public static <T> Func<T> functionUnion(List<Func<T>> funcs){
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        List<Set<Mapsto<T,T>>> defs = funcs.stream().map(f->f.def()).collect(Collectors.toList());
        return new Func<T>(union(doms), union(ranges), union(defs));
    }

    public static <T> Func<List<T>> functionProduct(List<Func<T>> funcs){
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        Set<List<T>> productDom = product(doms);
        Set<List<T>> productRange = product(ranges);
        Set<Mapsto<List<T>,List<T>>> productDef = new Set<>();
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
        Set<Mapsto<List<T>,List<T>>> productDef = new Set<>();
        Set<List<Mapsto<T,T>>> input = product(Collections.list(funcs, f->f.def()));
        for (List<Mapsto<T, T>> in : input){
            List<T> x = new ArrayList<>();
            List<T> y = new ArrayList<>();
            for (int i=0;i<in.size();i++){
                x.add(in.get(i).getFrom());
                y.add(in.get(i).getToo());
            }
            productDef.add(mapsto(x,y));
        }
        return new Func<>(productDom, productRange, productDef);
    }

    public static <T> Func<T> id(Set<T> set){
        Set<Mapsto<T,T>> def = Collections.set(set, e->mapsto(e,e));
        return new Func<>(set,set,def);
    }
}
