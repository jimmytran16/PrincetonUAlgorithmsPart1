// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Comparator;

public class FibonacciMinPQ<Key> implements Iterable<Key>
{
    private Node head;
    private Node min;
    private int size;
    private final Comparator<Key> comp;
    private HashMap<Integer, Node> table;
    
    public FibonacciMinPQ(final Comparator<Key> C) {
        this.table = new HashMap<Integer, Node>();
        this.comp = C;
    }
    
    public FibonacciMinPQ() {
        this.table = new HashMap<Integer, Node>();
        this.comp = new MyComparator();
    }
    
    public FibonacciMinPQ(final Key[] a) {
        this.table = new HashMap<Integer, Node>();
        this.comp = new MyComparator();
        for (final Key k : a) {
            this.insert(k);
        }
    }
    
    public FibonacciMinPQ(final Comparator<Key> C, final Key[] a) {
        this.table = new HashMap<Integer, Node>();
        this.comp = C;
        for (final Key k : a) {
            this.insert(k);
        }
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public int size() {
        return this.size;
    }
    
    public void insert(final Key key) {
        final Node x = new Node();
        x.key = key;
        ++this.size;
        this.head = this.insert(x, this.head);
        if (this.min == null) {
            this.min = this.head;
        }
        else {
            this.min = (this.greater(this.min.key, key) ? this.head : this.min);
        }
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.min.key;
    }
    
    public Key delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        this.head = this.cut(this.min, this.head);
        final Node x = this.min.child;
        final Key key = this.min.key;
        this.min.key = null;
        if (x != null) {
            this.head = this.meld(this.head, x);
            this.min.child = null;
        }
        --this.size;
        if (!this.isEmpty()) {
            this.consolidate();
        }
        else {
            this.min = null;
        }
        return key;
    }
    
    public FibonacciMinPQ<Key> union(final FibonacciMinPQ<Key> that) {
        this.head = this.meld(this.head, that.head);
        this.min = (this.greater(this.min.key, that.min.key) ? that.min : this.min);
        this.size += that.size;
        return this;
    }
    
    private boolean greater(final Key n, final Key m) {
        return n != null && (m == null || this.comp.compare(n, m) > 0);
    }
    
    private void link(final Node root1, final Node root2) {
        root2.child = this.insert(root1, root2.child);
        ++root2.order;
    }
    
    private void consolidate() {
        this.table.clear();
        Node x = this.head;
        int maxOrder = 0;
        this.min = this.head;
        Node y = null;
        Node z = null;
        do {
            y = x;
            x = x.next;
            for (z = this.table.get(y.order); z != null; z = this.table.get(y.order)) {
                this.table.remove(y.order);
                if (this.greater(y.key, z.key)) {
                    this.link(y, z);
                    y = z;
                }
                else {
                    this.link(z, y);
                }
            }
            this.table.put(y.order, y);
            if (y.order > maxOrder) {
                maxOrder = y.order;
            }
        } while (x != this.head);
        this.head = null;
        for (final Node n : this.table.values()) {
            if (n != null) {
                this.min = (this.greater(this.min.key, n.key) ? n : this.min);
                this.head = this.insert(n, this.head);
            }
        }
    }
    
    private Node insert(final Node x, final Node head) {
        if (head == null) {
            x.prev = x;
            x.next = x;
        }
        else {
            head.prev.next = x;
            x.next = head;
            x.prev = head.prev;
            head.prev = x;
        }
        return x;
    }
    
    private Node cut(final Node x, final Node head) {
        if (x.next == x) {
            x.next = null;
            return x.prev = null;
        }
        x.next.prev = x.prev;
        x.prev.next = x.next;
        final Node res = x.next;
        x.next = null;
        x.prev = null;
        if (head == x) {
            return res;
        }
        return head;
    }
    
    private Node meld(final Node x, final Node y) {
        if (x == null) {
            return y;
        }
        if (y == null) {
            return x;
        }
        x.prev.next = y.next;
        y.next.prev = x.prev;
        x.prev = y;
        return y.next = x;
    }
    
    @Override
    public Iterator<Key> iterator() {
        return new MyIterator();
    }
    
    private class Node
    {
        Key key;
        int order;
        Node prev;
        Node next;
        Node child;
    }
    
    private class MyIterator implements Iterator<Key>
    {
        private FibonacciMinPQ<Key> copy;
        
        public MyIterator() {
            this.copy = new FibonacciMinPQ<Key>(FibonacciMinPQ.this.comp);
            this.insertAll(FibonacciMinPQ.this.head);
        }
        
        private void insertAll(final Node head) {
            if (head == null) {
                return;
            }
            Node x = head;
            do {
                this.copy.insert(x.key);
                this.insertAll(x.child);
                x = x.next;
            } while (x != head);
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean hasNext() {
            return !this.copy.isEmpty();
        }
        
        @Override
        public Key next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.copy.delMin();
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
