// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>
{
    private int maxN;
    private int n;
    private int[] pq;
    private int[] qp;
    private Key[] keys;
    
    public IndexMinPQ(final int maxN) {
        if (maxN < 0) {
            throw new IllegalArgumentException();
        }
        this.maxN = maxN;
        this.n = 0;
        this.keys = (Key[])new Comparable[maxN + 1];
        this.pq = new int[maxN + 1];
        this.qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; ++i) {
            this.qp[i] = -1;
        }
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public boolean contains(final int i) {
        this.validateIndex(i);
        return this.qp[i] != -1;
    }
    
    public int size() {
        return this.n;
    }
    
    public void insert(final int i, final Key key) {
        this.validateIndex(i);
        if (this.contains(i)) {
            throw new IllegalArgumentException("index is already in the priority queue");
        }
        ++this.n;
        this.qp[i] = this.n;
        this.pq[this.n] = i;
        this.keys[i] = key;
        this.swim(this.n);
    }
    
    public int minIndex() {
        if (this.n == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.pq[1];
    }
    
    public Key minKey() {
        if (this.n == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.keys[this.pq[1]];
    }
    
    public int delMin() {
        if (this.n == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        final int min = this.pq[1];
        this.exch(1, this.n--);
        this.sink(1);
        assert min == this.pq[this.n + 1];
        this.qp[min] = -1;
        this.keys[min] = null;
        this.pq[this.n + 1] = -1;
        return min;
    }
    
    public Key keyOf(final int i) {
        this.validateIndex(i);
        if (!this.contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        return this.keys[i];
    }
    
    public void changeKey(final int i, final Key key) {
        this.validateIndex(i);
        if (!this.contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        this.keys[i] = key;
        this.swim(this.qp[i]);
        this.sink(this.qp[i]);
    }
    
    @Deprecated
    public void change(final int i, final Key key) {
        this.changeKey(i, key);
    }
    
    public void decreaseKey(final int i, final Key key) {
        this.validateIndex(i);
        if (!this.contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        if (this.keys[i].compareTo(key) == 0) {
            throw new IllegalArgumentException("Calling decreaseKey() with a key equal to the key in the priority queue");
        }
        if (this.keys[i].compareTo(key) < 0) {
            throw new IllegalArgumentException("Calling decreaseKey() with a key strictly greater than the key in the priority queue");
        }
        this.keys[i] = key;
        this.swim(this.qp[i]);
    }
    
    public void increaseKey(final int i, final Key key) {
        this.validateIndex(i);
        if (!this.contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        if (this.keys[i].compareTo(key) == 0) {
            throw new IllegalArgumentException("Calling increaseKey() with a key equal to the key in the priority queue");
        }
        if (this.keys[i].compareTo(key) > 0) {
            throw new IllegalArgumentException("Calling increaseKey() with a key strictly less than the key in the priority queue");
        }
        this.keys[i] = key;
        this.sink(this.qp[i]);
    }
    
    public void delete(final int i) {
        this.validateIndex(i);
        if (!this.contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        final int index = this.qp[i];
        this.exch(index, this.n--);
        this.swim(index);
        this.sink(index);
        this.keys[i] = null;
        this.qp[i] = -1;
    }
    
    private void validateIndex(final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        if (i >= this.maxN) {
            throw new IllegalArgumentException("index >= capacity: " + i);
        }
    }
    
    private boolean greater(final int i, final int j) {
        return this.keys[this.pq[i]].compareTo(this.keys[this.pq[j]]) > 0;
    }
    
    private void exch(final int i, final int j) {
        final int swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
        this.qp[this.pq[i]] = i;
        this.qp[this.pq[j]] = j;
    }
    
    private void swim(int k) {
        while (k > 1 && this.greater(k / 2, k)) {
            this.exch(k, k / 2);
            k /= 2;
        }
    }
    
    private void sink(int k) {
        while (2 * k <= this.n) {
            int j = 2 * k;
            if (j < this.n && this.greater(j, j + 1)) {
                ++j;
            }
            if (!this.greater(k, j)) {
                break;
            }
            this.exch(k, j);
            k = j;
        }
    }
    
    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }
    
    public static void main(final String[] args) {
        final String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };
        final IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for (int i = 0; i < strings.length; ++i) {
            pq.insert(i, strings[i]);
        }
        while (!pq.isEmpty()) {
            final int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }
        StdOut.println();
        for (int i = 0; i < strings.length; ++i) {
            pq.insert(i, strings[i]);
        }
        for (final int j : pq) {
            StdOut.println(j + " " + strings[j]);
        }
        while (!pq.isEmpty()) {
            pq.delMin();
        }
    }
    
    private class HeapIterator implements Iterator<Integer>
    {
        private IndexMinPQ<Key> copy;
        
        public HeapIterator() {
            this.copy = new IndexMinPQ<Key>(IndexMinPQ.this.pq.length - 1);
            for (int i = 1; i <= IndexMinPQ.this.n; ++i) {
                this.copy.insert(IndexMinPQ.this.pq[i], IndexMinPQ.this.keys[IndexMinPQ.this.pq[i]]);
            }
        }
        
        @Override
        public boolean hasNext() {
            return !this.copy.isEmpty();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Integer next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.copy.delMin();
        }
    }
}
