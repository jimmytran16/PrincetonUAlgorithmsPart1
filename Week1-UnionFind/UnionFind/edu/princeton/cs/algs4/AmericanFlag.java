// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class AmericanFlag
{
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32;
    private static final int R = 256;
    private static final int CUTOFF = 15;
    
    private AmericanFlag() {
    }
    
    private static int charAt(final String s, final int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }
    
    public static void sort(final String[] a) {
        sort(a, 0, a.length - 1);
    }
    
    public static void sort(final String[] a, int lo, int hi) {
        final Stack<Integer> st = new Stack<Integer>();
        final int[] first = new int[258];
        final int[] next = new int[258];
        int d = 0;
        st.push(lo);
        st.push(hi);
        st.push(d);
        while (!st.isEmpty()) {
            d = st.pop();
            hi = st.pop();
            lo = st.pop();
            if (hi <= lo + 15) {
                insertion(a, lo, hi, d);
            }
            else {
                for (int i = lo; i <= hi; ++i) {
                    final int c = charAt(a[i], d) + 1;
                    final int[] array = first;
                    final int n = c + 1;
                    ++array[n];
                }
                first[0] = lo;
                for (int c2 = 0; c2 <= 256; ++c2) {
                    final int[] array2 = first;
                    final int n2 = c2 + 1;
                    array2[n2] += first[c2];
                    if (c2 > 0 && first[c2 + 1] - 1 > first[c2]) {
                        st.push(first[c2]);
                        st.push(first[c2 + 1] - 1);
                        st.push(d + 1);
                    }
                }
                for (int c2 = 0; c2 < 258; ++c2) {
                    next[c2] = first[c2];
                }
                for (int k = lo; k <= hi; ++k) {
                    int c;
                    for (c = charAt(a[k], d) + 1; first[c] > k; c = charAt(a[k], d) + 1) {
                        exch(a, k, next[c]++);
                    }
                    final int[] array3 = next;
                    final int n3 = c;
                    ++array3[n3];
                }
                for (int c2 = 0; c2 < 258; ++c2) {
                    next[c2] = (first[c2] = 0);
                }
            }
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
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(final int[] a, int lo, int hi) {
        final Stack<Integer> st = new Stack<Integer>();
        final int[] first = new int[257];
        final int[] next = new int[257];
        final int mask = 255;
        int d = 0;
        st.push(lo);
        st.push(hi);
        st.push(d);
        while (!st.isEmpty()) {
            d = st.pop();
            hi = st.pop();
            lo = st.pop();
            if (hi <= lo + 15) {
                insertion(a, lo, hi, d);
            }
            else {
                final int shift = 32 - 8 * d - 8;
                for (int i = lo; i <= hi; ++i) {
                    final int c = a[i] >> shift & mask;
                    final int[] array = first;
                    final int n = c + 1;
                    ++array[n];
                }
                first[0] = lo;
                for (int c2 = 0; c2 < 256; ++c2) {
                    final int[] array2 = first;
                    final int n2 = c2 + 1;
                    array2[n2] += first[c2];
                    if (d < 3 && first[c2 + 1] - 1 > first[c2]) {
                        st.push(first[c2]);
                        st.push(first[c2 + 1] - 1);
                        st.push(d + 1);
                    }
                }
                for (int c2 = 0; c2 < 257; ++c2) {
                    next[c2] = first[c2];
                }
                for (int k = lo; k <= hi; ++k) {
                    int c;
                    for (c = (a[k] >> shift & mask); first[c] > k; c = (a[k] >> shift & mask)) {
                        exch(a, k, next[c]++);
                    }
                    final int[] array3 = next;
                    final int n3 = c;
                    ++array3[n3];
                }
                for (int c2 = 0; c2 < 257; ++c2) {
                    next[c2] = (first[c2] = 0);
                }
            }
        }
    }
    
    private static void insertion(final int[] a, final int lo, final int hi, final int d) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); --j) {
                exch(a, j, j - 1);
            }
        }
    }
    
    private static void exch(final int[] a, final int i, final int j) {
        final int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private static boolean less(final int v, final int w, final int d) {
        final int mask = 255;
        for (int i = d; i < 4; ++i) {
            final int shift = 32 - 8 * i - 8;
            final int a = v >> shift & mask;
            final int b = w >> shift & mask;
            if (a < b) {
                return true;
            }
            if (a > b) {
                return false;
            }
        }
        return false;
    }
    
    public static void main(final String[] args) {
        if (args.length > 0 && args[0].equals("int")) {
            final int[] a = StdIn.readAllInts();
            sort(a);
            for (int i = 0; i < a.length; ++i) {
                StdOut.println(a[i]);
            }
        }
        else {
            final String[] a2 = StdIn.readAllStrings();
            sort(a2);
            for (int i = 0; i < a2.length; ++i) {
                StdOut.println(a2[i]);
            }
        }
    }
}
