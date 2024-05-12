package io.github.rkeeves;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.rkeeves.Jugs.*;
import static io.github.rkeeves.Lists.zipWith;
import static java.lang.String.format;

public class Main {

    public static void main(String[] args) {
        System.out.println("2 Jugs Problem:");
        System.out.println(jugs2Problem());
        System.out.println();
        System.out.println("3 Jugs Problem:");
        System.out.println(jugs3Problem());
        System.out.println();
    }

    public static String jugs2Problem() {
        final var start = jugs(
                jug(0, 3),
                jug(0, 5)
        );
        var L3 = 0;
        var L5 = 1;
        Predicate<List<Jug>> isGoal = jugs -> jugs.get(L5).v() == 4;
        Function<List<Jug>, Stream<Cmd>> getCommands = __ -> Stream.of(
                fill(L3),
                fill(L5),
                empty(L3),
                empty(L5),
                pour(L3, L5),
                pour(L5, L3)
        );
        return Search.bfs(start, isGoal, getCommands, Jugs::runCmd)
                .map(commands -> pretty(start, commands))
                .orElse("No Solution");
    }

    public static String jugs3Problem() {
        var start = jugs(
                jug(0, 3),
                jug(0, 5),
                jug(8, 8)
        );
        var L3 = 0;
        var L5 = 1;
        var L8 = 2;
        Predicate<List<Jug>> isGoal = jugs -> jugs.get(L5).v() == 4 && jugs.get(L8).v() == 4;
        Function<List<Jug>, Stream<Cmd>> getCommands = __ -> Stream.of(
                pour(L3, L5),
                pour(L3, L8),
                pour(L5, L3),
                pour(L5, L8),
                pour(L8, L3),
                pour(L8, L5)
        );
        return Search.bfs(start, isGoal, getCommands, Jugs::runCmd)
                .map(commands -> pretty(start, commands))
                .orElse("No Solution");
    }

    public static String pretty(List<Jug> js) {
        return js.stream()
                .map(j -> format("[%d/%d]", j.v(), j.cap()))
                .collect(Collectors.joining(" "));
    }

    public static String pretty(Cmd cmd) {
        return cmd.fold(
                i -> format("Fill #%d", i),
                i -> format("Empty #%d", i),
                (i, j) -> format("Pour #%d -> #%d", i, j)
        );
    }

    public static String pretty(List<Jug> seed, List<Cmd> cmds) {
        var states = runCmds(seed, cmds);
        List<String> cmds_ = Stream.concat(Stream.of("Init"), cmds.stream().map(Main::pretty)).collect(Collectors.toList());
        return zipWith((cmdText, state) -> format("%s%n%s", cmdText, pretty(state)), cmds_, states)
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}