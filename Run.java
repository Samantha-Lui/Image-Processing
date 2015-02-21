/* Run.java */

/**
 *  Run is a class used internally by RunIterator class.  A RunIterator object
 *  is a doubly-linked list, and a Run is a node of a doubly-linked
 *  list. Each Run has four references:  one to the previous node in the list,
 *  one to the next node in the list, one to an RGB object and one to the run 
 *  length of the run represented by the node.
 *
 */

class Run {
	
	Run prev;
	Run next;
	RGB rgb;
	int runLength;


  /**
   *  Run() (with four parameters) constructs a list node referencing the
   *  RGB item "rgb" and the run length "runLength", whose previous list 
   *  node is to be "prev" and next list node is to be "next".
   */
  Run(Run prev, Run next, int runLength, RGB rgb) {
    this.prev = prev;
    this.next = next;
    this.runLength = runLength;
    this.rgb = rgb;
  }
  
  /**
   * equals() returns true if this object is identical to the
   * specified Run.
   * @param run the specified Run object.
   * @return true if this is identical to the specified Run object.
   */
  public boolean equals(Run run){
	  return prev==run.prev && next==run.next && rgb.equals(run.rgb) && runLength == run.runLength;
  }
  
  /**
   * toString() returns a String representation of this object
   * in the format (previous node, next node, run length, RGB).
   * 
   * @return s A String representation of this object.
   */
  public String toString(){
	  String s = "(" + prev + "," + next + "," + runLength + "," + rgb +")";
	  return s;
  }
}
