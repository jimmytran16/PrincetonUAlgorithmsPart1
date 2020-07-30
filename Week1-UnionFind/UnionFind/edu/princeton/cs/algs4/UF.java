// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class UF
{
    private int[] parent;
    private byte[] rank;
    private int count;
    
    public UF(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.count = n;
        this.parent = new int[n];
        this.rank = new byte[n];
        for (int i = 0; i < n; ++i) {
            this.parent[i] = i;
            this.rank[i] = 0;
        }
    }
    
    public int find(int p) {
        this.validate(p);
        while (p != this.parent[p]) {
            this.parent[p] = this.parent[this.parent[p]];
            p = this.parent[p];
        }
        return p;
    }
    
    public int count() {
        return this.count;
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
        if (this.rank[rootP] < this.rank[rootQ]) {
            this.parent[rootP] = rootQ;
        }
        else if (this.rank[rootP] > this.rank[rootQ]) {
            this.parent[rootQ] = rootP;
        }
        else {
            this.parent[rootQ] = rootP;
            final byte[] rank = this.rank;
            final int n = rootP;
            ++rank[n];
        }
        --this.count;
    }
    
    private void validate(final int p) {
        final int n = this.parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final UF uf = new UF(n);
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
