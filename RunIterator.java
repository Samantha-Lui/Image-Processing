/* RunIterator.java */

/**
 *  The RunIterator class iterates over a RunLengthEncoding and allows other
 *  classes to inspect the runs in a run-length encoding, one run at a time.
 *  A newly constructed RunIterator "points" to the first run in the encoding
 *  used to construct it.  Each time next() is invoked, it returns a run
 *  (represented as an array of four ints); a sequence of calls to next()
 *  returns run in consecutive order until every run has been returned.
 *
 *  Client classes should never call the RunIterator constructor directly;
 *  instead they should invoke the iterator() method on a RunLengthEncoding
 *  object, which will construct a properly initialized RunIterator for the
 *  client.
 *
 *  Calls to hasNext() determine whether another run is available, or whether
 *  the iterator has reached the end of the run-length encoding.  When
 *  a RunIterator reaches the end of an encoding, it is no longer useful, and
 *  the next() method may throw an exception; thus it is recommended to check
 *  hasNext() before each call to next(). Invoke the reset() method to iterate
 *  through the encoding again.
 */


import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class RunIterator implements Iterator {

	// Head and tail of the doubly-linked list.
	private Run head, tail;
	// Total number of nodes in the linked list.
	private int size;
	// Reference to the current node at which the 
	// iterator is traveling.
	private Run cur;


  /**
   *  RunIterator() constructs an empty iterator.  Runs are added
   *  to the iterator through the call to the add() method after a RunIterator 
   *  has been initiated.
   */
  public RunIterator() {
	  head = null;
	  tail = null;
	  size = 0;
	  cur = null;
  }

  /**
   * current() returns the run length, red, green and blue intensities
   * of "this" as an array.
   * 
   * @return An integer array containing the run length, red, green and 
   * blue intensities of "this"
   */
  public int [] current(){
	  int [] currentContent = {cur.runLength, (short) cur.rgb.getRGB()[0], 
			  (short) cur.rgb.getRGB()[1], (short) cur.rgb.getRGB()[2]};
    return currentContent;
  }
  
  /**
   * size() returns the total number of runs in the run-length encoding.
   * 
   * @return the total number of runs in the run-length encoding.
   */
  public int size(){
	  return size;
  }
  
  /**
   *  hasNext() returns true if this iterator has more runs.  If it returns
   *  false, then the next call to next() may throw an exception.
   *
   *  @return true if the iterator has more elements.
   */
  public boolean hasNext() {
    return size != 0 && cur.next != null;
  }
  
  /**
   * reset() returns the current pointer to the head of the iterator.
   */
  public void reset() {
	    cur = head;
	  }

  /**
   *  next() returns an array of 4 ints that specifies the current run in the
   *  sequence.  It also advances the iterator to the next run, so that the
   *  next call to next() will return the following run.
   *
   *  If "this" RunIterator has returned every run, it cannot be expected to
   *  behave well.  Thus, the hasNext() method is invoked prior to every call
   *  to the next() method to prevent the NoSuchElementException.
   *
   *  @return an array of 4 ints that specify the current run in the sequence.
   *  The pixel count is in index [0]; the red value is in index [1]; the green
   *  value is in index [2]; and the blue value is in index [3].
   */
  public int[] next() {
    // Construct a new array of 4 ints, fill in its values, and return it.
    // Don't forget to advance the RunIterator's pointer so that the next
    // call to next() will return the subsequent run.
    // Replace the following line with your solution.
	  Run next = cur.next;
	  int [] nextContent = {next.runLength, next.rgb.getRGB()[0], 
			  				next.rgb.getRGB()[1], next.rgb.getRGB()[2]};
	  cur = next;
    return nextContent;
  }

  /**
   *  append() appends a Run object with the specified run length and RGB to 
   *  the end of this iterator.  The number of nodes is also incremented by
   *  one each time the method is invoked.
   *  
   * @param runlength The specified run length.
   * @param rgb The specific RGB object.
   */
  public void append(int runlength, RGB rgb) {
	  
	  // Add the first run to the iterator.
	  // Such run refers to both the head and tail
	  // of the iterator.
	  if(size == 0){
		  head = new Run(head, tail, runlength, rgb);
		  tail = head;
		  cur = head;
	  }
	  else{
		  // Append the second run to the iterator creating
		  // a distinct tail from the head.
		  if(size == 1){
			  tail = new Run(head, null, runlength, rgb);
			  head.next = tail;
		  }
		  // Append a run to the iterator as a new distinct tail.
		  else{
			  tail.next = new Run(tail, null, runlength, rgb);
			  tail = tail.next;
		  }
	  }
	  size++;
  }
  
  /**
   * pr() returns true if the previous node of the current node in 
   * "this" iterator has the specified RGB values.
   * 
   * @param r The specified RGB object.
   * @return True if the previous node of the current node in 
   * "this" iterator has the specified RGB values.
   */
  protected boolean pr(RGB r){
	  return cur.prev.rgb.equals(r);
  }
  
  /**
   * nr() returns true if the next node of the current node in 
   * "this" iterator has the specified RGB values.
   * 
   * @param r The specified RGB object.
   * @return True if the next node of the current node in 
   * "this" iterator has the specified RGB values.
   */
  protected boolean nr(RGB r){
	  return cur.next.rgb.equals(r);
  }
  
  /**
   * modifyCurrentNode() changes the RGB object at the specified position 
   * of the current node of this iterator to the specified RGB object 
   * and then updates the run-length encoding accordingly.
   * 
   * 
   * @param m The specific position in the current node.
   * @param r The specific RGB object.
   */
  public void modifyCurrentNode(int m, RGB r){
	  
	  // The current run covers a single pixel.
	  if(cur.runLength==1){
		  
		  // There is only one run in the run-length encoding.
		  if(size==1){
			  /*
			   * 1
			   * Change the RGB of the current run to r. 
			   */
			  cur.rgb = r;
			  return;
		  }
		  // There are more than one runs in the run-length encoding.
		  else{
			  // Current node is the head of the iterator.
			  if(cur==head){
				  
				  // The next node has the same RGB as the specified RGB.
				  if(nr(r)){
					  /*
					   * 2
					   * The current node absorbs the next node.
					   */
					  size--;
					  cur.runLength += cur.next.runLength;
					  cur.next = cur.next.next;
					  if(cur.next != null)
						  cur.next.prev = cur;
					  else
						  tail = cur;
					  return;
				  }
				// The next node has a different RGB from the specified RGB.
				  else{
					  /*
					   * 24
					   * Change the RGB of the current run to r. 
					   */
					  cur.rgb = r;
					  return;
				  }
			  }
			  // Current node is not the head of the iterator.
			  else{
				  if(cur==tail){
					  // The previous node has the same RGB as the specified RGB.
					  if(pr(r)){
						  /*
						   * 3
						   * The previous node absorbs the current node.
						   */
						  size--;
						  cur.prev.runLength += cur.runLength;
						  cur.prev.next = cur.next;
						  tail = cur.prev;
					  }
					  // The previous node has a different RGB from the specified RGB.
					  else{
						  /*
						   * 25
						   * Change the RGB of the current run to r. 
						   */
						  cur.rgb = r;
						  return;
					  }
				  }
				  // The current node is between two other nodes.
				  else{
					  // The specified RGB is different from that of the previous 
					  // node and the next node.
					  if(!pr(r) && !nr(r)){
						  /*
						   * 4
						   * Change the RGB of the current run to r. 
						   */
						  cur.rgb = r;
						  return;
					  }
					  // The specified RGB is the same as that of the previous 
					  // node and the next node.
					  else{
						  if(pr(r) && nr(r)){
							  /*
							   * 5
							   * The previous node absorbs the current and 
							   * the next node.
							   */
							  size -= 2;
							  cur.prev.runLength += cur.runLength+cur.next.runLength;
							  cur.prev.next = cur.next.next;
							  if(cur.prev.next != null)
								  cur.prev.next.prev = cur.prev;
							  else
								  tail = cur.prev;
							  return;
						  }
						  // The specified RGB is exclusively the same as that of the 
						  // previous node or the next node.
						  else{
							  // The specified RGB is the same as that of the previous node
							  // only.
							  if(pr(r)){
								  /*
								   * 6
								   * The previous node absorbs the current node.
								   */
								  size--;
								  cur.prev.runLength += cur.runLength;
								  cur.prev.next = cur.next;
								  cur.next.prev = cur.prev;
								  return;
							  }
							  // The specified RGB is the same as that of the next node
							  // only.
							  else{
								  /*
								   * 7
								   * The current node absorbs the next node.
								   */
								  size--;
								  cur.runLength += cur.next.runLength;
								  cur.next = cur.next.next;
								  if(cur.next != null)
									  cur.next.prev = cur;
								  else
									  tail = cur;
								  return;
							  }
						  }
					  }
				  }
			  }
		  }
		  
	  }
	  // The run represented by the current node covers more than one pixels.
	  else{
		  // The position within the current node to be modified is not
		  // at either of the ends.
		  if(1<m && m<cur.runLength){
			  
			  // There is a single run in the run-length encoding. 
			  if(size==1){
				  /*
				   * 8
				   * The current node splits into three parts with the
				   * modification in the middle.
				   */
				  size += 2;
				  Run first, middle, last;
				  first = new Run(null, null, m-1, cur.rgb);
				  middle = new Run(first, null, 1, r);
				  last = new Run(middle, null, cur.runLength-m, cur.rgb);
				  first.next = middle;
				  middle.next = last;
				  head = first;
				  tail = last;
				  return;
			  }
			  // The are more than one runs in the run-length encoding.
			  else{
				  // Current node is the head of the iterator.
				  if(cur==head){
					  /*
					   * 9
					   * The current node splits into three parts with the
					   * modification in the middle.
					   */
					  size += 2;
					  Run first, middle, last;
					  first = new Run(cur.prev, null, m-1, cur.rgb);
					  middle = new Run(first, null, 1, r);
					  last = new Run(middle, cur.next, cur.runLength-m, cur.rgb);
					  first.next = middle;
					  middle.next = last;
					  head = first;
					  cur.next.prev = last;
					  return;
				  }
				// Current node is not the head of the iterator.
				  else{
					// Current node is the tail of the iterator.
					  if(cur==tail){
						  /*
						   * 10
						   * The current node splits into three parts with the
						   * modification in the middle.
						   */
						  size += 2;
						  Run first, middle, last;
						  first = new Run(cur.prev, null, m-1,cur.rgb);
						  middle = new Run(first, null, 1, r);
						  last = new Run(middle, null, cur.runLength-m, cur.rgb);
						  first.next = middle;
						  middle.next = last;
						  cur.prev.next = first;
						  tail = last;
						  return;
					  }
					  // The current node is in between two other nodes.
					  else{
						  /*
						   * 11
						   * The current node splits into three parts with the
						   * modification in the middle.
						   */
						  size += 2;
						  Run first, middle, last;
						  first = new Run(cur.prev, null, m-1,cur.rgb);
						  middle = new Run(first, null, 1, r);
						  last = new Run(middle, cur.next, cur.runLength-m, cur.rgb);
						  first.next = middle;
						  middle.next = last;
						  cur.prev.next = first;
						  cur.next.prev = last;
						  return;
					  }
				  }
			  }
		  }
		  // The position with the current node to be modified is at either end
		  // of the iterator.
		  else{
			  // There is only one node in the iterator.
			  if(size==1){
				  // The position is the start of the run.
				  if(m==1){
					  /*
					   * 12
					   * The first pixel of the current node is cut out
					   * to form an individual node, forming the new head.
					   */
					  size++;
					  cur.runLength--;
					  Run item = new Run(null, cur, 1, r);
					  cur.prev = item;
					  head = item;
					  return;
				  }
				// The position is the end of the run.
				  else{
					  /*
					   * 13
					   * The last pixel of the current node is cut out
					   * to form an individual node, forming the new tail.
					   */
					  size++;
					  cur.runLength--;
					  Run item = new Run(cur, null, 1, r);
					  cur.next = item;
					  tail = item;
					  return;
				  }
			  }
			  //There are multiple runs in the run-length encoding.
			  else{
				  if(cur==head){
					// The position is the start of the run.
					  if(m==1){
						  /*
						   * 14
						   * The first pixel of the current node is cut out
						   * to form an individual node, forming the new head.
						   */
						  size++;
						  cur.runLength--;
						  Run item = new Run(null, cur, 1, r);
						  cur.prev = item;
						  head = item;
						  return;
					  }
					// The position is the end of the run.
					  else{
						  // The modification has the same RGB as the next 
						  // run.
						  if(nr(r)){
							  /*
							   * 15
							   * The last pixel of the current run gets absorbed
							   * by the next node.
							   */
							  cur.runLength--;
							  cur.next.runLength++;
							  return;
						  }
						  else{
							  /*
							   * 16
							   * The last pixel of the current run is cut off 
							   * to for a new run between the current and the next 
							   * run.
							   */
							  size++;
							  cur.runLength--;
							  Run item = new Run(cur, cur.next, 1, r);
							  cur.next = item;
							  cur.next.prev = item;
							  return;
						  }
					  }
				  }
				  // The current node is not the head of the iterator.
				  else{
					  // Current node is the tail.
					  if(cur==tail){
						  // Position to be modified is at the end of the run.
						  if(m==cur.runLength){
							  /*
							   * 17
							   * The last pixel is cut off from the current run, 
							   * forming a new tail.
							   */
							  size++;
							  cur.runLength--;
							  Run item = new Run(cur, null, 1, r);
							  cur.next = item;
							  tail = item;
							  return;
						  }
						// Position to be modified is at the start of the run.
						  else{
							  // The modification is the same as the RGB of the 
							  //previous node.
							  if(pr(r)){
								  /*
								   * 18
								   * The first pixel of the current run gets 
								   * absorbed by the previous node.
								   */
								  cur.prev.runLength++;
								  cur.runLength--;
								  return;
							  }
							  else{
								  /*
								   * 19
								   * The first pixel of the current run
								   * is cut off, forming an individual 
								   * run between the previous node and the 
								   * current node.
								   */
								  size++;
								  cur.runLength--;
								  Run item = new Run(cur.prev, cur, 1, r);
								  cur.prev.next = item;
								  cur.prev = item;
								  return;
							  }
						  }
					  }
					  // The current node is between two other nodes.
					  else{
						  // The position is the start of the run.
						  if(m==1){
							  // The modification is the same as the RGB of the 
							  // previous node.
							  if(pr(r)){
								  /*
								   * 20
								   * The first pixel in the current run is
								   * absorbed by the previous node.
								   */
								  cur.runLength--;
								  cur.prev.runLength++;
								  return;
							  }
							  else{
								  /*
								   * 21
								   * The first pixel of the current node is cut 
								   * off, forming an individual node between
								   * the current and previous node.
								   */
								  size++;
								  cur.runLength--;
								  Run item = new Run(cur.prev, cur, 1, r);
								  cur.prev.next = item;
								  cur.prev = item;
								  return;
							  }
						  }
						// The position is the end of the run.
						  else{
							  // The modification is the same as the RGB of the 
							  // next node.
							  if(nr(r)){
								  /*
								   * 22
								   * The last pixel of the current run is absorbed
								   * by the next node.
								   */
								  cur.runLength--;
								  cur.next.runLength--;
								  return;
							  }
							  else{
								  /*
								   * 23
								   * The last pixel is cut off from the current node,
								   * forming an individual node between the current and 
								   * the next node.
								   */
								  size++;
								  cur.runLength--;
								  Run item = new Run(cur, cur.next, 1, r);
								  cur.next = item;
								  cur.next.prev = item;
								  return;
							  }
						  }
					  }
				  }
			  }
		  }
	  }
  }
  
  /**
   *  toString() returns a String representation of this RunIterator
   *  in the format (head, tail, current node, size).
   *
   *  @return a String representation of this RunIterator.
   */
  public String toString() {
    
	  String s =  "(" + head + "," + tail + "," + cur + "," + size +")";
    return s;
  }  
  
  /**
   *  remove() would remove from the underlying run-length encoding the run
   *  identified by this iterator, but we are NOT implementing it.
   *
   *  DO NOT CHANGE THIS METHOD.
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
