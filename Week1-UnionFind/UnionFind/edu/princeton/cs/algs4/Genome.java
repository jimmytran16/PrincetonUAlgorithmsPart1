// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Genome
{
    private Genome() {
    }
    
    public static void compress() {
        final Alphabet DNA = Alphabet.DNA;
        final String s = BinaryStdIn.readString();
        final int n = s.length();
        BinaryStdOut.write(n);
        for (int i = 0; i < n; ++i) {
            final int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, 2);
        }
        BinaryStdOut.close();
    }
    
    public static void expand() {
        final Alphabet DNA = Alphabet.DNA;
        for (int n = BinaryStdIn.readInt(), i = 0; i < n; ++i) {
            final char c = BinaryStdIn.readChar(2);
            BinaryStdOut.write(DNA.toChar(c), 8);
        }
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
