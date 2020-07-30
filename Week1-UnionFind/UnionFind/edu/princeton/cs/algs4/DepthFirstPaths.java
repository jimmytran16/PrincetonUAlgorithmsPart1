// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DepthFirstPaths
{
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    
    public DepthFirstPaths(final Graph G, final int s) {
        this.s = s;
        this.edgeTo = new int[G.V()];
        this.marked = new boolean[G.V()];
        this.validateVertex(s);
        this.dfs(G, s);
    }
    
    private void dfs(final Graph G, final int v) {
        this.marked[v] = true;
        for (final int w : G.adj(v)) {
            if (!this.marked[w]) {
                this.edgeTo[w] = v;
                this.dfs(G, w);
            }
        }
    }
    
    public boolean hasPathTo(final int v) {
        this.validateVertex(v);
        return this.marked[v];
    }
    
    public Iterable<Integer> pathTo(final int v) {
        this.validateVertex(v);
        if (!this.hasPathTo(v)) {
            return null;
        }
        final Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != this.s; x = this.edgeTo[x]) {
            path.push(x);
        }
        path.push(this.s);
        return path;
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Graph G = new Graph(in);
        final int s = Integer.parseInt(args[1]);
        final DepthFirstPaths dfs = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (final int x : dfs.pathTo(v)) {
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
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }
        }
    }
}
