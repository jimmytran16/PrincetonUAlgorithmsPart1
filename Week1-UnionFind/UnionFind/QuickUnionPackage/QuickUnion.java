package QuickUnionPackage; /* This package is used for the Perlocation homework (Perlocations.java) */

import java.util.*;
import java.io.*;


public class QuickUnion {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components
    public int check[]; //this will be an array to check if the site is open or closed

    /**
     * Initializes an empty union-find data structure and gives all values ..
     * of the arrays elements to itself (parent[N] = N)
     * @param  n the number of elements to be made
     * @throws IllegalArgumentException if the argument(n) is less than 0
     */
    public QuickUnion(int n) {
        parent = new int[n];
        check = new int[n];
        count = n;
        for (int i = 1; i < n; i++) {
            parent[i] = i; /* Assign all values to itself */
        }
        for (int x = 1; x < n; x++) {
            check[x] = 0; /* Assign all values to be zero so it can be closed */
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) /* Go to the root of that current node by looping through until the value of the element is itself. */
            p = parent[p];
        return p; /* return the ROOT */
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public boolean connected(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        System.out.printf("ROOT OF q --%d ",rootQ);
        System.out.printf("ROOT OF p --%d ",rootP);
        return rootP == rootQ;
    }

    /* Merges the nodes together by getting the root of {p} to point to the root of {q} */
    public void union(int p, int q) {
        int rootP = find(p); /* Make the root of {p} and {q} */
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ; /* Make the root of {p} point to the root of {q} -- {q} is the parent root now */
        count--;
    }
}
