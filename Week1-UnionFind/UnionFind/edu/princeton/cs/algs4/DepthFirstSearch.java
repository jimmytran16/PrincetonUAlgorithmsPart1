// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DepthFirstSearch
{
    private boolean[] marked;
    private int count;
    
    public DepthFirstSearch(final Graph G, final int s) {
        this.marked = new boolean[G.V()];
        this.validateVertex(s);
        this.dfs(G, s);
    }
    
    private void dfs(final Graph G, final int v) {
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
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Graph G = new Graph(in);
        final int s = Integer.parseInt(args[1]);
        final DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (search.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
        if (search.count() != G.V()) {
            StdOut.println("NOT connected");
        }
        else {
            StdOut.println("connected");
        }
    }
}
