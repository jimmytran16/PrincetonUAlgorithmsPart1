// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.BufferedInputStream;

public final class BinaryStdIn
{
    private static final int EOF = -1;
    private static BufferedInputStream in;
    private static int buffer;
    private static int n;
    private static boolean isInitialized;
    
    private BinaryStdIn() {
    }
    
    private static void initialize() {
        BinaryStdIn.in = new BufferedInputStream(System.in);
        BinaryStdIn.buffer = 0;
        BinaryStdIn.n = 0;
        fillBuffer();
        BinaryStdIn.isInitialized = true;
    }
    
    private static void fillBuffer() {
        try {
            BinaryStdIn.buffer = BinaryStdIn.in.read();
            BinaryStdIn.n = 8;
        }
        catch (IOException e) {
            System.out.println("EOF");
            BinaryStdIn.buffer = -1;
            BinaryStdIn.n = -1;
        }
    }
    
    public static void close() {
        if (!BinaryStdIn.isInitialized) {
            initialize();
        }
        try {
            BinaryStdIn.in.close();
            BinaryStdIn.isInitialized = false;
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Could not close BinaryStdIn", ioe);
        }
    }
    
    public static boolean isEmpty() {
        if (!BinaryStdIn.isInitialized) {
            initialize();
        }
        return BinaryStdIn.buffer == -1;
    }
    
    public static boolean readBoolean() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        --BinaryStdIn.n;
        final boolean bit = (BinaryStdIn.buffer >> BinaryStdIn.n & 0x1) == 0x1;
        if (BinaryStdIn.n == 0) {
            fillBuffer();
        }
        return bit;
    }
    
    public static char readChar() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        if (BinaryStdIn.n == 8) {
            final int x = BinaryStdIn.buffer;
            fillBuffer();
            return (char)(x & 0xFF);
        }
        int x = BinaryStdIn.buffer;
        x <<= 8 - BinaryStdIn.n;
        final int oldN = BinaryStdIn.n;
        fillBuffer();
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        BinaryStdIn.n = oldN;
        x |= BinaryStdIn.buffer >>> BinaryStdIn.n;
        return (char)(x & 0xFF);
    }
    
    public static char readChar(final int r) {
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }
        if (r == 8) {
            return readChar();
        }
        char x = '\0';
        for (int i = 0; i < r; ++i) {
            x <<= 1;
            final boolean bit = readBoolean();
            if (bit) {
                x |= '\u0001';
            }
        }
        return x;
    }
    
    public static String readString() {
        if (isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        final StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            final char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static short readShort() {
        short x = 0;
        for (int i = 0; i < 2; ++i) {
            final char c = readChar();
            x <<= 8;
            x |= (short)c;
        }
        return x;
    }
    
    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; ++i) {
            final char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
    
    public static int readInt(final int r) {
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }
        if (r == 32) {
            return readInt();
        }
        int x = 0;
        for (int i = 0; i < r; ++i) {
            x <<= 1;
            final boolean bit = readBoolean();
            if (bit) {
                x |= 0x1;
            }
        }
        return x;
    }
    
    public static long readLong() {
        long x = 0L;
        for (int i = 0; i < 8; ++i) {
            final char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
    
    public static double readDouble() {
        return Double.longBitsToDouble(readLong());
    }
    
    public static float readFloat() {
        return Float.intBitsToFloat(readInt());
    }
    
    public static byte readByte() {
        final char c = readChar();
        return (byte)(c & '\u00ff');
    }
    
    public static void main(final String[] args) {
        while (!isEmpty()) {
            final char c = readChar();
            BinaryStdOut.write(c);
        }
        BinaryStdOut.flush();
    }
}
