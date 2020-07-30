// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<Item> implements Iterable<Item>
{
    private int n;
    private Node first;
    private Node last;
    
    public LinkedQueue() {
        this.first = null;
        this.last = null;
        this.n = 0;
        assert this.check();
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
        return this.first.item;
    }
    
    public void enqueue(final Item item) {
        final Node oldlast = this.last;
        (this.last = new Node()).item = item;
        this.last.next = null;
        if (this.isEmpty()) {
            this.first = this.last;
        }
        else {
            oldlast.next = this.last;
        }
        ++this.n;
        assert this.check();
    }
    
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        final Item item = this.first.item;
        this.first = this.first.next;
        --this.n;
        if (this.isEmpty()) {
            this.last = null;
        }
        assert this.check();
        return item;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (final Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }
    
    private boolean check() {
        if (this.n < 0) {
            return false;
        }
        if (this.n == 0) {
            if (this.first != null) {
                return false;
            }
            if (this.last != null) {
                return false;
            }
        }
        else if (this.n == 1) {
            if (this.first == null || this.last == null) {
                return false;
            }
            if (this.first != this.last) {
                return false;
            }
            if (this.first.next != null) {
                return false;
            }
        }
        else {
            if (this.first == null || this.last == null) {
                return false;
            }
            if (this.first == this.last) {
                return false;
            }
            if (this.first.next == null) {
                return false;
            }
            if (this.last.next != null) {
                return false;
            }
            int numberOfNodes = 0;
            for (Node x = this.first; x != null && numberOfNodes <= this.n; ++numberOfNodes, x = x.next) {}
            if (numberOfNodes != this.n) {
                return false;
            }
            Node lastNode;
            for (lastNode = this.first; lastNode.next != null; lastNode = lastNode.next) {}
            if (this.last != lastNode) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }
    
    public static void main(final String[] args) {
        final LinkedQueue<String> queue = new LinkedQueue<String>();
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
    
    private class Node
    {
        private Item item;
        private Node next;
    }
    
    private class LinkedIterator implements Iterator<Item>
    {
        private Node current;
        
        private LinkedIterator() {
            this.current = LinkedQueue.this.first;
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
