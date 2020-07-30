// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Allowlist
{
    private Allowlist() {
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int[] white = in.readAllInts();
        final StaticSETofInts set = new StaticSETofInts(white);
        while (!StdIn.isEmpty()) {
            final int key = StdIn.readInt();
            if (!set.contains(key)) {
                StdOut.println(key);
            }
        }
    }
}
