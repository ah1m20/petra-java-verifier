package com.cognitionbox.petra.ast.interp.util.loggers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StatesLogging {

    public static <T> String toString(Collection<T> collection){
        String escape = "";
        String sep = ",";
        Iterator<T> iterator = collection.iterator();
        if (!iterator.hasNext()){
            return escape+"{"+escape+"}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(escape+"{");
        sb.append(elementToString(iterator.next())+(iterator.hasNext()?"\n":""));
        while (iterator.hasNext()){
            sb.append(sep+elementToString(iterator.next())+(iterator.hasNext()?"\n":""));
        }
        sb.append(escape+"}");
        return sb.toString();
    }

    private static <T> String elementToString(T t){
        if (t instanceof List){
            List list = (List)t;
            Iterator<T> iterator = list.iterator();
            if (!iterator.hasNext()){
                return "()";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(iterator.next());
            while (iterator.hasNext()){
                sb.append(","+iterator.next());
            }
            sb.append(")");
            return sb.toString();
        } else {
            return t.toString();
        }
    }
}
