// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EdgeWeightedGraph
{
    private static final String NEWLINE;
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    
    public EdgeWeightedGraph(final int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Edge>[])new Bag[V];
        for (int v = 0; v < V; ++v) {
            this.adj[v] = new Bag<Edge>();
        }
    }
    
    public EdgeWeightedGraph(final int V, final int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges must be nonnegative");
        }
        for (int i = 0; i < E; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final double weight = Math.round(100.0 * StdRandom.uniform()) / 100.0;
            final Edge e = new Edge(v, w, weight);
            this.addEdge(e);
        }
    }
    
    public EdgeWeightedGraph(final In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            this.V = in.readInt();
            this.adj = (Bag<Edge>[])new Bag[this.V];
            for (int v = 0; v < this.V; ++v) {
                this.adj[v] = new Bag<Edge>();
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
                final Edge e = new Edge(v2, w, weight);
                this.addEdge(e);
            }
        }
        catch (NoSuchElementException e2) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e2);
        }
    }
    
    public EdgeWeightedGraph(final EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); ++v) {
            final Stack<Edge> reverse = new Stack<Edge>();
            for (final Edge e : G.adj[v]) {
                reverse.push(e);
            }
            for (final Edge e : reverse) {
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
    
    public void addEdge(final Edge e) {
        final int v = e.either();
        final int w = e.other(v);
        this.validateVertex(v);
        this.validateVertex(w);
        this.adj[v].add(e);
        this.adj[w].add(e);
        ++this.E;
    }
    
    public Iterable<Edge> adj(final int v) {
        this.validateVertex(v);
        return this.adj[v];
    }
    
    public int degree(final int v) {
        this.validateVertex(v);
        return this.adj[v].size();
    }
    
    public Iterable<Edge> edges() {
        final Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < this.V; ++v) {
            int selfLoops = 0;
            for (final Edge e : this.adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                else {
                    if (e.other(v) != v) {
                        continue;
                    }
                    if (selfLoops % 2 == 0) {
                        list.add(e);
                    }
                    ++selfLoops;
                }
            }
        }
        return list;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.V + " " + this.E + EdgeWeightedGraph.NEWLINE);
        for (int v = 0; v < this.V; ++v) {
            s.append(v + ": ");
            for (final Edge e : this.adj[v]) {
                s.append(e + "  ");
            }
            s.append(EdgeWeightedGraph.NEWLINE);
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        StdOut.println(G);
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
