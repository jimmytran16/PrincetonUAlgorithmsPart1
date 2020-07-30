// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Comparator;

public class Insertion
{
    private Insertion() {
    }
    
    public static void sort(final Comparable[] a) {
        for (int n = a.length, i = 1; i < n; ++i) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); --j) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }
    
    public static void sort(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo + 1; i < hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi);
    }
    
    public static void sort(final Object[] a, final Comparator comparator) {
        for (int n = a.length, i = 1; i < n; ++i) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); --j) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i, comparator);
        }
        assert isSorted(a, comparator);
    }
    
    public static void sort(final Object[] a, final int lo, final int hi, final Comparator comparator) {
        for (int i = lo + 1; i < hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1], comparator); --j) {
                exch(a, j, j - 1);
            }
        }
        assert isSorted(a, lo, hi, comparator);
    }
    
    public static int[] indexSort(final Comparable[] a) {
        final int n = a.length;
        final int[] index = new int[n];
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); --j) {
                exch(index, j, j - 1);
            }
        }
        return index;
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static boolean less(final Object v, final Object w, final Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }
    
    private static void exch(final Object[] a, final int i, final int j) {
        final Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    private static void exch(final int[] a, final int i, final int j) {
        final int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    private static boolean isSorted(final Comparable[] a) {
        return isSorted(a, 0, a.length);
    }
    
    private static boolean isSorted(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo + 1; i < hi; ++i) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isSorted(final Object[] a, final Comparator comparator) {
        return isSorted(a, 0, a.length, comparator);
    }
    
    private static boolean isSorted(final Object[] a, final int lo, final int hi, final Comparator comparator) {
        for (int i = lo + 1; i < hi; ++i) {
            if (less(a[i], a[i - 1], comparator)) {
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
