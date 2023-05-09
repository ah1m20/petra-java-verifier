package ast.interp.util.stream;

import java.util.Collection;

public final class Streams {
    public static <T> StreamWrapper<T> stream(Collection<T> collection){
        return new StreamWrapper<>(collection.stream());
    }
}
