// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LongestRepeatedSubstring
{
    private LongestRepeatedSubstring() {
    }
    
    public static String lrs(final String text) {
        final int n = text.length();
        final SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < n; ++i) {
            final int length = sa.lcp(i);
            if (length > lrs.length()) {
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        return lrs;
    }
    
    public static void main(final String[] args) {
        final String text = StdIn.readAll().replaceAll("\\s+", " ");
        StdOut.println("'" + lrs(text) + "'");
    }
}
