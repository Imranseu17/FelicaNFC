package com.example.root.officeapp.lang;

import java.util.Collection;
import java.util.Iterator;

public final class StringUtils {
    public static final String EMPTY = "";
    public static final String SPACE = " ";

    private StringUtils() {
        throw new AssertionError();
    }

    public static String byteToHex(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int length = data.length;
        for (int i = 0; i < length; i++) {
            builder.append(String.format("%02X", new Object[]{Byte.valueOf(data[i])}));
        }
        return builder.toString();
    }

    public static String replaceLineBreaksWithSpace(String string) {
        return replaceLineBreaks(string, SPACE);
    }

    public static String collapseLineBreaks(String string) {
        return replaceLineBreaks(string, "");
    }

    public static String replaceLineBreaks(String string, String replace) {
        if (string == null) {
            return null;
        }
        return string.replaceAll("\\r\\n|\\r|\\n", replace);
    }

    public static String removeWhiteSpaces(String string) {
        if (string == null) {
            return null;
        }
        return string.replaceAll("\\s+", "");
    }

    public static String tryTrim(String string) {
        if (string == null) {
            return null;
        }
        return string.trim();
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n = s.length();
        int m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        int i;
        if (n > m) {
            CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int[] p = new int[(n + 1)];
        int[] d = new int[(n + 1)];
        for (i = 0; i <= n; i++) {
            p[i] = i;
        }
        int[] p2 = p;
        for (int j = 1; j <= m; j++) {
            char tj = t.charAt(j - 1);
            d[0] = j;
            for (i = 1; i <= n; i++) {
                d[i] = Math.min(Math.min(d[i - 1] + 1, p2[i] + 1), p2[i - 1] + (s.charAt(i + -1) == tj ? 0 : 1));
            }
            int[] pd = p2;
            p2 = d;
            d = pd;
        }
        return p2[n];
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int bufSize = endIndex - startIndex;
        if (bufSize <= 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder(bufSize * ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1));
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static <T> String join(Iterator<T> iterator, String separator) {
        String str = null;
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (iterator.hasNext()) {
            StringBuilder buf = new StringBuilder(256);
            if (first != null) {
                buf.append(first);
            }
            while (iterator.hasNext()) {
                if (separator != null) {
                    buf.append(separator);
                }
                Object obj = iterator.next();
                if (obj != null) {
                    buf.append(obj);
                }
            }
            return buf.toString();
        }
        if (first != null) {
            str = first.toString();
        }
        return str;
    }

    public static <T> String join(Collection<T> collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }
}
