// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item>
{
    private Node<Item> first;
    private Node<Item> last;
    private int n;
    
    public Queue() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public int size() {
        return this.n;
    }
    
    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return (Item)((Node<Object>)this.first).item;
    }
    
    public void enqueue(final Item item) {
        final Node<Item> oldlast = this.last;
        ((Node<Object>)(this.last = new Node<Item>())).item = item;
        ((Node<Object>)this.last).next = null;
        if (this.isEmpty()) {
            this.first = this.last;
        }
        else {
            ((Node<Object>)oldlast).next = (Node<Object>)this.last;
        }
        ++this.n;
    }
    
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        final Item item = (Item)((Node<Object>)this.first).item;
        this.first = (Node<Item>)((Node<Object>)this.first).next;
        --this.n;
        if (this.isEmpty()) {
            this.last = null;
        }
        return item;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (final Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }
    
    public static void main(final String[] args) {
        final Queue<String> queue = new Queue<String>();
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
    
    private static class Node<Item>
    {
        private Item item;
        private Node<Item> next;
    }
    
    private class LinkedIterator implements Iterator<Item>
    {
        private Node<Item> current;
        
        public LinkedIterator(final Node<Item> first) {
            this.current = first;
        }
        
        @Override
        public boolean hasNext() {
            return this.current != null;
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
            final Item item = (Item)((Node<Object>)this.current).item;
            this.current = (Node<Item>)((Node<Object>)this.current).next;
            return item;
        }
    }
}
