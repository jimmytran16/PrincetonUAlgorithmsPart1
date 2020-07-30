// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value>
{
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n;
    
    public BinarySearchST() {
        this(2);
    }
    
    public BinarySearchST(final int capacity) {
        this.n = 0;
        this.keys = (Key[])new Comparable[capacity];
        this.vals = (Value[])new Object[capacity];
    }
    
    private void resize(final int capacity) {
        assert capacity >= this.n;
        final Key[] tempk = (Key[])new Comparable[capacity];
        final Value[] tempv = (Value[])new Object[capacity];
        for (int i = 0; i < this.n; ++i) {
            tempk[i] = this.keys[i];
            tempv[i] = this.vals[i];
        }
        this.vals = tempv;
        this.keys = tempk;
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
        if (this.isEmpty()) {
            return null;
        }
        final int i = this.rank(key);
        if (i < this.n && this.keys[i].compareTo(key) == 0) {
            return this.vals[i];
        }
        return null;
    }
    
    public int rank(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        int lo = 0;
        int hi = this.n - 1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            final int cmp = key.compareTo(this.keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            }
            else {
                if (cmp <= 0) {
                    return mid;
                }
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            this.delete(key);
            return;
        }
        final int i = this.rank(key);
        if (i < this.n && this.keys[i].compareTo(key) == 0) {
            this.vals[i] = val;
            return;
        }
        if (this.n == this.keys.length) {
            this.resize(2 * this.keys.length);
        }
        for (int j = this.n; j > i; --j) {
            this.keys[j] = this.keys[j - 1];
            this.vals[j] = this.vals[j - 1];
        }
        this.keys[i] = key;
        this.vals[i] = val;
        ++this.n;
        assert this.check();
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (this.isEmpty()) {
            return;
        }
        final int i = this.rank(key);
        if (i == this.n || this.keys[i].compareTo(key) != 0) {
            return;
        }
        for (int j = i; j < this.n - 1; ++j) {
            this.keys[j] = this.keys[j + 1];
            this.vals[j] = this.vals[j + 1];
        }
        --this.n;
        this.keys[this.n] = null;
        this.vals[this.n] = null;
        if (this.n > 0 && this.n == this.keys.length / 4) {
            this.resize(this.keys.length / 2);
        }
        assert this.check();
    }
    
    public void deleteMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        this.delete(this.min());
    }
    
    public void deleteMax() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        this.delete(this.max());
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        return this.keys[0];
    }
    
    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        return this.keys[this.n - 1];
    }
    
    public Key select(final int k) {
        if (k < 0 || k >= this.size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return this.keys[k];
    }
    
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        final int i = this.rank(key);
        if (i < this.n && key.compareTo(this.keys[i]) == 0) {
            return this.keys[i];
        }
        if (i == 0) {
            return null;
        }
        return this.keys[i - 1];
    }
    
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        final int i = this.rank(key);
        if (i == this.n) {
            return null;
        }
        return this.keys[i];
    }
    
    public int size(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is null");
        }
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (this.contains(hi)) {
            return this.rank(hi) - this.rank(lo) + 1;
        }
        return this.rank(hi) - this.rank(lo);
    }
    
    public Iterable<Key> keys() {
        return this.keys(this.min(), this.max());
    }
    
    public Iterable<Key> keys(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        final Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        for (int i = this.rank(lo); i < this.rank(hi); ++i) {
            queue.enqueue(this.keys[i]);
        }
        if (this.contains(hi)) {
            queue.enqueue(this.keys[this.rank(hi)]);
        }
        return queue;
    }
    
    private boolean check() {
        return this.isSorted() && this.rankCheck();
    }
    
    private boolean isSorted() {
        for (int i = 1; i < this.size(); ++i) {
            if (this.keys[i].compareTo(this.keys[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }
    
    private boolean rankCheck() {
        for (int i = 0; i < this.size(); ++i) {
            if (i != this.rank(this.select(i))) {
                return false;
            }
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.keys[i].compareTo(this.select(this.rank(this.keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
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
