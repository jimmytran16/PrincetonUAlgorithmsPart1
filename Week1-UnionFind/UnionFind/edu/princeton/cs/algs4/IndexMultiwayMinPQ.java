// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class IndexMultiwayMinPQ<Key> implements Iterable<Integer>
{
    private final int d;
    private int n;
    private int nmax;
    private int[] pq;
    private int[] qp;
    private Key[] keys;
    private final Comparator<Key> comp;
    
    public IndexMultiwayMinPQ(final int N, final int D) {
        if (N < 0) {
            throw new IllegalArgumentException("Maximum number of elements cannot be negative");
        }
        if (D < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = D;
        this.nmax = N;
        this.pq = new int[this.nmax + D];
        this.qp = new int[this.nmax + D];
        this.keys = (Key[])new Comparable[this.nmax + D];
        for (int i = 0; i < this.nmax + D; this.qp[i++] = -1) {}
        this.comp = new MyComparator();
    }
    
    public IndexMultiwayMinPQ(final int N, final Comparator<Key> C, final int D) {
        if (N < 0) {
            throw new IllegalArgumentException("Maximum number of elements cannot be negative");
        }
        if (D < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = D;
        this.nmax = N;
        this.pq = new int[this.nmax + D];
        this.qp = new int[this.nmax + D];
        this.keys = (Key[])new Comparable[this.nmax + D];
        for (int i = 0; i < this.nmax + D; this.qp[i++] = -1) {}
        this.comp = C;
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public boolean contains(final int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        return this.qp[i + this.d] != -1;
    }
    
    public int size() {
        return this.n;
    }
    
    public void insert(final int i, final Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (this.contains(i)) {
            throw new IllegalArgumentException("Index already there");
        }
        this.keys[i + this.d] = key;
        this.pq[this.n + this.d] = i;
        this.qp[i + this.d] = this.n;
        this.swim(this.n++);
    }
    
    public int minIndex() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.pq[this.d];
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.keys[this.pq[this.d] + this.d];
    }
    
    public int delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        final int min = this.pq[this.d];
        this.exch(0, --this.n);
        this.sink(0);
        this.qp[min + this.d] = -1;
        this.keys[this.pq[this.n + this.d] + this.d] = null;
        this.pq[this.n + this.d] = -1;
        return min;
    }
    
    public Key keyOf(final int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        return this.keys[i + this.d];
    }
    
    public void changeKey(final int i, final Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        final Key tmp = this.keys[i + this.d];
        this.keys[i + this.d] = key;
        if (this.comp.compare(key, tmp) <= 0) {
            this.swim(this.qp[i + this.d]);
        }
        else {
            this.sink(this.qp[i + this.d]);
        }
    }
    
    public void decreaseKey(final int i, final Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.comp.compare(this.keys[i + this.d], key) <= 0) {
            throw new IllegalArgumentException("Calling with this argument would not decrease the Key");
        }
        this.keys[i + this.d] = key;
        this.swim(this.qp[i + this.d]);
    }
    
    public void increaseKey(final int i, final Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.comp.compare(this.keys[i + this.d], key) >= 0) {
            throw new IllegalArgumentException("Calling with this argument would not increase the Key");
        }
        this.keys[i + this.d] = key;
        this.sink(this.qp[i + this.d]);
    }
    
    public void delete(final int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        final int idx = this.qp[i + this.d];
        this.exch(idx, --this.n);
        this.swim(idx);
        this.sink(idx);
        this.keys[i + this.d] = null;
        this.qp[i + this.d] = -1;
    }
    
    private boolean greater(final int i, final int j) {
        return this.comp.compare(this.keys[this.pq[i + this.d] + this.d], this.keys[this.pq[j + this.d] + this.d]) > 0;
    }
    
    private void exch(final int x, final int y) {
        final int i = x + this.d;
        final int j = y + this.d;
        final int swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
        this.qp[this.pq[i] + this.d] = x;
        this.qp[this.pq[j] + this.d] = y;
    }
    
    private void swim(final int i) {
        if (i > 0 && this.greater((i - 1) / this.d, i)) {
            this.exch(i, (i - 1) / this.d);
            this.swim((i - 1) / this.d);
        }
    }
    
    private void sink(int i) {
        if (this.d * i + 1 >= this.n) {
            return;
        }
        for (int min = this.minChild(i); min < this.n && this.greater(i, min); i = min, min = this.minChild(i)) {
            this.exch(i, min);
        }
    }
    
    private int minChild(final int i) {
        final int loBound = this.d * i + 1;
        final int hiBound = this.d * i + this.d;
        int min = loBound;
        for (int cur = loBound; cur <= hiBound; ++cur) {
            if (cur < this.n && this.greater(min, cur)) {
                min = cur;
            }
        }
        return min;
    }
    
    @Override
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<Integer>
    {
        IndexMultiwayMinPQ<Key> clone;
        
        public MyIterator() {
            this.clone = new IndexMultiwayMinPQ<Key>(IndexMultiwayMinPQ.this.nmax, IndexMultiwayMinPQ.this.comp, IndexMultiwayMinPQ.this.d);
            for (int i = 0; i < IndexMultiwayMinPQ.this.n; ++i) {
                this.clone.insert(IndexMultiwayMinPQ.this.pq[i + IndexMultiwayMinPQ.this.d], IndexMultiwayMinPQ.this.keys[IndexMultiwayMinPQ.this.pq[i + IndexMultiwayMinPQ.this.d] + IndexMultiwayMinPQ.this.d]);
            }
        }
        
        @Override
        public boolean hasNext() {
            return !this.clone.isEmpty();
        }
        
        @Override
        public Integer next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.clone.delMin();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private class MyComparator implements Comparator<Key>
    {
        @Override
        public int compare(final Key key1, final Key key2) {
            return ((Comparable)key1).compareTo(key2);
        }
    }
}
