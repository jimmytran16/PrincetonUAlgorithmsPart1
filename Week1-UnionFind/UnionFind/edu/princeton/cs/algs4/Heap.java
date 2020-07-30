// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Heap
{
    private Heap() {
    }
    
    public static void sort(final Comparable[] pq) {
        final int n = pq.length;
        for (int k = n / 2; k >= 1; --k) {
            sink(pq, k, n);
        }
        int k = n;
        while (k > 1) {
            exch(pq, 1, k--);
            sink(pq, 1, k);
        }
    }
    
    private static void sink(final Comparable[] pq, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j + 1)) {
                ++j;
            }
            if (!less(pq, k, j)) {
                break;
            }
            exch(pq, k, j);
            k = j;
        }
    }
    
    private static boolean less(final Comparable[] pq, final int i, final int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }
    
    private static void exch(final Object[] pq, final int i, final int j) {
        final Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
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
