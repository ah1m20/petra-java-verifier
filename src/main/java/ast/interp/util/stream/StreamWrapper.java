package ast.interp.util.stream;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamWrapper<T> {
    private final Stream<T> stream;

    public StreamWrapper(Stream<T> stream) {
        this.stream = stream;
    }

    public <R> StreamWrapper<R> map(Function<T, R> mapper) {
        return new StreamWrapper(this.stream.map(mapper));
    }

    public StreamWrapper<T> filter(Predicate<T> predicate) {
        return new StreamWrapper(this.stream.filter(predicate));
    }

    public boolean allMatch(Predicate<T> toHold){
        return this.stream.allMatch(toHold);
    }

    public boolean anyMatch(Predicate<T> toHold){
        return this.stream.anyMatch(toHold);
    }

    public List<T> toList() {
        return this.stream.collect(Collectors.toList());
    }

    public Set<T> toSet() {
        return this.stream.collect(Collectors.toSet());
    }

    public <R> List<R> toList(Function<T, R> mapper) {
        return this.map(mapper).toList();
    }

    public <R> Set<R> toSet(Function<T, R> mapper) {
        return this.map(mapper).toSet();
    }

    public List<T> toList(Predicate<T> predicate) {
        return this.filter(predicate).toList();
    }

    public Set<T> toSet(Predicate<T> predicate) {
        return this.filter(predicate).toSet();
    }

}