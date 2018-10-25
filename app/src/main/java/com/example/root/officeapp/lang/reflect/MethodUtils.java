package com.example.root.officeapp.lang.reflect;

public final class MethodUtils {
    public static final String TAG = MethodUtils.class.getSimpleName();

    private MethodUtils() {
        throw new AssertionError();
    }

    public static <T> void ensureArgumentNotNull(T arg) {
        if (arg == null) {
            throw new NullPointerException("the argument should not be null");
        }
    }
}
