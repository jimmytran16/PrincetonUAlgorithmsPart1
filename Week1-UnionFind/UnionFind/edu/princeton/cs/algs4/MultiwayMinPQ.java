// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class MultiwayMinPQ<Key> implements Iterable<Key>
{
    private final int d;
    private int n;
    private int order;
    private Key[] keys;
    private final Comparator<Key> comp;
    
    public MultiwayMinPQ(final int d) {
        if (d < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = d;
        this.order = 1;
        this.keys = (Key[])new Comparable[d << 1];
        this.comp = new MyComparator();
    }
    
    public MultiwayMinPQ(final Comparator<Key> comparator, final int d) {
        if (d < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = d;
        this.order = 1;
        this.keys = (Key[])new Comparable[d << 1];
        this.comp = comparator;
    }
    
    public MultiwayMinPQ(final Key[] a, final int d) {
        if (d < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = d;
        this.order = 1;
        this.keys = (Key[])new Comparable[d << 1];
        this.comp = new MyComparator();
        for (final Key key : a) {
            this.insert(key);
        }
    }
    
    public MultiwayMinPQ(final Comparator<Key> comparator, final Key[] a, final int d) {
        if (d < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = d;
        this.order = 1;
        this.keys = (Key[])new Comparable[d << 1];
        this.comp = comparator;
        for (final Key key : a) {
            this.insert(key);
        }
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public int size() {
        return this.n;
    }
    
    public void insert(final Key key) {
        this.keys[this.n + this.d] = key;
        this.swim(this.n++);
        if (this.n == this.keys.length - this.d) {
            this.resize(this.getN(this.order + 1) + this.d);
            ++this.order;
        }
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.keys[this.d];
    }
    
    public Key delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        this.exch(0, --this.n);
        this.sink(0);
        final Key min = this.keys[this.n + this.d];
        this.keys[this.n + this.d] = null;
        final int number = this.getN(this.order - 2);
        if (this.order > 1 && this.n == number) {
            this.resize(number + (int)Math.pow(this.d, this.order - 1) + this.d);
            --this.order;
        }
        return min;
    }
    
    private boolean greater(final int x, final int y) {
        final int i = x + this.d;
        final int j = y + this.d;
        return this.keys[i] != null && (this.keys[j] == null || this.comp.compare(this.keys[i], this.keys[j]) > 0);
    }
    
    private void exch(final int x, final int y) {
        final int i = x + this.d;
        final int j = y + this.d;
        final Key swap = this.keys[i];
        this.keys[i] = this.keys[j];
        this.keys[j] = swap;
    }
    
    private int getN(final int order) {
        return (1 - (int)Math.pow(this.d, order + 1)) / (1 - this.d);
    }
    
    private void swim(final int i) {
        if (i > 0 && this.greater((i - 1) / this.d, i)) {
            this.exch(i, (i - 1) / this.d);
            this.swim((i - 1) / this.d);
        }
    }
    
    private void sink(int i) {
        final int child = this.d * i + 1;
        if (child >= this.n) {
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
    
    private void resize(final int N) {
        final Key[] array = (Key[])new Comparable[N];
        for (int i = 0; i < Math.min(this.keys.length, array.length); ++i) {
            array[i] = this.keys[i];
            this.keys[i] = null;
        }
        this.keys = array;
    }
    
    @Override
    public Iterator<Key> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<Key>
    {
        MultiwayMinPQ<Key> data;
        
        public MyIterator() {
            (this.data = new MultiwayMinPQ<Key>(MultiwayMinPQ.this.comp, MultiwayMinPQ.this.d)).keys = (Key[])new Comparable[MultiwayMinPQ.this.keys.length];
            this.data.n = MultiwayMinPQ.this.n;
            for (int i = 0; i < MultiwayMinPQ.this.keys.length; ++i) {
                this.data.keys[i] = MultiwayMinPQ.this.keys[i];
            }
        }
        
        @Override
        public boolean hasNext() {
            return !this.data.isEmpty();
        }
        
        @Override
        public Key next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.data.delMin();
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
