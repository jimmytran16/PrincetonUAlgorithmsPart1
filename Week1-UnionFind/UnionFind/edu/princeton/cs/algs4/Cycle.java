// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class Cycle
{
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    
    public Cycle(final Graph G) {
        if (this.hasSelfLoop(G)) {
            return;
        }
        if (this.hasParallelEdges(G)) {
            return;
        }
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, -1, v);
            }
        }
    }
    
    private boolean hasSelfLoop(final Graph G) {
        for (int v = 0; v < G.V(); ++v) {
            for (final int w : G.adj(v)) {
                if (v == w) {
                    (this.cycle = new Stack<Integer>()).push(v);
                    this.cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasParallelEdges(final Graph G) {
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            for (final int w : G.adj(v)) {
                if (this.marked[w]) {
                    (this.cycle = new Stack<Integer>()).push(v);
                    this.cycle.push(w);
                    this.cycle.push(v);
                    return true;
                }
                this.marked[w] = true;
            }
            for (final int w : G.adj(v)) {
                this.marked[w] = false;
            }
        }
        return false;
    }
    
    public boolean hasCycle() {
        return this.cycle != null;
    }
    
    public Iterable<Integer> cycle() {
        return this.cycle;
    }
    
    private void dfs(final Graph G, final int u, final int v) {
        this.marked[v] = true;
        for (final int w : G.adj(v)) {
            if (this.cycle != null) {
                return;
            }
            if (!this.marked[w]) {
                this.dfs(G, this.edgeTo[w] = v, w);
            }
            else {
                if (w == u) {
                    continue;
                }
                this.cycle = new Stack<Integer>();
                for (int x = v; x != w; x = this.edgeTo[x]) {
                    this.cycle.push(x);
                }
                this.cycle.push(w);
                this.cycle.push(v);
            }
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Graph G = new Graph(in);
        final Cycle finder = new Cycle(G);
        if (finder.hasCycle()) {
            for (final int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("Graph is acyclic");
        }
    }
}
