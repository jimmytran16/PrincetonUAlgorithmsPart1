// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class FlowNetwork
{
    private static final String NEWLINE;
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;
    
    public FlowNetwork(final int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = (Bag<FlowEdge>[])new Bag[V];
        for (int v = 0; v < V; ++v) {
            this.adj[v] = new Bag<FlowEdge>();
        }
    }
    
    public FlowNetwork(final int V, final int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges must be nonnegative");
        }
        for (int i = 0; i < E; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double capacity = StdRandom.uniform(100);
            this.addEdge(new FlowEdge(v, w, capacity));
        }
    }
    
    public FlowNetwork(final In in) {
        this(in.readInt());
        final int E = in.readInt();
        if (E < 0) {
            throw new IllegalArgumentException("number of edges must be nonnegative");
        }
        for (int i = 0; i < E; ++i) {
            final int v = in.readInt();
            final int w = in.readInt();
            this.validateVertex(v);
            this.validateVertex(w);
            final double capacity = in.readDouble();
            this.addEdge(new FlowEdge(v, w, capacity));
        }
    }
    
    public int V() {
        return this.V;
    }
    
    public int E() {
        return this.E;
    }
    
    private void validateVertex(final int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.V - 1));
        }
    }
    
    public void addEdge(final FlowEdge e) {
        final int v = e.from();
        final int w = e.to();
        this.validateVertex(v);
        this.validateVertex(w);
        this.adj[v].add(e);
        this.adj[w].add(e);
        ++this.E;
    }
    
    public Iterable<FlowEdge> adj(final int v) {
        this.validateVertex(v);
        return this.adj[v];
    }
    
    public Iterable<FlowEdge> edges() {
        final Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (int v = 0; v < this.V; ++v) {
            for (final FlowEdge e : this.adj(v)) {
                if (e.to() != v) {
                    list.add(e);
                }
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.V + " " + this.E + FlowNetwork.NEWLINE);
        for (int v = 0; v < this.V; ++v) {
            s.append(v + ":  ");
            for (final FlowEdge e : this.adj[v]) {
                if (e.to() != v) {
                    s.append(e + "  ");
                }
            }
            s.append(FlowNetwork.NEWLINE);
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
