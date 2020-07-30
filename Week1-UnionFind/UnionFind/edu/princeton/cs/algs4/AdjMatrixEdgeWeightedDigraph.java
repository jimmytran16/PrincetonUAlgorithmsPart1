// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class AdjMatrixEdgeWeightedDigraph
{
    private static final String NEWLINE;
    private final int V;
    private int E;
    private DirectedEdge[][] adj;
    
    public AdjMatrixEdgeWeightedDigraph(final int V) {
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = new DirectedEdge[V][V];
    }
    
    public AdjMatrixEdgeWeightedDigraph(final int V, final int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("number of edges must be nonnegative");
        }
        if (E > V * V) {
            throw new IllegalArgumentException("too many edges");
        }
        while (this.E != E) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double weight = Math.round(100.0 * StdRandom.uniform()) / 100.0;
            this.addEdge(new DirectedEdge(v, w, weight));
        }
    }
    
    public int V() {
        return this.V;
    }
    
    public int E() {
        return this.E;
    }
    
    public void addEdge(final DirectedEdge e) {
        final int v = e.from();
        final int w = e.to();
        this.validateVertex(v);
        this.validateVertex(w);
        if (this.adj[v][w] == null) {
            ++this.E;
            this.adj[v][w] = e;
        }
    }
    
    public Iterable<DirectedEdge> adj(final int v) {
        this.validateVertex(v);
        return new AdjIterator(v);
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.V + " " + this.E + AdjMatrixEdgeWeightedDigraph.NEWLINE);
        for (int v = 0; v < this.V; ++v) {
            s.append(v + ": ");
            for (final DirectedEdge e : this.adj(v)) {
                s.append(e + "  ");
            }
            s.append(AdjMatrixEdgeWeightedDigraph.NEWLINE);
        }
        return s.toString();
    }
    
    private void validateVertex(final int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V, E);
        StdOut.println(G);
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
    
    private class AdjIterator implements Iterator<DirectedEdge>, Iterable<DirectedEdge>
    {
        private int v;
        private int w;
        
        public AdjIterator(final int v) {
            this.w = 0;
            this.v = v;
        }
        
        @Override
        public Iterator<DirectedEdge> iterator() {
            return this;
        }
        
        @Override
        public boolean hasNext() {
            while (this.w < AdjMatrixEdgeWeightedDigraph.this.V) {
                if (AdjMatrixEdgeWeightedDigraph.this.adj[this.v][this.w] != null) {
                    return true;
                }
                ++this.w;
            }
            return false;
        }
        
        @Override
        public DirectedEdge next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return AdjMatrixEdgeWeightedDigraph.this.adj[this.v][this.w++];
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
