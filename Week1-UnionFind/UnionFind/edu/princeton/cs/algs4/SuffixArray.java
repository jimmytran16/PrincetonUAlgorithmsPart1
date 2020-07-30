// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;

public class SuffixArray
{
    private Suffix[] suffixes;
    
    public SuffixArray(final String text) {
        final int n = text.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; ++i) {
            this.suffixes[i] = new Suffix(text, i);
        }
        Arrays.sort(this.suffixes);
    }
    
    public int length() {
        return this.suffixes.length;
    }
    
    public int index(final int i) {
        if (i < 0 || i >= this.suffixes.length) {
            throw new IllegalArgumentException();
        }
        return this.suffixes[i].index;
    }
    
    public int lcp(final int i) {
        if (i < 1 || i >= this.suffixes.length) {
            throw new IllegalArgumentException();
        }
        return lcpSuffix(this.suffixes[i], this.suffixes[i - 1]);
    }
    
    private static int lcpSuffix(final Suffix s, final Suffix t) {
        final int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) != t.charAt(i)) {
                return i;
            }
        }
        return n;
    }
    
    public String select(final int i) {
        if (i < 0 || i >= this.suffixes.length) {
            throw new IllegalArgumentException();
        }
        return this.suffixes[i].toString();
    }
    
    public int rank(final String query) {
        int lo = 0;
        int hi = this.suffixes.length - 1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            final int cmp = compare(query, this.suffixes[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            }
            else {
                if (cmp <= 0) {
                    return mid;
                }
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private static int compare(final String query, final Suffix suffix) {
        for (int n = Math.min(query.length(), suffix.length()), i = 0; i < n; ++i) {
            if (query.charAt(i) < suffix.charAt(i)) {
                return -1;
            }
            if (query.charAt(i) > suffix.charAt(i)) {
                return 1;
            }
        }
        return query.length() - suffix.length();
    }
    
    public static void main(final String[] args) {
        final String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
        final SuffixArray suffix = new SuffixArray(s);
        StdOut.println("  i ind lcp rnk select");
        StdOut.println("---------------------------");
        for (int i = 0; i < s.length(); ++i) {
            final int index = suffix.index(i);
            final String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            assert s.substring(index).equals(suffix.select(i));
            final int rank = suffix.rank(s.substring(index));
            if (i == 0) {
                StdOut.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
            }
            else {
                final int lcp = suffix.lcp(i);
                StdOut.printf("%3d %3d %3d %3d %s\n", i, index, lcp, rank, ith);
            }
        }
    }
    
    private static class Suffix implements Comparable<Suffix>
    {
        private final String text;
        private final int index;
        
        private Suffix(final String text, final int index) {
            this.text = text;
            this.index = index;
        }
        
        private int length() {
            return this.text.length() - this.index;
        }
        
        private char charAt(final int i) {
            return this.text.charAt(this.index + i);
        }
        
        @Override
        public int compareTo(final Suffix that) {
            if (this == that) {
                return 0;
            }
            for (int n = Math.min(this.length(), that.length()), i = 0; i < n; ++i) {
                if (this.charAt(i) < that.charAt(i)) {
                    return -1;
                }
                if (this.charAt(i) > that.charAt(i)) {
                    return 1;
                }
            }
            return this.length() - that.length();
        }
        
        @Override
        public String toString() {
            return this.text.substring(this.index);
        }
    }
}
