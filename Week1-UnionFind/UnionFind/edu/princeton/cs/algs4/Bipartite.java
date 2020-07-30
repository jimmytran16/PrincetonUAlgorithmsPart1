// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class Bipartite
{
    private boolean isBipartite;
    private boolean[] color;
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    
    public Bipartite(final Graph G) {
        this.isBipartite = true;
        this.color = new boolean[G.V()];
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
        assert this.check(G);
    }
    
    private void dfs(final Graph G, final int v) {
        this.marked[v] = true;
        for (final int w : G.adj(v)) {
            if (this.cycle != null) {
                return;
            }
            if (!this.marked[w]) {
                this.edgeTo[w] = v;
                this.color[w] = !this.color[v];
                this.dfs(G, w);
            }
            else {
                if (this.color[w] != this.color[v]) {
                    continue;
                }
                this.isBipartite = false;
                (this.cycle = new Stack<Integer>()).push(w);
                for (int x = v; x != w; x = this.edgeTo[x]) {
                    this.cycle.push(x);
                }
                this.cycle.push(w);
            }
        }
    }
    
    public boolean isBipartite() {
        return this.isBipartite;
    }
    
    public boolean color(final int v) {
        this.validateVertex(v);
        if (!this.isBipartite) {
            throw new UnsupportedOperationException("graph is not bipartite");
        }
        return this.color[v];
    }
    
    public Iterable<Integer> oddCycle() {
        return this.cycle;
    }
    
    private boolean check(final Graph G) {
        if (this.isBipartite) {
            for (int v = 0; v < G.V(); ++v) {
                for (final int w : G.adj(v)) {
                    if (this.color[v] == this.color[w]) {
                        System.err.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
                        return false;
                    }
                }
            }
        }
        else {
            int first = -1;
            int last = -1;
            for (final int v2 : this.oddCycle()) {
                if (first == -1) {
                    first = v2;
                }
                last = v2;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
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
        final int V1 = Integer.parseInt(args[0]);
        final int V2 = Integer.parseInt(args[1]);
        final int E = Integer.parseInt(args[2]);
        final int F = Integer.parseInt(args[3]);
        final Graph G = GraphGenerator.bipartite(V1, V2, E);
        for (int i = 0; i < F; ++i) {
            final int v = StdRandom.uniform(V1 + V2);
            final int w = StdRandom.uniform(V1 + V2);
            G.addEdge(v, w);
        }
        StdOut.println(G);
        final Bipartite b = new Bipartite(G);
        if (b.isBipartite()) {
            StdOut.println("Graph is bipartite");
            for (int v = 0; v < G.V(); ++v) {
                StdOut.println(v + ": " + b.color(v));
            }
        }
        else {
            StdOut.print("Graph has an odd-length cycle: ");
            for (final int x : b.oddCycle()) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }
    }
}
