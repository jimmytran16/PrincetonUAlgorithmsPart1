// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;

public class StaticSETofInts
{
    private int[] a;
    
    public StaticSETofInts(final int[] keys) {
        this.a = new int[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            this.a[i] = keys[i];
        }
        Arrays.sort(this.a);
        for (int i = 1; i < this.a.length; ++i) {
            if (this.a[i] == this.a[i - 1]) {
                throw new IllegalArgumentException("Argument arrays contains duplicate keys.");
            }
        }
    }
    
    public boolean contains(final int key) {
        return this.rank(key) != -1;
    }
    
    public int rank(final int key) {
        int lo = 0;
        int hi = this.a.length - 1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            if (key < this.a[mid]) {
                hi = mid - 1;
            }
            else {
                if (key <= this.a[mid]) {
                    return mid;
                }
                lo = mid + 1;
            }
        }
        return -1;
    }
}
