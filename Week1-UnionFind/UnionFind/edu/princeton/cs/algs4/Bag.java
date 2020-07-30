// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Bag<Item> implements Iterable<Item>
{
    private Node<Item> first;
    private int n;
    
    public Bag() {
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
        final Node<Item> oldfirst = this.first;
        ((Node<Object>)(this.first = new Node<Item>())).item = item;
        ((Node<Object>)this.first).next = (Node<Object>)oldfirst;
        ++this.n;
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }
    
    public static void main(final String[] args) {
        final Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            bag.add(item);
        }
        StdOut.println("size of bag = " + bag.size());
        for (final String s : bag) {
            StdOut.println(s);
        }
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
