import java.io.*;

public class UnionFind {
    private int[] id;
    public UnionFind(int N){
        id = new int[N];
        for (int i = 0; i < N; i++) /* Make arrays of objects 0 - 9 and assign the ID value to itself */
          id[i] = i;

        //ELEMENT # (THE OBJECT ITSELF) [0][1][2][3][4][5][6][7][8][9]
        //ELEMENT VALUE (THE ID)        [0][1][2][3][4][5][6][7][8][9] <-- CHANGES OVER TIME
    }

    public boolean connected(int id1, int id2){ /* If they have the same ID, that means that are both connected */
        return id[id1] == id[id2];
    }

    public void union(int id1, int id2) {
        int id_1 = id[id1]; /* Get the values of the current id of the objects and assign it to the vars */
        int id_2 = id[id2];
        for (int i = 0; i < id.length; i++) { /* Loop through the array and see if the id */
            if (id[i] == id_1) {id[i] = id_2;} /* merge the two objects together by changing any ELEMENT with value id_1 to id_2, making all objects union */
            /* ALL elements that are related to one another, all are merged into 1 ID value */
        }
      }

    public static void main(String[] args) {
        UnionFind q = new UnionFind(10); /* Call an instance of the UnionFind class and pass in 10 as a SIZE */
        /* Connect these pairs together */
        q.union(3, 8);
        q.union(5, 2);
        q.union(2, 3);
        q.union(9, 1);
        q.union(7, 4);
        q.union(3, 9);
      //AFTER Union
      //ELEMENT # (THE OBJECT ITSELF) [0][1][2][3][4][5][6][7][8][9]
      //ELEMENT VALUE (THE ID)        [0][1][1][1][4][1][6][4][1][1] <-- VALUES AFTER UNION
        System.out.println("Prints OUT all the elements and its values(ID)");
        for (int i = 0; i < 10; i++) /* Print out the objects with its ID */
            System.out.println("object "+i+"= "+q.id[i] + " ");

        System.out.println("CHECKING IF the 2 pairs are connected! ");
        System.out.println("2 and 3 "+q.connected(2,3));
        System.out.println("2 and 4 "+q.connected(2,4));
        System.out.println("2 and 5 "+q.connected(2,5));
        System.out.println("5 and 3 "+q.connected(5,3));
        System.out.println("1 and 3 "+q.connected(1,3));
        System.out.println("3 and 9"+q.connected(3,9));
    }
}
