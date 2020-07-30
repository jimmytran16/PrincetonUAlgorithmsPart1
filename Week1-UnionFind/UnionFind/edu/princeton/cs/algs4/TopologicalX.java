// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class TopologicalX
{
    private Queue<Integer> order;
    private int[] ranks;
    
    public TopologicalX(final Digraph G) {
        final int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            indegree[v] = G.indegree(v);
        }
        this.ranks = new int[G.V()];
        this.order = new Queue<Integer>();
        int count = 0;
        final Queue<Integer> queue = new Queue<Integer>();
        for (int v2 = 0; v2 < G.V(); ++v2) {
            if (indegree[v2] == 0) {
                queue.enqueue(v2);
            }
        }
        while (!queue.isEmpty()) {
            final int v2 = queue.dequeue();
            this.order.enqueue(v2);
            this.ranks[v2] = count++;
            for (final int w : G.adj(v2)) {
                final int[] array = indegree;
                final int n = w;
                --array[n];
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        if (count != G.V()) {
            this.order = null;
        }
        assert this.check(G);
    }
    
    public TopologicalX(final EdgeWeightedDigraph G) {
        final int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            indegree[v] = G.indegree(v);
        }
        this.ranks = new int[G.V()];
        this.order = new Queue<Integer>();
        int count = 0;
        final Queue<Integer> queue = new Queue<Integer>();
        for (int v2 = 0; v2 < G.V(); ++v2) {
            if (indegree[v2] == 0) {
                queue.enqueue(v2);
            }
        }
        while (!queue.isEmpty()) {
            final int v2 = queue.dequeue();
            this.order.enqueue(v2);
            this.ranks[v2] = count++;
            for (final DirectedEdge e : G.adj(v2)) {
                final int w = e.to();
                final int[] array = indegree;
                final int n = w;
                --array[n];
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        if (count != G.V()) {
            this.order = null;
        }
        assert this.check(G);
    }
    
    public Iterable<Integer> order() {
        return this.order;
    }
    
    public boolean hasOrder() {
        return this.order != null;
    }
    
    public int rank(final int v) {
        this.validateVertex(v);
        if (this.hasOrder()) {
            return this.ranks[v];
        }
        return -1;
    }
    
    private boolean check(final Digraph G) {
        if (this.hasOrder()) {
            final boolean[] found = new boolean[G.V()];
            for (int i = 0; i < G.V(); ++i) {
                found[this.rank(i)] = true;
            }
            for (int i = 0; i < G.V(); ++i) {
                if (!found[i]) {
                    System.err.println("No vertex with rank " + i);
                    return false;
                }
            }
            for (int v = 0; v < G.V(); ++v) {
                for (final int w : G.adj(v)) {
                    if (this.rank(v) > this.rank(w)) {
                        System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n", v, w, v, this.rank(v), w, this.rank(w));
                        return false;
                    }
                }
            }
            int r = 0;
            for (final int v2 : this.order()) {
                if (this.rank(v2) != r) {
                    System.err.println("order() and rank() inconsistent");
                    return false;
                }
                ++r;
            }
        }
        return true;
    }
    
    private boolean check(final EdgeWeightedDigraph G) {
        if (this.hasOrder()) {
            final boolean[] found = new boolean[G.V()];
            for (int i = 0; i < G.V(); ++i) {
                found[this.rank(i)] = true;
            }
            for (int i = 0; i < G.V(); ++i) {
                if (!found[i]) {
                    System.err.println("No vertex with rank " + i);
                    return false;
                }
            }
            for (int v = 0; v < G.V(); ++v) {
                for (final DirectedEdge e : G.adj(v)) {
                    final int w = e.to();
                    if (this.rank(v) > this.rank(w)) {
                        System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n", v, w, v, this.rank(v), w, this.rank(w));
                        return false;
                    }
                }
            }
            int r = 0;
            for (final int v2 : this.order()) {
                if (this.rank(v2) != r) {
                    System.err.println("order() and rank() inconsistent");
                    return false;
                }
                ++r;
            }
        }
        return true;
    }
    
    private void validateVertex(final int v) {
        final int V = this.ranks.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final int F = Integer.parseInt(args[2]);
        final Digraph G1 = DigraphGenerator.dag(V, E);
        final EdgeWeightedDigraph G2 = new EdgeWeightedDigraph(V);
        for (int v = 0; v < G1.V(); ++v) {
            for (final int w : G1.adj(v)) {
                G2.addEdge(new DirectedEdge(v, w, 0.0));
            }
        }
        for (int i = 0; i < F; ++i) {
            final int v2 = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            G1.addEdge(v2, w);
            G2.addEdge(new DirectedEdge(v2, w, 0.0));
        }
        StdOut.println(G1);
        StdOut.println();
        StdOut.println(G2);
        final TopologicalX topological1 = new TopologicalX(G1);
        if (!topological1.hasOrder()) {
            StdOut.println("Not a DAG");
        }
        else {
            StdOut.print("Topological order: ");
            for (final int v3 : topological1.order()) {
                StdOut.print(v3 + " ");
            }
            StdOut.println();
        }
        final TopologicalX topological2 = new TopologicalX(G2);
        if (!topological2.hasOrder()) {
            StdOut.println("Not a DAG");
        }
        else {
            StdOut.print("Topological order: ");
            for (final int v4 : topological2.order()) {
                StdOut.print(v4 + " ");
            }
            StdOut.println();
        }
    }
}
