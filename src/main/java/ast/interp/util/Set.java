package ast.interp.util;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public class Set<T> extends LinkedHashSet<T> {
    Set(){}
    Set(Collection<T> collection){
        super(collection);
    }
    public List<T> toList(){
        return new ArrayList<>(this);
    }

    @Override
    public String toString(){
        return toString(true);
    }

    public String toString(boolean latex){
        String escape = latex?"\\":"";
        Iterator<T> iterator = this.iterator();
        if (!iterator.hasNext()){
            return escape+"{"+escape+"}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(escape+"{");
        sb.append(elementToString(iterator.next()));
        while (iterator.hasNext()){
            sb.append(","+elementToString(iterator.next()));
        }
        sb.append(escape+"}");
        return sb.toString();
    }

    private String elementToString(T t){
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
