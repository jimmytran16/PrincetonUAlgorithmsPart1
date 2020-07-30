// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class KruskalMST
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private double weight;
    private Queue<Edge> mst;
    
    public KruskalMST(final EdgeWeightedGraph G) {
        this.mst = new Queue<Edge>();
        final MinPQ<Edge> pq = new MinPQ<Edge>();
        for (final Edge e : G.edges()) {
            pq.insert(e);
        }
        final UF uf = new UF(G.V());
        while (!pq.isEmpty() && this.mst.size() < G.V() - 1) {
            final Edge e = pq.delMin();
            final int v = e.either();
            final int w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                this.mst.enqueue(e);
                this.weight += e.weight();
            }
        }
        assert this.check(G);
    }
    
    public Iterable<Edge> edges() {
        return this.mst;
    }
    
    public double weight() {
        return this.weight;
    }
    
    private boolean check(final EdgeWeightedGraph G) {
        double total = 0.0;
        for (final Edge e : this.edges()) {
            total += e.weight();
        }
        if (Math.abs(total - this.weight()) > 1.0E-12) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, this.weight());
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
            for (final Edge f : this.mst) {
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
        final KruskalMST mst = new KruskalMST(G);
        for (final Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
