// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class BoyerMoore
{
    private final int R;
    private int[] right;
    private char[] pattern;
    private String pat;
    
    public BoyerMoore(final String pat) {
        this.R = 256;
        this.pat = pat;
        this.right = new int[this.R];
        for (int c = 0; c < this.R; ++c) {
            this.right[c] = -1;
        }
        for (int j = 0; j < pat.length(); ++j) {
            this.right[pat.charAt(j)] = j;
        }
    }
    
    public BoyerMoore(final char[] pattern, final int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; ++j) {
            this.pattern[j] = pattern[j];
        }
        this.right = new int[R];
        for (int c = 0; c < R; ++c) {
            this.right[c] = -1;
        }
        for (int j = 0; j < pattern.length; ++j) {
            this.right[pattern[j]] = j;
        }
    }
    
    public int search(final String txt) {
        final int m = this.pat.length();
        final int n = txt.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; --j) {
                if (this.pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - this.right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return n;
    }
    
    public int search(final char[] text) {
        final int m = this.pattern.length;
        final int n = text.length;
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; --j) {
                if (this.pattern[j] != text[i + j]) {
                    skip = Math.max(1, j - this.right[text[i + j]]);
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return n;
    }
    
    public static void main(final String[] args) {
        final String pat = args[0];
        final String txt = args[1];
        final char[] pattern = pat.toCharArray();
        final char[] text = txt.toCharArray();
        final BoyerMoore boyermoore1 = new BoyerMoore(pat);
        final BoyerMoore boyermoore2 = new BoyerMoore(pattern, 256);
        final int offset1 = boyermoore1.search(txt);
        final int offset2 = boyermoore2.search(text);
        StdOut.println("text:    " + txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset1; ++i) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset2; ++i) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
