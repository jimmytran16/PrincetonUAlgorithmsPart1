// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key>
{
    private TreeMap<Key, Value> st;
    
    public ST() {
        this.st = new TreeMap<Key, Value>();
    }
    
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null key");
        }
        return this.st.get(key);
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (val == null) {
            this.st.remove(key);
        }
        else {
            this.st.put(key, val);
        }
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with null key");
        }
        this.st.remove(key);
    }
    
    public void remove(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with null key");
        }
        this.st.remove(key);
    }
    
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls contains() with null key");
        }
        return this.st.containsKey(key);
    }
    
    public int size() {
        return this.st.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public Iterable<Key> keys() {
        return this.st.keySet();
    }
    
    @Deprecated
    @Override
    public Iterator<Key> iterator() {
        return this.st.keySet().iterator();
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return this.st.firstKey();
    }
    
    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls max() with empty symbol table");
        }
        return this.st.lastKey();
    }
    
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        final Key k = this.st.ceilingKey(key);
        if (k == null) {
            throw new NoSuchElementException("argument to ceiling() is too large");
        }
        return k;
    }
    
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        final Key k = this.st.floorKey(key);
        if (k == null) {
            throw new NoSuchElementException("argument to floor() is too small");
        }
        return k;
    }
    
    public static void main(final String[] args) {
        final ST<String, Integer> st = new ST<String, Integer>();
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
