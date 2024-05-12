# homework-project-utils-psy-wgn

You know that Mommy made `homework-project-utils` some time ago.

Well, I'm just an [old friend of Mommy's](https://youtu.be/sPfjd2bayVs?t=130).

## What is homework-project-utils?

[homework-project-utils](https://github.com/INBPM0420L/homework-project-utils/) is a Java utility library for solving puzzles (generic graph search etc.).

Solving puzzles and Java are part of the curriculum at my old University.

`homework-project-utils` can be used as an aide during the course.

Aka `homework-project-utils` was made for the kids!

There is an example repo [homework-project-utils-examples](https://github.com/jeszy75/homework-project-utils-examples).

It solves **a** Three Water Jug problem.

But... what if you want to solve it with different parameters?

Or... what if you want to solve the [Two Water Jugs from the movie Die Hard](https://youtu.be/6cAbgAaEOVE?t=37)?

Wouldn't it be nice to be able to solve these?

## Ok, then what is homework-project-utils-psy-wgn?

Essentially `homework-project-utils-psy-wgn` is a dumb Java program which prints to stdout line noise.
_(No, don't believe Perl programmers, line noise is line noise. Just because they call it a fancy name like `TAP` it is still just line noise.)._

First it solves a 2 Jugs problem, where you start from an empty 3L and an empty 5L jug and have to reach 4L water via possible commands:

- `Fill i`: fill the i-th jug
- `Empty i`: empty the i-th jug
- `Pour i j`: pour as much as you can from i-th jug to j-th jug

```
2 Jugs Problem:
Init
[0/3] [0/5]
Fill #1
[0/3] [5/5]
Pour #1 -> #0
[3/3] [2/5]
Empty #0
[0/3] [2/5]
Pour #1 -> #0
[2/3] [0/5]
Fill #1
[2/3] [5/5]
Pour #1 -> #0
[3/3] [4/5]
```

Then it solves a 3 Jugs problem, where you start from an empty 3L, 5L jug and a full 8L and at the end you have to reach two with 4L water via possible commands:

- `Pour i j`: pour as much as you can from i-th jug to j-th jug

```
3 Jugs Problem:
Init
[0/3] [0/5] [8/8]
Pour #2 -> #1
[0/3] [5/5] [3/8]
Pour #1 -> #0
[3/3] [2/5] [3/8]
Pour #0 -> #2
[0/3] [2/5] [6/8]
Pour #1 -> #0
[2/3] [0/5] [6/8]
Pour #2 -> #1
[2/3] [5/5] [1/8]
Pour #1 -> #0
[3/3] [4/5] [1/8]
Pour #0 -> #2
[0/3] [4/5] [4/8]
```

My aim was to decouple the high level business logic, like:

- what is the goal?
- what commands can we do?
- what is the initial state?

from implementation, aka I tried to hardcode as little as possible.

The only really deeply hardcoded things are the Church encoded commands types, but you can freely choose what command to use at least:

- for example, the 2 Jugs problem uses: `fill`s, `empty`s, `pour`s,
- while the 3 Jugs problem uses only `pour`s.
