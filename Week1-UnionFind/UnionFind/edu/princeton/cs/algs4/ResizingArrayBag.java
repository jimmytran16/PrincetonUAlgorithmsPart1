// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class ResizingArrayBag<Item> implements Iterable<Item>
{
    private Item[] a;
    private int n;
    
    public ResizingArrayBag() {
        this.a = (Item[])new Object[2];
        this.n = 0;
    }
    
    public boolean isEmpty() {
        return this.n == 0;
    }
    
    public int size() {
        return this.n;
    }
    
    private void resize(final int capacity) {
        assert capacity >= this.n;
        final Item[] copy = (Item[])new Object[capacity];
        for (int i = 0; i < this.n; ++i) {
            copy[i] = this.a[i];
        }
        this.a = copy;
    }
    
    public void add(final Item item) {
        if (this.n == this.a.length) {
            this.resize(2 * this.a.length);
        }
        this.a[this.n++] = item;
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    public static void main(final String[] args) {
        final ResizingArrayBag<String> bag = new ResizingArrayBag<String>();
        bag.add("Hello");
        bag.add("World");
        bag.add("how");
        bag.add("are");
        bag.add("you");
        for (final String s : bag) {
            StdOut.println(s);
        }
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private int i;
        
        private ArrayIterator() {
            this.i = 0;
        }
        
        @Override
        public boolean hasNext() {
            return this.i < ResizingArrayBag.this.n;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return ResizingArrayBag.this.a[this.i++];
        }
    }
}
