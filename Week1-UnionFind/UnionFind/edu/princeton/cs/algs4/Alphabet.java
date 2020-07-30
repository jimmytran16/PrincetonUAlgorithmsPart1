// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Alphabet
{
    public static final Alphabet BINARY;
    public static final Alphabet OCTAL;
    public static final Alphabet DECIMAL;
    public static final Alphabet HEXADECIMAL;
    public static final Alphabet DNA;
    public static final Alphabet LOWERCASE;
    public static final Alphabet UPPERCASE;
    public static final Alphabet PROTEIN;
    public static final Alphabet BASE64;
    public static final Alphabet ASCII;
    public static final Alphabet EXTENDED_ASCII;
    public static final Alphabet UNICODE16;
    private char[] alphabet;
    private int[] inverse;
    private final int R;
    
    public Alphabet(final String alpha) {
        final boolean[] unicode = new boolean[65535];
        for (int i = 0; i < alpha.length(); ++i) {
            final char c = alpha.charAt(i);
            if (unicode[c]) {
                throw new IllegalArgumentException("Illegal alphabet: repeated character = '" + c + "'");
            }
            unicode[c] = true;
        }
        this.alphabet = alpha.toCharArray();
        this.R = alpha.length();
        this.inverse = new int[65535];
        for (int i = 0; i < this.inverse.length; ++i) {
            this.inverse[i] = -1;
        }
        for (int c2 = 0; c2 < this.R; ++c2) {
            this.inverse[this.alphabet[c2]] = c2;
        }
    }
    
    private Alphabet(final int radix) {
        this.R = radix;
        this.alphabet = new char[this.R];
        this.inverse = new int[this.R];
        for (int i = 0; i < this.R; ++i) {
            this.alphabet[i] = (char)i;
        }
        for (int i = 0; i < this.R; ++i) {
            this.inverse[i] = i;
        }
    }
    
    public Alphabet() {
        this(256);
    }
    
    public boolean contains(final char c) {
        return this.inverse[c] != -1;
    }
    
    @Deprecated
    public int R() {
        return this.R;
    }
    
    public int radix() {
        return this.R;
    }
    
    public int lgR() {
        int lgR = 0;
        for (int t = this.R - 1; t >= 1; t /= 2) {
            ++lgR;
        }
        return lgR;
    }
    
    public int toIndex(final char c) {
        if (c >= this.inverse.length || this.inverse[c] == -1) {
            throw new IllegalArgumentException("Character " + c + " not in alphabet");
        }
        return this.inverse[c];
    }
    
    public int[] toIndices(final String s) {
        final char[] source = s.toCharArray();
        final int[] target = new int[s.length()];
        for (int i = 0; i < source.length; ++i) {
            target[i] = this.toIndex(source[i]);
        }
        return target;
    }
    
    public char toChar(final int index) {
        if (index < 0 || index >= this.R) {
            throw new IllegalArgumentException("index must be between 0 and " + this.R + ": " + index);
        }
        return this.alphabet[index];
    }
    
    public String toChars(final int[] indices) {
        final StringBuilder s = new StringBuilder(indices.length);
        for (int i = 0; i < indices.length; ++i) {
            s.append(this.toChar(indices[i]));
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final int[] encoded1 = Alphabet.BASE64.toIndices("NowIsTheTimeForAllGoodMen");
        final String decoded1 = Alphabet.BASE64.toChars(encoded1);
        StdOut.println(decoded1);
        final int[] encoded2 = Alphabet.DNA.toIndices("AACGAACGGTTTACCCCG");
        final String decoded2 = Alphabet.DNA.toChars(encoded2);
        StdOut.println(decoded2);
        final int[] encoded3 = Alphabet.DECIMAL.toIndices("01234567890123456789");
        final String decoded3 = Alphabet.DECIMAL.toChars(encoded3);
        StdOut.println(decoded3);
    }
    
    static {
        BINARY = new Alphabet("01");
        OCTAL = new Alphabet("01234567");
        DECIMAL = new Alphabet("0123456789");
        HEXADECIMAL = new Alphabet("0123456789ABCDEF");
        DNA = new Alphabet("ACGT");
        LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
        UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
        BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
        ASCII = new Alphabet(128);
        EXTENDED_ASCII = new Alphabet(256);
        UNICODE16 = new Alphabet(65536);
    }
}
