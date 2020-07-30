// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class Topological
{
    private Iterable<Integer> order;
    private int[] rank;
    
    public Topological(final Digraph G) {
        final DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            final DepthFirstOrder dfs = new DepthFirstOrder(G);
            this.order = dfs.reversePost();
            this.rank = new int[G.V()];
            int i = 0;
            for (final int v : this.order) {
                this.rank[v] = i++;
            }
        }
    }
    
    public Topological(final EdgeWeightedDigraph G) {
        final EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            final DepthFirstOrder dfs = new DepthFirstOrder(G);
            this.order = dfs.reversePost();
        }
    }
    
    public Iterable<Integer> order() {
        return this.order;
    }
    
    public boolean hasOrder() {
        return this.order != null;
    }
    
    @Deprecated
    public boolean isDAG() {
        return this.hasOrder();
    }
    
    public int rank(final int v) {
        this.validateVertex(v);
        if (this.hasOrder()) {
            return this.rank[v];
        }
        return -1;
    }
    
    private void validateVertex(final int v) {
        final int V = this.rank.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final String filename = args[0];
        final String delimiter = args[1];
        final SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        final Topological topological = new Topological(sg.digraph());
        for (final int v : topological.order()) {
            StdOut.println(sg.nameOf(v));
        }
    }
}
