// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class AcyclicSP
{
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    
    public AcyclicSP(final EdgeWeightedDigraph G, final int s) {
        this.distTo = new double[G.V()];
        this.edgeTo = new DirectedEdge[G.V()];
        this.validateVertex(s);
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Double.POSITIVE_INFINITY;
        }
        this.distTo[s] = 0.0;
        final Topological topological = new Topological(G);
        if (!topological.hasOrder()) {
            throw new IllegalArgumentException("Digraph is not acyclic.");
        }
        for (final int v2 : topological.order()) {
            for (final DirectedEdge e : G.adj(v2)) {
                this.relax(e);
            }
        }
    }
    
    private void relax(final DirectedEdge e) {
        final int v = e.from();
        final int w = e.to();
        if (this.distTo[w] > this.distTo[v] + e.weight()) {
            this.distTo[w] = this.distTo[v] + e.weight();
            this.edgeTo[w] = e;
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
    
    private void validateVertex(final int v) {
        final int V = this.distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int s = Integer.parseInt(args[1]);
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        final AcyclicSP sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (final DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }
    }
}
