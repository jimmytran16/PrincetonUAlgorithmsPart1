// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Comparator;

public class MergeX
{
    private static final int CUTOFF = 7;
    
    private MergeX() {
    }
    
    private static void merge(final Comparable[] src, final Comparable[] dst, final int lo, final int mid, final int hi) {
        assert isSorted(src, lo, mid);
        assert isSorted(src, mid + 1, hi);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; ++k) {
            if (i > mid) {
                dst[k] = src[j++];
            }
            else if (j > hi) {
                dst[k] = src[i++];
            }
            else if (less(src[j], src[i])) {
                dst[k] = src[j++];
            }
            else {
                dst[k] = src[i++];
            }
        }
        assert isSorted(dst, lo, hi);
    }
    
    private static void sort(final Comparable[] src, final Comparable[] dst, final int lo, final int hi) {
        if (hi <= lo + 7) {
            insertionSort(dst, lo, hi);
            return;
        }
        final int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid);
        sort(dst, src, mid + 1, hi);
        if (!less(src[mid + 1], src[mid])) {
            System.arraycopy(src, lo, dst, lo, hi - lo + 1);
            return;
        }
        merge(src, dst, lo, mid, hi);
    }
    
    public static void sort(final Comparable[] a) {
        final Comparable[] aux = a.clone();
        sort(aux, a, 0, a.length - 1);
        assert isSorted(a);
    }
    
    private static void insertionSort(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
                exch(a, j, j - 1);
            }
        }
    }
    
    private static void exch(final Object[] a, final int i, final int j) {
        final Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    private static boolean less(final Comparable a, final Comparable b) {
        return a.compareTo(b) < 0;
    }
    
    private static boolean less(final Object a, final Object b, final Comparator comparator) {
        return comparator.compare(a, b) < 0;
    }
    
    public static void sort(final Object[] a, final Comparator comparator) {
        final Object[] aux = a.clone();
        sort(aux, a, 0, a.length - 1, comparator);
        assert isSorted(a, comparator);
    }
    
    private static void merge(final Object[] src, final Object[] dst, final int lo, final int mid, final int hi, final Comparator comparator) {
        assert isSorted(src, lo, mid, comparator);
        assert isSorted(src, mid + 1, hi, comparator);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; ++k) {
            if (i > mid) {
                dst[k] = src[j++];
            }
            else if (j > hi) {
                dst[k] = src[i++];
            }
            else if (less(src[j], src[i], comparator)) {
                dst[k] = src[j++];
            }
            else {
                dst[k] = src[i++];
            }
        }
        assert isSorted(dst, lo, hi, comparator);
    }
    
    private static void sort(final Object[] src, final Object[] dst, final int lo, final int hi, final Comparator comparator) {
        if (hi <= lo + 7) {
            insertionSort(dst, lo, hi, comparator);
            return;
        }
        final int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid, comparator);
        sort(dst, src, mid + 1, hi, comparator);
        if (!less(src[mid + 1], src[mid], comparator)) {
            System.arraycopy(src, lo, dst, lo, hi - lo + 1);
            return;
        }
        merge(src, dst, lo, mid, hi, comparator);
    }
    
    private static void insertionSort(final Object[] a, final int lo, final int hi, final Comparator comparator) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1], comparator); --j) {
                exch(a, j, j - 1);
            }
        }
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
        return isSorted(a, 0, a.length - 1, comparator);
    }
    
    private static boolean isSorted(final Object[] a, final int lo, final int hi, final Comparator comparator) {
        for (int i = lo + 1; i <= hi; ++i) {
            if (less(a[i], a[i - 1], comparator)) {
                return false;
            }
        }
        return true;
    }
    
    private static void show(final Object[] a) {
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
