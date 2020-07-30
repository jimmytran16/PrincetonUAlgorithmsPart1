// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class RandomSeq
{
    private RandomSeq() {
    }
    
    public static void main(final String[] args) {
        final int n = Integer.parseInt(args[0]);
        if (args.length == 1) {
            for (int i = 0; i < n; ++i) {
                final double x = StdRandom.uniform();
                StdOut.println(x);
            }
        }
        else {
            if (args.length != 3) {
                throw new IllegalArgumentException("Invalid number of arguments");
            }
            final double lo = Double.parseDouble(args[1]);
            final double hi = Double.parseDouble(args[2]);
            for (int j = 0; j < n; ++j) {
                final double x2 = StdRandom.uniform(lo, hi);
                StdOut.printf("%.2f\n", x2);
            }
        }
    }
}
