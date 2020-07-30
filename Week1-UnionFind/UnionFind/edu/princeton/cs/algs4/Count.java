// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Count
{
    private Count() {
    }
    
    public static void main(final String[] args) {
        final Alphabet alphabet = new Alphabet(args[0]);
        final int R = alphabet.radix();
        final int[] count = new int[R];
        while (StdIn.hasNextChar()) {
            final char c = StdIn.readChar();
            if (alphabet.contains(c)) {
                final int[] array = count;
                final int index = alphabet.toIndex(c);
                ++array[index];
            }
        }
        for (int c2 = 0; c2 < R; ++c2) {
            StdOut.println(alphabet.toChar(c2) + " " + count[c2]);
        }
    }
}
