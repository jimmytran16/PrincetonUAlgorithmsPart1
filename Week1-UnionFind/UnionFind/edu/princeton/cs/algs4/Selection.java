// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Comparator;

public class Selection
{
    private Selection() {
    }
    
    public static void sort(final Comparable[] a) {
        for (int n = a.length, i = 0; i < n; ++i) {
            int min = i;
            for (int j = i + 1; j < n; ++j) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, i, min);
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }
    
    public static void sort(final Object[] a, final Comparator comparator) {
        for (int n = a.length, i = 0; i < n; ++i) {
            int min = i;
            for (int j = i + 1; j < n; ++j) {
                if (less(comparator, a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, i, min);
            assert isSorted(a, comparator, 0, i);
        }
        assert isSorted(a, comparator);
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static boolean less(final Comparator comparator, final Object v, final Object w) {
        return comparator.compare(v, w) < 0;
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
    
    private static boolean isSorted(final Object[] a, final Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }
    
    private static boolean isSorted(final Object[] a, final Comparator comparator, final int lo, final int hi) {
        for (int i = lo + 1; i <= hi; ++i) {
            if (less(comparator, a[i], a[i - 1])) {
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
