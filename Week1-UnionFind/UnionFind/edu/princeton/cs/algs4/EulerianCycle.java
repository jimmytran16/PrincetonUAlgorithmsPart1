// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class EulerianCycle
{
    private Stack<Integer> cycle;
    
    public EulerianCycle(final Graph G) {
        this.cycle = new Stack<Integer>();
        if (G.E() == 0) {
            return;
        }
        for (int v = 0; v < G.V(); ++v) {
            if (G.degree(v) % 2 != 0) {
                return;
            }
        }
        final Queue<Edge>[] adj = (Queue<Edge>[])new Queue[G.V()];
        for (int v2 = 0; v2 < G.V(); ++v2) {
            adj[v2] = new Queue<Edge>();
        }
        for (int v2 = 0; v2 < G.V(); ++v2) {
            int selfLoops = 0;
            for (final int w : G.adj(v2)) {
                if (v2 == w) {
                    if (selfLoops % 2 == 0) {
                        final Edge e = new Edge(v2, w);
                        adj[v2].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    ++selfLoops;
                }
                else {
                    if (v2 >= w) {
                        continue;
                    }
                    final Edge e = new Edge(v2, w);
                    adj[v2].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }
        final int s = nonIsolatedVertex(G);
        final Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        this.cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v3 = stack.pop();
            while (!adj[v3].isEmpty()) {
                final Edge edge = adj[v3].dequeue();
                if (edge.isUsed) {
                    continue;
                }
                edge.isUsed = true;
                stack.push(v3);
                v3 = edge.other(v3);
            }
            this.cycle.push(v3);
        }
        if (this.cycle.size() != G.E() + 1) {
            this.cycle = null;
        }
        assert this.certifySolution(G);
    }
    
    public Iterable<Integer> cycle() {
        return this.cycle;
    }
    
    public boolean hasEulerianCycle() {
        return this.cycle != null;
    }
    
    private static int nonIsolatedVertex(final Graph G) {
        for (int v = 0; v < G.V(); ++v) {
            if (G.degree(v) > 0) {
                return v;
            }
        }
        return -1;
    }
    
    private static boolean satisfiesNecessaryAndSufficientConditions(final Graph G) {
        if (G.E() == 0) {
            return false;
        }
        for (int v = 0; v < G.V(); ++v) {
            if (G.degree(v) % 2 != 0) {
                return false;
            }
        }
        final int s = nonIsolatedVertex(G);
        final BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v2 = 0; v2 < G.V(); ++v2) {
            if (G.degree(v2) > 0 && !bfs.hasPathTo(v2)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean certifySolution(final Graph G) {
        if (this.hasEulerianCycle() == (this.cycle() == null)) {
            return false;
        }
        if (this.hasEulerianCycle() != satisfiesNecessaryAndSufficientConditions(G)) {
            return false;
        }
        if (this.cycle == null) {
            return true;
        }
        if (this.cycle.size() != G.E() + 1) {
            return false;
        }
        int first = -1;
        int last = -1;
        for (final int v : this.cycle()) {
            if (first == -1) {
                first = v;
            }
            last = v;
        }
        return first == last;
    }
    
    private static void unitTest(final Graph G, final String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);
        final EulerianCycle euler = new EulerianCycle(G);
        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (final int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final Graph G1 = GraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");
        final Graph G2 = GraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");
        final Graph G3 = new Graph(V);
        unitTest(G3, "empty graph");
        final Graph G4 = new Graph(V);
        final int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");
        final Graph H1 = GraphGenerator.eulerianCycle(V / 2, E / 2);
        final Graph H2 = GraphGenerator.eulerianCycle(V - V / 2, E - E / 2);
        final int[] perm = new int[V];
        for (int i = 0; i < V; ++i) {
            perm[i] = i;
        }
        StdRandom.shuffle(perm);
        final Graph G5 = new Graph(V);
        for (int v5 = 0; v5 < H1.V(); ++v5) {
            for (final int w : H1.adj(v5)) {
                G5.addEdge(perm[v5], perm[w]);
            }
        }
        for (int v5 = 0; v5 < H2.V(); ++v5) {
            for (final int w : H2.adj(v5)) {
                G5.addEdge(perm[V / 2 + v5], perm[V / 2 + w]);
            }
        }
        unitTest(G5, "Union of two disjoint cycles");
        final Graph G6 = GraphGenerator.simple(V, E);
        unitTest(G6, "simple graph");
    }
    
    private static class Edge
    {
        private final int v;
        private final int w;
        private boolean isUsed;
        
        public Edge(final int v, final int w) {
            this.v = v;
            this.w = w;
            this.isUsed = false;
        }
        
        public int other(final int vertex) {
            if (vertex == this.v) {
                return this.w;
            }
            if (vertex == this.w) {
                return this.v;
            }
            throw new IllegalArgumentException("Illegal endpoint");
        }
    }
}
