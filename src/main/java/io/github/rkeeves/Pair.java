package io.github.rkeeves;

public record Pair<A, B>(A fst, B snd) {

    public static <A, B> Pair<A, B> pair(A fst, B snd) { return new Pair<>(fst, snd); }
}
