// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class InsertionX
{
    private InsertionX() {
    }
    
    public static void sort(final Comparable[] a) {
        final int n = a.length;
        int exchanges = 0;
        for (int i = n - 1; i > 0; --i) {
            if (less(a[i], a[i - 1])) {
                exch(a, i, i - 1);
                ++exchanges;
            }
        }
        if (exchanges == 0) {
            return;
        }
        for (int i = 2; i < n; ++i) {
            Comparable v;
            int j;
            for (v = a[i], j = i; less(v, a[j - 1]); --j) {
                a[j] = a[j - 1];
            }
            a[j] = v;
        }
        assert isSorted(a);
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static void exch(final Object[] a, final int i, final int j) {
        final Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    private static boolean isSorted(final Comparable[] a) {
        for (int i = 1; i < a.length; ++i) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }
    
    private static void show(final Comparable[] a) {
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
    }
    
    public static void main(final String[] args) {
        final String[] a = StdIn.readAllStrings();
        sort(a);
        show(a);
    }
}
