// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class PrimMST
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    
    public PrimMST(final EdgeWeightedGraph G) {
        this.edgeTo = new Edge[G.V()];
        this.distTo = new double[G.V()];
        this.marked = new boolean[G.V()];
        this.pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Double.POSITIVE_INFINITY;
        }
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.prim(G, v);
            }
        }
        assert this.check(G);
    }
    
    private void prim(final EdgeWeightedGraph G, final int s) {
        this.distTo[s] = 0.0;
        this.pq.insert(s, this.distTo[s]);
        while (!this.pq.isEmpty()) {
            final int v = this.pq.delMin();
            this.scan(G, v);
        }
    }
    
    private void scan(final EdgeWeightedGraph G, final int v) {
        this.marked[v] = true;
        for (final Edge e : G.adj(v)) {
            final int w = e.other(v);
            if (this.marked[w]) {
                continue;
            }
            if (e.weight() >= this.distTo[w]) {
                continue;
            }
            this.distTo[w] = e.weight();
            this.edgeTo[w] = e;
            if (this.pq.contains(w)) {
                this.pq.decreaseKey(w, this.distTo[w]);
            }
            else {
                this.pq.insert(w, this.distTo[w]);
            }
        }
    }
    
    public Iterable<Edge> edges() {
        final Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < this.edgeTo.length; ++v) {
            final Edge e = this.edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }
    
    public double weight() {
        double weight = 0.0;
        for (final Edge e : this.edges()) {
            weight += e.weight();
        }
        return weight;
    }
    
    private boolean check(final EdgeWeightedGraph G) {
        double totalWeight = 0.0;
        for (final Edge e : this.edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - this.weight()) > 1.0E-12) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, this.weight());
            return false;
        }
        UF uf = new UF(G.V());
        for (final Edge e2 : this.edges()) {
            final int v = e2.either();
            final int w = e2.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }
        for (final Edge e2 : G.edges()) {
            final int v = e2.either();
            final int w = e2.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }
        for (final Edge e2 : this.edges()) {
            uf = new UF(G.V());
            for (final Edge f : this.edges()) {
                final int x = f.either();
                final int y = f.other(x);
                if (f != e2) {
                    uf.union(x, y);
                }
            }
            for (final Edge f : G.edges()) {
                final int x = f.either();
                final int y = f.other(x);
                if (uf.find(x) != uf.find(y) && f.weight() < e2.weight()) {
                    System.err.println("Edge " + f + " violates cut optimality conditions");
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        final PrimMST mst = new PrimMST(G);
        for (final Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
