/* Practice two dimensional arrays */

public class TwoDemArray{
  private int array[][];
  private int size;
 /**
  * Constructor that initializes an array
  * @param size the size of the 2-D array (size by size)
  */
  public TwoDemArray(int size){
    if(size <= 0){
      throw new IllegalArgumentException("The size has to be greater than 0 ");
    }
    this.size = size;
    array = new int[size][size];
  }
  public void open(int x, int y){ /* Give that coordinate the value 1 if we open it */
    validateDomain(x,y);
    array[x][y] = 1;
  }
  public boolean isOpen(int x,int y){ /* Boolean function --  Check if that particular coordinate is open or not */
    return 1 == array[x][y];
  }
  public void print(){ /* Print out the graph of the 2-D array */
    int counter = 0;
    for(int i = 0 ;i<size;i++){
      for(int x = 0;x<size;x++){
        System.out.print(array[i][x]+ "   ");
        counter ++;
        if(counter % size == 0){
          System.out.println();
        }
      }
    }
    System.out.println(counter);
  }
  public void validateDomain(int n, int x){
    if(size < n || size < x){
      throw new IllegalArgumentException("The point ("+n+","+x+") does not exist!");
    }
  }
  public static void main(String arg[]){
    try{
      TwoDemArray array = new TwoDemArray(1);
      array.open(2,1);
      array.open(3,1);
      array.open(4,1);
      array.open(0,0);
      array.print();
      System.out.println(array.isOpen(4,1));
      System.out.println(array.isOpen(4,2));
      System.out.println(array.isOpen(0,0));
    }
    catch(Exception error){
      System.out.println(error);
    }
  }
}
