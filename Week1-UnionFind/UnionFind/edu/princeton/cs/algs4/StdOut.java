//
// Decompiled by Procyon v0.5.36
//

package edu.princeton.cs.algs4;

import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

public final class StdOut
{
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static PrintWriter out;

    private StdOut() {
    }

    public static void println() {
        StdOut.out.println();
    }

    public static void println(final Object x) {
        StdOut.out.println(x);
    }

    public static void println(final boolean x) {
        StdOut.out.println(x);
    }

    public static void println(final char x) {
        StdOut.out.println(x);
    }

    public static void println(final double x) {
        StdOut.out.println(x);
    }

    public static void println(final float x) {
        StdOut.out.println(x);
    }

    public static void println(final int x) {
        StdOut.out.println(x);
    }

    public static void println(final long x) {
        StdOut.out.println(x);
    }

    public static void println(final short x) {
        StdOut.out.println(x);
    }

    public static void println(final byte x) {
        StdOut.out.println(x);
    }

    public static void print() {
        StdOut.out.flush();
    }

    public static void print(final Object x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final boolean x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final char x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final double x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final float x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final int x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final long x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final short x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void print(final byte x) {
        StdOut.out.print(x);
        StdOut.out.flush();
    }

    public static void printf(final String format, final Object... args) {
        StdOut.out.printf(StdOut.LOCALE, format, args);
        StdOut.out.flush();
    }

    public static void printf(final Locale locale, final String format, final Object... args) {
        StdOut.out.printf(locale, format, args);
        StdOut.out.flush();
    }

    public static void main(final String[] args) {
        println("Test");
        println(17);
        println(true);
        printf("%.6f\n", 0.14285714285714285);
    }

    static {
        LOCALE = Locale.US;
        try {
            StdOut.out = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"), true);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }
}
