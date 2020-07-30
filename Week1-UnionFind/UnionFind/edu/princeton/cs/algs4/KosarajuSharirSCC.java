// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class KosarajuSharirSCC
{
    private boolean[] marked;
    private int[] id;
    private int count;
    
    public KosarajuSharirSCC(final Digraph G) {
        final DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        for (final int v : dfs.reversePost()) {
            if (!this.marked[v]) {
                this.dfs(G, v);
                ++this.count;
            }
        }
        assert this.check(G);
    }
    
    private void dfs(final Digraph G, final int v) {
        this.marked[v] = true;
        this.id[v] = this.count;
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
        }
    }
    
    public int count() {
        return this.count;
    }
    
    public boolean stronglyConnected(final int v, final int w) {
        this.validateVertex(v);
        this.validateVertex(w);
        return this.id[v] == this.id[w];
    }
    
    public int id(final int v) {
        this.validateVertex(v);
        return this.id[v];
    }
    
    private boolean check(final Digraph G) {
        final TransitiveClosure tc = new TransitiveClosure(G);
        for (int v = 0; v < G.V(); ++v) {
            for (int w = 0; w < G.V(); ++w) {
                if (this.stronglyConnected(v, w) != (tc.reachable(v, w) && tc.reachable(w, v))) {
                    return false;
                }
            }
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
        final KosarajuSharirSCC scc = new KosarajuSharirSCC(G);
        final int m = scc.count();
        StdOut.println(m + " strong components");
        final Queue<Integer>[] components = (Queue<Integer>[])new Queue[m];
        for (int i = 0; i < m; ++i) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); ++v) {
            components[scc.id(v)].enqueue(v);
        }
        for (int i = 0; i < m; ++i) {
            for (final int v2 : components[i]) {
                StdOut.print(v2 + " ");
            }
            StdOut.println();
        }
    }
}
