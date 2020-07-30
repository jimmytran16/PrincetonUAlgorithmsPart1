// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class NonrecursiveDirectedDFS
{
    private boolean[] marked;
    
    public NonrecursiveDirectedDFS(final Digraph G, final int s) {
        this.marked = new boolean[G.V()];
        this.validateVertex(s);
        final Iterator<Integer>[] adj = (Iterator<Integer>[])new Iterator[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            adj[v] = G.adj(v).iterator();
        }
        final Stack<Integer> stack = new Stack<Integer>();
        this.marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            final int v2 = stack.peek();
            if (adj[v2].hasNext()) {
                final int w = adj[v2].next();
                if (this.marked[w]) {
                    continue;
                }
                this.marked[w] = true;
                stack.push(w);
            }
            else {
                stack.pop();
            }
        }
    }
    
    public boolean marked(final int v) {
        this.validateVertex(v);
        return this.marked[v];
    }
    
    private void validateVertex(final int v) {
        final int V = this.marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final int s = Integer.parseInt(args[1]);
        final NonrecursiveDirectedDFS dfs = new NonrecursiveDirectedDFS(G, s);
        for (int v = 0; v < G.V(); ++v) {
            if (dfs.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }
}
