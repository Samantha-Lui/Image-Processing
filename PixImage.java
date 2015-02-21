/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  
 */

public class PixImage {

	private int width, height;
	// The array of RGB values.
	private RGB [][] rgbs;


  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
	  this.width = width;
	  this.height = height;
	  rgbs = new RGB[width][height];
	  for(int i=0; i<width; i++)
		  for(int j=0; j<height; j++)
			  rgbs[i][j] = new RGB();
  }
 
  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
    return width;
  }

  /**
   * getHeight() returns the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
    return height;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x, int y) {
	  return rgbs[x][y].getRGB()[0];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x, int y) {
	  return rgbs[x][y].getRGB()[1];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x, int y) {
	  return rgbs[x][y].getRGB()[2];
  }
  
  /**
   * getRGB() returns the RGB object of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the RGB object of the pixel at coordinate (x, y).
   */
  public RGB getRgbAt(int x, int y) {
	  return rgbs[x][y];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
	  if ((red<0 || red>255) || (green<0 || green>255) || (blue<0 || blue>255))
		  return;  
	  rgbs[x][y].setRGB(red, green, blue);
  }

  /**
   * toString() returns a String representation of this PixImage
   * consisting of the width, height, and the RGB values at the 
   * four corners and the center of this PixImage.
   *
   * @return a String representation of this PixImage consisting of
   * the width, height, and the RGB values at the four corners and 
   * the center of this PixImage.
   */
  public String toString() {
	  String w = "Width: " + width + "\n";
	  String h = "Height: " + height + "\n";
	  String nw = "NW: " + getRed(0,0) + "_" +  getGreen(0,0) + "_" + getBlue(0,0) + "\n";
	  String ne = "NE: " + getRed(width-1,0) + "_" +  getGreen(width-1,0) + 
			  				"_" + getBlue(width-1,0) + "\n";
	  String se = "SE: " + getRed(width-1,height-1) + "_" +  getGreen(width-1,height-1) + 
			  				"_" + getBlue(width-1,height-1) + "\n";
	  String sw = "SW: " + getRed(0,height-1) + "_" +  getGreen(0,height-1) + 
			  				"_" + getBlue(0,height-1) + "\n";
	  String center = "Center: " + getRed(width/2,height/2) + "_" +  getGreen(width/2,height/2) + 
			  						"_" + getBlue(width/2,height/2) + "\n";
    return w + h + nw + ne + se + sw + center;
  }

  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  public PixImage boxBlur(int numIterations) {

	  if(numIterations>0){
		  
		  // The PixImage resulted from a pass of blurring.
		  PixImage result = new PixImage(width, height);
		  // The version of PixImage prior to the most recent 
		  // result.
		  PixImage previous = new PixImage(width, height);
		  for(int x=0; x<width; x++)
			  for(int y=0; y<height; y++){
				  previous.setPixel(x, y, rgbs[x][y].getRGB()[0], 
							rgbs[x][y].getRGB()[1], 
						  	rgbs[x][y].getRGB()[2]);
			  }

		  for(int n=0; n<numIterations; n++){

			// Interior pixels
			  for(int x=1; x<width-1; x++)
				  for(int y=1; y<height-1; y++){
					  RGB init = new RGB((short)0, (short)0, (short)0);
					  for(int i=-1; i<=1; i++)
						  for(int j=-1; j<=1; j++){
							  init = RGB.add(init, previous.getRgbAt(x+i,y+j));
						  }
					  init = RGB.divide(init, 9);
					  result.setPixel(x, y, init.getRGB()[0],
							  	init.getRGB()[1],
							  	init.getRGB()[2]);
				  }
			  
			// Horizontal edges
			  for(int x=1; x<width-1; x++){
				  
				  // North edge
				  RGB init = new RGB((short)0, (short)0, (short)0);
				  for(int i=-1; i<=1; i++)
					  for(int j=0; j<=1; j++){
						  init = RGB.add(init, previous.getRgbAt(x+i,0+j));
					  }
				  init = RGB.divide(init, 6);
				  result.setPixel(x, 0, init.getRGB()[0],
						  	init.getRGB()[1],
						  	init.getRGB()[2]);
				  
				// South edge
				  init = new RGB((short)0, (short)0, (short)0);
				  for(int i=-1; i<=1; i++)
					  for(int j=0; j<=1; j++){
						  init = RGB.add(init, previous.getRgbAt(x+i,height-1-j));
					  }
				  init = RGB.divide(init, 6);
				  result.setPixel(x, height-1, init.getRGB()[0],
						  	       init.getRGB()[1],
						  	       init.getRGB()[2]);
			  }
			  
			// Vertical edges
			  for(int y=1; y<height-1; y++){
				  
				  // West edge
				  RGB init = new RGB((short)0, (short)0, (short)0);
				  for(int i=0; i<=1; i++)
					  for(int j=-1; j<=1; j++){
						  init = RGB.add(init, previous.getRgbAt(0+i,y+j));
					  }
				  init = RGB.divide(init, 6);
				  result.setPixel(0, y, init.getRGB()[0],
						  	init.getRGB()[1],
						  	init.getRGB()[2]);
				  
				// East edge
				  init = new RGB((short)0, (short)0, (short)0);
				  for(int i=0; i<=1; i++)
					  for(int j=-1; j<=1; j++){
						  init = RGB.add(init, previous.getRgbAt(width-1-i,y+j));
					  }
				  init = RGB.divide(init, 6);
				  result.setPixel(width-1, y, init.getRGB()[0],
						  	      init.getRGB()[1],
						  	      init.getRGB()[2]);
			  }
			  
			// Corners
			  RGB nw = new RGB((short)0, (short)0, (short)0); // NW corner
			  RGB ne = new RGB((short)0, (short)0, (short)0); // NE corner
			  RGB se = new RGB((short)0, (short)0, (short)0); // SE corner
			  RGB sw = new RGB((short)0, (short)0, (short)0); // SW corner
			  for(int i=0; i<=1; i++)
				  for(int j=0; j<=1; j++){
					  nw = RGB.add(nw, previous.getRgbAt(i,j));
					  ne = RGB.add(ne, previous.getRgbAt(width-1-i,j));
					  se = RGB.add(se, previous.getRgbAt(width-1-i,height-1-j));
					  sw = RGB.add(sw, previous.getRgbAt(i,height-1-j));
				  }
			  nw = RGB.divide(nw, 4);
			  ne = RGB.divide(ne, 4);
			  se = RGB.divide(se, 4);
			  sw = RGB.divide(sw, 4);
			  result.setPixel(0, 0, nw.getRGB()[0], nw.getRGB()[1], nw.getRGB()[2]);
			  result.setPixel(width-1, 0, ne.getRGB()[0], ne.getRGB()[1], ne.getRGB()[2]);
			  result.setPixel(width-1,height-1, se.getRGB()[0], se.getRGB()[1], se.getRGB()[2]);
			  result.setPixel(0, height-1, sw.getRGB()[0], sw.getRGB()[1], sw.getRGB()[2]);
			  
			// Update the previous version of the PixImage.
			  for(int x=0; x<width; x++)
				  for(int y=0; y<height; y++){
					  previous.setPixel(x, y, result.getRed(x,y), 
							  	  result.getGreen(x,y),  
							  	  result.getBlue(x,y));
				  }
		  } 
		return result;
	  }
	  
	  return this;
  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Ensure the returned intensity is in the range 0...255, regardless of
    // the input value.
    if (intensity < 0) {
      intensity = 0;
    } else if (intensity > 255) {
      intensity = 255;
    }
    return intensity;
  }

  /**
   *
   * The Coordinates class is a container class holding
   * two integer values x and y as the x- and y-coordinates
   */
  private class Coordinates{
	  
	  int x, y;
	  
	  /**
	   * Coordinates() (with two parameters) constructs the coordinate
	   * representation of a specified point.
	   * 
	   * @param x The x-coordinate of the point.
	   * @param y The y-coordinate of the point.
	   */
	  public Coordinates(int x, int y){
		  this.x = x;
		  this.y = y;
	  }
	  
	  public String toString(){
		  return("(" + x + "," + y + ")");
	  }
  }
  
  /**
   *  getNeighbors() returns the neighbors of the pixel including itself
   *  for gradient calculations. If the pixel is on a boundary, getNeighbors()
   *  creates the virtual neighbors by reflecting the actual neighbors of the 
   *  pixel across the boundary. For example, the pixel (-1,2) is treated as 
   *  if it had the same RGB intensity as (0,2) and (1, height) is treated as 
   *  if it had the same RGB intensity as (1, height-1).
   * 
   *  @param x the x-coordinate of the pixel.
   *  @param y the y-coordinate of the pixel.
   *  @return a 3-by-3 array of the neighbors of the pixel including itself in 
   *  the center.
   */
  private Coordinates[][] getNeighbors(int x, int y){
	  
	  Coordinates[][] neighbors = new Coordinates[3][3];
	  for(int j=0; j<3; j++){
		  for(int i=0; i<3; i++){
			  
			  int a = x+i-1;
			  int b = y+j-1;
			  
			// Reflects across a boundary 
			  if(a==-1)
				  a = 0;
			  if(a==width)
				  a = width-1;
			  if(b==-1)
				  b = 0;
			  if(b==height)
				  b = height-1;
			  
			  neighbors[i][j] = new Coordinates(a,b);
		  }
	  }

	  return(neighbors);
  }

  /**
   * gx() returns the intensity gradient in the x-direction
   * at (x,y).
   *  
   * @param intensity an array of intensities of the pixel (x,y)
   * and its surrounding neighbors. 
   * @return the intensity gradient in the x-direction at (x,y).
   */
  private int gx(short [][] intensity){
	  
	int gx = 0;
	int [][] sobelX = {{1,2,1},{0,0,0},{-1,-2,-1}};
	for(int j=0; j<3; j++){
		for(int i=0; i<3; i++){
			gx += sobelX[i][j]*intensity[i][j];
		}
	}
	return gx;
  }
 
  /**
   * gy() returns the intensity gradient in the y-direction
   * at (x,y).
   *  
   * @param intensity an array of intensities of the pixel (x,y)
   * and its surrounding neighbors. 
   * @return the intensity gradient in the y-direction at (x,y).
   */
  private int gy(short [][] intensity){
	
	int gy = 0;
	int [][] sobelY = {{1,0,-1},{2,0,-2},{1,0,-1}};
	for(int j=0; j<3; j++){
		for(int i=0; i<3; i++){
			gy += sobelY[i][j]*intensity[i][j];
		}
	}
	return gy;
  }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage sobelEdges() {

	  PixImage result = new PixImage(width, height);  
	  
	  for(int x=0; x<width; x++){
		  for(int y=0; y<height; y++){

			  // Finds the nine neighbors of the pixels at (x,y).
			  Coordinates [][] neighbors = getNeighbors(x,y);
			  
			  short [][] redIntensity = new short[3][3];
			  short [][] greenIntensity = new short[3][3];
			  short [][] blueIntensity = new short[3][3];
			  // Finds the red, green and blue intensity of each neighbor.
			  for(int j=0; j<3; j++){
				  for(int i=0; i<3; i++){
					  int a = neighbors[i][j].x;
					  int b = neighbors[i][j].y;
					  redIntensity[i][j] = getRed(a, b);
					  greenIntensity[i][j] = getGreen(a, b);
					  blueIntensity[i][j] = getBlue(a, b);
				  }
			  }
			  
			  // Calculates gx for the red, green and blue intensity at (x,y).
			  int gxRed = gx(redIntensity);
			  int gxGreen = gx(greenIntensity);
			  int gxBlue = gx(blueIntensity);

			  // Calculates gy for the red, green and blue intensity at (x,y).
			  int gyRed = gy(redIntensity);
			  int gyGreen = gy(greenIntensity);
			  int gyBlue = gy(blueIntensity);
			  
			  // Calculates the energy at (x,y).
			  long energy = gxRed*gxRed + gyRed*gyRed + 
					  gxGreen*gxGreen + gyGreen*gyGreen + 
					    gxBlue*gxBlue + gyBlue*gyBlue;

			  
			  // Calculates the gray scale of the energy.
			  short grayScale = mag2gray(energy);
			  
			  // Set the gray-scaled energy value as the pixel of
			  // result at (x,y).
			  result.setPixel(x, y, grayScale, grayScale, grayScale);
		  }
	  }	  
	  return result;
  }

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
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * main() runs a series of tests to ensure that the convolutions (box blur
   * and Sobel) are correct.
   */
  public static void main(String[] args) {

    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });

    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");

    
    System.out.println("Test Blurring 1");
    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));

    System.out.println("Test Blurring 2");
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));

    System.out.println("Test Blurring 3");
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));



    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());
    
    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 },
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
           
  }
}

