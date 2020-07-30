// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class BinomialMinPQ<Key> implements Iterable<Key>
{
    private Node head;
    private final Comparator<Key> comp;
    
    public BinomialMinPQ() {
        this.comp = new MyComparator();
    }
    
    public BinomialMinPQ(final Comparator<Key> C) {
        this.comp = C;
    }
    
    public BinomialMinPQ(final Key[] a) {
        this.comp = new MyComparator();
        for (final Key k : a) {
            this.insert(k);
        }
    }
    
    public BinomialMinPQ(final Comparator<Key> C, final Key[] a) {
        this.comp = C;
        for (final Key k : a) {
            this.insert(k);
        }
    }
    
    public boolean isEmpty() {
        return this.head == null;
    }
    
    public int size() {
        int result = 0;
        for (Node node = this.head; node != null; node = node.sibling) {
            if (node.order > 30) {
                throw new ArithmeticException("The number of elements cannot be evaluated, but the priority queue is still valid.");
            }
            final int tmp = 1 << node.order;
            result |= tmp;
        }
        return result;
    }
    
    public void insert(final Key key) {
        final Node x = new Node();
        x.key = key;
        x.order = 0;
        final BinomialMinPQ<Key> H = new BinomialMinPQ<Key>();
        H.head = x;
        this.head = this.union(H).head;
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        Node min = this.head;
        for (Node current = this.head; current.sibling != null; current = current.sibling) {
            min = (this.greater(min.key, current.sibling.key) ? current : min);
        }
        return min.key;
    }
    
    public Key delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        final Node min = this.eraseMin();
        Node x = (min.child == null) ? min : min.child;
        if (min.child != null) {
            min.child = null;
            Node prevx = null;
            for (Node nextx = x.sibling; nextx != null; nextx = nextx.sibling) {
                x.sibling = prevx;
                prevx = x;
                x = nextx;
            }
            x.sibling = prevx;
            final BinomialMinPQ<Key> H = new BinomialMinPQ<Key>();
            H.head = x;
            this.head = this.union(H).head;
        }
        return min.key;
    }
    
    public BinomialMinPQ<Key> union(final BinomialMinPQ<Key> heap) {
        if (heap == null) {
            throw new IllegalArgumentException("Cannot merge a Binomial Heap with null");
        }
        this.head = this.merge(new Node(), this.head, heap.head).sibling;
        Node x = this.head;
        Node prevx = null;
        for (Node nextx = x.sibling; nextx != null; nextx = x.sibling) {
            if (x.order < nextx.order || (nextx.sibling != null && nextx.sibling.order == x.order)) {
                prevx = x;
                x = nextx;
            }
            else if (this.greater(nextx.key, x.key)) {
                x.sibling = nextx.sibling;
                this.link(nextx, x);
            }
            else {
                if (prevx == null) {
                    this.head = nextx;
                }
                else {
                    prevx.sibling = nextx;
                }
                this.link(x, nextx);
                x = nextx;
            }
        }
        return this;
    }
    
    private boolean greater(final Key n, final Key m) {
        return n != null && (m == null || this.comp.compare(n, m) > 0);
    }
    
    private void link(final Node root1, final Node root2) {
        root1.sibling = root2.child;
        root2.child = root1;
        ++root2.order;
    }
    
    private Node eraseMin() {
        Node min = this.head;
        Node previous = null;
        for (Node current = this.head; current.sibling != null; current = current.sibling) {
            if (this.greater(min.key, current.sibling.key)) {
                previous = current;
                min = current.sibling;
            }
        }
        previous.sibling = min.sibling;
        if (min == this.head) {
            this.head = min.sibling;
        }
        return min;
    }
    
    private Node merge(final Node h, final Node x, final Node y) {
        if (x == null && y == null) {
            return h;
        }
        if (x == null) {
            h.sibling = this.merge(y, null, y.sibling);
        }
        else if (y == null) {
            h.sibling = this.merge(x, x.sibling, null);
        }
        else if (x.order < y.order) {
            h.sibling = this.merge(x, x.sibling, y);
        }
        else {
            h.sibling = this.merge(y, x, y.sibling);
        }
        return h;
    }
    
    @Override
    public Iterator<Key> iterator() {
        return new MyIterator();
    }
    
    private class Node
    {
        Key key;
        int order;
        Node child;
        Node sibling;
    }
    
    private class MyIterator implements Iterator<Key>
    {
        BinomialMinPQ<Key> data;
        
        public MyIterator() {
            (this.data = new BinomialMinPQ<Key>(BinomialMinPQ.this.comp)).head = this.clone(BinomialMinPQ.this.head, null);
        }
        
        private Node clone(final Node x, final Node parent) {
            if (x == null) {
                return null;
            }
            final Node node = new Node();
            node.key = x.key;
            node.sibling = this.clone(x.sibling, parent);
            node.child = this.clone(x.child, node);
            return node;
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
