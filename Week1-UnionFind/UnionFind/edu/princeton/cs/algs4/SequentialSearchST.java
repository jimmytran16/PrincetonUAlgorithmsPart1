// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class SequentialSearchST<Key, Value>
{
    private int n;
    private Node first;
    
    public int size() {
        return this.n;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return this.get(key) != null;
    }
    
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        for (Node x = this.first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            this.delete(key);
            return;
        }
        for (Node x = this.first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        this.first = new Node(key, val, this.first);
        ++this.n;
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        this.first = this.delete(this.first, key);
    }
    
    private Node delete(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        if (key.equals(x.key)) {
            --this.n;
            return x.next;
        }
        x.next = this.delete(x.next, key);
        return x;
    }
    
    public Iterable<Key> keys() {
        final Queue<Key> queue = new Queue<Key>();
        for (Node x = this.first; x != null; x = x.next) {
            queue.enqueue(x.key);
        }
        return queue;
    }
    
    public static void main(final String[] args) {
        final SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            st.put(key, i);
            ++i;
        }
        for (final String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
    
    private class Node
    {
        private Key key;
        private Value val;
        private Node next;
        
        public Node(final Key key, final Value val, final Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
}
