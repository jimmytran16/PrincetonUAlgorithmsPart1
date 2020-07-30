// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class CC
{
    private boolean[] marked;
    private int[] id;
    private int[] size;
    private int count;
    
    public CC(final Graph G) {
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        this.size = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
                ++this.count;
            }
        }
    }
    
    public CC(final EdgeWeightedGraph G) {
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        this.size = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
                ++this.count;
            }
        }
    }
    
    private void dfs(final Graph G, final int v) {
        this.marked[v] = true;
        this.id[v] = this.count;
        final int[] size = this.size;
        final int count = this.count;
        ++size[count];
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
    }
    
    private void dfs(final EdgeWeightedGraph G, final int v) {
        this.marked[v] = true;
        this.id[v] = this.count;
        final int[] size = this.size;
        final int count = this.count;
        ++size[count];
        for (final Edge e : G.adj(v)) {
            final int w = e.other(v);
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
    }
    
    public int id(final int v) {
        this.validateVertex(v);
        return this.id[v];
    }
    
    public int size(final int v) {
        this.validateVertex(v);
        return this.size[this.id[v]];
    }
    
    public int count() {
        return this.count;
    }
    
    public boolean connected(final int v, final int w) {
        this.validateVertex(v);
        this.validateVertex(w);
        return this.id(v) == this.id(w);
    }
    
    @Deprecated
    public boolean areConnected(final int v, final int w) {
        this.validateVertex(v);
        this.validateVertex(w);
        return this.id(v) == this.id(w);
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Graph G = new Graph(in);
        final CC cc = new CC(G);
        final int m = cc.count();
        StdOut.println(m + " components");
        final Queue<Integer>[] components = (Queue<Integer>[])new Queue[m];
        for (int i = 0; i < m; ++i) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); ++v) {
            components[cc.id(v)].enqueue(v);
        }
        for (int i = 0; i < m; ++i) {
            for (final int v2 : components[i]) {
                StdOut.print(v2 + " ");
            }
            StdOut.println();
        }
    }
}
