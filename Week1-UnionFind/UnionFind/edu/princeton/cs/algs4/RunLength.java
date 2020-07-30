// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class RunLength
{
    private static final int R = 256;
    private static final int LG_R = 8;
    
    private RunLength() {
    }
    
    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            for (int run = BinaryStdIn.readInt(8), i = 0; i < run; ++i) {
                BinaryStdOut.write(b);
            }
            b = !b;
        }
        BinaryStdOut.close();
    }
    
    public static void compress() {
        char run = '\0';
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            final boolean b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(run, 8);
                run = '\u0001';
                old = !old;
            }
            else {
                if (run == '\u00ff') {
                    BinaryStdOut.write(run, 8);
                    run = '\0';
                    BinaryStdOut.write(run, 8);
                }
                ++run;
            }
        }
        BinaryStdOut.write(run, 8);
        BinaryStdOut.close();
    }
    
    public static void main(final String[] args) {
        if (args[0].equals("-")) {
            compress();
        }
        else {
            if (!args[0].equals("+")) {
                throw new IllegalArgumentException("Illegal command line argument");
            }
            expand();
        }
    }
}
