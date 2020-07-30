// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTreeST<Key extends Comparable<Key>, Value>
{
    private Node root;
    
    public boolean isEmpty() {
        return this.root == null;
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
    
    public int height() {
        return this.height(this.root);
    }
    
    private int height(final Node x) {
        if (x == null) {
            return -1;
        }
        return x.height;
    }
    
    public Value get(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        final Node x = this.get(this.root, key);
        if (x == null) {
            return null;
        }
        return x.val;
    }
    
    private Node get(final Node x, final Key key) {
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
        return x;
    }
    
    public boolean contains(final Key key) {
        return this.get(key) != null;
    }
    
    public void put(final Key key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
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
            return new Node(key, val, 0, 1);
        }
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = this.put(x.left, key, val);
        }
        else {
            if (cmp <= 0) {
                x.val = val;
                return x;
            }
            x.right = this.put(x.right, key, val);
        }
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        return this.balance(x);
    }
    
    private Node balance(Node x) {
        if (this.balanceFactor(x) < -1) {
            if (this.balanceFactor(x.right) > 0) {
                x.right = this.rotateRight(x.right);
            }
            x = this.rotateLeft(x);
        }
        else if (this.balanceFactor(x) > 1) {
            if (this.balanceFactor(x.left) < 0) {
                x.left = this.rotateLeft(x.left);
            }
            x = this.rotateRight(x);
        }
        return x;
    }
    
    private int balanceFactor(final Node x) {
        return this.height(x.left) - this.height(x.right);
    }
    
    private Node rotateRight(final Node x) {
        final Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        y.height = 1 + Math.max(this.height(y.left), this.height(y.right));
        return y;
    }
    
    private Node rotateLeft(final Node x) {
        final Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        y.height = 1 + Math.max(this.height(y.left), this.height(y.right));
        return y;
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!this.contains(key)) {
            return;
        }
        this.root = this.delete(this.root, key);
        assert this.check();
    }
    
    private Node delete(Node x, final Key key) {
        final int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = this.delete(x.left, key);
        }
        else if (cmp > 0) {
            x.right = this.delete(x.right, key);
        }
        else {
            if (x.left == null) {
                return x.right;
            }
            if (x.right == null) {
                return x.left;
            }
            final Node y = x;
            x = this.min(y.right);
            x.right = this.deleteMin(y.right);
            x.left = y.left;
        }
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        return this.balance(x);
    }
    
    public void deleteMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called deleteMin() with empty symbol table");
        }
        this.root = this.deleteMin(this.root);
        assert this.check();
    }
    
    private Node deleteMin(final Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = this.deleteMin(x.left);
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        return this.balance(x);
    }
    
    public void deleteMax() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called deleteMax() with empty symbol table");
        }
        this.root = this.deleteMax(this.root);
        assert this.check();
    }
    
    private Node deleteMax(final Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = this.deleteMax(x.right);
        x.size = 1 + this.size(x.left) + this.size(x.right);
        x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
        return this.balance(x);
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
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
            throw new NoSuchElementException("called max() with empty symbol table");
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
            throw new NoSuchElementException("called floor() with empty symbol table");
        }
        final Node x = this.floor(this.root, key);
        if (x == null) {
            return null;
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
        final Node y = this.floor(x.right, key);
        if (y != null) {
            return y;
        }
        return x;
    }
    
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        if (this.isEmpty()) {
            throw new NoSuchElementException("called ceiling() with empty symbol table");
        }
        final Node x = this.ceiling(this.root, key);
        if (x == null) {
            return null;
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
        if (cmp > 0) {
            return this.ceiling(x.right, key);
        }
        final Node y = this.ceiling(x.left, key);
        if (y != null) {
            return y;
        }
        return x;
    }
    
    public Key select(final int k) {
        if (k < 0 || k >= this.size()) {
            throw new IllegalArgumentException("k is not in range 0-" + (this.size() - 1));
        }
        final Node x = this.select(this.root, k);
        return x.key;
    }
    
    private Node select(final Node x, final int k) {
        if (x == null) {
            return null;
        }
        final int t = this.size(x.left);
        if (t > k) {
            return this.select(x.left, k);
        }
        if (t < k) {
            return this.select(x.right, k - t - 1);
        }
        return x;
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
        return this.keysInOrder();
    }
    
    public Iterable<Key> keysInOrder() {
        final Queue<Key> queue = new Queue<Key>();
        this.keysInOrder(this.root, queue);
        return queue;
    }
    
    private void keysInOrder(final Node x, final Queue<Key> queue) {
        if (x == null) {
            return;
        }
        this.keysInOrder(x.left, queue);
        queue.enqueue(x.key);
        this.keysInOrder(x.right, queue);
    }
    
    public Iterable<Key> keysLevelOrder() {
        final Queue<Key> queue = new Queue<Key>();
        if (!this.isEmpty()) {
            final Queue<Node> queue2 = new Queue<Node>();
            queue2.enqueue(this.root);
            while (!queue2.isEmpty()) {
                final Node x = queue2.dequeue();
                queue.enqueue(x.key);
                if (x.left != null) {
                    queue2.enqueue(x.left);
                }
                if (x.right != null) {
                    queue2.enqueue(x.right);
                }
            }
        }
        return queue;
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
    
    private boolean check() {
        if (!this.isBST()) {
            StdOut.println("Symmetric order not consistent");
        }
        if (!this.isAVL()) {
            StdOut.println("AVL property not consistent");
        }
        if (!this.isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
        }
        if (!this.isRankConsistent()) {
            StdOut.println("Ranks not consistent");
        }
        return this.isBST() && this.isAVL() && this.isSizeConsistent() && this.isRankConsistent();
    }
    
    private boolean isAVL() {
        return this.isAVL(this.root);
    }
    
    private boolean isAVL(final Node x) {
        if (x == null) {
            return true;
        }
        final int bf = this.balanceFactor(x);
        return bf <= 1 && bf >= -1 && this.isAVL(x.left) && this.isAVL(x.right);
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
        final AVLTreeST<String, Integer> st = new AVLTreeST<String, Integer>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            st.put(key, i);
            ++i;
        }
        for (final String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println();
    }
    
    private class Node
    {
        private final Key key;
        private Value val;
        private int height;
        private int size;
        private Node left;
        private Node right;
        
        public Node(final Key key, final Value val, final int height, final int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.height = height;
        }
    }
}
