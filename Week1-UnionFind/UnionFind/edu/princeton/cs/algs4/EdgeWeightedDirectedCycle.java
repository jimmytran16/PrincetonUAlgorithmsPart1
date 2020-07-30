// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class EdgeWeightedDirectedCycle
{
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private boolean[] onStack;
    private Stack<DirectedEdge> cycle;
    
    public EdgeWeightedDirectedCycle(final EdgeWeightedDigraph G) {
        this.marked = new boolean[G.V()];
        this.onStack = new boolean[G.V()];
        this.edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.dfs(G, v);
            }
        }
        assert this.check();
    }
    
    private void dfs(final EdgeWeightedDigraph G, final int v) {
        this.onStack[v] = true;
        this.marked[v] = true;
        for (final DirectedEdge e : G.adj(v)) {
            final int w = e.to();
            if (this.cycle != null) {
                return;
            }
            if (!this.marked[w]) {
                this.edgeTo[w] = e;
                this.dfs(G, w);
            }
            else {
                if (this.onStack[w]) {
                    this.cycle = new Stack<DirectedEdge>();
                    DirectedEdge f;
                    for (f = e; f.from() != w; f = this.edgeTo[f.from()]) {
                        this.cycle.push(f);
                    }
                    this.cycle.push(f);
                    return;
                }
                continue;
            }
        }
        this.onStack[v] = false;
    }
    
    public boolean hasCycle() {
        return this.cycle != null;
    }
    
    public Iterable<DirectedEdge> cycle() {
        return this.cycle;
    }
    
    private boolean check() {
        if (this.hasCycle()) {
            DirectedEdge first = null;
            DirectedEdge last = null;
            for (final DirectedEdge e : this.cycle()) {
                if (first == null) {
                    first = e;
                }
                if (last != null && last.to() != e.from()) {
                    System.err.printf("cycle edges %s and %s not incident\n", last, e);
                    return false;
                }
                last = e;
            }
            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final int F = Integer.parseInt(args[2]);
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < E; ++i) {
            int v;
            int w;
            do {
                v = StdRandom.uniform(V);
                w = StdRandom.uniform(V);
            } while (v >= w);
            final double weight = StdRandom.uniform();
            G.addEdge(new DirectedEdge(v, w, weight));
        }
        for (int i = 0; i < F; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double weight = StdRandom.uniform(0.0, 1.0);
            G.addEdge(new DirectedEdge(v, w, weight));
        }
        StdOut.println(G);
        final EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Cycle: ");
            for (final DirectedEdge e : finder.cycle()) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No directed cycle");
        }
    }
}
