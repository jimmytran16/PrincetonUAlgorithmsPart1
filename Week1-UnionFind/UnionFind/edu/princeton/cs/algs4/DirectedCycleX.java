// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DirectedCycleX
{
    private Stack<Integer> cycle;
    
    public DirectedCycleX(final Digraph G) {
        final int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            indegree[v] = G.indegree(v);
        }
        final Queue<Integer> queue = new Queue<Integer>();
        for (int v2 = 0; v2 < G.V(); ++v2) {
            if (indegree[v2] == 0) {
                queue.enqueue(v2);
            }
        }
        while (!queue.isEmpty()) {
            final int v2 = queue.dequeue();
            for (final int w : G.adj(v2)) {
                final int[] array = indegree;
                final int n = w;
                --array[n];
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        final int[] edgeTo = new int[G.V()];
        int root = -1;
        for (int v3 = 0; v3 < G.V(); ++v3) {
            if (indegree[v3] != 0) {
                root = v3;
                for (final int w2 : G.adj(v3)) {
                    if (indegree[w2] > 0) {
                        edgeTo[w2] = v3;
                    }
                }
            }
        }
        if (root != -1) {
            for (boolean[] visited = new boolean[G.V()]; !visited[root]; root = edgeTo[root]) {
                visited[root] = true;
            }
            this.cycle = new Stack<Integer>();
            int v4 = root;
            do {
                this.cycle.push(v4);
                v4 = edgeTo[v4];
            } while (v4 != root);
            this.cycle.push(root);
        }
        assert this.check();
    }
    
    public Iterable<Integer> cycle() {
        return this.cycle;
    }
    
    public boolean hasCycle() {
        return this.cycle != null;
    }
    
    private boolean check() {
        if (this.hasCycle()) {
            int first = -1;
            int last = -1;
            for (final int v : this.cycle()) {
                if (first == -1) {
                    first = v;
                }
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final int F = Integer.parseInt(args[2]);
        final Digraph G = DigraphGenerator.dag(V, E);
        for (int i = 0; i < F; ++i) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            G.addEdge(v, w);
        }
        StdOut.println(G);
        final DirectedCycleX finder = new DirectedCycleX(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (final int v2 : finder.cycle()) {
                StdOut.print(v2 + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }
}
