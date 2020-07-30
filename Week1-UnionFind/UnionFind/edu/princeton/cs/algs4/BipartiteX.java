// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class BipartiteX
{
    private static final boolean WHITE = false;
    private static final boolean BLACK = true;
    private boolean isBipartite;
    private boolean[] color;
    private boolean[] marked;
    private int[] edgeTo;
    private Queue<Integer> cycle;
    
    public BipartiteX(final Graph G) {
        this.isBipartite = true;
        this.color = new boolean[G.V()];
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V() && this.isBipartite; ++v) {
            if (!this.marked[v]) {
                this.bfs(G, v);
            }
        }
        assert this.check(G);
    }
    
    private void bfs(final Graph G, final int s) {
        final Queue<Integer> q = new Queue<Integer>();
        this.color[s] = false;
        this.marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            final int v = q.dequeue();
            for (final int w : G.adj(v)) {
                if (!this.marked[w]) {
                    this.marked[w] = true;
                    this.edgeTo[w] = v;
                    this.color[w] = !this.color[v];
                    q.enqueue(w);
                }
                else {
                    if (this.color[w] == this.color[v]) {
                        this.isBipartite = false;
                        this.cycle = new Queue<Integer>();
                        final Stack<Integer> stack = new Stack<Integer>();
                        int x = v;
                        for (int y = w; x != y; x = this.edgeTo[x], y = this.edgeTo[y]) {
                            stack.push(x);
                            this.cycle.enqueue(y);
                        }
                        stack.push(x);
                        while (!stack.isEmpty()) {
                            this.cycle.enqueue(stack.pop());
                        }
                        this.cycle.enqueue(w);
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    public boolean isBipartite() {
        return this.isBipartite;
    }
    
    public boolean color(final int v) {
        this.validateVertex(v);
        if (!this.isBipartite) {
            throw new UnsupportedOperationException("Graph is not bipartite");
        }
        return this.color[v];
    }
    
    public Iterable<Integer> oddCycle() {
        return this.cycle;
    }
    
    private boolean check(final Graph G) {
        if (this.isBipartite) {
            for (int v = 0; v < G.V(); ++v) {
                for (final int w : G.adj(v)) {
                    if (this.color[v] == this.color[w]) {
                        System.err.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
                        return false;
                    }
                }
            }
        }
        else {
            int first = -1;
            int last = -1;
            for (final int v2 : this.oddCycle()) {
                if (first == -1) {
                    first = v2;
                }
                last = v2;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
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
    
    public static void main(final String[] args) {
        final int V1 = Integer.parseInt(args[0]);
        final int V2 = Integer.parseInt(args[1]);
        final int E = Integer.parseInt(args[2]);
        final int F = Integer.parseInt(args[3]);
        final Graph G = GraphGenerator.bipartite(V1, V2, E);
        for (int i = 0; i < F; ++i) {
            final int v = StdRandom.uniform(V1 + V2);
            final int w = StdRandom.uniform(V1 + V2);
            G.addEdge(v, w);
        }
        StdOut.println(G);
        final BipartiteX b = new BipartiteX(G);
        if (b.isBipartite()) {
            StdOut.println("Graph is bipartite");
            for (int v = 0; v < G.V(); ++v) {
                StdOut.println(v + ": " + b.color(v));
            }
        }
        else {
            StdOut.print("Graph has an odd-length cycle: ");
            for (final int x : b.oddCycle()) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }
    }
}
