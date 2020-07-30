// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<Item> implements Iterable<Item>
{
    private int n;
    private Node first;
    
    public LinkedStack() {
        this.first = null;
        this.n = 0;
        assert this.check();
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public int size() {
        return this.n;
    }
    
    public void push(final Item item) {
        final Node oldfirst = this.first;
        (this.first = new Node()).item = item;
        this.first.next = oldfirst;
        ++this.n;
        assert this.check();
    }
    
    public Item pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        final Item item = this.first.item;
        this.first = this.first.next;
        --this.n;
        assert this.check();
        return item;
    }
    
    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return this.first.item;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (final Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }
    
    private boolean check() {
        if (this.n < 0) {
            return false;
        }
        if (this.n == 0) {
            if (this.first != null) {
                return false;
            }
        }
        else if (this.n == 1) {
            if (this.first == null) {
                return false;
            }
            if (this.first.next != null) {
                return false;
            }
        }
        else {
            if (this.first == null) {
                return false;
            }
            if (this.first.next == null) {
                return false;
            }
        }
        int numberOfNodes = 0;
        for (Node x = this.first; x != null && numberOfNodes <= this.n; ++numberOfNodes, x = x.next) {}
        return numberOfNodes == this.n;
    }
    
    public static void main(final String[] args) {
        final LinkedStack<String> stack = new LinkedStack<String>();
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
    
    private class Node
    {
        private Item item;
        private Node next;
    }
    
    private class LinkedIterator implements Iterator<Item>
    {
        private Node current;
        
        private LinkedIterator() {
            this.current = LinkedStack.this.first;
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
