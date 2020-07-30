// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class BinaryInsertion
{
    private BinaryInsertion() {
    }
    
    public static void sort(final Comparable[] a) {
        for (int n = a.length, i = 1; i < n; ++i) {
            final Comparable v = a[i];
            int lo = 0;
            int hi = i;
            while (lo < hi) {
                final int mid = lo + (hi - lo) / 2;
                if (less(v, a[mid])) {
                    hi = mid;
                }
                else {
                    lo = mid + 1;
                }
            }
            for (int j = i; j > lo; --j) {
                a[j] = a[j - 1];
            }
            a[lo] = v;
        }
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
