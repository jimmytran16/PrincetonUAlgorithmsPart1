// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class BreadthFirstDirectedPaths
{
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    
    public BreadthFirstDirectedPaths(final Digraph G, final int s) {
        this.marked = new boolean[G.V()];
        this.distTo = new int[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Integer.MAX_VALUE;
        }
        this.validateVertex(s);
        this.bfs(G, s);
    }
    
    public BreadthFirstDirectedPaths(final Digraph G, final Iterable<Integer> sources) {
        this.marked = new boolean[G.V()];
        this.distTo = new int[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Integer.MAX_VALUE;
        }
        this.validateVertices(sources);
        this.bfs(G, sources);
    }
    
    private void bfs(final Digraph G, final int s) {
        final Queue<Integer> q = new Queue<Integer>();
        this.marked[s] = true;
        this.distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            final int v = q.dequeue();
            for (final int w : G.adj(v)) {
                if (!this.marked[w]) {
                    this.edgeTo[w] = v;
                    this.distTo[w] = this.distTo[v] + 1;
                    this.marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }
    
    private void bfs(final Digraph G, final Iterable<Integer> sources) {
        final Queue<Integer> q = new Queue<Integer>();
        for (final int s : sources) {
            this.marked[s] = true;
            this.distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            final int v = q.dequeue();
            for (final int w : G.adj(v)) {
                if (!this.marked[w]) {
                    this.edgeTo[w] = v;
                    this.distTo[w] = this.distTo[v] + 1;
                    this.marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }
    
    public boolean hasPathTo(final int v) {
        this.validateVertex(v);
        return this.marked[v];
    }
    
    public int distTo(final int v) {
        this.validateVertex(v);
        return this.distTo[v];
    }
    
    public Iterable<Integer> pathTo(final int v) {
        this.validateVertex(v);
        if (!this.hasPathTo(v)) {
            return null;
        }
        final Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; this.distTo[x] != 0; x = this.edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    private void validateVertices(final Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (final Integer v : vertices) {
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            this.validateVertex(v);
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final int s = Integer.parseInt(args[1]);
        final BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (final int x : bfs.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    }
                    else {
                        StdOut.print("->" + x);
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }
        }
    }
}
