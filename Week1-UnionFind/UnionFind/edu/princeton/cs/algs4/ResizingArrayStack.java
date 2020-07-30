// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayStack<Item> implements Iterable<Item>
{
    private Item[] a;
    private int n;
    
    public ResizingArrayStack() {
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
    
    public void push(final Item item) {
        if (this.n == this.a.length) {
            this.resize(2 * this.a.length);
        }
        this.a[this.n++] = item;
    }
    
    public Item pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        final Item item = this.a[this.n - 1];
        this.a[this.n - 1] = null;
        --this.n;
        if (this.n > 0 && this.n == this.a.length / 4) {
            this.resize(this.a.length / 2);
        }
        return item;
    }
    
    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return this.a[this.n - 1];
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    
    public static void main(final String[] args) {
        final ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            }
            else {
                if (stack.isEmpty()) {
                    continue;
                }
                StdOut.print(stack.pop() + " ");
            }
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
    
    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i;
        
        public ReverseArrayIterator() {
            this.i = ResizingArrayStack.this.n - 1;
        }
        
        @Override
        public boolean hasNext() {
            return this.i >= 0;
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
            return ResizingArrayStack.this.a[this.i--];
        }
    }
}
