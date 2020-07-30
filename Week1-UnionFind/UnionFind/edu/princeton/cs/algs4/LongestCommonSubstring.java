// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LongestCommonSubstring
{
    private LongestCommonSubstring() {
    }
    
    private static String lcp(final String s, final int p, final String t, final int q) {
        final int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; ++i) {
            if (s.charAt(p + i) != t.charAt(q + i)) {
                return s.substring(p, p + i);
            }
        }
        return s.substring(p, p + n);
    }
    
    private static int compare(final String s, final int p, final String t, final int q) {
        for (int n = Math.min(s.length() - p, t.length() - q), i = 0; i < n; ++i) {
            if (s.charAt(p + i) != t.charAt(q + i)) {
                return s.charAt(p + i) - t.charAt(q + i);
            }
        }
        if (s.length() - p < t.length() - q) {
            return -1;
        }
        if (s.length() - p > t.length() - q) {
            return 1;
        }
        return 0;
    }
    
    public static String lcs(final String s, final String t) {
        final SuffixArray suffix1 = new SuffixArray(s);
        final SuffixArray suffix2 = new SuffixArray(t);
        String lcs = "";
        int i = 0;
        int j = 0;
        while (i < s.length() && j < t.length()) {
            final int p = suffix1.index(i);
            final int q = suffix2.index(j);
            final String x = lcp(s, p, t, q);
            if (x.length() > lcs.length()) {
                lcs = x;
            }
            if (compare(s, p, t, q) < 0) {
                ++i;
            }
            else {
                ++j;
            }
        }
        return lcs;
    }
    
    public static void main(final String[] args) {
        final In in1 = new In(args[0]);
        final In in2 = new In(args[1]);
        final String s = in1.readAll().trim().replaceAll("\\s+", " ");
        final String t = in2.readAll().trim().replaceAll("\\s+", " ");
        StdOut.println("'" + lcs(s, t) + "'");
    }
}
