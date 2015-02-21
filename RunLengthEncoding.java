/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  
 *
 *  See the README file accompanying this project for additional details.
 */

import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

	private int width, height;
	private RunIterator runIt;
	private RGB [] rgb;
	private int [] runLengths;
	

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */

  public RunLengthEncoding(int width, int height) {
	  
	  this.width = width;
	  this.height = height;
	  
	  rgb = new RGB[1];
	  rgb[0] = new RGB((short)0, (short)0, (short)0);
	  
	  runLengths = new int[1];
	  runLengths[0] = width * height;
	  
	  // The iterator that carries the run-length encoding of 
	  // this object.
	  runIt = iterator();
  }

  /**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */

  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {

	  this.width = width;
	  this.height = height;
	  
	  rgb = new RGB[red.length];
	  for(int i=0; i<red.length; i++)
		  rgb[i] = new RGB((short)red[i], (short)green[i], (short)blue[i]);
	  
	  this.runLengths = runLengths;
	  
	  runIt = iterator();
  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    
	  RunIterator runIterator = new RunIterator();
	  
	  // Starting from the first run, append the
	  // runs to the iterator.
	  for(int i=0; i<rgb.length; i++){
		  runIterator.append(runLengths[i], rgb[i]);
	  }
	  
	  // Retire the runLength and rgb arrays for
	  // memory efficiency since now they are redundant.
	  runLengths = null;
	  rgb = null;
	  
	  return runIterator;
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  public PixImage toPixImage() {
    
	  PixImage pix = new PixImage(width, height);
	  
	  int count = 0;
	  
	  int[] first = runIt.current();
	  for(int i=0; i<first[0]; i++){
		  pix.setPixel(count%width, (int) (count/width), 
				  		(short) first[1], (short) first[2], (short)first[3]);
		  count++;
	  }
	  while(runIt.hasNext()){
		  int [] next = runIt.next();
		  for(int i=0; i<next[0]; i++){
			  pix.setPixel(count%width, (int) (count/width), 
					  		(short) next[1], (short) next[2], (short)next[3]);
			  count++;
		  }
	  }
	  
	  runIt.reset();
	  if(count != width*height)
		  System.out.println("INCORRECT IMPLEMENTATION OF toPixImage()");
	  return pix;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding
   *  in the format of (width, height, String representation of the iterator).
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return s a String representation of this RunLengthEncoding.
   */
  public String toString() { 
	  String s =  "(" + width + "," + height + "," + runIt + ")";
	  return s;
  }

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   *
   *  @param image is the PixImage to run-length encode.
   */
  public RunLengthEncoding(PixImage image) {

	  width = image.getWidth();
	  height = image.getHeight();
	  
	  // Convert the specified PixImage into an
	  // array of RGB objects.
	  RGB [] orig = new RGB[width*height];
	  int index = 0;
	  for(int y=0; y<height; y++){
		  for(int x=0; x<width; x++){
			  orig[index] = image.getRgbAt(x, y);
			  index++;
		  }
	  }  
	  
	  // Calculates the run-length of the specified
	  // PixImage.
	  int [] runs = new int[index];
	  RGB [] result = new RGB[index];
	  RGB current = orig[0];
	  int n = 0;
	  int counter = 1;
	  for(int i=1; i<orig.length; i++){
		  if(current.equals(orig[i])){
			  counter++;
		  }
		  else{
			 result[n] = current;
			 runs[n] = counter;
			 n++;
			 current = orig[i];
			 counter = 1;
		  }
	  }
	  // The last run.
	  result[n] = current;
	  runs[n] = counter;
	  n++;
	  // Run-length encode the specified PixImage.
	  runLengths = new int[n];
	  rgb = new RGB[n];
	  for(int i=0; i<n; i++){
		  runLengths[i] = runs[i];
		  rgb[i] = result[i];
	  }
	  // Store the encoding into the iterator.
	  runIt = iterator();
	  
	  // Check for correctness.
	  check(); 
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  public void check() {
    
	  // Refresh the iterator.
	  runIt.reset();
	  
	  int sum = 0; // Number of pixels covered in the run-length encoding.
	  
	  int [] current = new int[4];
	  
	  RGB cur = null;
	  if(runIt.size()>0){
		  current = runIt.current();
		  sum += current[0];
		  cur = new RGB((short) current[1], (short) current[2], (short) current[3]);
	  }  
	  while(runIt.hasNext()){
		  int [] next = runIt.next();
		  RGB nex = new RGB((short) next[1], (short) next[2], (short) next[3]);
		  sum += next[0];
		  // Check whether two consecutive runs have the same RGB intensities.
		  if(cur.equals(nex)){
			  System.out.println("Two consecutive runs have the same RGB intensities.");
		  }
		  current = runIt.current();
		  cur = new RGB((short) current[1], (short) current[2], (short) current[3]);
	  } 
	  runIt.reset();
	  
	  // Check if the sum of all run lengths equal the number of pixels in the image.
	  if(sum != width*height)
		  System.out.println("Number of pixels resulted from encoding: " + sum + 
				  				" while it should be " + width*height);
  }

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding is compressed as much as possible;
   *  there are not two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    
	  // Calculate the corresponding position of the specified coordinates
	  // when the pixels are to be laid in a single row as in the run-length
	  // encoding algorithm.
	  int position = y*width + x + 1;
	  
	  // Number of pixels covered by the runs.
	  int cover = 0;
	  
	  // The PixImage is empty.
	  if(runIt.size() < 1)
		  return;
	  
	  // Find the run in the encoding that holds the specified pixel.
	  int [] itemN = runIt.current();
	  cover += itemN[0];
	  while(cover<position && runIt.hasNext()){
		  itemN = runIt.next();
		  cover += itemN[0];
	  }
	  
	  // Set the RGB intensities of the pixel to the specified values 
	  // if they are different.
	  if(itemN[1]!=red || itemN[2]!=green || itemN[3]!=blue)
		  runIt.modifyCurrentNode(itemN[0]-(cover-position), new RGB(red, green, blue));
	  
	  // Check for correctness.
	  check();
  }


  /**
   * TEST CODE:  
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,
                 (short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {

    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });
    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    rle1.check();
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");
    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");
    
    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });
    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);  
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");
    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");
    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");

    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });
    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);    
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");
    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");
    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");
    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");
    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");

    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });   
    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +  
    			"on a 3x2 image.  Input image:");
    System.out.print(image4);    
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);    
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");
    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");
    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");
    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");
    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}

