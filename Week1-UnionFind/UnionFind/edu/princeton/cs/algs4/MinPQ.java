// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class MinPQ<Key> implements Iterable<Key>
{
    private Key[] pq;
    private int n;
    private Comparator<Key> comparator;
    
    public MinPQ(final int initCapacity) {
        this.pq = (Key[])new Object[initCapacity + 1];
        this.n = 0;
    }
    
    public MinPQ() {
        this(1);
    }
    
    public MinPQ(final int initCapacity, final Comparator<Key> comparator) {
        this.comparator = comparator;
        this.pq = (Key[])new Object[initCapacity + 1];
        this.n = 0;
    }
    
    public MinPQ(final Comparator<Key> comparator) {
        this(1, comparator);
    }
    
    public MinPQ(final Key[] keys) {
        this.n = keys.length;
        this.pq = (Key[])new Object[keys.length + 1];
        for (int i = 0; i < this.n; ++i) {
            this.pq[i + 1] = keys[i];
        }
        for (int k = this.n / 2; k >= 1; --k) {
            this.sink(k);
        }
        assert this.isMinHeap();
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public int size() {
        return this.n;
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.pq[1];
    }
    
    private void resize(final int capacity) {
        assert capacity > this.n;
        final Key[] temp = (Key[])new Object[capacity];
        for (int i = 1; i <= this.n; ++i) {
            temp[i] = this.pq[i];
        }
        this.pq = temp;
    }
    
    public void insert(final Key x) {
        if (this.n == this.pq.length - 1) {
            this.resize(2 * this.pq.length);
        }
        this.pq[++this.n] = x;
        this.swim(this.n);
        assert this.isMinHeap();
    }
    
    public Key delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        final Key min = this.pq[1];
        this.exch(1, this.n--);
        this.sink(1);
        this.pq[this.n + 1] = null;
        if (this.n > 0 && this.n == (this.pq.length - 1) / 4) {
            this.resize(this.pq.length / 2);
        }
        assert this.isMinHeap();
        return min;
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
    
    private boolean greater(final int i, final int j) {
        if (this.comparator == null) {
            return ((Comparable)this.pq[i]).compareTo(this.pq[j]) > 0;
        }
        return this.comparator.compare(this.pq[i], this.pq[j]) > 0;
    }
    
    private void exch(final int i, final int j) {
        final Key swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
    }
    
    private boolean isMinHeap() {
        for (int i = 1; i <= this.n; ++i) {
            if (this.pq[i] == null) {
                return false;
            }
        }
        for (int i = this.n + 1; i < this.pq.length; ++i) {
            if (this.pq[i] != null) {
                return false;
            }
        }
        return this.pq[0] == null && this.isMinHeapOrdered(1);
    }
    
    private boolean isMinHeapOrdered(final int k) {
        if (k > this.n) {
            return true;
        }
        final int left = 2 * k;
        final int right = 2 * k + 1;
        return (left > this.n || !this.greater(k, left)) && (right > this.n || !this.greater(k, right)) && this.isMinHeapOrdered(left) && this.isMinHeapOrdered(right);
    }
    
    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }
    
    public static void main(final String[] args) {
        final MinPQ<String> pq = new MinPQ<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            }
            else {
                if (pq.isEmpty()) {
                    continue;
                }
                StdOut.print(pq.delMin() + " ");
            }
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
    
    private class HeapIterator implements Iterator<Key>
    {
        private MinPQ<Key> copy;
        
        public HeapIterator() {
            if (MinPQ.this.comparator == null) {
                this.copy = new MinPQ<Key>(MinPQ.this.size());
            }
            else {
                this.copy = new MinPQ<Key>(MinPQ.this.size(), MinPQ.this.comparator);
            }
            for (int i = 1; i <= MinPQ.this.n; ++i) {
                this.copy.insert(MinPQ.this.pq[i]);
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
        public Key next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.copy.delMin();
        }
    }
}
