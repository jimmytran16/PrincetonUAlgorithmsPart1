// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class InplaceMSD
{
    private static final int R = 256;
    private static final int CUTOFF = 15;
    
    private InplaceMSD() {
    }
    
    public static void sort(final String[] a) {
        final int n = a.length;
        sort(a, 0, n - 1, 0);
    }
    
    private static int charAt(final String s, final int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }
    
    private static void sort(final String[] a, final int lo, final int hi, final int d) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
            return;
        }
        final int[] heads = new int[258];
        final int[] tails = new int[257];
        for (int i = lo; i <= hi; ++i) {
            final int c = charAt(a[i], d);
            final int[] array = heads;
            final int n = c + 2;
            ++array[n];
        }
        heads[0] = lo;
        for (int r = 0; r < 257; ++r) {
            final int[] array2 = heads;
            final int n2 = r + 1;
            array2[n2] += heads[r];
            tails[r] = heads[r + 1];
        }
        for (int r = 0; r < 257; ++r) {
            while (heads[r] < tails[r]) {
                for (int c = charAt(a[heads[r]], d); c + 1 != r; c = charAt(a[heads[r]], d)) {
                    exch(a, heads[r], heads[c + 1]++);
                }
                final int[] array3 = heads;
                final int n3 = r;
                ++array3[n3];
            }
        }
        for (int r = 0; r < 256; ++r) {
            sort(a, tails[r], tails[r + 1] - 1, d + 1);
        }
    }
    
    private static void insertion(final String[] a, final int lo, final int hi, final int d) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); --j) {
                exch(a, j, j - 1);
            }
        }
    }
    
    private static void exch(final String[] a, final int i, final int j) {
        final String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private static boolean less(final String v, final String w, final int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); ++i) {
            if (v.charAt(i) < w.charAt(i)) {
                return true;
            }
            if (v.charAt(i) > w.charAt(i)) {
                return false;
            }
        }
        return v.length() < w.length();
    }
    
    public static void main(final String[] args) {
        final String[] a = StdIn.readAllStrings();
        final int n = a.length;
        sort(a);
        for (int i = 0; i < n; ++i) {
            StdOut.println(a[i]);
        }
    }
}
