// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class BreadthFirstPaths
{
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    
    public BreadthFirstPaths(final Graph G, final int s) {
        this.marked = new boolean[G.V()];
        this.distTo = new int[G.V()];
        this.edgeTo = new int[G.V()];
        this.validateVertex(s);
        this.bfs(G, s);
        assert this.check(G, s);
    }
    
    public BreadthFirstPaths(final Graph G, final Iterable<Integer> sources) {
        this.marked = new boolean[G.V()];
        this.distTo = new int[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Integer.MAX_VALUE;
        }
        this.validateVertices(sources);
        this.bfs(G, sources);
    }
    
    private void bfs(final Graph G, final int s) {
        final Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); ++v) {
            this.distTo[v] = Integer.MAX_VALUE;
        }
        this.distTo[s] = 0;
        this.marked[s] = true;
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
    
    private void bfs(final Graph G, final Iterable<Integer> sources) {
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
    
    private boolean check(final Graph G, final int s) {
        if (this.distTo[s] != 0) {
            StdOut.println("distance of source " + s + " to itself = " + this.distTo[s]);
            return false;
        }
        for (int v = 0; v < G.V(); ++v) {
            for (final int w : G.adj(v)) {
                if (this.hasPathTo(v) != this.hasPathTo(w)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("hasPathTo(" + v + ") = " + this.hasPathTo(v));
                    StdOut.println("hasPathTo(" + w + ") = " + this.hasPathTo(w));
                    return false;
                }
                if (this.hasPathTo(v) && this.distTo[w] > this.distTo[v] + 1) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("distTo[" + v + "] = " + this.distTo[v]);
                    StdOut.println("distTo[" + w + "] = " + this.distTo[w]);
                    return false;
                }
            }
        }
        for (int w2 = 0; w2 < G.V(); ++w2) {
            if (this.hasPathTo(w2)) {
                if (w2 != s) {
                    final int v2 = this.edgeTo[w2];
                    if (this.distTo[w2] != this.distTo[v2] + 1) {
                        StdOut.println("shortest path edge " + v2 + "-" + w2);
                        StdOut.println("distTo[" + v2 + "] = " + this.distTo[v2]);
                        StdOut.println("distTo[" + w2 + "] = " + this.distTo[w2]);
                        return false;
                    }
                }
            }
        }
        return true;
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
        final Graph G = new Graph(in);
        final int s = Integer.parseInt(args[1]);
        final BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (final int x : bfs.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    }
                    else {
                        StdOut.print("-" + x);
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
