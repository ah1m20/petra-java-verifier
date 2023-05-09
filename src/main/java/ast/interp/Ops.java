package ast.interp;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public final class Ops {

    public static void main(String[] args){
        Set<String> a = new HashSet<>();
        a.add("x");
        a.add("y");
        Set<String> b = new HashSet<>();
        b.add("a");
        b.add("b");
        System.out.println(Ops.product(a,b));
    }
    public static <T> SortedSet<T> union(Set<T>... sets){
        SortedSet<T> newSet = new TreeSet<>();
        for (Set<T> s : sets){
            newSet.addAll(s);
        }
        return newSet;
    }

    public static <T> SortedSet<T> union(List<Set<T>> sets){
        SortedSet<T> newSet = new TreeSet<>();
        for (Set<T> s : sets){
            newSet.addAll(s);
        }
        return newSet;
    }

    public static <T> SortedSet<T> intersect(Set<T> a, Set<T> b){
        SortedSet<T> newSet = new TreeSet<>();
        newSet.addAll(a);
        newSet.retainAll(b);
        return newSet;
    }

    public static <T> Set<List<T>> product(Set<T>... sets){
        return Sets.cartesianProduct(sets);
    }

    public static <T> Set<List<T>> product(List<Set<T>> sets){
        return Sets.cartesianProduct(sets);
    }

    public static <T> Func<T> functionUnion(List<Func<T>> funcs){
        List<String> names = funcs.stream().map(f->f.name()).collect(Collectors.toList());
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        List<Set<Mapsto<T,T>>> defs = funcs.stream().map(f->f.def()).collect(Collectors.toList());
        return new Func<T>(Ops.union(doms), Ops.union(ranges), Ops.union(defs));
    }

    public static <T> Func<List<T>> functionProduct(List<Func<T>> funcs){
        List<String> names = funcs.stream().map(f->f.name()).collect(Collectors.toList());
        List<Set<T>> doms = funcs.stream().map(f->f.dom()).collect(Collectors.toList());
        List<Set<T>> ranges = funcs.stream().map(f->f.range()).collect(Collectors.toList());
        Set<List<T>> productDom = Ops.product(doms);
        Set<List<T>> productRange = Ops.product(ranges);
        SortedSet<Mapsto<List<T>,List<T>>> productDef = new TreeSet<>();
        for (List<T> in : productDom){
            List<T> out = new ArrayList<>();
            for (int i=0;i<in.size();i++){
                out.set(i,funcs.get(i).apply(in.get(i)));
            }
            productDef.add(new Mapsto<>(in,out));
        }
        return new Func<>(productDom, productRange, productDef);
    }
}
