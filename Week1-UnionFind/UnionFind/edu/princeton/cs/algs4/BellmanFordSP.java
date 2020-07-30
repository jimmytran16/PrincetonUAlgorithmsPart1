// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class BellmanFordSP
{
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private Queue<Integer> queue;
    private int cost;
    private Iterable<DirectedEdge> cycle;
    
    public BellmanFordSP(final EdgeWeightedDigraph G, final int s) {
        this.distTo = new double[G.V()];
        this.edgeTo = new DirectedEdge[G.V()];
        this.onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        (this.queue = new Queue<Integer>()).enqueue(s);
        this.onQueue[s] = true;
        while (!this.queue.isEmpty() && !this.hasNegativeCycle()) {
            final int v = this.queue.dequeue();
            this.onQueue[v] = false;
            this.relax(G, v);
        }
        assert this.check(G, s);
    }
    
    private void relax(final EdgeWeightedDigraph G, final int v) {
        for (final DirectedEdge e : G.adj(v)) {
            final int w = e.to();
            if (this.distTo[w] > this.distTo[v] + e.weight()) {
                this.distTo[w] = this.distTo[v] + e.weight();
                this.edgeTo[w] = e;
                if (!this.onQueue[w]) {
                    this.queue.enqueue(w);
                    this.onQueue[w] = true;
                }
            }
            if (++this.cost % G.V() == 0) {
                this.findNegativeCycle();
                if (this.hasNegativeCycle()) {
                    return;
                }
                continue;
            }
        }
    }
    
    public boolean hasNegativeCycle() {
        return this.cycle != null;
    }
    
    public Iterable<DirectedEdge> negativeCycle() {
        return this.cycle;
    }
    
    private void findNegativeCycle() {
        final int V = this.edgeTo.length;
        final EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; ++v) {
            if (this.edgeTo[v] != null) {
                spt.addEdge(this.edgeTo[v]);
            }
        }
        final EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        this.cycle = finder.cycle();
    }
    
    public double distTo(final int v) {
        this.validateVertex(v);
        if (this.hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return this.distTo[v];
    }
    
    public boolean hasPathTo(final int v) {
        this.validateVertex(v);
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public Iterable<DirectedEdge> pathTo(final int v) {
        this.validateVertex(v);
        if (this.hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!this.hasPathTo(v)) {
            return null;
        }
        final Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = this.edgeTo[v]; e != null; e = this.edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
    
    private boolean check(final EdgeWeightedDigraph G, final int s) {
        if (this.hasNegativeCycle()) {
            double weight = 0.0;
            for (final DirectedEdge e : this.negativeCycle()) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }
        else {
            if (this.distTo[s] != 0.0 || this.edgeTo[s] != null) {
                System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.V(); ++v) {
                if (v != s) {
                    if (this.edgeTo[v] == null && this.distTo[v] != Double.POSITIVE_INFINITY) {
                        System.err.println("distTo[] and edgeTo[] inconsistent");
                        return false;
                    }
                }
            }
            for (int v = 0; v < G.V(); ++v) {
                for (final DirectedEdge e2 : G.adj(v)) {
                    final int w = e2.to();
                    if (this.distTo[v] + e2.weight() < this.distTo[w]) {
                        System.err.println("edge " + e2 + " not relaxed");
                        return false;
                    }
                }
            }
            for (int w2 = 0; w2 < G.V(); ++w2) {
                if (this.edgeTo[w2] != null) {
                    final DirectedEdge e3 = this.edgeTo[w2];
                    final int v2 = e3.from();
                    if (w2 != e3.to()) {
                        return false;
                    }
                    if (this.distTo[v2] + e3.weight() != this.distTo[w2]) {
                        System.err.println("edge " + e3 + " on shortest path not tight");
                        return false;
                    }
                }
            }
        }
        StdOut.println("Satisfies optimality conditions");
        StdOut.println();
        return true;
    }
    
    private void validateVertex(final int v) {
        final int V = this.distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int s = Integer.parseInt(args[1]);
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        final BellmanFordSP sp = new BellmanFordSP(G, s);
        if (sp.hasNegativeCycle()) {
            for (final DirectedEdge e : sp.negativeCycle()) {
                StdOut.println(e);
            }
        }
        else {
            for (int v = 0; v < G.V(); ++v) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (final DirectedEdge e2 : sp.pathTo(v)) {
                        StdOut.print(e2 + "   ");
                    }
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d           no path\n", s, v);
                }
            }
        }
    }
}
