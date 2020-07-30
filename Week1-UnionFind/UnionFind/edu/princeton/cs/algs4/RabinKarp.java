// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp
{
    private String pat;
    private long patHash;
    private int m;
    private long q;
    private int R;
    private long RM;
    
    public RabinKarp(final char[] pattern, final int R) {
        this.pat = String.valueOf(pattern);
        this.R = R;
        throw new UnsupportedOperationException("Operation not supported yet");
    }
    
    public RabinKarp(final String pat) {
        this.pat = pat;
        this.R = 256;
        this.m = pat.length();
        this.q = longRandomPrime();
        this.RM = 1L;
        for (int i = 1; i <= this.m - 1; ++i) {
            this.RM = this.R * this.RM % this.q;
        }
        this.patHash = this.hash(pat, this.m);
    }
    
    private long hash(final String key, final int m) {
        long h = 0L;
        for (int j = 0; j < m; ++j) {
            h = (this.R * h + key.charAt(j)) % this.q;
        }
        return h;
    }
    
    private boolean check(final String txt, final int i) {
        for (int j = 0; j < this.m; ++j) {
            if (this.pat.charAt(j) != txt.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }
    
    public int search(final String txt) {
        final int n = txt.length();
        if (n < this.m) {
            return n;
        }
        long txtHash = this.hash(txt, this.m);
        if (this.patHash == txtHash && this.check(txt, 0)) {
            return 0;
        }
        for (int i = this.m; i < n; ++i) {
            txtHash = (txtHash + this.q - this.RM * txt.charAt(i - this.m) % this.q) % this.q;
            txtHash = (txtHash * this.R + txt.charAt(i)) % this.q;
            final int offset = i - this.m + 1;
            if (this.patHash == txtHash && this.check(txt, offset)) {
                return offset;
            }
        }
        return n;
    }
    
    private static long longRandomPrime() {
        final BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
    
    public static void main(final String[] args) {
        final String pat = args[0];
        final String txt = args[1];
        final RabinKarp searcher = new RabinKarp(pat);
        final int offset = searcher.search(txt);
        StdOut.println("text:    " + txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; ++i) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
