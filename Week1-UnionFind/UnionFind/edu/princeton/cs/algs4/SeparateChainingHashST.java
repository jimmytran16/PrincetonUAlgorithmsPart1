// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class SeparateChainingHashST<Key, Value>
{
    private static final int INIT_CAPACITY = 4;
    private int n;
    private int m;
    private SequentialSearchST<Key, Value>[] st;
    
    public SeparateChainingHashST() {
        this(4);
    }
    
    public SeparateChainingHashST(final int m) {
        this.m = m;
        this.st = (SequentialSearchST<Key, Value>[])new SequentialSearchST[m];
        for (int i = 0; i < m; ++i) {
            this.st[i] = new SequentialSearchST<Key, Value>();
        }
    }
    
    private void resize(final int chains) {
        final SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < this.m; ++i) {
            for (final Key key : this.st[i].keys()) {
                temp.put(key, this.st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }
    
    private int hash(final Key key) {
        return (key.hashCode() & Integer.MAX_VALUE) % this.m;
    }
    
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
        final int i = this.hash(key);
        return this.st[i].get(key);
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            this.delete(key);
            return;
        }
        if (this.n >= 10 * this.m) {
            this.resize(2 * this.m);
        }
        final int i = this.hash(key);
        if (!this.st[i].contains(key)) {
            ++this.n;
        }
        this.st[i].put(key, val);
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        final int i = this.hash(key);
        if (this.st[i].contains(key)) {
            --this.n;
        }
        this.st[i].delete(key);
        if (this.m > 4 && this.n <= 2 * this.m) {
            this.resize(this.m / 2);
        }
    }
    
    public Iterable<Key> keys() {
        final Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < this.m; ++i) {
            for (final Key key : this.st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }
    
    public static void main(final String[] args) {
        final SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
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
}
