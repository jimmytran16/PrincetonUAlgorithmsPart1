// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class TarjanSCC
{
    private boolean[] marked;
    private int[] id;
    private int[] low;
    private int pre;
    private int count;
    private Stack<Integer> stack;
    
    public TarjanSCC(final Digraph G) {
        this.marked = new boolean[G.V()];
        this.stack = new Stack<Integer>();
        this.id = new int[G.V()];
        this.low = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
        assert this.check(G);
    }
    
    private void dfs(final Digraph G, final int v) {
        this.marked[v] = true;
        this.low[v] = this.pre++;
        int min = this.low[v];
        this.stack.push(v);
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.dfs(G, w);
            }
            if (this.low[w] < min) {
                min = this.low[w];
            }
        }
        if (min < this.low[v]) {
            this.low[v] = min;
            return;
        }
        int w2;
        do {
            w2 = this.stack.pop();
            this.id[w2] = this.count;
            this.low[w2] = G.V();
        } while (w2 != v);
        ++this.count;
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
        final TarjanSCC scc = new TarjanSCC(G);
        final int m = scc.count();
        StdOut.println(m + " components");
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
