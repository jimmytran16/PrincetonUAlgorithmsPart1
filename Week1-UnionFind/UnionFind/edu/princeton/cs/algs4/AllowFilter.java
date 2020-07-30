// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class AllowFilter
{
    private AllowFilter() {
    }
    
    public static void main(final String[] args) {
        final SET<String> set = new SET<String>();
        final In in = new In(args[0]);
        while (!in.isEmpty()) {
            final String word = in.readString();
            set.add(word);
        }
        while (!StdIn.isEmpty()) {
            final String word = StdIn.readString();
            if (set.contains(word)) {
                StdOut.println(word);
            }
        }
    }
}
