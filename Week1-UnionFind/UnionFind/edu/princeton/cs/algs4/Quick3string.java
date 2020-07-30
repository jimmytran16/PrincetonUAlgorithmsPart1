// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Quick3string
{
    private static final int CUTOFF = 15;
    
    private Quick3string() {
    }
    
    public static void sort(final String[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1, 0);
        assert isSorted(a);
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
        int lt = lo;
        int gt = hi;
        final int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            final int t = charAt(a[i], d);
            if (t < v) {
                exch(a, lt++, i++);
            }
            else if (t > v) {
                exch(a, i, gt--);
            }
            else {
                ++i;
            }
        }
        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
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
        assert v.substring(0, d).equals(w.substring(0, d));
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
    
    private static boolean isSorted(final String[] a) {
        for (int i = 1; i < a.length; ++i) {
            if (a[i].compareTo(a[i - 1]) < 0) {
                return false;
            }
        }
        return true;
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
