// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class TST<Value>
{
    private int n;
    private Node<Value> root;
    
    public int size() {
        return this.n;
    }
    
    public boolean contains(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return this.get(key) != null;
    }
    
    public Value get(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        final Node<Value> x = this.get(this.root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value)((Node<Object>)x).val;
    }
    
    private Node<Value> get(final Node<Value> x, final String key, final int d) {
        if (x == null) {
            return null;
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        final char c = key.charAt(d);
        if (c < ((Node<Object>)x).c) {
            return (Node<Value>)this.get(((Node<Object>)x).left, key, d);
        }
        if (c > ((Node<Object>)x).c) {
            return (Node<Value>)this.get(((Node<Object>)x).right, key, d);
        }
        if (d < key.length() - 1) {
            return (Node<Value>)this.get(((Node<Object>)x).mid, key, d + 1);
        }
        return x;
    }
    
    public void put(final String key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!this.contains(key)) {
            ++this.n;
        }
        else if (val == null) {
            --this.n;
        }
        this.root = this.put(this.root, key, val, 0);
    }
    
    private Node<Value> put(Node<Value> x, final String key, final Value val, final int d) {
        final char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            ((Node<Object>)x).c = c;
        }
        if (c < ((Node<Object>)x).c) {
            ((Node<Object>)x).left = this.put(((Node<Object>)x).left, key, val, d);
        }
        else if (c > ((Node<Object>)x).c) {
            ((Node<Object>)x).right = this.put(((Node<Object>)x).right, key, val, d);
        }
        else if (d < key.length() - 1) {
            ((Node<Object>)x).mid = this.put(((Node<Object>)x).mid, key, val, d + 1);
        }
        else {
            ((Node<Object>)x).val = val;
        }
        return x;
    }
    
    public String longestPrefixOf(final String query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0) {
            return null;
        }
        int length = 0;
        Node<Value> x = this.root;
        int i = 0;
        while (x != null && i < query.length()) {
            final char c = query.charAt(i);
            if (c < ((Node<Object>)x).c) {
                x = (Node<Value>)((Node<Object>)x).left;
            }
            else if (c > ((Node<Object>)x).c) {
                x = (Node<Value>)((Node<Object>)x).right;
            }
            else {
                ++i;
                if (((Node<Object>)x).val != null) {
                    length = i;
                }
                x = (Node<Value>)((Node<Object>)x).mid;
            }
        }
        return query.substring(0, length);
    }
    
    public Iterable<String> keys() {
        final Queue<String> queue = new Queue<String>();
        this.collect(this.root, new StringBuilder(), queue);
        return queue;
    }
    
    public Iterable<String> keysWithPrefix(final String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        final Queue<String> queue = new Queue<String>();
        final Node<Value> x = this.get(this.root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (((Node<Object>)x).val != null) {
            queue.enqueue(prefix);
        }
        this.collect(((Node<Object>)x).mid, new StringBuilder(prefix), queue);
        return queue;
    }
    
    private void collect(final Node<Value> x, final StringBuilder prefix, final Queue<String> queue) {
        if (x == null) {
            return;
        }
        this.collect(((Node<Object>)x).left, prefix, queue);
        if (((Node<Object>)x).val != null) {
            queue.enqueue(prefix.toString() + ((Node<Object>)x).c);
        }
        this.collect(((Node<Object>)x).mid, prefix.append(((Node<Object>)x).c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        this.collect(((Node<Object>)x).right, prefix, queue);
    }
    
    public Iterable<String> keysThatMatch(final String pattern) {
        final Queue<String> queue = new Queue<String>();
        this.collect(this.root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }
    
    private void collect(final Node<Value> x, final StringBuilder prefix, final int i, final String pattern, final Queue<String> queue) {
        if (x == null) {
            return;
        }
        final char c = pattern.charAt(i);
        if (c == '.' || c < ((Node<Object>)x).c) {
            this.collect(((Node<Object>)x).left, prefix, i, pattern, queue);
        }
        if (c == '.' || c == ((Node<Object>)x).c) {
            if (i == pattern.length() - 1 && ((Node<Object>)x).val != null) {
                queue.enqueue(prefix.toString() + ((Node<Object>)x).c);
            }
            if (i < pattern.length() - 1) {
                this.collect(((Node<Object>)x).mid, prefix.append(((Node<Object>)x).c), i + 1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > ((Node<Object>)x).c) {
            this.collect(((Node<Object>)x).right, prefix, i, pattern, queue);
        }
    }
    
    public static void main(final String[] args) {
        final TST<Integer> st = new TST<Integer>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            st.put(key, i);
            ++i;
        }
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (final String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();
        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
        StdOut.println();
        StdOut.println("keysWithPrefix(\"shor\"):");
        for (final String s : st.keysWithPrefix("shor")) {
            StdOut.println(s);
        }
        StdOut.println();
        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (final String s : st.keysThatMatch(".he.l.")) {
            StdOut.println(s);
        }
    }
    
    private static class Node<Value>
    {
        private char c;
        private Node<Value> left;
        private Node<Value> mid;
        private Node<Value> right;
        private Value val;
    }
}
