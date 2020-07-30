
/* Improvement of QuickUnion (Weighted) the Algorithm , self approach */

public class QuickUnion2 {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components
    private int size[]; //array that keeps track of the size of the existing trees

    /**
     * Initializes an empty union-find data structure and gives all values ..
     * of the arrays elements to itself (parent[N] = N)
     * @param  n the number of elements to be made
     * @throws IllegalArgumentException if the argument(n) is less than 0
     */
    public QuickUnion2(int n) {
        parent = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i; /* Assign all of the element's value to itself */
        }
    }

    public int count() {
        return count;
    }

    private boolean compareSize(int p, int q){
      int counterp = 1;
      int counterq = 1;
      while(p!=parent[p]){
        counterp++;
      }
      while(q!=parent[q]){
        counterq++;
      }
      System.out.println("SIZE OF P- "+counterp);
      System.out.println("SIZE OF q- "+counterq);
      if(counterp == counterq || counterp < counterq){ /* if both have the same size or p's tree is smaller than q's tree then return true*/
        return true;
      }
      return false; /* if q's tree is greater than return false */
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
        if (rootP == rootQ) return; /* if they are already in the same tree (HAVE SAME ROOT), then do nothing */
        if(compareSize(p,q)){ /* if p's tree is bigger or if they are the same size */
          parent[rootQ] = rootP;
        }else{
          parent[rootP] = rootQ; /* Make the root of {p} point to the root of {q} -- {q} is the parent root now */
        }
        count--;
    }

    public static void main(String[] args) {
        QuickUnion2 uf = new QuickUnion2(10); /* init the QuickUnion2 instance , passing in 10 as a size of the items in array */
        /* Connect them together */
        uf.union(1,2);
        uf.union(2,4);
        // uf.union(5,6);
        // uf.union(5,3);
        // uf.union(7,8);
        // uf.union(9,0);
        /* Check if connected */
        System.out.println("PRINT IF CONNECTED");
        System.out.println("------------------\n");
        System.out.println("Connected? 9,0-"+uf.connected(9,0));
        System.out.println("Connected? 3,4-"+uf.connected(3,4));
        System.out.println("Connected? 4,6-"+uf.connected(4,6));
        System.out.println("Connected? 7,8-"+uf.connected(7,8));
        System.out.println("Connected? 0,1-"+uf.connected(0,1));
    }
}
