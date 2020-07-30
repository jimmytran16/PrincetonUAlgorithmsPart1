// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class DoublingTest
{
    private static final int MAXIMUM_INTEGER = 1000000;
    
    private DoublingTest() {
    }
    
    public static double timeTrial(final int n) {
        final int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = StdRandom.uniform(-1000000, 1000000);
        }
        final Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        return timer.elapsedTime();
    }
    
    public static void main(final String[] args) {
        int n = 250;
        while (true) {
            final double time = timeTrial(n);
            StdOut.printf("%7d %7.1f\n", n, time);
            n += n;
        }
    }
}
