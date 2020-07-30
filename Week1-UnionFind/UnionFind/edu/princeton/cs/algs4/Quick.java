// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Quick
{
    private Quick() {
    }
    
    public static void sort(final Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }
    
    private static void sort(final Comparable[] a, final int lo, final int hi) {
        if (hi <= lo) {
            return;
        }
        final int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
        assert isSorted(a, lo, hi);
    }
    
    private static int partition(final Comparable[] a, final int lo, final int hi) {
        int i = lo;
        int j = hi + 1;
        final Comparable v = a[lo];
        while (true) {
            if (!less(a[++i], v) || i == hi) {
                while (less(v, a[--j]) && j != lo) {}
                if (i >= j) {
                    break;
                }
                exch(a, i, j);
            }
        }
        exch(a, lo, j);
        return j;
    }
    
    public static Comparable select(final Comparable[] a, final int k) {
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("index is not between 0 and " + a.length + ": " + k);
        }
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;
        while (hi > lo) {
            final int i = partition(a, lo, hi);
            if (i > k) {
                hi = i - 1;
            }
            else {
                if (i >= k) {
                    return a[i];
                }
                lo = i + 1;
            }
        }
        return a[lo];
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v != w && v.compareTo(w) < 0;
    }
    
    private static void exch(final Object[] a, final int i, final int j) {
        final Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    private static boolean isSorted(final Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
    
    private static boolean isSorted(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo + 1; i <= hi; ++i) {
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
        assert isSorted(a);
        StdRandom.shuffle(a);
        StdOut.println();
        for (int i = 0; i < a.length; ++i) {
            final String ith = (String)select(a, i);
            StdOut.println(ith);
        }
    }
}
