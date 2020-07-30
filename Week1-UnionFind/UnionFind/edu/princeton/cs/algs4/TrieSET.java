// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class TrieSET implements Iterable<String>
{
    private static final int R = 256;
    private Node root;
    private int n;
    
    public boolean contains(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        final Node x = this.get(this.root, key, 0);
        return x != null && x.isString;
    }
    
    private Node get(final Node x, final String key, final int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        final char c = key.charAt(d);
        return this.get(x.next[c], key, d + 1);
    }
    
    public void add(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to add() is null");
        }
        this.root = this.add(this.root, key, 0);
    }
    
    private Node add(Node x, final String key, final int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            if (!x.isString) {
                ++this.n;
            }
            x.isString = true;
        }
        else {
            final char c = key.charAt(d);
            x.next[c] = this.add(x.next[c], key, d + 1);
        }
        return x;
    }
    
    public int size() {
        return this.n;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public Iterator<String> iterator() {
        return this.keysWithPrefix("").iterator();
    }
    
    public Iterable<String> keysWithPrefix(final String prefix) {
        final Queue<String> results = new Queue<String>();
        final Node x = this.get(this.root, prefix, 0);
        this.collect(x, new StringBuilder(prefix), results);
        return results;
    }
    
    private void collect(final Node x, final StringBuilder prefix, final Queue<String> results) {
        if (x == null) {
            return;
        }
        if (x.isString) {
            results.enqueue(prefix.toString());
        }
        for (char c = '\0'; c < '\u0100'; ++c) {
            prefix.append(c);
            this.collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    public Iterable<String> keysThatMatch(final String pattern) {
        final Queue<String> results = new Queue<String>();
        final StringBuilder prefix = new StringBuilder();
        this.collect(this.root, prefix, pattern, results);
        return results;
    }
    
    private void collect(final Node x, final StringBuilder prefix, final String pattern, final Queue<String> results) {
        if (x == null) {
            return;
        }
        final int d = prefix.length();
        if (d == pattern.length() && x.isString) {
            results.enqueue(prefix.toString());
        }
        if (d == pattern.length()) {
            return;
        }
        final char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = '\0'; ch < '\u0100'; ++ch) {
                prefix.append(ch);
                this.collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            this.collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    public String longestPrefixOf(final String query) {
        if (query == null) {
            throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        }
        final int length = this.longestPrefixOf(this.root, query, 0, -1);
        if (length == -1) {
            return null;
        }
        return query.substring(0, length);
    }
    
    private int longestPrefixOf(final Node x, final String query, final int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.isString) {
            length = d;
        }
        if (d == query.length()) {
            return length;
        }
        final char c = query.charAt(d);
        return this.longestPrefixOf(x.next[c], query, d + 1, length);
    }
    
    public void delete(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        this.root = this.delete(this.root, key, 0);
    }
    
    private Node delete(final Node x, final String key, final int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            if (x.isString) {
                --this.n;
            }
            x.isString = false;
        }
        else {
            final char c = key.charAt(d);
            x.next[c] = this.delete(x.next[c], key, d + 1);
        }
        if (x.isString) {
            return x;
        }
        for (int c2 = 0; c2 < 256; ++c2) {
            if (x.next[c2] != null) {
                return x;
            }
        }
        return null;
    }
    
    public static void main(final String[] args) {
        final TrieSET set = new TrieSET();
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            set.add(key);
        }
        if (set.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (final String key2 : set) {
                StdOut.println(key2);
            }
            StdOut.println();
        }
        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(set.longestPrefixOf("shellsort"));
        StdOut.println();
        StdOut.println("longestPrefixOf(\"xshellsort\"):");
        StdOut.println(set.longestPrefixOf("xshellsort"));
        StdOut.println();
        StdOut.println("keysWithPrefix(\"shor\"):");
        for (final String s : set.keysWithPrefix("shor")) {
            StdOut.println(s);
        }
        StdOut.println();
        StdOut.println("keysWithPrefix(\"shortening\"):");
        for (final String s : set.keysWithPrefix("shortening")) {
            StdOut.println(s);
        }
        StdOut.println();
        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (final String s : set.keysThatMatch(".he.l.")) {
            StdOut.println(s);
        }
    }
    
    private static class Node
    {
        private Node[] next;
        private boolean isString;
        
        private Node() {
            this.next = new Node[256];
        }
    }
}
