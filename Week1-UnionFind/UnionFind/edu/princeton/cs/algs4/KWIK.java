// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class KWIK
{
    private KWIK() {
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int context = Integer.parseInt(args[1]);
        final String text = in.readAll().replaceAll("\\s+", " ");
        final int n = text.length();
        final SuffixArray sa = new SuffixArray(text);
        while (StdIn.hasNextLine()) {
            final String query = StdIn.readLine();
            for (int i = sa.rank(query); i < n; ++i) {
                final int from1 = sa.index(i);
                final int to1 = Math.min(n, from1 + query.length());
                if (!query.equals(text.substring(from1, to1))) {
                    break;
                }
                final int from2 = Math.max(0, sa.index(i) - context);
                final int to2 = Math.min(n, sa.index(i) + context + query.length());
                StdOut.println(text.substring(from2, to2));
            }
            StdOut.println();
        }
    }
}
