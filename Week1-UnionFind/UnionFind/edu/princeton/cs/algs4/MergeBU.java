// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class MergeBU
{
    private MergeBU() {
    }
    
    private static void merge(final Comparable[] a, final Comparable[] aux, final int lo, final int mid, final int hi) {
        for (int k = lo; k <= hi; ++k) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int l = lo; l <= hi; ++l) {
            if (i > mid) {
                a[l] = aux[j++];
            }
            else if (j > hi) {
                a[l] = aux[i++];
            }
            else if (less(aux[j], aux[i])) {
                a[l] = aux[j++];
            }
            else {
                a[l] = aux[i++];
            }
        }
    }
    
    public static void sort(final Comparable[] a) {
        final int n = a.length;
        final Comparable[] aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len + len) {
                final int mid = lo + len - 1;
                final int hi = Math.min(lo + len + len - 1, n - 1);
                merge(a, aux, lo, mid, hi);
            }
        }
        assert isSorted(a);
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
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
