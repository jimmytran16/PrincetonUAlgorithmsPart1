// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class FloydWarshall
{
    private boolean hasNegativeCycle;
    private double[][] distTo;
    private DirectedEdge[][] edgeTo;
    
    public FloydWarshall(final AdjMatrixEdgeWeightedDigraph G) {
        final int V = G.V();
        this.distTo = new double[V][V];
        this.edgeTo = new DirectedEdge[V][V];
        for (int v = 0; v < V; ++v) {
            for (int w = 0; w < V; ++w) {
                this.distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }
        for (int v = 0; v < G.V(); ++v) {
            for (final DirectedEdge e : G.adj(v)) {
                this.distTo[e.from()][e.to()] = e.weight();
                this.edgeTo[e.from()][e.to()] = e;
            }
            if (this.distTo[v][v] >= 0.0) {
                this.distTo[v][v] = 0.0;
                this.edgeTo[v][v] = null;
            }
        }
        for (int i = 0; i < V; ++i) {
            for (int v2 = 0; v2 < V; ++v2) {
                if (this.edgeTo[v2][i] != null) {
                    for (int w2 = 0; w2 < V; ++w2) {
                        if (this.distTo[v2][w2] > this.distTo[v2][i] + this.distTo[i][w2]) {
                            this.distTo[v2][w2] = this.distTo[v2][i] + this.distTo[i][w2];
                            this.edgeTo[v2][w2] = this.edgeTo[i][w2];
                        }
                    }
                    if (this.distTo[v2][v2] < 0.0) {
                        this.hasNegativeCycle = true;
                        return;
                    }
                }
            }
        }
        assert this.check(G);
    }
    
    public boolean hasNegativeCycle() {
        return this.hasNegativeCycle;
    }
    
    public Iterable<DirectedEdge> negativeCycle() {
        int v = 0;
        while (v < this.distTo.length) {
            if (this.distTo[v][v] < 0.0) {
                final int V = this.edgeTo.length;
                final EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; ++w) {
                    if (this.edgeTo[v][w] != null) {
                        spt.addEdge(this.edgeTo[v][w]);
                    }
                }
                final EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
            else {
                ++v;
            }
        }
        return null;
    }
    
    public boolean hasPath(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        return this.distTo[s][t] < Double.POSITIVE_INFINITY;
    }
    
    public double dist(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        if (this.hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return this.distTo[s][t];
    }
    
    public Iterable<DirectedEdge> path(final int s, final int t) {
        this.validateVertex(s);
        this.validateVertex(t);
        if (this.hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!this.hasPath(s, t)) {
            return null;
        }
        final Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = this.edgeTo[s][t]; e != null; e = this.edgeTo[s][e.from()]) {
            path.push(e);
        }
        return path;
    }
    
    private boolean check(final AdjMatrixEdgeWeightedDigraph G) {
        if (!this.hasNegativeCycle()) {
            for (int v = 0; v < G.V(); ++v) {
                for (final DirectedEdge e : G.adj(v)) {
                    final int w = e.to();
                    for (int i = 0; i < G.V(); ++i) {
                        if (this.distTo[i][w] > this.distTo[i][v] + e.weight()) {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
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
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
        for (int i = 0; i < E; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double weight = Math.round(100.0 * (StdRandom.uniform() - 0.15)) / 100.0;
            if (v == w) {
                G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
            }
            else {
                G.addEdge(new DirectedEdge(v, w, weight));
            }
        }
        StdOut.println(G);
        final FloydWarshall spt = new FloydWarshall(G);
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
        if (spt.hasNegativeCycle()) {
            StdOut.println("Negative cost cycle:");
            for (final DirectedEdge e : spt.negativeCycle()) {
                StdOut.println(e);
            }
            StdOut.println();
        }
        else {
            for (int v = 0; v < G.V(); ++v) {
                for (int w = 0; w < G.V(); ++w) {
                    if (spt.hasPath(v, w)) {
                        StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (final DirectedEdge e2 : spt.path(v, w)) {
                            StdOut.print(e2 + "  ");
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
}
