// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return this.size(this.root);
    }
    
    private int size(final Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }
    
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return this.get(key) != null;
    }
    
    public Value get(final Key key) {
        return this.get(this.root, key);
    }
    
    private Value get(final Node x, final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return this.get(x.left, key);
        }
        if (cmp > 0) {
            return this.get(x.right, key);
        }
        return x.val;
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (val == null) {
            this.delete(key);
            return;
        }
        this.root = this.put(this.root, key, val);
        assert this.check();
    }
    
    private Node put(final Node x, final Key key, final Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = this.put(x.left, key, val);
        }
        else if (cmp > 0) {
            x.right = this.put(x.right, key, val);
        }
        else {
            x.val = val;
        }
        x.size = 1 + this.size(x.left) + this.size(x.right);
        return x;
    }
    
    public void deleteMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        this.root = this.deleteMin(this.root);
        assert this.check();
    }
    
    private Node deleteMin(final Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = this.deleteMin(x.left);
        x.size = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }
    
    public void deleteMax() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        this.root = this.deleteMax(this.root);
        assert this.check();
    }
    
    private Node deleteMax(final Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = this.deleteMax(x.right);
        x.size = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        this.root = this.delete(this.root, key);
        assert this.check();
    }
    
    private Node delete(Node x, final Key key) {
        if (x == null) {
            return null;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = this.delete(x.left, key);
        }
        else if (cmp > 0) {
            x.right = this.delete(x.right, key);
        }
        else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            final Node t = x;
            x = this.min(t.right);
            x.right = this.deleteMin(t.right);
            x.left = t.left;
        }
        x.size = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return this.min(this.root).key;
    }
    
    private Node min(final Node x) {
        if (x.left == null) {
            return x;
        }
        return this.min(x.left);
    }
    
    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls max() with empty symbol table");
        }
        return this.max(this.root).key;
    }
    
    private Node max(final Node x) {
        if (x.right == null) {
            return x;
        }
        return this.max(x.right);
    }
    
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }
        final Node x = this.floor(this.root, key);
        if (x == null) {
            throw new NoSuchElementException("argument to floor() is too small");
        }
        return x.key;
    }
    
    private Node floor(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return this.floor(x.left, key);
        }
        final Node t = this.floor(x.right, key);
        if (t != null) {
            return t;
        }
        return x;
    }
    
    public Key floor2(final Key key) {
        final Key x = this.floor2(this.root, key, null);
        if (x == null) {
            throw new NoSuchElementException("argument to floor() is too small");
        }
        return x;
    }
    
    private Key floor2(final Node x, final Key key, final Key best) {
        if (x == null) {
            return best;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return this.floor2(x.left, key, best);
        }
        if (cmp > 0) {
            return this.floor2(x.right, key, x.key);
        }
        return x.key;
    }
    
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        if (this.isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        }
        final Node x = this.ceiling(this.root, key);
        if (x == null) {
            throw new NoSuchElementException("argument to floor() is too large");
        }
        return x.key;
    }
    
    private Node ceiling(final Node x, final Key key) {
        if (x == null) {
            return null;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp >= 0) {
            return this.ceiling(x.right, key);
        }
        final Node t = this.ceiling(x.left, key);
        if (t != null) {
            return t;
        }
        return x;
    }
    
    public Key select(final int rank) {
        if (rank < 0 || rank >= this.size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return this.select(this.root, rank);
    }
    
    private Key select(final Node x, final int rank) {
        if (x == null) {
            return null;
        }
        final int leftSize = this.size(x.left);
        if (leftSize > rank) {
            return this.select(x.left, rank);
        }
        if (leftSize < rank) {
            return this.select(x.right, rank - leftSize - 1);
        }
        return x.key;
    }
    
    public int rank(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        return this.rank(key, this.root);
    }
    
    private int rank(final Key key, final Node x) {
        if (x == null) {
            return 0;
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return this.rank(key, x.left);
        }
        if (cmp > 0) {
            return 1 + this.size(x.left) + this.rank(key, x.right);
        }
        return this.size(x.left);
    }
    
    public Iterable<Key> keys() {
        if (this.isEmpty()) {
            return new Queue<Key>();
        }
        return this.keys(this.min(), this.max());
    }
    
    public Iterable<Key> keys(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        final Queue<Key> queue = new Queue<Key>();
        this.keys(this.root, queue, lo, hi);
        return queue;
    }
    
    private void keys(final Node x, final Queue<Key> queue, final Key lo, final Key hi) {
        if (x == null) {
            return;
        }
        final int cmplo = lo.compareTo(x.key);
        final int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) {
            this.keys(x.left, queue, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
            queue.enqueue(x.key);
        }
        if (cmphi > 0) {
            this.keys(x.right, queue, lo, hi);
        }
    }
    
    public int size(final Key lo, final Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is null");
        }
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (this.contains(hi)) {
            return this.rank(hi) - this.rank(lo) + 1;
        }
        return this.rank(hi) - this.rank(lo);
    }
    
    public int height() {
        return this.height(this.root);
    }
    
    private int height(final Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(this.height(x.left), this.height(x.right));
    }
    
    public Iterable<Key> levelOrder() {
        final Queue<Key> keys = new Queue<Key>();
        final Queue<Node> queue = new Queue<Node>();
        queue.enqueue(this.root);
        while (!queue.isEmpty()) {
            final Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }
    
    private boolean check() {
        if (!this.isBST()) {
            StdOut.println("Not in symmetric order");
        }
        if (!this.isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
        }
        if (!this.isRankConsistent()) {
            StdOut.println("Ranks not consistent");
        }
        return this.isBST() && this.isSizeConsistent() && this.isRankConsistent();
    }
    
    private boolean isBST() {
        return this.isBST(this.root, null, null);
    }
    
    private boolean isBST(final Node x, final Key min, final Key max) {
        return x == null || ((min == null || x.key.compareTo(min) > 0) && (max == null || x.key.compareTo(max) < 0) && this.isBST(x.left, min, x.key) && this.isBST(x.right, x.key, max));
    }
    
    private boolean isSizeConsistent() {
        return this.isSizeConsistent(this.root);
    }
    
    private boolean isSizeConsistent(final Node x) {
        return x == null || (x.size == this.size(x.left) + this.size(x.right) + 1 && this.isSizeConsistent(x.left) && this.isSizeConsistent(x.right));
    }
    
    private boolean isRankConsistent() {
        for (int i = 0; i < this.size(); ++i) {
            if (i != this.rank(this.select(i))) {
                return false;
            }
        }
        for (final Key key : this.keys()) {
            if (key.compareTo(this.select(this.rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final BST<String, Integer> st = new BST<String, Integer>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            st.put(key, i);
            ++i;
        }
        for (final String s : st.levelOrder()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println();
        for (final String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
    
    private class Node
    {
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int size;
        
        public Node(final Key key, final Value val, final int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
}
