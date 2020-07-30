// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class QuickFindUF
{
    private int[] id;
    private int count;
    
    public QuickFindUF(final int n) {
        this.count = n;
        this.id = new int[n];
        for (int i = 0; i < n; ++i) {
            this.id[i] = i;
        }
    }
    
    public int count() {
        return this.count;
    }
    
    public int find(final int p) {
        this.validate(p);
        return this.id[p];
    }
    
    private void validate(final int p) {
        final int n = this.id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }
    
    @Deprecated
    public boolean connected(final int p, final int q) {
        this.validate(p);
        this.validate(q);
        return this.id[p] == this.id[q];
    }
    
    public void union(final int p, final int q) {
        this.validate(p);
        this.validate(q);
        final int pID = this.id[p];
        final int qID = this.id[q];
        if (pID == qID) {
            return;
        }
        for (int i = 0; i < this.id.length; ++i) {
            if (this.id[i] == pID) {
                this.id[i] = qID;
            }
        }
        --this.count;
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final QuickFindUF uf = new QuickFindUF(n);
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
