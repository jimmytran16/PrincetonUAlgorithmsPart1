// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class LinkedBag<Item> implements Iterable<Item>
{
    private Node first;
    private int n;
    
    public LinkedBag() {
        this.first = null;
        this.n = 0;
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public int size() {
        return this.n;
    }
    
    public void add(final Item item) {
        final Node oldfirst = this.first;
        (this.first = new Node()).item = item;
        this.first.next = oldfirst;
        ++this.n;
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }
    
    public static void main(final String[] args) {
        final LinkedBag<String> bag = new LinkedBag<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            bag.add(item);
        }
        StdOut.println("size of bag = " + bag.size());
        for (final String s : bag) {
            StdOut.println(s);
        }
    }
    
    private class Node
    {
        private Item item;
        private Node next;
    }
    
    private class LinkedIterator implements Iterator<Item>
    {
        private Node current;
        
        public LinkedIterator() {
            this.current = LinkedBag.this.first;
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
            final Item item = this.current.item;
            this.current = this.current.next;
            return item;
        }
    }
}
