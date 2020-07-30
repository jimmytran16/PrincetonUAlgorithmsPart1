// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class IndexBinomialMinPQ<Key> implements Iterable<Integer>
{
    private Node<Key> head;
    private Node<Key>[] nodes;
    private int n;
    private final Comparator<Key> comparator;
    
    public IndexBinomialMinPQ(final int N) {
        if (N < 0) {
            throw new IllegalArgumentException("Cannot create a priority queue of negative size");
        }
        this.comparator = new MyComparator();
        this.nodes = (Node<Key>[])new Node[N];
        this.n = N;
    }
    
    public IndexBinomialMinPQ(final int N, final Comparator<Key> comparator) {
        if (N < 0) {
            throw new IllegalArgumentException("Cannot create a priority queue of negative size");
        }
        this.comparator = comparator;
        this.nodes = (Node<Key>[])new Node[N];
        this.n = N;
    }
    
    public boolean isEmpty() {
        return this.head == null;
    }
    
    public boolean contains(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        return this.nodes[i] != null;
    }
    
    public int size() {
        int result = 0;
        for (Node<Key> node = this.head; node != null; node = node.sibling) {
            if (node.order > 30) {
                throw new ArithmeticException("The number of elements cannot be evaluated, but the priority queue is still valid.");
            }
            final int tmp = 1 << node.order;
            result |= tmp;
        }
        return result;
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
        x.order = 0;
        this.nodes[i] = x;
        final IndexBinomialMinPQ<Key> H = new IndexBinomialMinPQ<Key>();
        H.head = x;
        this.head = this.union(H).head;
    }
    
    public int minIndex() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        Node<Key> min = this.head;
        for (Node<Key> current = this.head; current.sibling != null; current = current.sibling) {
            min = (this.greater(min.key, current.sibling.key) ? current.sibling : min);
        }
        return min.index;
    }
    
    public Key minKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        Node<Key> min = this.head;
        for (Node<Key> current = this.head; current.sibling != null; current = current.sibling) {
            min = (this.greater(min.key, current.sibling.key) ? current.sibling : min);
        }
        return min.key;
    }
    
    public int delMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        final Node<Key> min = this.eraseMin();
        Node<Key> x = (min.child == null) ? min : min.child;
        if (min.child != null) {
            min.child = null;
            Node<Key> prevx = null;
            for (Node<Key> nextx = x.sibling; nextx != null; nextx = nextx.sibling) {
                x.parent = null;
                x.sibling = prevx;
                prevx = x;
                x = nextx;
            }
            x.parent = null;
            x.sibling = prevx;
            final IndexBinomialMinPQ<Key> H = new IndexBinomialMinPQ<Key>();
            H.head = x;
            this.head = this.union(H).head;
        }
        return min.index;
    }
    
    public Key keyOf(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new IllegalArgumentException("Specified index is not in the queue");
        }
        return this.nodes[i].key;
    }
    
    public void changeKey(final int i, final Key key) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(i)) {
            throw new IllegalArgumentException("Specified index is not in the queue");
        }
        if (this.greater(this.nodes[i].key, key)) {
            this.decreaseKey(i, key);
        }
        else {
            this.increaseKey(i, key);
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
        this.swim(i);
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
        this.toTheRoot(i);
        Node<Key> x = this.erase(i);
        if (x.child != null) {
            final Node<Key> y = x;
            x = x.child;
            y.child = null;
            Node<Key> prevx = null;
            for (Node<Key> nextx = x.sibling; nextx != null; nextx = nextx.sibling) {
                x.parent = null;
                x.sibling = prevx;
                prevx = x;
                x = nextx;
            }
            x.parent = null;
            x.sibling = prevx;
            final IndexBinomialMinPQ<Key> H = new IndexBinomialMinPQ<Key>();
            H.head = x;
            this.head = this.union(H).head;
        }
    }
    
    private boolean greater(final Key n, final Key m) {
        return n != null && (m == null || this.comparator.compare(n, m) > 0);
    }
    
    private void exchange(final Node<Key> x, final Node<Key> y) {
        final Key tempKey = x.key;
        x.key = y.key;
        y.key = tempKey;
        final int tempInt = x.index;
        x.index = y.index;
        y.index = tempInt;
        this.nodes[x.index] = x;
        this.nodes[y.index] = y;
    }
    
    private void link(final Node<Key> root1, final Node<Key> root2) {
        root1.sibling = root2.child;
        root1.parent = root2;
        root2.child = root1;
        ++root2.order;
    }
    
    private void swim(final int i) {
        final Node<Key> x = this.nodes[i];
        final Node<Key> parent = x.parent;
        if (parent != null && this.greater(parent.key, x.key)) {
            this.exchange(x, parent);
            this.swim(i);
        }
    }
    
    private void toTheRoot(final int i) {
        final Node<Key> x = this.nodes[i];
        final Node<Key> parent = x.parent;
        if (parent != null) {
            this.exchange(x, parent);
            this.toTheRoot(i);
        }
    }
    
    private Node<Key> erase(final int i) {
        final Node<Key> reference = this.nodes[i];
        Node<Key> x = this.head;
        Node<Key> previous = null;
        while (x != reference) {
            previous = x;
            x = x.sibling;
        }
        previous.sibling = x.sibling;
        if (x == this.head) {
            this.head = this.head.sibling;
        }
        this.nodes[i] = null;
        return x;
    }
    
    private Node<Key> eraseMin() {
        Node<Key> min = this.head;
        Node<Key> previous = null;
        for (Node<Key> current = this.head; current.sibling != null; current = current.sibling) {
            if (this.greater(min.key, current.sibling.key)) {
                previous = current;
                min = current.sibling;
            }
        }
        previous.sibling = min.sibling;
        if (min == this.head) {
            this.head = min.sibling;
        }
        this.nodes[min.index] = null;
        return min;
    }
    
    private Node<Key> merge(final Node<Key> h, final Node<Key> x, final Node<Key> y) {
        if (x == null && y == null) {
            return h;
        }
        if (x == null) {
            h.sibling = (Node<Key>)this.merge((Node<Key>)y, (Node<Key>)null, (Node<Key>)y.sibling);
        }
        else if (y == null) {
            h.sibling = (Node<Key>)this.merge((Node<Key>)x, (Node<Key>)x.sibling, (Node<Key>)null);
        }
        else if (x.order < y.order) {
            h.sibling = (Node<Key>)this.merge((Node<Key>)x, (Node<Key>)x.sibling, (Node<Key>)y);
        }
        else {
            h.sibling = (Node<Key>)this.merge((Node<Key>)y, (Node<Key>)x, (Node<Key>)y.sibling);
        }
        return h;
    }
    
    private IndexBinomialMinPQ<Key> union(final IndexBinomialMinPQ<Key> heap) {
        this.head = this.merge(new Node<Key>(), this.head, heap.head).sibling;
        Node<Key> x = this.head;
        Node<Key> prevx = null;
        for (Node<Key> nextx = x.sibling; nextx != null; nextx = x.sibling) {
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
    
    private IndexBinomialMinPQ() {
        this.comparator = null;
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
        Node<Key> parent;
        Node<Key> child;
        Node<Key> sibling;
    }
    
    private class MyIterator implements Iterator<Integer>
    {
        IndexBinomialMinPQ<Key> data;
        
        public MyIterator() {
            (this.data = new IndexBinomialMinPQ<Key>(IndexBinomialMinPQ.this.n, IndexBinomialMinPQ.this.comparator)).head = this.clone(IndexBinomialMinPQ.this.head, null);
        }
        
        private Node<Key> clone(final Node<Key> x, final Node<Key> parent) {
            if (x == null) {
                return null;
            }
            final Node<Key> node = new Node<Key>();
            node.index = x.index;
            node.key = x.key;
            this.data.nodes[node.index] = node;
            node.parent = parent;
            node.sibling = (Node<Key>)this.clone((Node<Key>)x.sibling, (Node<Key>)parent);
            node.child = (Node<Key>)this.clone((Node<Key>)x.child, (Node<Key>)node);
            return node;
        }
        
        @Override
        public boolean hasNext() {
            return !this.data.isEmpty();
        }
        
        @Override
        public Integer next() {
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
