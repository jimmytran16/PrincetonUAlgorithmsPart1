// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Comparator;

public class IndexFibonacciMinPQ<Key> implements Iterable<Integer>
{
    private Node<Key>[] nodes;
    private Node<Key> head;
    private Node<Key> min;
    private int size;
    private int n;
    private final Comparator<Key> comp;
    private HashMap<Integer, Node<Key>> table;
    
    public IndexFibonacciMinPQ(final int N) {
        this.table = new HashMap<Integer, Node<Key>>();
        if (N < 0) {
            throw new IllegalArgumentException("Cannot create a priority queue of negative size");
        }
        this.n = N;
        this.nodes = (Node<Key>[])new Node[this.n];
        this.comp = new MyComparator();
    }
    
    public IndexFibonacciMinPQ(final Comparator<Key> C, final int N) {
        this.table = new HashMap<Integer, Node<Key>>();
        if (N < 0) {
            throw new IllegalArgumentException("Cannot create a priority queue of negative size");
        }
        this.n = N;
        this.nodes = (Node<Key>[])new Node[this.n];
        this.comp = C;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public boolean contains(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        return this.nodes[i] != null;
    }
    
    public int size() {
        return this.size;
    }
    
    public void insert(final int i, final Key key) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (this.contains(i)) {
            throw new IllegalArgumentException("Specified index is already in the queue");
        }
        final Node<Key> x = new Node<Key>();
        x.key = key;
        x.index = i;
        this.nodes[i] = x;
        ++this.size;
        this.head = this.insert(x, this.head);
        if (this.min == null) {
            this.min = this.head;
        }
        else {
            this.min = (this.greater(this.min.key, key) ? this.head : this.min);
        }
    }
    
    public int minIndex() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.min.index;
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.min.key;
    }
    
    public int delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        this.head = this.cut(this.min, this.head);
        Node<Key> x = this.min.child;
        final int index = this.min.index;
        this.min.key = null;
        if (x != null) {
            do {
                x.parent = null;
                x = x.next;
            } while (x != this.min.child);
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
        this.nodes[index] = null;
        return index;
    }
    
    public Key keyOf(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        return this.nodes[i].key;
    }
    
    public void changeKey(final int i, final Key key) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.greater(key, this.nodes[i].key)) {
            this.increaseKey(i, key);
        }
        else {
            this.decreaseKey(i, key);
        }
    }
    
    public void decreaseKey(final int i, final Key key) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.greater(key, this.nodes[i].key)) {
            throw new IllegalArgumentException("Calling with this argument would not decrease the key");
        }
        final Node<Key> x = this.nodes[i];
        x.key = key;
        if (this.greater(this.min.key, key)) {
            this.min = x;
        }
        if (x.parent != null && this.greater(x.parent.key, key)) {
            this.cut(i);
        }
    }
    
    public void increaseKey(final int i, final Key key) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.greater(this.nodes[i].key, key)) {
            throw new IllegalArgumentException("Calling with this argument would not increase the key");
        }
        this.delete(i);
        this.insert(i, key);
    }
    
    public void delete(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        Node<Key> x = this.nodes[i];
        x.key = null;
        if (x.parent != null) {
            this.cut(i);
        }
        this.head = this.cut(x, this.head);
        if (x.child != null) {
            Node<Key> child = x.child;
            x.child = null;
            x = child;
            do {
                child.parent = null;
                child = child.next;
            } while (child != x);
            this.head = this.meld(this.head, child);
        }
        if (!this.isEmpty()) {
            this.consolidate();
        }
        else {
            this.min = null;
        }
        this.nodes[i] = null;
        --this.size;
    }
    
    private boolean greater(final Key n, final Key m) {
        return n != null && (m == null || this.comp.compare(n, m) > 0);
    }
    
    private void link(final Node<Key> root1, final Node<Key> root2) {
        root1.parent = root2;
        root2.child = (Node<Key>)this.insert((Node<Key>)root1, (Node<Key>)root2.child);
        ++root2.order;
    }
    
    private void cut(final int i) {
        final Node<Key> x = this.nodes[i];
        final Node<Key> parent = x.parent;
        parent.child = (Node<Key>)this.cut((Node<Key>)x, (Node<Key>)parent.child);
        x.parent = null;
        final Node<Key> node = parent;
        --node.order;
        this.head = this.insert(x, this.head);
        parent.mark = !parent.mark;
        if (!parent.mark && parent.parent != null) {
            this.cut(parent.index);
        }
    }
    
    private void consolidate() {
        this.table.clear();
        Node<Key> x = this.head;
        int maxOrder = 0;
        this.min = this.head;
        Node<Key> y = null;
        Node<Key> z = null;
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
        for (final Node<Key> n : this.table.values()) {
            this.min = (this.greater(this.min.key, n.key) ? n : this.min);
            this.head = this.insert(n, this.head);
        }
    }
    
    private Node<Key> insert(final Node<Key> x, final Node<Key> head) {
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
    
    private Node<Key> cut(final Node<Key> x, final Node<Key> head) {
        if (x.next == x) {
            x.next = null;
            x.prev = null;
            return null;
        }
        x.next.prev = x.prev;
        x.prev.next = x.next;
        final Node<Key> res = x.next;
        x.next = null;
        x.prev = null;
        if (head == x) {
            return res;
        }
        return head;
    }
    
    private Node<Key> meld(final Node<Key> x, final Node<Key> y) {
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
    public Iterator<Integer> iterator() {
        return new MyIterator();
    }
    
    private class Node<Key>
    {
        Key key;
        int order;
        int index;
        Node<Key> prev;
        Node<Key> next;
        Node<Key> parent;
        Node<Key> child;
        boolean mark;
    }
    
    private class MyIterator implements Iterator<Integer>
    {
        private IndexFibonacciMinPQ<Key> copy;
        
        public MyIterator() {
            this.copy = new IndexFibonacciMinPQ<Key>(IndexFibonacciMinPQ.this.comp, IndexFibonacciMinPQ.this.n);
            for (final Node<Key> x : IndexFibonacciMinPQ.this.nodes) {
                if (x != null) {
                    this.copy.insert(x.index, x.key);
                }
            }
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
        public Integer next() {
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
