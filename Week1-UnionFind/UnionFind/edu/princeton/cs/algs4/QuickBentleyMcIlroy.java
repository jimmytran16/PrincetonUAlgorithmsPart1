// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class QuickBentleyMcIlroy
{
    private static final int INSERTION_SORT_CUTOFF = 8;
    private static final int MEDIAN_OF_3_CUTOFF = 40;
    
    private QuickBentleyMcIlroy() {
    }
    
    public static void sort(final Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(final Comparable[] a, final int lo, final int hi) {
        final int n = hi - lo + 1;
        if (n <= 8) {
            insertionSort(a, lo, hi);
            return;
        }
        if (n <= 40) {
            final int m = median3(a, lo, lo + n / 2, hi);
            exch(a, m, lo);
        }
        else {
            final int eps = n / 8;
            final int mid = lo + n / 2;
            final int m2 = median3(a, lo, lo + eps, lo + eps + eps);
            final int m3 = median3(a, mid - eps, mid, mid + eps);
            final int m4 = median3(a, hi - eps - eps, hi - eps, hi);
            final int ninther = median3(a, m2, m3, m4);
            exch(a, ninther, lo);
        }
        int i = lo;
        int j = hi + 1;
        int p = lo;
        int q = hi + 1;
        final Comparable v = a[lo];
        while (true) {
            if (!less(a[++i], v) || i == hi) {
                while (less(v, a[--j]) && j != lo) {}
                if (i == j && eq(a[i], v)) {
                    exch(a, ++p, i);
                }
                if (i >= j) {
                    break;
                }
                exch(a, i, j);
                if (eq(a[i], v)) {
                    exch(a, ++p, i);
                }
                if (!eq(a[j], v)) {
                    continue;
                }
                exch(a, --q, j);
            }
        }
        i = j + 1;
        for (int k = lo; k <= p; ++k) {
            exch(a, k, j--);
        }
        for (int k = hi; k >= q; --k) {
            exch(a, k, i++);
        }
        sort(a, lo, j);
        sort(a, i, hi);
    }
    
    private static void insertionSort(final Comparable[] a, final int lo, final int hi) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); --j) {
                exch(a, j, j - 1);
            }
        }
    }
    
    private static int median3(final Comparable[] a, final int i, final int j, final int k) {
        return less(a[i], a[j]) ? (less(a[j], a[k]) ? j : (less(a[i], a[k]) ? k : i)) : (less(a[k], a[j]) ? j : (less(a[k], a[i]) ? k : i));
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v != w && v.compareTo(w) < 0;
    }
    
    private static boolean eq(final Comparable v, final Comparable w) {
        return v == w || v.compareTo(w) == 0;
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
        assert isSorted(a);
        show(a);
    }
}
