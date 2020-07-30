// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class ThreeSum
{
    private ThreeSum() {
    }
    
    public static void printAll(final int[] a) {
        for (int n = a.length, i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    if (a[i] + a[j] + a[k] == 0) {
                        StdOut.println(a[i] + " " + a[j] + " " + a[k]);
                    }
                }
            }
        }
    }
    
    public static int count(final int[] a) {
        final int n = a.length;
        int count = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    if (a[i] + a[j] + a[k] == 0) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int[] a = in.readAllInts();
        final Stopwatch timer = new Stopwatch();
        final int count = count(a);
        StdOut.println("elapsed time = " + timer.elapsedTime());
        StdOut.println(count);
    }
}
