// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class EulerianPath
{
    private Stack<Integer> path;
    
    public EulerianPath(final Graph G) {
        this.path = null;
        int oddDegreeVertices = 0;
        int s = nonIsolatedVertex(G);
        for (int v = 0; v < G.V(); ++v) {
            if (G.degree(v) % 2 != 0) {
                ++oddDegreeVertices;
                s = v;
            }
        }
        if (oddDegreeVertices > 2) {
            return;
        }
        if (s == -1) {
            s = 0;
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
        final Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        this.path = new Stack<Integer>();
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
            this.path.push(v3);
        }
        if (this.path.size() != G.E() + 1) {
            this.path = null;
        }
        assert this.certifySolution(G);
    }
    
    public Iterable<Integer> path() {
        return this.path;
    }
    
    public boolean hasEulerianPath() {
        return this.path != null;
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
            return true;
        }
        int oddDegreeVertices = 0;
        for (int v = 0; v < G.V(); ++v) {
            if (G.degree(v) % 2 != 0) {
                ++oddDegreeVertices;
            }
        }
        if (oddDegreeVertices > 2) {
            return false;
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
        return this.hasEulerianPath() != (this.path() == null) && this.hasEulerianPath() == satisfiesNecessaryAndSufficientConditions(G) && (this.path == null || this.path.size() == G.E() + 1);
    }
    
    private static void unitTest(final Graph G, final String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);
        final EulerianPath euler = new EulerianPath(G);
        StdOut.print("Eulerian path:  ");
        if (euler.hasEulerianPath()) {
            for (final int v : euler.path()) {
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
        final Graph G3 = new Graph(G2);
        G3.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G3, "one random edge added to Eulerian path");
        final Graph G4 = new Graph(V);
        final int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");
        final Graph G5 = new Graph(V);
        G5.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G5, "single edge");
        final Graph G6 = new Graph(V);
        unitTest(G6, "empty graph");
        final Graph G7 = GraphGenerator.simple(V, E);
        unitTest(G7, "simple graph");
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
