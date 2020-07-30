// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Inversions
{
    private Inversions() {
    }
    
    private static long merge(final int[] a, final int[] aux, final int lo, final int mid, final int hi) {
        long inversions = 0L;
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
            else if (aux[j] < aux[i]) {
                a[l] = aux[j++];
                inversions += mid - i + 1;
            }
            else {
                a[l] = aux[i++];
            }
        }
        return inversions;
    }
    
    private static long count(final int[] a, final int[] b, final int[] aux, final int lo, final int hi) {
        long inversions = 0L;
        if (hi <= lo) {
            return 0L;
        }
        final int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, aux, lo, mid);
        inversions += count(a, b, aux, mid + 1, hi);
        inversions += merge(b, aux, lo, mid, hi);
        assert inversions == brute(a, lo, hi);
        return inversions;
    }
    
    public static long count(final int[] a) {
        final int[] b = new int[a.length];
        final int[] aux = new int[a.length];
        for (int i = 0; i < a.length; ++i) {
            b[i] = a[i];
        }
        final long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }
    
    private static <Key extends Comparable<Key>> long merge(final Key[] a, final Key[] aux, final int lo, final int mid, final int hi) {
        long inversions = 0L;
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
                inversions += mid - i + 1;
            }
            else {
                a[l] = aux[i++];
            }
        }
        return inversions;
    }
    
    private static <Key extends Comparable<Key>> long count(final Key[] a, final Key[] b, final Key[] aux, final int lo, final int hi) {
        long inversions = 0L;
        if (hi <= lo) {
            return 0L;
        }
        final int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, (Comparable[])aux, lo, mid);
        inversions += count(a, b, (Comparable[])aux, mid + 1, hi);
        inversions += merge(b, aux, lo, mid, hi);
        assert inversions == brute(a, lo, hi);
        return inversions;
    }
    
    public static <Key extends Comparable<Key>> long count(final Key[] a) {
        final Key[] b = a.clone();
        final Key[] aux = a.clone();
        final long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }
    
    private static <Key extends Comparable<Key>> boolean less(final Key v, final Key w) {
        return v.compareTo(w) < 0;
    }
    
    private static <Key extends Comparable<Key>> long brute(final Key[] a, final int lo, final int hi) {
        long inversions = 0L;
        for (int i = lo; i <= hi; ++i) {
            for (int j = i + 1; j <= hi; ++j) {
                if (less(a[j], a[i])) {
                    ++inversions;
                }
            }
        }
        return inversions;
    }
    
    private static long brute(final int[] a, final int lo, final int hi) {
        long inversions = 0L;
        for (int i = lo; i <= hi; ++i) {
            for (int j = i + 1; j <= hi; ++j) {
                if (a[j] < a[i]) {
                    ++inversions;
                }
            }
        }
        return inversions;
    }
    
    public static void main(final String[] args) {
        final int[] a = StdIn.readAllInts();
        final int n = a.length;
        final Integer[] b = new Integer[n];
        for (int i = 0; i < n; ++i) {
            b[i] = a[i];
        }
        StdOut.println(count(a));
        StdOut.println(count(b));
    }
}
