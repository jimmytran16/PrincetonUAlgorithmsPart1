// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class CPM
{
    private CPM() {
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final int source = 2 * n;
        final int sink = 2 * n + 1;
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * n + 2);
        for (int i = 0; i < n; ++i) {
            final double duration = StdIn.readDouble();
            G.addEdge(new DirectedEdge(source, i, 0.0));
            G.addEdge(new DirectedEdge(i + n, sink, 0.0));
            G.addEdge(new DirectedEdge(i, i + n, duration));
            for (int m = StdIn.readInt(), j = 0; j < m; ++j) {
                final int precedent = StdIn.readInt();
                G.addEdge(new DirectedEdge(n + i, precedent, 0.0));
            }
        }
        final AcyclicLP lp = new AcyclicLP(G, source);
        StdOut.println(" job   start  finish");
        StdOut.println("--------------------");
        for (int k = 0; k < n; ++k) {
            StdOut.printf("%4d %7.1f %7.1f\n", k, lp.distTo(k), lp.distTo(k + n));
        }
        StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }
}
