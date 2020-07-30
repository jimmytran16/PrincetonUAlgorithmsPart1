// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class QuickUnionUF
{
    private int[] parent;
    private int count;
    
    public QuickUnionUF(final int n) {
        this.parent = new int[n];
        this.count = n;
        for (int i = 0; i < n; ++i) {
            this.parent[i] = i;
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
    
    private void validate(final int p) {
        final int n = this.parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }
    
    @Deprecated
    public boolean connected(final int p, final int q) {
        return this.find(p) == this.find(q);
    }
    
    public void union(final int p, final int q) {
        final int rootP = this.find(p);
        final int rootQ = this.find(q);
        if (rootP == rootQ) {
            return;
        }
        this.parent[rootP] = rootQ;
        --this.count;
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final QuickUnionUF uf = new QuickUnionUF(n);
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
