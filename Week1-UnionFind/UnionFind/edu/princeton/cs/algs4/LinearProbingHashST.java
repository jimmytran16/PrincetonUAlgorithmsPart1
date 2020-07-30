// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class LinearProbingHashST<Key, Value>
{
    private static final int INIT_CAPACITY = 4;
    private int n;
    private int m;
    private Key[] keys;
    private Value[] vals;
    
    public LinearProbingHashST() {
        this(4);
    }
    
    public LinearProbingHashST(final int capacity) {
        this.m = capacity;
        this.n = 0;
        this.keys = (Key[])new Object[this.m];
        this.vals = (Value[])new Object[this.m];
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
    
    private int hash(final Key key) {
        return (key.hashCode() & Integer.MAX_VALUE) % this.m;
    }
    
    private void resize(final int capacity) {
        final LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < this.m; ++i) {
            if (this.keys[i] != null) {
                temp.put(this.keys[i], this.vals[i]);
            }
        }
        this.keys = temp.keys;
        this.vals = temp.vals;
        this.m = temp.m;
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            this.delete(key);
            return;
        }
        if (this.n >= this.m / 2) {
            this.resize(2 * this.m);
        }
        int i;
        for (i = this.hash(key); this.keys[i] != null; i = (i + 1) % this.m) {
            if (this.keys[i].equals(key)) {
                this.vals[i] = val;
                return;
            }
        }
        this.keys[i] = key;
        this.vals[i] = val;
        ++this.n;
    }
    
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        for (int i = this.hash(key); this.keys[i] != null; i = (i + 1) % this.m) {
            if (this.keys[i].equals(key)) {
                return this.vals[i];
            }
        }
        return null;
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!this.contains(key)) {
            return;
        }
        int i;
        for (i = this.hash(key); !key.equals(this.keys[i]); i = (i + 1) % this.m) {}
        this.keys[i] = null;
        this.vals[i] = null;
        for (i = (i + 1) % this.m; this.keys[i] != null; i = (i + 1) % this.m) {
            final Key keyToRehash = this.keys[i];
            final Value valToRehash = this.vals[i];
            this.keys[i] = null;
            this.vals[i] = null;
            --this.n;
            this.put(keyToRehash, valToRehash);
        }
        --this.n;
        if (this.n > 0 && this.n <= this.m / 8) {
            this.resize(this.m / 2);
        }
        assert this.check();
    }
    
    public Iterable<Key> keys() {
        final Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < this.m; ++i) {
            if (this.keys[i] != null) {
                queue.enqueue(this.keys[i]);
            }
        }
        return queue;
    }
    
    private boolean check() {
        if (this.m < 2 * this.n) {
            System.err.println("Hash table size m = " + this.m + "; array size n = " + this.n);
            return false;
        }
        for (int i = 0; i < this.m; ++i) {
            if (this.keys[i] != null) {
                if (this.get(this.keys[i]) != this.vals[i]) {
                    System.err.println("get[" + this.keys[i] + "] = " + this.get(this.keys[i]) + "; vals[i] = " + this.vals[i]);
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
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
