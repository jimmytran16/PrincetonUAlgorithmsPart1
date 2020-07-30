// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;

public class SegmentTree
{
    private Node[] heap;
    private int[] array;
    private int size;
    
    public SegmentTree(final int[] array) {
        this.array = Arrays.copyOf(array, array.length);
        this.size = (int)(2.0 * Math.pow(2.0, Math.floor(Math.log(array.length) / Math.log(2.0) + 1.0)));
        this.heap = new Node[this.size];
        this.build(1, 0, array.length);
    }
    
    public int size() {
        return this.array.length;
    }
    
    private void build(final int v, final int from, final int size) {
        this.heap[v] = new Node();
        this.heap[v].from = from;
        this.heap[v].to = from + size - 1;
        if (size == 1) {
            this.heap[v].sum = this.array[from];
            this.heap[v].min = this.array[from];
        }
        else {
            this.build(2 * v, from, size / 2);
            this.build(2 * v + 1, from + size / 2, size - size / 2);
            this.heap[v].sum = this.heap[2 * v].sum + this.heap[2 * v + 1].sum;
            this.heap[v].min = Math.min(this.heap[2 * v].min, this.heap[2 * v + 1].min);
        }
    }
    
    public int rsq(final int from, final int to) {
        return this.rsq(1, from, to);
    }
    
    private int rsq(final int v, final int from, final int to) {
        final Node n = this.heap[v];
        if (n.pendingVal != null && this.contains(n.from, n.to, from, to)) {
            return (to - from + 1) * n.pendingVal;
        }
        if (this.contains(from, to, n.from, n.to)) {
            return this.heap[v].sum;
        }
        if (this.intersects(from, to, n.from, n.to)) {
            this.propagate(v);
            final int leftSum = this.rsq(2 * v, from, to);
            final int rightSum = this.rsq(2 * v + 1, from, to);
            return leftSum + rightSum;
        }
        return 0;
    }
    
    public int rMinQ(final int from, final int to) {
        return this.rMinQ(1, from, to);
    }
    
    private int rMinQ(final int v, final int from, final int to) {
        final Node n = this.heap[v];
        if (n.pendingVal != null && this.contains(n.from, n.to, from, to)) {
            return n.pendingVal;
        }
        if (this.contains(from, to, n.from, n.to)) {
            return this.heap[v].min;
        }
        if (this.intersects(from, to, n.from, n.to)) {
            this.propagate(v);
            final int leftMin = this.rMinQ(2 * v, from, to);
            final int rightMin = this.rMinQ(2 * v + 1, from, to);
            return Math.min(leftMin, rightMin);
        }
        return Integer.MAX_VALUE;
    }
    
    public void update(final int from, final int to, final int value) {
        this.update(1, from, to, value);
    }
    
    private void update(final int v, final int from, final int to, final int value) {
        final Node n = this.heap[v];
        if (this.contains(from, to, n.from, n.to)) {
            this.change(n, value);
        }
        if (n.size() == 1) {
            return;
        }
        if (this.intersects(from, to, n.from, n.to)) {
            this.propagate(v);
            this.update(2 * v, from, to, value);
            this.update(2 * v + 1, from, to, value);
            n.sum = this.heap[2 * v].sum + this.heap[2 * v + 1].sum;
            n.min = Math.min(this.heap[2 * v].min, this.heap[2 * v + 1].min);
        }
    }
    
    private void propagate(final int v) {
        final Node n = this.heap[v];
        if (n.pendingVal != null) {
            this.change(this.heap[2 * v], n.pendingVal);
            this.change(this.heap[2 * v + 1], n.pendingVal);
            n.pendingVal = null;
        }
    }
    
    private void change(final Node n, final int value) {
        n.pendingVal = value;
        n.sum = n.size() * value;
        n.min = value;
        this.array[n.from] = value;
    }
    
    private boolean contains(final int from1, final int to1, final int from2, final int to2) {
        return from2 >= from1 && to2 <= to1;
    }
    
    private boolean intersects(final int from1, final int to1, final int from2, final int to2) {
        return (from1 <= from2 && to1 >= from2) || (from1 >= from2 && from1 <= to2);
    }
    
    public static void main(final String[] args) {
        SegmentTree st = null;
        final String cmd = "cmp";
        while (true) {
            final String[] line = StdIn.readLine().split(" ");
            if (line[0].equals("exit")) {
                break;
            }
            int arg1 = 0;
            int arg2 = 0;
            int arg3 = 0;
            if (line.length > 1) {
                arg1 = Integer.parseInt(line[1]);
            }
            if (line.length > 2) {
                arg2 = Integer.parseInt(line[2]);
            }
            if (line.length > 3) {
                arg3 = Integer.parseInt(line[3]);
            }
            if (!line[0].equals("set") && !line[0].equals("init") && st == null) {
                StdOut.println("Segment Tree not initialized");
            }
            else if (line[0].equals("set")) {
                final int[] array = new int[line.length - 1];
                for (int i = 0; i < line.length - 1; ++i) {
                    array[i] = Integer.parseInt(line[i + 1]);
                }
                st = new SegmentTree(array);
            }
            else if (line[0].equals("init")) {
                final int[] array = new int[arg1];
                Arrays.fill(array, arg2);
                st = new SegmentTree(array);
                for (int i = 0; i < st.size(); ++i) {
                    StdOut.print(st.rsq(i, i) + " ");
                }
                StdOut.println();
            }
            else if (line[0].equals("up")) {
                st.update(arg1, arg2, arg3);
                for (int i = 0; i < st.size(); ++i) {
                    StdOut.print(st.rsq(i, i) + " ");
                }
                StdOut.println();
            }
            else if (line[0].equals("rsq")) {
                StdOut.printf("Sum from %d to %d = %d%n", arg1, arg2, st.rsq(arg1, arg2));
            }
            else if (line[0].equals("rmq")) {
                StdOut.printf("Min from %d to %d = %d%n", arg1, arg2, st.rMinQ(arg1, arg2));
            }
            else {
                StdOut.println("Invalid command");
            }
        }
    }
    
    static class Node
    {
        int sum;
        int min;
        Integer pendingVal;
        int from;
        int to;
        
        Node() {
            this.pendingVal = null;
        }
        
        int size() {
            return this.to - this.from + 1;
        }
    }
}
