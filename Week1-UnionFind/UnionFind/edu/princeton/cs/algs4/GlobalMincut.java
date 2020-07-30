// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class GlobalMincut
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-11;
    private double weight;
    private boolean[] cut;
    private int V;
    
    public GlobalMincut(final EdgeWeightedGraph G) {
        this.weight = Double.POSITIVE_INFINITY;
        this.V = G.V();
        this.validate(G);
        this.minCut(G, 0);
        assert this.check(G);
    }
    
    private void validate(final EdgeWeightedGraph G) {
        if (G.V() < 2) {
            throw new IllegalArgumentException("number of vertices of G is less than 2");
        }
        for (final Edge e : G.edges()) {
            if (e.weight() < 0.0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }
    }
    
    public double weight() {
        return this.weight;
    }
    
    public boolean cut(final int v) {
        this.validateVertex(v);
        return this.cut[v];
    }
    
    private void makeCut(final int t, final UF uf) {
        for (int v = 0; v < this.cut.length; ++v) {
            this.cut[v] = (uf.find(v) == uf.find(t));
        }
    }
    
    private void minCut(EdgeWeightedGraph G, final int a) {
        final UF uf = new UF(G.V());
        final boolean[] marked = new boolean[G.V()];
        this.cut = new boolean[G.V()];
        CutPhase cp = new CutPhase(0.0, a, a);
        for (int v = G.V(); v > 1; --v) {
            cp = this.minCutPhase(G, marked, cp);
            if (cp.weight < this.weight) {
                this.weight = cp.weight;
                this.makeCut(cp.t, uf);
            }
            G = this.contractEdge(G, cp.s, cp.t);
            marked[cp.t] = true;
            uf.union(cp.s, cp.t);
        }
    }
    
    private CutPhase minCutPhase(final EdgeWeightedGraph G, final boolean[] marked, final CutPhase cp) {
        final IndexMaxPQ<Double> pq = new IndexMaxPQ<Double>(G.V());
        for (int v = 0; v < G.V(); ++v) {
            if (v != cp.s && !marked[v]) {
                pq.insert(v, 0.0);
            }
        }
        pq.insert(cp.s, Double.POSITIVE_INFINITY);
        while (!pq.isEmpty()) {
            final int v = pq.delMax();
            cp.s = cp.t;
            cp.t = v;
            for (final Edge e : G.adj(v)) {
                final int w = e.other(v);
                if (pq.contains(w)) {
                    pq.increaseKey(w, pq.keyOf(w) + e.weight());
                }
            }
        }
        cp.weight = 0.0;
        for (final Edge e2 : G.adj(cp.t)) {
            cp.weight += e2.weight();
        }
        return cp;
    }
    
    private EdgeWeightedGraph contractEdge(final EdgeWeightedGraph G, final int s, final int t) {
        final EdgeWeightedGraph H = new EdgeWeightedGraph(G.V());
        for (int v = 0; v < G.V(); ++v) {
            for (final Edge e : G.adj(v)) {
                final int w = e.other(v);
                if (v != s || w != t) {
                    if (v == t && w == s) {
                        continue;
                    }
                    if (v >= w) {
                        continue;
                    }
                    if (w == t) {
                        H.addEdge(new Edge(v, s, e.weight()));
                    }
                    else if (v == t) {
                        H.addEdge(new Edge(w, s, e.weight()));
                    }
                    else {
                        H.addEdge(new Edge(v, w, e.weight()));
                    }
                }
            }
        }
        return H;
    }
    
    private boolean check(final EdgeWeightedGraph G) {
        double value = Double.POSITIVE_INFINITY;
        final int s = 0;
        for (int t = 1; t < G.V(); ++t) {
            final FlowNetwork F = new FlowNetwork(G.V());
            for (final Edge e : G.edges()) {
                final int v = e.either();
                final int w = e.other(v);
                F.addEdge(new FlowEdge(v, w, e.weight()));
                F.addEdge(new FlowEdge(w, v, e.weight()));
            }
            final FordFulkerson maxflow = new FordFulkerson(F, s, t);
            value = Math.min(value, maxflow.value());
        }
        if (Math.abs(this.weight - value) > 1.0E-11) {
            System.err.println("Min cut weight = " + this.weight + " , max flow value = " + value);
            return false;
        }
        return true;
    }
    
    private void validateVertex(final int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        final GlobalMincut mc = new GlobalMincut(G);
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); ++v) {
            if (mc.cut(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
        StdOut.println("Min cut weight = " + mc.weight());
    }
    
    private class CutPhase
    {
        private double weight;
        private int s;
        private int t;
        
        public CutPhase(final double weight, final int s, final int t) {
            this.weight = weight;
            this.s = s;
            this.t = t;
        }
    }
}
