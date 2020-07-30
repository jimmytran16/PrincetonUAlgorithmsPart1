// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Shell
{
    private Shell() {
    }
    
    public static void sort(final Comparable[] a) {
        int n;
        int h;
        for (n = a.length, h = 1; h < n / 3; h = 3 * h + 1) {}
        while (h >= 1) {
            for (int i = h; i < n; ++i) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            assert isHsorted(a, h);
            h /= 3;
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
    
    private static boolean isHsorted(final Comparable[] a, final int h) {
        for (int i = h; i < a.length; ++i) {
            if (less(a[i], a[i - h])) {
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
