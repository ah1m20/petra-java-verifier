package com.cognitionbox.petra.ast.interp.util;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class TrueOps {
    private static boolean containsHelper(String in, Set<String> after){
        if (in.equals("true") && !after.contains("true")){
            return false;
        } else if (in.equals("true") && after.contains("true")){
            return true;
        } else if (!in.equals("true") && after.contains("true")){
            return true;
        } else if (!in.equals("true") && !after.contains("true")){
            return true;
        } else {
            return false;
        }
    }

    public static boolean equals(String ths, String p) {
        if (p.equals("true") && !ths.equals("true")){
            return false;
        } else if (p.equals("true") && ths.equals("true")){
            return true;
        } else if (!p.equals("true") && ths.equals("true")){
            return true;
        } else if (!p.equals("true") && !ths.equals("true")){
            return true;
        } else {
            return false;
        }
    }

//    public static <T> boolean subseteq(Set<T> a, Set<T> b){
//        return b.containsAll(a);
//    }
//
//    public static <T> boolean subseteq(Stream<T> subset, Set<T> superset) {
//        return subset.allMatch(superset::contains);
//    }

    public static <T> boolean isSubset(Stream<T> subset, Stream<T> superset) {
        return subset.allMatch(element -> superset.anyMatch(superElement -> superElement.equals(element)));
    }

    public static boolean subseteqTheta(Set<String> a, Set<String> b){
        for (String e : a)
            if (!containsHelper(e,b))
                return false;
        return true;
    }

//    public static <T> boolean subseteqOmega(Set<List<String>> a, Set<List<String>> b){
//        Stream<List<P>> pStreamOfA = a.stream().map(l->l.stream().map(s->new P(s)).collect(Collectors.toList()));
//        Stream<List<P>> pStreamOfB = b.stream().map(l->l.stream().map(s->new P(s)).collect(Collectors.toList()));
//        return isSubset(pStreamOfA,pStreamOfB);
//    }

//    public static <T> boolean subseteqOmega(Set<List<String>> a, Set<List<String>> b){
//        for (List<String> e : a){
//            List<P> ps = new ArrayList<>();
//            for (String p : e){
//                ps.add(new P(p));
//            }
//            if (!b.stream().map(l->l.stream().map(s->new P(s)).collect(Collectors.toList())).anyMatch(p->p.equals(ps))){
//                return false;
//            }
//        }
//        return true;
//    }

    public static <T> boolean subseteqOmega(Set<List<String>> a, Set<List<String>> b){
        return a.stream().allMatch(listA->b.stream().anyMatch(listB-> IntStream.range(0,listB.size()).allMatch(i->equals(listA.get(i),listB.get(i)))));
    }

}
