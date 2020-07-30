// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;

public final class BinaryStdOut
{
    private static BufferedOutputStream out;
    private static int buffer;
    private static int n;
    private static boolean isInitialized;
    
    private BinaryStdOut() {
    }
    
    private static void initialize() {
        BinaryStdOut.out = new BufferedOutputStream(System.out);
        BinaryStdOut.buffer = 0;
        BinaryStdOut.n = 0;
        BinaryStdOut.isInitialized = true;
    }
    
    private static void writeBit(final boolean bit) {
        if (!BinaryStdOut.isInitialized) {
            initialize();
        }
        BinaryStdOut.buffer <<= 1;
        if (bit) {
            BinaryStdOut.buffer |= 0x1;
        }
        ++BinaryStdOut.n;
        if (BinaryStdOut.n == 8) {
            clearBuffer();
        }
    }
    
    private static void writeByte(final int x) {
        if (!BinaryStdOut.isInitialized) {
            initialize();
        }
        assert x >= 0 && x < 256;
        if (BinaryStdOut.n == 0) {
            try {
                BinaryStdOut.out.write(x);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (int i = 0; i < 8; ++i) {
            final boolean bit = (x >>> 8 - i - 1 & 0x1) == 0x1;
            writeBit(bit);
        }
    }
    
    private static void clearBuffer() {
        if (!BinaryStdOut.isInitialized) {
            initialize();
        }
        if (BinaryStdOut.n == 0) {
            return;
        }
        if (BinaryStdOut.n > 0) {
            BinaryStdOut.buffer <<= 8 - BinaryStdOut.n;
        }
        try {
            BinaryStdOut.out.write(BinaryStdOut.buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        BinaryStdOut.n = 0;
        BinaryStdOut.buffer = 0;
    }
    
    public static void flush() {
        clearBuffer();
        try {
            BinaryStdOut.out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void close() {
        flush();
        try {
            BinaryStdOut.out.close();
            BinaryStdOut.isInitialized = false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void write(final boolean x) {
        writeBit(x);
    }
    
    public static void write(final byte x) {
        writeByte(x & 0xFF);
    }
    
    public static void write(final int x) {
        writeByte(x >>> 24 & 0xFF);
        writeByte(x >>> 16 & 0xFF);
        writeByte(x >>> 8 & 0xFF);
        writeByte(x >>> 0 & 0xFF);
    }
    
    public static void write(final int x, final int r) {
        if (r == 32) {
            write(x);
            return;
        }
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x < 0 || x >= 1 << r) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; ++i) {
            final boolean bit = (x >>> r - i - 1 & 0x1) == 0x1;
            writeBit(bit);
        }
    }
    
    public static void write(final double x) {
        write(Double.doubleToRawLongBits(x));
    }
    
    public static void write(final long x) {
        writeByte((int)(x >>> 56 & 0xFFL));
        writeByte((int)(x >>> 48 & 0xFFL));
        writeByte((int)(x >>> 40 & 0xFFL));
        writeByte((int)(x >>> 32 & 0xFFL));
        writeByte((int)(x >>> 24 & 0xFFL));
        writeByte((int)(x >>> 16 & 0xFFL));
        writeByte((int)(x >>> 8 & 0xFFL));
        writeByte((int)(x >>> 0 & 0xFFL));
    }
    
    public static void write(final float x) {
        write(Float.floatToRawIntBits(x));
    }
    
    public static void write(final short x) {
        writeByte(x >>> 8 & 0xFF);
        writeByte(x >>> 0 & 0xFF);
    }
    
    public static void write(final char x) {
        if (x < '\0' || x >= '\u0100') {
            throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        }
        writeByte(x);
    }
    
    public static void write(final char x, final int r) {
        if (r == 8) {
            write(x);
            return;
        }
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x >= 1 << r) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; ++i) {
            final boolean bit = (x >>> r - i - 1 & 0x1) == 0x1;
            writeBit(bit);
        }
    }
    
    public static void write(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            write(s.charAt(i));
        }
    }
    
    public static void write(final String s, final int r) {
        for (int i = 0; i < s.length(); ++i) {
            write(s.charAt(i), r);
        }
    }
    
    public static void main(final String[] args) {
        for (int m = Integer.parseInt(args[0]), i = 0; i < m; ++i) {
            write(i);
        }
        flush();
    }
}
