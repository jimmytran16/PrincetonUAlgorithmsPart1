// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class GraphGenerator
{
    private GraphGenerator() {
    }
    
    public static Graph simple(final int V, final int E) {
        if (E > V * (long)(V - 1) / 2L) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < 0) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Graph G = new Graph(V);
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
    
    public static Graph simple(final int V, final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        final Graph G = new Graph(V);
        for (int v = 0; v < V; ++v) {
            for (int w = v + 1; w < V; ++w) {
                if (StdRandom.bernoulli(p)) {
                    G.addEdge(v, w);
                }
            }
        }
        return G;
    }
    
    public static Graph complete(final int V) {
        return simple(V, 1.0);
    }
    
    public static Graph completeBipartite(final int V1, final int V2) {
        return bipartite(V1, V2, V1 * V2);
    }
    
    public static Graph bipartite(final int V1, final int V2, final int E) {
        if (E > V1 * (long)V2) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < 0) {
            throw new IllegalArgumentException("Too few edges");
        }
        final Graph G = new Graph(V1 + V2);
        final int[] vertices = new int[V1 + V2];
        for (int i = 0; i < V1 + V2; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        final SET<Edge> set = new SET<Edge>();
        while (G.E() < E) {
            final int j = StdRandom.uniform(V1);
            final int k = V1 + StdRandom.uniform(V2);
            final Edge e = new Edge(vertices[j], vertices[k]);
            if (!set.contains(e)) {
                set.add(e);
                G.addEdge(vertices[j], vertices[k]);
            }
        }
        return G;
    }
    
    public static Graph bipartite(final int V1, final int V2, final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        }
        final int[] vertices = new int[V1 + V2];
        for (int i = 0; i < V1 + V2; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        final Graph G = new Graph(V1 + V2);
        for (int j = 0; j < V1; ++j) {
            for (int k = 0; k < V2; ++k) {
                if (StdRandom.bernoulli(p)) {
                    G.addEdge(vertices[j], vertices[V1 + k]);
                }
            }
        }
        return G;
    }
    
    public static Graph path(final int V) {
        final Graph G = new Graph(V);
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
    
    public static Graph binaryTree(final int V) {
        final Graph G = new Graph(V);
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
    
    public static Graph cycle(final int V) {
        final Graph G = new Graph(V);
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
    
    public static Graph eulerianCycle(final int V, final int E) {
        if (E <= 0) {
            throw new IllegalArgumentException("An Eulerian cycle must have at least one edge");
        }
        if (V <= 0) {
            throw new IllegalArgumentException("An Eulerian cycle must have at least one vertex");
        }
        final Graph G = new Graph(V);
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
    
    public static Graph eulerianPath(final int V, final int E) {
        if (E < 0) {
            throw new IllegalArgumentException("negative number of edges");
        }
        if (V <= 0) {
            throw new IllegalArgumentException("An Eulerian path must have at least one vertex");
        }
        final Graph G = new Graph(V);
        final int[] vertices = new int[E + 1];
        for (int i = 0; i < E + 1; ++i) {
            vertices[i] = StdRandom.uniform(V);
        }
        for (int i = 0; i < E; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        return G;
    }
    
    public static Graph wheel(final int V) {
        if (V <= 1) {
            throw new IllegalArgumentException("Number of vertices must be at least 2");
        }
        final Graph G = new Graph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 1; i < V - 1; ++i) {
            G.addEdge(vertices[i], vertices[i + 1]);
        }
        G.addEdge(vertices[V - 1], vertices[1]);
        for (int i = 1; i < V; ++i) {
            G.addEdge(vertices[0], vertices[i]);
        }
        return G;
    }
    
    public static Graph star(final int V) {
        if (V <= 0) {
            throw new IllegalArgumentException("Number of vertices must be at least 1");
        }
        final Graph G = new Graph(V);
        final int[] vertices = new int[V];
        for (int i = 0; i < V; ++i) {
            vertices[i] = i;
        }
        StdRandom.shuffle(vertices);
        for (int i = 1; i < V; ++i) {
            G.addEdge(vertices[0], vertices[i]);
        }
        return G;
    }
    
    public static Graph regular(final int V, final int k) {
        if (V * k % 2 != 0) {
            throw new IllegalArgumentException("Number of vertices * k must be even");
        }
        final Graph G = new Graph(V);
        final int[] vertices = new int[V * k];
        for (int v = 0; v < V; ++v) {
            for (int j = 0; j < k; ++j) {
                vertices[v + V * j] = v;
            }
        }
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V * k / 2; ++i) {
            G.addEdge(vertices[2 * i], vertices[2 * i + 1]);
        }
        return G;
    }
    
    public static Graph tree(final int V) {
        final Graph G = new Graph(V);
        if (V == 1) {
            return G;
        }
        final int[] prufer = new int[V - 2];
        for (int i = 0; i < V - 2; ++i) {
            prufer[i] = StdRandom.uniform(V);
        }
        final int[] degree = new int[V];
        for (int v = 0; v < V; ++v) {
            degree[v] = 1;
        }
        for (int j = 0; j < V - 2; ++j) {
            final int[] array = degree;
            final int n = prufer[j];
            ++array[n];
        }
        final MinPQ<Integer> pq = new MinPQ<Integer>();
        for (int v2 = 0; v2 < V; ++v2) {
            if (degree[v2] == 1) {
                pq.insert(v2);
            }
        }
        for (int k = 0; k < V - 2; ++k) {
            final int v3 = pq.delMin();
            G.addEdge(v3, prufer[k]);
            final int[] array2 = degree;
            final int n2 = v3;
            --array2[n2];
            final int[] array3 = degree;
            final int n3 = prufer[k];
            --array3[n3];
            if (degree[prufer[k]] == 1) {
                pq.insert(prufer[k]);
            }
        }
        G.addEdge(pq.delMin(), pq.delMin());
        return G;
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final int V2 = V / 2;
        final int V3 = V - V2;
        StdOut.println("complete graph");
        StdOut.println(complete(V));
        StdOut.println();
        StdOut.println("simple");
        StdOut.println(simple(V, E));
        StdOut.println();
        StdOut.println("Erdos-Renyi");
        final double p = E / (V * (V - 1) / 2.0);
        StdOut.println(simple(V, p));
        StdOut.println();
        StdOut.println("complete bipartite");
        StdOut.println(completeBipartite(V2, V3));
        StdOut.println();
        StdOut.println("bipartite");
        StdOut.println(bipartite(V2, V3, E));
        StdOut.println();
        StdOut.println("Erdos Renyi bipartite");
        final double q = E / (double)(V2 * V3);
        StdOut.println(bipartite(V2, V3, q));
        StdOut.println();
        StdOut.println("path");
        StdOut.println(path(V));
        StdOut.println();
        StdOut.println("cycle");
        StdOut.println(cycle(V));
        StdOut.println();
        StdOut.println("binary tree");
        StdOut.println(binaryTree(V));
        StdOut.println();
        StdOut.println("tree");
        StdOut.println(tree(V));
        StdOut.println();
        StdOut.println("4-regular");
        StdOut.println(regular(V, 4));
        StdOut.println();
        StdOut.println("star");
        StdOut.println(star(V));
        StdOut.println();
        StdOut.println("wheel");
        StdOut.println(wheel(V));
        StdOut.println();
    }
    
    private static final class Edge implements Comparable<Edge>
    {
        private int v;
        private int w;
        
        private Edge(final int v, final int w) {
            if (v < w) {
                this.v = v;
                this.w = w;
            }
            else {
                this.v = w;
                this.w = v;
            }
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
