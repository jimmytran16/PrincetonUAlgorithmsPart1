// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DijkstraAllPairsSP
{
    private DijkstraSP[] all;
    
    public DijkstraAllPairsSP(final EdgeWeightedDigraph G) {
        this.all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.all[v] = new DijkstraSP(G, v);
        }
    }
    
    public Iterable<DirectedEdge> path(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        return this.all[s].pathTo(t);
    }
    
    public boolean hasPath(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        return this.dist(s, t) < Double.POSITIVE_INFINITY;
    }
    
    public double dist(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        return this.all[s].distTo(t);
    }
    
    private void validateVertex(final int v) {
        final int V = this.all.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        final DijkstraAllPairsSP spt = new DijkstraAllPairsSP(G);
        StdOut.printf("  ", new Object[0]);
        for (int v = 0; v < G.V(); ++v) {
            StdOut.printf("%6d ", v);
        }
        StdOut.println();
        for (int v = 0; v < G.V(); ++v) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); ++w) {
                if (spt.hasPath(v, w)) {
                    StdOut.printf("%6.2f ", spt.dist(v, w));
                }
                else {
                    StdOut.printf("  Inf ", new Object[0]);
                }
            }
            StdOut.println();
        }
        StdOut.println();
        for (int v = 0; v < G.V(); ++v) {
            for (int w = 0; w < G.V(); ++w) {
                if (spt.hasPath(v, w)) {
                    StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                    for (final DirectedEdge e : spt.path(v, w)) {
                        StdOut.print(e + "  ");
                    }
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d no path\n", v, w);
                }
            }
        }
    }
}
