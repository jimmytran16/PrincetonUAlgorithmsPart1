// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Merge
{
    private Merge() {
    }
    
    private static void merge(final Comparable[] a, final Comparable[] aux, final int lo, final int mid, final int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);
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
        assert isSorted(a, lo, hi);
    }
    
    private static void sort(final Comparable[] a, final Comparable[] aux, final int lo, final int hi) {
        if (hi <= lo) {
            return;
        }
        final int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }
    
    public static void sort(final Comparable[] a) {
        final Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        assert isSorted(a);
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
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
    
    private static void merge(final Comparable[] a, final int[] index, final int[] aux, final int lo, final int mid, final int hi) {
        for (int k = lo; k <= hi; ++k) {
            aux[k] = index[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int l = lo; l <= hi; ++l) {
            if (i > mid) {
                index[l] = aux[j++];
            }
            else if (j > hi) {
                index[l] = aux[i++];
            }
            else if (less(a[aux[j]], a[aux[i]])) {
                index[l] = aux[j++];
            }
            else {
                index[l] = aux[i++];
            }
        }
    }
    
    public static int[] indexSort(final Comparable[] a) {
        final int n = a.length;
        final int[] index = new int[n];
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }
        final int[] aux = new int[n];
        sort(a, index, aux, 0, n - 1);
        return index;
    }
    
    private static void sort(final Comparable[] a, final int[] index, final int[] aux, final int lo, final int hi) {
        if (hi <= lo) {
            return;
        }
        final int mid = lo + (hi - lo) / 2;
        sort(a, index, aux, lo, mid);
        sort(a, index, aux, mid + 1, hi);
        merge(a, index, aux, lo, mid, hi);
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
