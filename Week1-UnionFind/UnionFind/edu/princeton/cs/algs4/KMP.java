// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class KMP
{
    private final int R;
    private final int m;
    private int[][] dfa;
    
    public KMP(final String pat) {
        this.R = 256;
        this.m = pat.length();
        this.dfa = new int[this.R][this.m];
        this.dfa[pat.charAt(0)][0] = 1;
        int x = 0;
        for (int j = 1; j < this.m; ++j) {
            for (int c = 0; c < this.R; ++c) {
                this.dfa[c][j] = this.dfa[c][x];
            }
            this.dfa[pat.charAt(j)][j] = j + 1;
            x = this.dfa[pat.charAt(j)][x];
        }
    }
    
    public KMP(final char[] pattern, final int R) {
        this.R = R;
        this.m = pattern.length;
        final int m = pattern.length;
        this.dfa = new int[R][m];
        this.dfa[pattern[0]][0] = 1;
        int x = 0;
        for (int j = 1; j < m; ++j) {
            for (int c = 0; c < R; ++c) {
                this.dfa[c][j] = this.dfa[c][x];
            }
            this.dfa[pattern[j]][j] = j + 1;
            x = this.dfa[pattern[j]][x];
        }
    }
    
    public int search(final String txt) {
        int n;
        int i;
        int j;
        for (n = txt.length(), i = 0, j = 0; i < n && j < this.m; j = this.dfa[txt.charAt(i)][j], ++i) {}
        if (j == this.m) {
            return i - this.m;
        }
        return n;
    }
    
    public int search(final char[] text) {
        int n;
        int i;
        int j;
        for (n = text.length, i = 0, j = 0; i < n && j < this.m; j = this.dfa[text[i]][j], ++i) {}
        if (j == this.m) {
            return i - this.m;
        }
        return n;
    }
    
    public static void main(final String[] args) {
        final String pat = args[0];
        final String txt = args[1];
        final char[] pattern = pat.toCharArray();
        final char[] text = txt.toCharArray();
        final KMP kmp1 = new KMP(pat);
        final int offset1 = kmp1.search(txt);
        final KMP kmp2 = new KMP(pattern, 256);
        final int offset2 = kmp2.search(text);
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
