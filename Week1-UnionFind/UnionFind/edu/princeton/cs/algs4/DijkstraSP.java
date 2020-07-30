// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DijkstraSP
{
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    
    public DijkstraSP(final EdgeWeightedDigraph G, final int s) {
        for (final DirectedEdge e : G.edges()) {
            if (e.weight() < 0.0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }
        this.distTo = new double[G.V()];
        this.edgeTo = new DirectedEdge[G.V()];
        this.validateVertex(s);
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        (this.pq = new IndexMinPQ<Double>(G.V())).insert(s, this.distTo[s]);
        while (!this.pq.isEmpty()) {
            final int v = this.pq.delMin();
            for (final DirectedEdge e2 : G.adj(v)) {
                this.relax(e2);
            }
        }
        assert this.check(G, s);
    }
    
    private void relax(final DirectedEdge e) {
        final int v = e.from();
        final int w = e.to();
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
    
    public Iterable<DirectedEdge> pathTo(final int v) {
        this.validateVertex(v);
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
        for (final DirectedEdge e : G.edges()) {
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
                final DirectedEdge e = this.edgeTo[w2];
                final int v2 = e.from();
                if (w2 != e.to()) {
                    return false;
                }
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
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        final int s = Integer.parseInt(args[1]);
        final DijkstraSP sp = new DijkstraSP(G, s);
        for (int t = 0; t < G.V(); ++t) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (final DirectedEdge e : sp.pathTo(t)) {
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
