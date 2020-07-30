import QuickUnionPackage.QuickUnion;

public class Perlocation{
  /**
   * Initializes an array size with NxN number of objects
   * Create the array with initially blocked sites, meaning that it's root is itself
   * @param  n the dimension of the array {n x n}
   */
  public QuickUnion grid;
  private int gridSize;
  private int dimension;
  private int openSiteCounter;
  private int top[];
  private int bottom[];

  public Perlocation(int n){ /* This constructor will define an array that has N x N  size objects */
    grid = new QuickUnion(n*n); /* call an instance of a QuickUnion and pass in the size of the grid */
    gridSize = n*n;
    dimension = n;
    top = new int[n];
    bottom = new int[n];
    int counter = 0;
    // populating the top and bottom rows with its values
    for(int i = 1; i < top.length; i++){
      top[i] = i;
    }
    for(int x = top.length + 1; x > gridSize - n; x-- ){
      top[counter++] = x;
    }
  }

  // opens the site (row, col) if it is not open already

  public void open(int row, int col){
     /* Check if this site is already open, if it is open then don't do anything */
    if(isOpen(row,col)){
      return;
    }
    int element = dimension * (row - 1) + col;
    grid.check[element] = 1; // Set the position in the grid to be open

    /* Check if adjacents are open sites, if yes then connect all together */
    // connectAllOpenAdjacents(row , column, element);
    openSiteCounter++; /* increment the open sites variable to keep track of open sites */

  }

  // public void connectAllOpenAdjacents(int row, int column,int element){
  //   if()
  //   /* Check if it is on the left edge or right edge in the grid */
  //   /* Check if  */
  //   // if(!(dimension-1)%element == 0 || element == 1)){
  //   //
  //   // }
  //   // else{
  //   //
  //   // }
  //   //If the number is a multiple of 6, then that means that they are on the far right edge
  //   //If you subtract the number by 1, and it is the multiple of 6, that means that is in the left edges
  // }

  // is the site (row, col) open?

  public boolean isOpen(int row, int col){
    int element = dimension * (row - 1) + col;
    if (grid.check[element] == 0){ /* if returns 0 that means that it is not open */
      return false;
    }else{
      return true;
    }
  }

  // is the site (row, col) full? -- that means that the coordinate is connected to the top

  public boolean isFull(int row, int col){
    int element = dimension * (row - 1) + col;
    for(int i = 0; i < top.length; i++){
      if(element == top[i]){
        return true;
      }
      if(grid.connected(element,top[i])){
        return true;
      }
    }
    return false;
  }

  // returns the number of open sites

  public int numberOfOpenSites(){
    return openSiteCounter;
  }

  // does the system percolate?

  public boolean percolates(){
    /* Compare the bottom row to the top to see if there are any connections by calling the connected() function of the QuickUnion DS */
    for(int i = 0; i < bottom.length; i++){
      for(int x = 0;x< top.length;x++){
        if(grid.connected(bottom[i],top[x])){
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String avgs[]){
    /* define a 5 x 5 grid */
    Perlocation perlocate = new Perlocation(6);
    perlocate.open(1,1);
    System.out.println(perlocate.isOpen(1,1));
    perlocate.open(1,2);
    System.out.println(perlocate.numberOfOpenSites());
    System.out.println(perlocate.percolates());
  }
}
