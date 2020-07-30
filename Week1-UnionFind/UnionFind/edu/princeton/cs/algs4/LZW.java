// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LZW
{
    private static final int R = 256;
    private static final int L = 4096;
    private static final int W = 12;
    
    private LZW() {
    }
    
    public static void compress() {
        String input = BinaryStdIn.readString();
        final TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < 256; ++i) {
            st.put("" + (char)i, i);
        }
        int code = 257;
        while (input.length() > 0) {
            final String s = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(s), 12);
            final int t = s.length();
            if (t < input.length() && code < 4096) {
                st.put(input.substring(0, t + 1), code++);
            }
            input = input.substring(t);
        }
        BinaryStdOut.write(256, 12);
        BinaryStdOut.close();
    }
    
    public static void expand() {
        final String[] st = new String[4096];
        int i;
        for (i = 0; i < 256; ++i) {
            st[i] = "" + (char)i;
        }
        st[i++] = "";
        int codeword = BinaryStdIn.readInt(12);
        if (codeword == 256) {
            return;
        }
        String val = st[codeword];
        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(12);
            if (codeword == 256) {
                break;
            }
            String s = st[codeword];
            if (i == codeword) {
                s = val + val.charAt(0);
            }
            if (i < 4096) {
                st[i++] = val + s.charAt(0);
            }
            val = s;
        }
        BinaryStdOut.close();
    }
    
    public static void main(final String[] args) {
        if (args[0].equals("-")) {
            compress();
        }
        else {
            if (!args[0].equals("+")) {
                throw new IllegalArgumentException("Illegal command line argument");
            }
            expand();
        }
    }
}
