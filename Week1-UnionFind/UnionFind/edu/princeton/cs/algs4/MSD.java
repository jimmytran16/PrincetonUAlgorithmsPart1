// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class MSD
{
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;
    private static final int R = 256;
    private static final int CUTOFF = 15;
    
    private MSD() {
    }
    
    public static void sort(final String[] a) {
        final int n = a.length;
        final String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux);
    }
    
    private static int charAt(final String s, final int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }
    
    private static void sort(final String[] a, final int lo, final int hi, final int d, final String[] aux) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
            return;
        }
        final int[] count = new int[258];
        for (int i = lo; i <= hi; ++i) {
            final int c = charAt(a[i], d);
            final int[] array = count;
            final int n = c + 2;
            ++array[n];
        }
        for (int r = 0; r < 257; ++r) {
            final int[] array2 = count;
            final int n2 = r + 1;
            array2[n2] += count[r];
        }
        for (int i = lo; i <= hi; ++i) {
            final int c = charAt(a[i], d);
            aux[count[c + 1]++] = a[i];
        }
        for (int i = lo; i <= hi; ++i) {
            a[i] = aux[i - lo];
        }
        for (int r = 0; r < 256; ++r) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
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
    
    public static void sort(final int[] a) {
        final int n = a.length;
        final int[] aux = new int[n];
        sort(a, 0, n - 1, 0, aux);
    }
    
    private static void sort(final int[] a, final int lo, final int hi, final int d, final int[] aux) {
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
            return;
        }
        final int[] count = new int[257];
        final int mask = 255;
        final int shift = 32 - 8 * d - 8;
        for (int i = lo; i <= hi; ++i) {
            final int c = a[i] >> shift & mask;
            final int[] array = count;
            final int n = c + 1;
            ++array[n];
        }
        for (int r = 0; r < 256; ++r) {
            final int[] array2 = count;
            final int n2 = r + 1;
            array2[n2] += count[r];
        }
        for (int i = lo; i <= hi; ++i) {
            final int c = a[i] >> shift & mask;
            aux[count[c]++] = a[i];
        }
        for (int i = lo; i <= hi; ++i) {
            a[i] = aux[i - lo];
        }
        if (d == 4) {
            return;
        }
        if (count[0] > 0) {
            sort(a, lo, lo + count[0] - 1, d + 1, aux);
        }
        for (int r = 0; r < 256; ++r) {
            if (count[r + 1] > count[r]) {
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
            }
        }
    }
    
    private static void insertion(final int[] a, final int lo, final int hi, final int d) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && a[j] < a[j - 1]; --j) {
                exch(a, j, j - 1);
            }
        }
    }
    
    private static void exch(final int[] a, final int i, final int j) {
        final int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
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
