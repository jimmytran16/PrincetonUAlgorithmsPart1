// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayQueue<Item> implements Iterable<Item>
{
    private Item[] q;
    private int n;
    private int first;
    private int last;
    
    public ResizingArrayQueue() {
        this.q = (Item[])new Object[2];
        this.n = 0;
        this.first = 0;
        this.last = 0;
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
            copy[i] = this.q[(this.first + i) % this.q.length];
        }
        this.q = copy;
        this.first = 0;
        this.last = this.n;
    }
    
    public void enqueue(final Item item) {
        if (this.n == this.q.length) {
            this.resize(2 * this.q.length);
        }
        this.q[this.last++] = item;
        if (this.last == this.q.length) {
            this.last = 0;
        }
        ++this.n;
    }
    
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        final Item item = this.q[this.first];
        this.q[this.first] = null;
        --this.n;
        ++this.first;
        if (this.first == this.q.length) {
            this.first = 0;
        }
        if (this.n > 0 && this.n == this.q.length / 4) {
            this.resize(this.q.length / 2);
        }
        return item;
    }
    
    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return this.q[this.first];
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    public static void main(final String[] args) {
        final ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            }
            else {
                if (queue.isEmpty()) {
                    continue;
                }
                StdOut.print(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private int i;
        
        private ArrayIterator() {
            this.i = 0;
        }
        
        @Override
        public boolean hasNext() {
            return this.i < ResizingArrayQueue.this.n;
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
            final Item item = ResizingArrayQueue.this.q[(this.i + ResizingArrayQueue.this.first) % ResizingArrayQueue.this.q.length];
            ++this.i;
            return item;
        }
    }
}
