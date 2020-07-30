// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DepthFirstOrder
{
    private boolean[] marked;
    private int[] pre;
    private int[] post;
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;
    private int preCounter;
    private int postCounter;
    
    public DepthFirstOrder(final Digraph G) {
        this.pre = new int[G.V()];
        this.post = new int[G.V()];
        this.postorder = new Queue<Integer>();
        this.preorder = new Queue<Integer>();
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
        assert this.check();
    }
    
    public DepthFirstOrder(final EdgeWeightedDigraph G) {
        this.pre = new int[G.V()];
        this.post = new int[G.V()];
        this.postorder = new Queue<Integer>();
        this.preorder = new Queue<Integer>();
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
    }
    
    private void dfs(final Digraph G, final int v) {
        this.marked[v] = true;
        this.pre[v] = this.preCounter++;
        this.preorder.enqueue(v);
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
        this.postorder.enqueue(v);
        this.post[v] = this.postCounter++;
    }
    
    private void dfs(final EdgeWeightedDigraph G, final int v) {
        this.marked[v] = true;
        this.pre[v] = this.preCounter++;
        this.preorder.enqueue(v);
        for (final DirectedEdge e : G.adj(v)) {
            final int w = e.to();
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
        this.postorder.enqueue(v);
        this.post[v] = this.postCounter++;
    }
    
    public int pre(final int v) {
        this.validateVertex(v);
        return this.pre[v];
    }
    
    public int post(final int v) {
        this.validateVertex(v);
        return this.post[v];
    }
    
    public Iterable<Integer> post() {
        return this.postorder;
    }
    
    public Iterable<Integer> pre() {
        return this.preorder;
    }
    
    public Iterable<Integer> reversePost() {
        final Stack<Integer> reverse = new Stack<Integer>();
        for (final int v : this.postorder) {
            reverse.push(v);
        }
        return reverse;
    }
    
    private boolean check() {
        int r = 0;
        for (final int v : this.post()) {
            if (this.post(v) != r) {
                StdOut.println("post(v) and post() inconsistent");
                return false;
            }
            ++r;
        }
        r = 0;
        for (final int v : this.pre()) {
            if (this.pre(v) != r) {
                StdOut.println("pre(v) and pre() inconsistent");
                return false;
            }
            ++r;
        }
        return true;
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   v  pre post");
        StdOut.println("--------------");
        for (int v = 0; v < G.V(); ++v) {
            StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }
        StdOut.print("Preorder:  ");
        for (final int v2 : dfs.pre()) {
            StdOut.print(v2 + " ");
        }
        StdOut.println();
        StdOut.print("Postorder: ");
        for (final int v2 : dfs.post()) {
            StdOut.print(v2 + " ");
        }
        StdOut.println();
        StdOut.print("Reverse postorder: ");
        for (final int v2 : dfs.reversePost()) {
            StdOut.print(v2 + " ");
        }
        StdOut.println();
    }
}
