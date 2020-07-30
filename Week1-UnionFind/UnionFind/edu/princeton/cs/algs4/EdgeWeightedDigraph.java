// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EdgeWeightedDigraph
{
    private static final String NEWLINE;
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;
    
    public EdgeWeightedDigraph(final int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        this.adj = (Bag<DirectedEdge>[])new Bag[V];
        for (int v = 0; v < V; ++v) {
            this.adj[v] = new Bag<DirectedEdge>();
        }
    }
    
    public EdgeWeightedDigraph(final int V, final int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
        }
        for (int i = 0; i < E; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double weight = 0.01 * StdRandom.uniform(100);
            final DirectedEdge e = new DirectedEdge(v, w, weight);
            this.addEdge(e);
        }
    }
    
    public EdgeWeightedDigraph(final In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            this.V = in.readInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            }
            this.indegree = new int[this.V];
            this.adj = (Bag<DirectedEdge>[])new Bag[this.V];
            for (int v = 0; v < this.V; ++v) {
                this.adj[v] = new Bag<DirectedEdge>();
            }
            final int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("Number of edges must be nonnegative");
            }
            for (int i = 0; i < E; ++i) {
                final int v2 = in.readInt();
                final int w = in.readInt();
                this.validateVertex(v2);
                this.validateVertex(w);
                final double weight = in.readDouble();
                this.addEdge(new DirectedEdge(v2, w, weight));
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }
    
    public EdgeWeightedDigraph(final EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); ++v) {
            this.indegree[v] = G.indegree(v);
        }
        for (int v = 0; v < G.V(); ++v) {
            final Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            for (final DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (final DirectedEdge e : reverse) {
                this.adj[v].add(e);
            }
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
    
    public void addEdge(final DirectedEdge e) {
        final int v = e.from();
        final int w = e.to();
        this.validateVertex(v);
        this.validateVertex(w);
        this.adj[v].add(e);
        final int[] indegree = this.indegree;
        final int n = w;
        ++indegree[n];
        ++this.E;
    }
    
    public Iterable<DirectedEdge> adj(final int v) {
        this.validateVertex(v);
        return this.adj[v];
    }
    
    public int outdegree(final int v) {
        this.validateVertex(v);
        return this.adj[v].size();
    }
    
    public int indegree(final int v) {
        this.validateVertex(v);
        return this.indegree[v];
    }
    
    public Iterable<DirectedEdge> edges() {
        final Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < this.V; ++v) {
            for (final DirectedEdge e : this.adj(v)) {
                list.add(e);
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.V + " " + this.E + EdgeWeightedDigraph.NEWLINE);
        for (int v = 0; v < this.V; ++v) {
            s.append(v + ": ");
            for (final DirectedEdge e : this.adj[v]) {
                s.append(e + "  ");
            }
            s.append(EdgeWeightedDigraph.NEWLINE);
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println(G);
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
