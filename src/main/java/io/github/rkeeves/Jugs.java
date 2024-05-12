package io.github.rkeeves;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.github.rkeeves.Eq.eq;
import static io.github.rkeeves.Lists.mapi;
import static io.github.rkeeves.Lists.scanl;
import static java.lang.Math.min;

public final class Jugs {
    public record Jug(int v, int cap) {}

    public static Jug jug(int v, int cap) { return new Jug(v, cap); }

    public static List<Jug> jugs(Jug... jugs) { return List.of(jugs); }

    @FunctionalInterface
    public interface Cmd {
        <T> T fold(Function<Integer, T> fill, Function<Integer, T> empty, BiFunction<Integer, Integer, T> pour);
    }

    public static Cmd fill(int i) {
        return new Cmd() {
            @Override
            public <T> T fold(Function<Integer, T> fill, Function<Integer, T> empty, BiFunction<Integer, Integer, T> pour) {
                return fill.apply(i);
            }
        };
    }

    public static Cmd empty(int i) {
        return new Cmd() {
            @Override
            public <T> T fold(Function<Integer, T> fill, Function<Integer, T> empty, BiFunction<Integer, Integer, T> pour) {
                return empty.apply(i);
            }
        };
    }

    public static Cmd pour(int i, int j) {
        return new Cmd() {
            @Override
            public <T> T fold(Function<Integer, T> fill, Function<Integer, T> empty, BiFunction<Integer, Integer, T> pour) {
                return pour.apply(i, j);
            }
        };
    }

    public static List<Jug> runCmd(Cmd cmd, List<Jug> xs) {
        return cmd.fold(
                (i) -> mapi((k, x) -> eq(k, i) ? jug(x.cap, x.cap) : x, xs),
                (i) -> mapi((k, x) -> eq(k, i) ? jug(0, x.cap) : x, xs),
                (i, j) -> {
                    int n = xs.size();
                    if (eq(i, j) || i < 0 || n <= i ||  j < 0 || n <= j) return xs;
                    var src = xs.get(i);
                    var dst = xs.get(j);
                    var d = min(src.v,dst.cap - dst.v);
                    return mapi((k, x) -> eq(k, i) ? jug(src.v - d, src.cap) : eq(k, j) ? jug(dst.v + d, dst.cap) : x, xs);
                }
        );
    }

    public static List<List<Jug>> runCmds(List<Jug> seed, List<Cmd> cmds) {
       return scanl((s, cmd) -> runCmd(cmd, s), seed, cmds);
    }
}
