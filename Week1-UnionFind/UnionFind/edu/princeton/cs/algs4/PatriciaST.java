// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class PatriciaST<Value>
{
    private Node head;
    private int count;
    
    public PatriciaST() {
        (this.head = new Node("", null, 0)).left = this.head;
        this.head.right = this.head;
        this.count = 0;
    }
    
    public void put(final String key, final Value val) {
        if (key == null) {
            throw new IllegalArgumentException("called put(null)");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("invalid key");
        }
        if (val == null) {
            this.delete(key);
        }
        Node x = this.head;
        Node p;
        do {
            p = x;
            if (safeBitTest(key, x.b)) {
                x = x.right;
            }
            else {
                x = x.left;
            }
        } while (p.b < x.b);
        if (!x.key.equals(key)) {
            final int b = firstDifferingBit(x.key, key);
            x = this.head;
            do {
                p = x;
                if (safeBitTest(key, x.b)) {
                    x = x.right;
                }
                else {
                    x = x.left;
                }
            } while (p.b < x.b && x.b < b);
            final Node t = new Node(key, val, b);
            if (safeBitTest(key, b)) {
                t.left = x;
                t.right = t;
            }
            else {
                t.left = t;
                t.right = x;
            }
            if (safeBitTest(key, p.b)) {
                p.right = t;
            }
            else {
                p.left = t;
            }
            ++this.count;
        }
        else {
            x.val = val;
        }
    }
    
    public Value get(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("called get(null)");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("invalid key");
        }
        Node x = this.head;
        Node p;
        do {
            p = x;
            if (safeBitTest(key, x.b)) {
                x = x.right;
            }
            else {
                x = x.left;
            }
        } while (p.b < x.b);
        if (x.key.equals(key)) {
            return x.val;
        }
        return null;
    }
    
    public void delete(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("called delete(null)");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("invalid key");
        }
        Node p = this.head;
        Node x = this.head;
        Node g;
        do {
            g = p;
            p = x;
            if (safeBitTest(key, x.b)) {
                x = x.right;
            }
            else {
                x = x.left;
            }
        } while (p.b < x.b);
        if (x.key.equals(key)) {
            Node y = this.head;
            Node z;
            do {
                z = y;
                if (safeBitTest(key, y.b)) {
                    y = y.right;
                }
                else {
                    y = y.left;
                }
            } while (y != x);
            if (x == p) {
                Node c;
                if (safeBitTest(key, x.b)) {
                    c = x.left;
                }
                else {
                    c = x.right;
                }
                if (safeBitTest(key, z.b)) {
                    z.right = c;
                }
                else {
                    z.left = c;
                }
            }
            else {
                Node c;
                if (safeBitTest(key, p.b)) {
                    c = p.left;
                }
                else {
                    c = p.right;
                }
                if (safeBitTest(key, g.b)) {
                    g.right = c;
                }
                else {
                    g.left = c;
                }
                if (safeBitTest(key, z.b)) {
                    z.right = p;
                }
                else {
                    z.left = p;
                }
                p.left = x.left;
                p.right = x.right;
                p.b = x.b;
            }
            --this.count;
        }
    }
    
    public boolean contains(final String key) {
        return this.get(key) != null;
    }
    
    boolean isEmpty() {
        return this.count == 0;
    }
    
    int size() {
        return this.count;
    }
    
    public Iterable<String> keys() {
        final Queue<String> queue = new Queue<String>();
        if (this.head.left != this.head) {
            this.keys(this.head.left, 0, queue);
        }
        if (this.head.right != this.head) {
            this.keys(this.head.right, 0, queue);
        }
        return queue;
    }
    
    private void keys(final Node x, final int b, final Queue<String> queue) {
        if (x.b > b) {
            this.keys(x.left, x.b, queue);
            queue.enqueue(x.key);
            this.keys(x.right, x.b, queue);
        }
    }
    
    private static boolean safeBitTest(final String key, final int b) {
        if (b < key.length() * 16) {
            return bitTest(key, b) != 0;
        }
        return b <= key.length() * 16 + 15;
    }
    
    private static int bitTest(final String key, final int b) {
        return key.charAt(b >>> 4) >>> (b & 0xF) & 0x1;
    }
    
    private static int safeCharAt(final String key, final int i) {
        if (i < key.length()) {
            return key.charAt(i);
        }
        if (i > key.length()) {
            return 0;
        }
        return 65535;
    }
    
    private static int firstDifferingBit(final String k1, final String k2) {
        int i = 0;
        int c1 = safeCharAt(k1, 0) & 0xFFFFFFFE;
        int c2 = safeCharAt(k2, 0) & 0xFFFFFFFE;
        if (c1 == c2) {
            for (i = 1; safeCharAt(k1, i) == safeCharAt(k2, i); ++i) {}
            c1 = safeCharAt(k1, i);
            c2 = safeCharAt(k2, i);
        }
        int b;
        for (b = 0; (c1 >>> b & 0x1) == (c2 >>> b & 0x1); ++b) {}
        return i * 16 + b;
    }
    
    public static void main(final String[] args) {
        final PatriciaST<Integer> st = new PatriciaST<Integer>();
        int limitItem = 1000000;
        int limitPass = 1;
        int countPass = 0;
        boolean ok = true;
        if (args.length > 0) {
            limitItem = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            limitPass = Integer.parseInt(args[1]);
        }
        do {
            final String[] a = new String[limitItem];
            final int[] v = new int[limitItem];
            StdOut.printf("Creating dataset (%d items)...\n", limitItem);
            for (int i = 0; i < limitItem; ++i) {
                a[i] = Integer.toString(i, 16);
                v[i] = i;
            }
            StdOut.printf("Shuffling...\n", new Object[0]);
            StdRandom.shuffle(v);
            StdOut.printf("Adding (%d items)...\n", limitItem);
            for (int i = 0; i < limitItem; ++i) {
                st.put(a[v[i]], v[i]);
            }
            int countKeys = 0;
            StdOut.printf("Iterating...\n", new Object[0]);
            for (final String key : st.keys()) {
                ++countKeys;
            }
            StdOut.printf("%d items iterated\n", countKeys);
            if (countKeys != limitItem) {
                ok = false;
            }
            if (countKeys != st.size()) {
                ok = false;
            }
            StdOut.printf("Shuffling...\n", new Object[0]);
            StdRandom.shuffle(v);
            final int limitDelete = limitItem / 2;
            StdOut.printf("Deleting (%d items)...\n", limitDelete);
            for (int j = 0; j < limitDelete; ++j) {
                st.delete(a[v[j]]);
            }
            countKeys = 0;
            StdOut.printf("Iterating...\n", new Object[0]);
            for (final String key2 : st.keys()) {
                ++countKeys;
            }
            StdOut.printf("%d items iterated\n", countKeys);
            if (countKeys != limitItem - limitDelete) {
                ok = false;
            }
            if (countKeys != st.size()) {
                ok = false;
            }
            int countDelete = 0;
            int countRemain = 0;
            StdOut.printf("Checking...\n", new Object[0]);
            for (int k = 0; k < limitItem; ++k) {
                if (k < limitDelete) {
                    if (!st.contains(a[v[k]])) {
                        ++countDelete;
                    }
                }
                else {
                    final int val = st.get(a[v[k]]);
                    if (val == v[k]) {
                        ++countRemain;
                    }
                }
            }
            StdOut.printf("%d items found and %d (deleted) items missing\n", countRemain, countDelete);
            if (countRemain + countDelete != limitItem) {
                ok = false;
            }
            if (countRemain != st.size()) {
                ok = false;
            }
            if (st.isEmpty()) {
                ok = false;
            }
            StdOut.printf("Deleting the rest (%d items)...\n", limitItem - countDelete);
            for (int k = countDelete; k < limitItem; ++k) {
                st.delete(a[v[k]]);
            }
            if (!st.isEmpty()) {
                ok = false;
            }
            ++countPass;
            if (ok) {
                StdOut.printf("PASS %d TESTS SUCCEEDED\n", countPass);
            }
            else {
                StdOut.printf("PASS %d TESTS FAILED\n", countPass);
            }
        } while (ok && countPass < limitPass);
        if (!ok) {
            throw new RuntimeException("TESTS FAILED");
        }
    }
    
    private class Node
    {
        private Node left;
        private Node right;
        private String key;
        private Value val;
        private int b;
        
        public Node(final String key, final Value val, final int b) {
            this.key = key;
            this.val = val;
            this.b = b;
        }
    }
}
