package io.github.rkeeves;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.lang.Math.min;

// If you really care about intermediate Lists...
// You should look up the work of smart people:
// https://wiki.haskell.org/Correctness_of_short_cut_fusion
// Here I do dumb things.
public final class Lists {

    public static <A> List<A> nil() {
        return Collections.emptyList();
    }

    public static <A, B, C> List<C> zipWith(BiFunction<A, B, C> f, List<A> as, List<B> bs) {
        return IntStream.range(0, min(as.size(), bs.size()))
                .mapToObj(i -> f.apply(as.get(i), bs.get(i)))
                .toList();
    }

    public static <A, B> List<B> mapi(BiFunction<Integer, A, B> f, List<A> xs) {
        return IntStream.range(0, xs.size()).mapToObj(i -> f.apply(i, xs.get(i))).toList();
    }

    public static <A, B> List<B> scanl(BiFunction<B, A, B> f, B b, List<A> as) {
        var bs = new LinkedList<B>();
        bs.add(b);
        for (var a : as) {
            b = f.apply(b, a);
            bs.add(b);
        }
        return bs;
    }

    public static <A> List<A> addTail(List<A> xs, A x) {
        var ys = new LinkedList<>(xs); ys.add(x); return ys;
    }
}
