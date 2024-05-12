package io.github.rkeeves;

import java.util.Objects;

public final class Eq {

    public static boolean eq(Object a, Object b) {
        return Objects.equals(a, b);
    }
}
