// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class BoruvkaMST
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private Bag<Edge> mst;
    private double weight;
    
    public BoruvkaMST(final EdgeWeightedGraph G) {
        this.mst = new Bag<Edge>();
        final UF uf = new UF(G.V());
        for (int t = 1; t < G.V() && this.mst.size() < G.V() - 1; t += t) {
            final Edge[] closest = new Edge[G.V()];
            for (final Edge e : G.edges()) {
                final int v = e.either();
                final int w = e.other(v);
                final int i = uf.find(v);
                final int j = uf.find(w);
                if (i == j) {
                    continue;
                }
                if (closest[i] == null || less(e, closest[i])) {
                    closest[i] = e;
                }
                if (closest[j] != null && !less(e, closest[j])) {
                    continue;
                }
                closest[j] = e;
            }
            for (int k = 0; k < G.V(); ++k) {
                final Edge e = closest[k];
                if (e != null) {
                    final int v = e.either();
                    final int w = e.other(v);
                    if (uf.find(v) != uf.find(w)) {
                        this.mst.add(e);
                        this.weight += e.weight();
                        uf.union(v, w);
                    }
                }
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
    
    private static boolean less(final Edge e, final Edge f) {
        return e.compareTo(f) < 0;
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
        final BoruvkaMST mst = new BoruvkaMST(G);
        for (final Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
