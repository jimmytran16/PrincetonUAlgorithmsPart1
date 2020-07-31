
class StringStack{ /* Stack implementation using a linked list */
  private Node first = null; /* property of the stack class that holds the value of the item that is on top of the list */

  private class Node{ // Node class
    String item;
    Node next;
  }
  public boolean isEmpty(){ return first == null; } /* check if the first instance is null , if not then it is not empty */

  /* function to push new item into stack */
  public void push(String item){
    Node oldfirst = first; /* init the first one of the top of the list to be the 'oldfirst' */
    first = new Node(); /* make a new node */
    first.item = item; /* give the node its value */
    first.next = oldfirst; /* set the new item to point to the old first (the one that was on the top before the new item was pushed) */
  }

  /* function to pop the stack */
  public String pop(){
    String item = first.item; /* get the item that is on top of the stack */
    first = first.next; /* assign the next item on the stack to be the first (top of the stack) */
    return item; /* return the item */
  }
}

class StringArrayStack{ /* Stack implemenetation using array */
  private String[] s;
  private int N = 0;

  public StringArrayStack(int size){ s = new String[size];} /* Constructor taking in a int size params and initializing an instance of array */

  public boolean isEmpty(){ return N == 0; } /* checking if the size of array/stack is empty */

  public void push(String item){ /* adding an item to the array and incrementing the size N of its current compacity of items */
    s[N++] = item;
  }

  public String pop(){ /* returning the last element that was added by N element, and then decrementing the N */
    return s[--N];
  }
}

class StringStackTesting{
  public static void main(String args[]){
    StringStack stacker = new StringStack();
    stacker.push("Jimmy");
    stacker.push("is");
    stacker.push("cool!");
    System.out.println("POPPED - - "+stacker.pop());
  }
}
