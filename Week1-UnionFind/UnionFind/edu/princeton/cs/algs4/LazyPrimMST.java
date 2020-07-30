// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class LazyPrimMST
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private double weight;
    private Queue<Edge> mst;
    private boolean[] marked;
    private MinPQ<Edge> pq;
    
    public LazyPrimMST(final EdgeWeightedGraph G) {
        this.mst = new Queue<Edge>();
        this.pq = new MinPQ<Edge>();
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v]) {
                this.prim(G, v);
            }
        }
        assert this.check(G);
    }
    
    private void prim(final EdgeWeightedGraph G, final int s) {
        this.scan(G, s);
        while (!this.pq.isEmpty()) {
            final Edge e = this.pq.delMin();
            final int v = e.either();
            final int w = e.other(v);
            assert this.marked[v] || this.marked[w];
            if (this.marked[v] && this.marked[w]) {
                continue;
            }
            this.mst.enqueue(e);
            this.weight += e.weight();
            if (!this.marked[v]) {
                this.scan(G, v);
            }
            if (this.marked[w]) {
                continue;
            }
            this.scan(G, w);
        }
    }
    
    private void scan(final EdgeWeightedGraph G, final int v) {
        assert !this.marked[v];
        this.marked[v] = true;
        for (final Edge e : G.adj(v)) {
            if (!this.marked[e.other(v)]) {
                this.pq.insert(e);
            }
        }
    }
    
    public Iterable<Edge> edges() {
        return this.mst;
    }
    
    public double weight() {
        return this.weight;
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
        final LazyPrimMST mst = new LazyPrimMST(G);
        for (final Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
