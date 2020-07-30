// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item>
{
    private Node<Item> first;
    private int n;
    
    public Stack() {
        this.first = null;
        this.n = 0;
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public int size() {
        return this.n;
    }
    
    public void push(final Item item) {
        final Node<Item> oldfirst = this.first;
        ((Node<Object>)(this.first = new Node<Item>())).item = item;
        ((Node<Object>)this.first).next = (Node<Object>)oldfirst;
        ++this.n;
    }
    
    public Item pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        final Item item = (Item)((Node<Object>)this.first).item;
        this.first = (Node<Item>)((Node<Object>)this.first).next;
        --this.n;
        return item;
    }
    
    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return (Item)((Node<Object>)this.first).item;
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
        final Stack<String> stack = new Stack<String>();
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
