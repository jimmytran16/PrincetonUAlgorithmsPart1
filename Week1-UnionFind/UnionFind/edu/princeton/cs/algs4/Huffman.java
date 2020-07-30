// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Huffman
{
    private static final int R = 256;
    
    private Huffman() {
    }
    
    public static void compress() {
        final String s = BinaryStdIn.readString();
        final char[] input = s.toCharArray();
        final int[] freq = new int[256];
        for (int i = 0; i < input.length; ++i) {
            final int[] array = freq;
            final char c = input[i];
            ++array[c];
        }
        final Node root = buildTrie(freq);
        final String[] st = new String[256];
        buildCode(st, root, "");
        writeTrie(root);
        BinaryStdOut.write(input.length);
        for (int j = 0; j < input.length; ++j) {
            final String code = st[input[j]];
            for (int k = 0; k < code.length(); ++k) {
                if (code.charAt(k) == '0') {
                    BinaryStdOut.write(false);
                }
                else {
                    if (code.charAt(k) != '1') {
                        throw new IllegalStateException("Illegal state");
                    }
                    BinaryStdOut.write(true);
                }
            }
        }
        BinaryStdOut.close();
    }
    
    private static Node buildTrie(final int[] freq) {
        final MinPQ<Node> pq = new MinPQ<Node>();
        for (char c = '\0'; c < '\u0100'; ++c) {
            if (freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }
        while (pq.size() > 1) {
            final Node left = pq.delMin();
            final Node right = pq.delMin();
            final Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }
    
    private static void writeTrie(final Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }
    
    private static void buildCode(final String[] st, final Node x, final String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
        }
    }
    
    public static void expand() {
        final Node root = readTrie();
        for (int length = BinaryStdIn.readInt(), i = 0; i < length; ++i) {
            Node x = root;
            while (!x.isLeaf()) {
                final boolean bit = BinaryStdIn.readBoolean();
                if (bit) {
                    x = x.right;
                }
                else {
                    x = x.left;
                }
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }
    
    private static Node readTrie() {
        final boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        }
        return new Node('\0', -1, readTrie(), readTrie());
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
    
    private static class Node implements Comparable<Node>
    {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;
        
        Node(final char ch, final int freq, final Node left, final Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
        private boolean isLeaf() {
            assert this.left != null && this.right != null;
            return this.left == null && this.right == null;
        }
        
        @Override
        public int compareTo(final Node that) {
            return this.freq - that.freq;
        }
    }
}
