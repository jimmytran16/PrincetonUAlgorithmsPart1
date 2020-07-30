// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LSD
{
    private static final int BITS_PER_BYTE = 8;
    
    private LSD() {
    }
    
    public static void sort(final String[] a, final int w) {
        final int n = a.length;
        final int R = 256;
        final String[] aux = new String[n];
        for (int d = w - 1; d >= 0; --d) {
            final int[] count = new int[R + 1];
            for (int i = 0; i < n; ++i) {
                final int[] array = count;
                final int n2 = a[i].charAt(d) + '\u0001';
                ++array[n2];
            }
            for (int r = 0; r < R; ++r) {
                final int[] array2 = count;
                final int n3 = r + 1;
                array2[n3] += count[r];
            }
            for (int i = 0; i < n; ++i) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < n; ++i) {
                a[i] = aux[i];
            }
        }
    }
    
    public static void sort(final int[] a) {
        final int BITS = 32;
        final int R = 256;
        final int MASK = 255;
        final int w = 4;
        final int n = a.length;
        final int[] aux = new int[n];
        for (int d = 0; d < 4; ++d) {
            final int[] count = new int[257];
            for (int i = 0; i < n; ++i) {
                final int c = a[i] >> 8 * d & 0xFF;
                final int[] array = count;
                final int n2 = c + 1;
                ++array[n2];
            }
            for (int r = 0; r < 256; ++r) {
                final int[] array2 = count;
                final int n3 = r + 1;
                array2[n3] += count[r];
            }
            if (d == 3) {
                final int shift1 = count[256] - count[128];
                final int shift2 = count[128];
                for (int r2 = 0; r2 < 128; ++r2) {
                    final int[] array3 = count;
                    final int n4 = r2;
                    array3[n4] += shift1;
                }
                for (int r2 = 128; r2 < 256; ++r2) {
                    final int[] array4 = count;
                    final int n5 = r2;
                    array4[n5] -= shift2;
                }
            }
            for (int i = 0; i < n; ++i) {
                final int c = a[i] >> 8 * d & 0xFF;
                aux[count[c]++] = a[i];
            }
            for (int i = 0; i < n; ++i) {
                a[i] = aux[i];
            }
        }
    }
    
    public static void main(final String[] args) {
        final String[] a = StdIn.readAllStrings();
        final int n = a.length;
        final int w = a[0].length();
        for (int i = 0; i < n; ++i) {
            assert a[i].length() == w : "Strings must have fixed length";
        }
        sort(a, w);
        for (int i = 0; i < n; ++i) {
            StdOut.println(a[i]);
        }
    }
}
