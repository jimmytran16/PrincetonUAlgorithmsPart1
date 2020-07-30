// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class DigraphGenerator
{
    private DigraphGenerator() {
    }
    
    public static Digraph simple(final int V, final int E) {
        if (E > V * (long)(V - 1)) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < 0) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Digraph G = new Digraph(V);
        final SET<Edge> set = new SET<Edge>();
        while (G.E() < E) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final Edge e = new Edge(v, w);
            if (v != w && !set.contains(e)) {
                set.add(e);
                G.addEdge(v, w);
            }
        }
        return G;
    }
    
    public static Digraph simple(final int V, final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        final Digraph G = new Digraph(V);
        for (int v = 0; v < V; ++v) {
            for (int w = 0; w < V; ++w) {
                if (v != w && StdRandom.bernoulli(p)) {
                    G.addEdge(v, w);
                }
            }
        }
        return G;
    }
    
    public static Digraph complete(final int V) {
        final Digraph G = new Digraph(V);
        for (int v = 0; v < V; ++v) {
            for (int w = 0; w < V; ++w) {
                if (v != w) {
                    G.addEdge(v, w);
                }
            }
        }
        return G;
    }
    
    public static Digraph dag(final int V, final int E) {
        if (E > V * (long)(V - 1) / 2L) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < 0) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Digraph G = new Digraph(V);
        final SET<Edge> set = new SET<Edge>();
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        while (G.E() < E) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final Edge e = new Edge(v, w);
            if (v < w && !set.contains(e)) {
                set.add(e);
                G.addEdge(vertices[v], vertices[w]);
            }
        }
        return G;
    }
    
    public static Digraph tournament(final int V) {
        final Digraph G = new Digraph(V);
        for (int v = 0; v < G.V(); ++v) {
            for (int w = v + 1; w < G.V(); ++w) {
                if (StdRandom.bernoulli(0.5)) {
                    G.addEdge(v, w);
                }
                else {
                    G.addEdge(w, v);
                }
            }
        }
        return G;
    }
    
    public static Digraph completeRootedInDAG(final int V) {
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V; ++i) {
            for (int j = i + 1; j < V; ++j) {
                G.addEdge(vertices[i], vertices[j]);
            }
        }
        return G;
    }
    
    public static Digraph rootedInDAG(final int V, final int E) {
        if (E > V * (long)(V - 1) / 2L) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < V - 1) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Digraph G = new Digraph(V);
        final SET<Edge> set = new SET<Edge>();
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int v = 0; v < V - 1; ++v) {
            final int w = StdRandom.uniform(v + 1, V);
            final Edge e = new Edge(v, w);
            set.add(e);
            G.addEdge(vertices[v], vertices[w]);
        }
        while (G.E() < E) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final Edge e = new Edge(v, w);
            if (v < w && !set.contains(e)) {
                set.add(e);
                G.addEdge(vertices[v], vertices[w]);
            }
        }
        return G;
    }
    
    public static Digraph completeRootedOutDAG(final int V) {
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V; ++i) {
            for (int j = i + 1; j < V; ++j) {
                G.addEdge(vertices[j], vertices[i]);
            }
        }
        return G;
    }
    
    public static Digraph rootedOutDAG(final int V, final int E) {
        if (E > V * (long)(V - 1) / 2L) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < V - 1) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Digraph G = new Digraph(V);
        final SET<Edge> set = new SET<Edge>();
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int v = 0; v < V - 1; ++v) {
            final int w = StdRandom.uniform(v + 1, V);
            final Edge e = new Edge(w, v);
            set.add(e);
            G.addEdge(vertices[w], vertices[v]);
        }
        while (G.E() < E) {
            final int v = StdRandom.uniform(V);
            final int w = StdRandom.uniform(V);
            final Edge e = new Edge(w, v);
            if (v < w && !set.contains(e)) {
                set.add(e);
                G.addEdge(vertices[w], vertices[v]);
            }
        }
        return G;
    }
    
    public static Digraph rootedInTree(final int V) {
        return rootedInDAG(V, V - 1);
    }
    
    public static Digraph rootedOutTree(final int V) {
        return rootedOutDAG(V, V - 1);
    }
    
    public static Digraph path(final int V) {
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V - 1; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        return G;
    }
    
    public static Digraph binaryTree(final int V) {
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 1; i < V; ++i) {
            G.addEdge(vertices[i], vertices[(i - 1) / 2]);
        }
        return G;
    }
    
    public static Digraph cycle(final int V) {
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V - 1; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        G.addEdge(vertices[V - 1], vertices[0]);
        return G;
    }
    
    public static Digraph eulerianCycle(final int V, final int E) {
        if (E <= 0) {
            throw new IllegalArgumentException("An Eulerian cycle must have at least one edge");
        }
        if (V <= 0) {
            throw new IllegalArgumentException("An Eulerian cycle must have at least one vertex");
        }
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[E];
        for (int i = 0; i < E; ++i) {
            vertices[i] = StdRandom.uniform(V);
        }
        for (int i = 0; i < E - 1; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        G.addEdge(vertices[E - 1], vertices[0]);
        return G;
    }
    
    public static Digraph eulerianPath(final int V, final int E) {
        if (E < 0) {
            throw new IllegalArgumentException("negative number of edges");
        }
        if (V <= 0) {
            throw new IllegalArgumentException("An Eulerian path must have at least one vertex");
        }
        final Digraph G = new Digraph(V);
        final int[] vertices = new int[E + 1];
        for (int i = 0; i < E + 1; ++i) {
            vertices[i] = StdRandom.uniform(V);
        }
        for (int i = 0; i < E; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        return G;
    }
    
    public static Digraph strong(final int V, final int E, final int c) {
        if (c >= V || c <= 0) {
            throw new IllegalArgumentException("Number of components must be between 1 and V");
        }
        if (E <= 2 * (V - c)) {
            throw new IllegalArgumentException("Number of edges must be at least 2(V-c)");
        }
        if (E > V * (long)(V - 1) / 2L) {
            throw new IllegalArgumentException("Too many edges");
        }
        final Digraph G = new Digraph(V);
        final SET<Edge> set = new SET<Edge>();
        final int[] label = new int[V];
        for (int v = 0; v < V; ++v) {
            label[v] = StdRandom.uniform(c);
        }
        for (int i = 0; i < c; ++i) {
            int count = 0;
            for (int v2 = 0; v2 < G.V(); ++v2) {
                if (label[v2] == i) {
                    ++count;
                }
            }
            final int[] vertices = new int[count];
            int j = 0;
            for (int v3 = 0; v3 < V; ++v3) {
                if (label[v3] == i) {
                    vertices[j++] = v3;
                }
            }
            StdRandom.shuffle(vertices);
            for (int v3 = 0; v3 < count - 1; ++v3) {
                final int w = StdRandom.uniform(v3 + 1, count);
                final Edge e = new Edge(w, v3);
                set.add(e);
                G.addEdge(vertices[w], vertices[v3]);
            }
            for (int v3 = 0; v3 < count - 1; ++v3) {
                final int w = StdRandom.uniform(v3 + 1, count);
                final Edge e = new Edge(v3, w);
                set.add(e);
                G.addEdge(vertices[v3], vertices[w]);
            }
        }
        while (G.E() < E) {
            final int v = StdRandom.uniform(V);
            final int w2 = StdRandom.uniform(V);
            final Edge e2 = new Edge(v, w2);
            if (!set.contains(e2) && v != w2 && label[v] <= label[w2]) {
                set.add(e2);
                G.addEdge(v, w2);
            }
        }
        return G;
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        StdOut.println("complete graph");
        StdOut.println(complete(V));
        StdOut.println();
        StdOut.println("simple");
        StdOut.println(simple(V, E));
        StdOut.println();
        StdOut.println("path");
        StdOut.println(path(V));
        StdOut.println();
        StdOut.println("cycle");
        StdOut.println(cycle(V));
        StdOut.println();
        StdOut.println("Eulierian path");
        StdOut.println(eulerianPath(V, E));
        StdOut.println();
        StdOut.println("Eulierian cycle");
        StdOut.println(eulerianCycle(V, E));
        StdOut.println();
        StdOut.println("binary tree");
        StdOut.println(binaryTree(V));
        StdOut.println();
        StdOut.println("tournament");
        StdOut.println(tournament(V));
        StdOut.println();
        StdOut.println("DAG");
        StdOut.println(dag(V, E));
        StdOut.println();
        StdOut.println("rooted-in DAG");
        StdOut.println(rootedInDAG(V, E));
        StdOut.println();
        StdOut.println("rooted-out DAG");
        StdOut.println(rootedOutDAG(V, E));
        StdOut.println();
        StdOut.println("rooted-in tree");
        StdOut.println(rootedInTree(V));
        StdOut.println();
        StdOut.println("rooted-out DAG");
        StdOut.println(rootedOutTree(V));
        StdOut.println();
    }
    
    private static final class Edge implements Comparable<Edge>
    {
        private final int v;
        private final int w;
        
        private Edge(final int v, final int w) {
            this.v = v;
            this.w = w;
        }
        
        @Override
        public int compareTo(final Edge that) {
            if (this.v < that.v) {
                return -1;
            }
            if (this.v > that.v) {
                return 1;
            }
            if (this.w < that.w) {
                return -1;
            }
            if (this.w > that.w) {
                return 1;
            }
            return 0;
        }
    }
}
