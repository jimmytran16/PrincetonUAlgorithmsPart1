// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DirectedDFS
{
    private boolean[] marked;
    private int count;
    
    public DirectedDFS(final Digraph G, final int s) {
        this.marked = new boolean[G.V()];
        this.validateVertex(s);
        this.dfs(G, s);
    }
    
    public DirectedDFS(final Digraph G, final Iterable<Integer> sources) {
        this.marked = new boolean[G.V()];
        this.validateVertices(sources);
        for (final int v : sources) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
    }
    
    private void dfs(final Digraph G, final int v) {
        ++this.count;
        this.marked[v] = true;
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
    }
    
    public boolean marked(final int v) {
        this.validateVertex(v);
        return this.marked[v];
    }
    
    public int count() {
        return this.count;
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    private void validateVertices(final Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (final Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            this.validateVertex(v);
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final Bag<Integer> sources = new Bag<Integer>();
        for (int i = 1; i < args.length; ++i) {
            final int s = Integer.parseInt(args[i]);
            sources.add(s);
        }
        final DirectedDFS dfs = new DirectedDFS(G, sources);
        for (int v = 0; v < G.V(); ++v) {
            if (dfs.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }
}
