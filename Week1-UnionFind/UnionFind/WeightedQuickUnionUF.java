/* Improvement of the QuickUnion Algorithm , (Weighted) Quick Union */

import java.util.HashMap;
import java.util.Map;

public class WeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    public int[] size;     // size[i] = number of elements in subtree rooted at i
    private int count;      // number of components

    public WeightedQuickUnionUF(int n) {
        count = n;
        /* init the array parent and size */
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; /* assign the root of the current node to itself */
            size[i] = 1; /* inits all the value of the node's tree to be of value (size) 1 */
        }
    }

    public int count() {
        return count;
    }
    public int find(int p) { /* get the ROOT of the node and returns it */
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }


    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) { /* if the size of p's tree is smaller than the size of q's tree then ..... */
            System.out.println("Root Q - "+parent[rootP]+"---->"+rootQ+"(new parent)");
            parent[rootP] = rootQ; // then point p's ROOT to q's ROOT
            size[rootQ] += size[rootP]; /* add up the size of p's tree to the size of q's tree */
            System.out.println("NEW SIZE OF ROOT Q - "+size[rootQ]);
        }
        else {
          System.out.println("Root P - "+parent[rootQ]+"---->"+rootP+"(new parent)");
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ]; /* increment the size of p's ROOT with the size of q's tree */
            System.out.println("NEW SIZE OF ROOT P - "+size[rootP]);
        }
        count--;
    }

    public static void main(String[] args) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
        uf.union(1,2);
        uf.union(2,3);
        uf.union(4,2);
        uf.union(1,5);
        uf.union(1,6);
        uf.union(1,7);
        uf.union(1,8);
        uf.union(0,9);

        /* Using hashmap to store the pairs to pass inside connected parameter to see if they are part of a union */
        Map<Integer,Integer> pairs = new HashMap<>(); /* Hashmap to contain pairs to compare if connected */
        pairs.put(9,8);
        pairs.put(7,0);
        pairs.put(0,9);
        pairs.put(4,6);
        pairs.put(2,1);

        /* iterate through hashmap checking pairs if connected */
        for(Map.Entry<Integer,Integer> entry: pairs.entrySet()){
          System.out.println(entry.getKey() + " & " + entry.getValue() + " connected ?: " + uf.connected(entry.getKey(),entry.getValue()));
        }

    }

}
