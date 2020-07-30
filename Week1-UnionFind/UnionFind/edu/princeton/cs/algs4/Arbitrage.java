// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class Arbitrage
{
    private Arbitrage() {
    }
    
    public static void main(final String[] args) {
        final int V = StdIn.readInt();
        final String[] name = new String[V];
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; ++v) {
            name[v] = StdIn.readString();
            for (int w = 0; w < V; ++w) {
                final double rate = StdIn.readDouble();
                final DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }
        final BellmanFordSP spt = new BellmanFordSP(G, 0);
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (final DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        }
        else {
            StdOut.println("No arbitrage opportunity");
        }
    }
}
