// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class WeightedQuickUnionUF
{
    private int[] parent;
    private int[] size;
    private int count;
    
    public WeightedQuickUnionUF(final int n) {
        this.count = n;
        this.parent = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; ++i) {
            this.parent[i] = i;
            this.size[i] = 1;
        }
    }
    
    public int count() {
        return this.count;
    }
    
    public int find(int p) {
        this.validate(p);
        while (p != this.parent[p]) {
            p = this.parent[p];
        }
        return p;
    }
    
    @Deprecated
    public boolean connected(final int p, final int q) {
        return this.find(p) == this.find(q);
    }
    
    private void validate(final int p) {
        final int n = this.parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }
    
    public void union(final int p, final int q) {
        final int rootP = this.find(p);
        final int rootQ = this.find(q);
        if (rootP == rootQ) {
            return;
        }
        if (this.size[rootP] < this.size[rootQ]) {
            this.parent[rootP] = rootQ;
            final int[] size = this.size;
            final int n = rootQ;
            size[n] += this.size[rootP];
        }
        else {
            this.parent[rootQ] = rootP;
            final int[] size2 = this.size;
            final int n2 = rootP;
            size2[n2] += this.size[rootQ];
        }
        --this.count;
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            final int p = StdIn.readInt();
            final int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)) {
                continue;
            }
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
