// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Knuth
{
    private Knuth() {
    }
    
    public static void shuffle(final Object[] a) {
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = (int)(Math.random() * (i + 1));
            final Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
    
    public static void shuffleAlternate(final Object[] a) {
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = i + (int)(Math.random() * (n - i));
            final Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
    
    public static void main(final String[] args) {
        final String[] a = StdIn.readAllStrings();
        shuffle(a);
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
    }
}
