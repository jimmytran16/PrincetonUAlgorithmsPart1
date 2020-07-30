// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Quick3way
{
    private Quick3way() {
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
        int lt = lo;
        int gt = hi;
        final Comparable v = a[lo];
        int i = lo + 1;
        while (i <= gt) {
            final int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exch(a, lt++, i++);
            }
            else if (cmp > 0) {
                exch(a, i, gt--);
            }
            else {
                ++i;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
        assert isSorted(a, lo, hi);
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
    }
}
