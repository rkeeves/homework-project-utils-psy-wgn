package io.github.rkeeves;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static io.github.rkeeves.Lists.addTail;
import static io.github.rkeeves.Lists.nil;
import static io.github.rkeeves.Pair.pair;

public final class Search {

    public static <S, C> Optional<List<C>> bfs(
            S seed,
            Predicate<S> isGoal,
            Function<S, Stream<C>> getCommands,
            BiFunction<C, S, S> runCommand) {
        Deque<Pair<S, List<C>>> q = new LinkedList<>();
        Pair<S, List<C>> start = pair(seed, nil());
        Set<S> seen = new HashSet<>();
        q.add(start);
        seen.add(start.fst());
        while (!q.isEmpty()) {
            var x = q.pollFirst();
            var s = x.fst();
            var path = x.snd();
            if (isGoal.test(s)) {
                return Optional.of(path);
            }
            getCommands.apply(s)
                    .map(cmd -> pair(runCommand.apply(cmd, s), addTail(path, cmd)))
                    .forEach(y -> {
                        if (seen.add(y.fst())) {
                            q.offerLast(y);
                        }
                    });
        }
        return Optional.empty();
    }
}
