// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Graph
{
    private static final String NEWLINE;
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    
    public Graph(final int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Integer>[])new Bag[V];
        for (int v = 0; v < V; ++v) {
            this.adj[v] = new Bag<Integer>();
        }
    }
    
    public Graph(final In in) {
        if (in == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            this.V = in.readInt();
            if (this.V < 0) {
                throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            }
            this.adj = (Bag<Integer>[])new Bag[this.V];
            for (int v = 0; v < this.V; ++v) {
                this.adj[v] = new Bag<Integer>();
            }
            final int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            }
            for (int i = 0; i < E; ++i) {
                final int v2 = in.readInt();
                final int w = in.readInt();
                this.validateVertex(v2);
                this.validateVertex(w);
                this.addEdge(v2, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }
    
    public Graph(final Graph G) {
        this.V = G.V();
        this.E = G.E();
        if (this.V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.adj = (Bag<Integer>[])new Bag[this.V];
        for (int v = 0; v < this.V; ++v) {
            this.adj[v] = new Bag<Integer>();
        }
        for (int v = 0; v < G.V(); ++v) {
            final Stack<Integer> reverse = new Stack<Integer>();
            for (final int w : G.adj[v]) {
                reverse.push(w);
            }
            for (final int w : reverse) {
                this.adj[v].add(w);
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
    
    public void addEdge(final int v, final int w) {
        this.validateVertex(v);
        this.validateVertex(w);
        ++this.E;
        this.adj[v].add(w);
        this.adj[w].add(v);
    }
    
    public Iterable<Integer> adj(final int v) {
        this.validateVertex(v);
        return this.adj[v];
    }
    
    public int degree(final int v) {
        this.validateVertex(v);
        return this.adj[v].size();
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(this.V + " vertices, " + this.E + " edges " + Graph.NEWLINE);
        for (int v = 0; v < this.V; ++v) {
            s.append(v + ": ");
            for (final int w : this.adj[v]) {
                s.append(w + " ");
            }
            s.append(Graph.NEWLINE);
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Graph G = new Graph(in);
        StdOut.println(G);
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
