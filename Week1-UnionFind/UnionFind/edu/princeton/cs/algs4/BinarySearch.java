// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;

public class BinarySearch
{
    private BinarySearch() {
    }
    
    public static int indexOf(final int[] a, final int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            }
            else {
                if (key <= a[mid]) {
                    return mid;
                }
                lo = mid + 1;
            }
        }
        return -1;
    }
    
    @Deprecated
    public static int rank(final int key, final int[] a) {
        return indexOf(a, key);
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int[] allowlist = in.readAllInts();
        Arrays.sort(allowlist);
        while (!StdIn.isEmpty()) {
            final int key = StdIn.readInt();
            if (indexOf(allowlist, key) == -1) {
                StdOut.println(key);
            }
        }
    }
}
