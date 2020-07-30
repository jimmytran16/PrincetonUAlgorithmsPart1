// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DijkstraUndirectedSP
{
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;
    
    public DijkstraUndirectedSP(final EdgeWeightedGraph G, final int s) {
        for (final Edge e : G.edges()) {
            if (e.weight() < 0.0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }
        this.distTo = new double[G.V()];
        this.edgeTo = new Edge[G.V()];
        this.validateVertex(s);
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        (this.pq = new IndexMinPQ<Double>(G.V())).insert(s, this.distTo[s]);
        while (!this.pq.isEmpty()) {
            final int v = this.pq.delMin();
            for (final Edge e2 : G.adj(v)) {
                this.relax(e2, v);
            }
        }
        assert this.check(G, s);
    }
    
    private void relax(final Edge e, final int v) {
        final int w = e.other(v);
        if (this.distTo[w] > this.distTo[v] + e.weight()) {
            this.distTo[w] = this.distTo[v] + e.weight();
            this.edgeTo[w] = e;
            if (this.pq.contains(w)) {
                this.pq.decreaseKey(w, this.distTo[w]);
            }
            else {
                this.pq.insert(w, this.distTo[w]);
            }
        }
    }
    
    public double distTo(final int v) {
        this.validateVertex(v);
        return this.distTo[v];
    }
    
    public boolean hasPathTo(final int v) {
        this.validateVertex(v);
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public Iterable<Edge> pathTo(final int v) {
        this.validateVertex(v);
        if (!this.hasPathTo(v)) {
            return null;
        }
        final Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = this.edgeTo[v]; e != null; e = this.edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }
    
    private boolean check(final EdgeWeightedGraph G, final int s) {
        for (final Edge e : G.edges()) {
            if (e.weight() < 0.0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }
        if (this.distTo[s] != 0.0 || this.edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
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
            for (final Edge e2 : G.adj(v)) {
                final int w = e2.other(v);
                if (this.distTo[v] + e2.weight() < this.distTo[w]) {
                    System.err.println("edge " + e2 + " not relaxed");
                    return false;
                }
            }
        }
        for (int w2 = 0; w2 < G.V(); ++w2) {
            if (this.edgeTo[w2] != null) {
                final Edge e = this.edgeTo[w2];
                if (w2 != e.either() && w2 != e.other(e.either())) {
                    return false;
                }
                final int v2 = e.other(w2);
                if (this.distTo[v2] + e.weight() != this.distTo[w2]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
        }
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
        final EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        final int s = Integer.parseInt(args[1]);
        final DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);
        for (int t = 0; t < G.V(); ++t) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (final Edge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }
}
