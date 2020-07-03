import PerlocationPackage.Perlocation;

public class Perlocations implements Perlocation{
  /**
   * Initializes an empty 2 dimensional array with default values to itself
   * Create the array with initially blocked sites
   * @param  n the dimensions of the array {n x n}
   */
  @Override
  public Percolation(int n){}

  // opens the site (row, col) if it is not open already
  @Override
  public void open(int row, int col){}

  // is the site (row, col) open?
  @Override
  public boolean isOpen(int row, int col){}

  // is the site (row, col) full?
  @Override
  public boolean isFull(int row, int col){}

  // returns the number of open sites
  @Override
  public int numberOfOpenSites(){}

  // does the system percolate?
  @Override
  public boolean percolates(){}

  public static void main(String avgs[]){

  }
}
