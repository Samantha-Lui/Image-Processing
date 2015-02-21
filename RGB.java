/* RGB.java */

/**
 *  The RGB class defines an object that represents the RGB values of a pixel.
 *  The class also contains two static methods for image processing: the add()
 *  method which returns the result of summing the respective RGB values of 
 *  two specified pixels and divide() which returns the result of dividing the 
 *  respective RGB values of a specified pixel by a specified integer.
 *  
 */

public class RGB {
	
	private short red, green, blue;
	
	  /**
	   *  Run() (with zero parameter) constructs an RGB object having zero
	   *  for the red, green and blue intensities; i.e., the corresponding 
	   *  pixel has a solid black color.
	   */
	public RGB(){
		red = 0;
		green = 0;
		blue = 0;
	}
	
	/**
	 *  Run() (with three parameters) constructs an RGB object having the specified
	 *  red, green and blue intensities.
	 *  
	 * @param r the specified red intensity ranging between 0 and 255.
	 * @param g the specified green intensity ranging between 0 and 255.
	 * @param b the specified blue intensity ranging between 0 and 255.
	 */
	public RGB(short r, short g, short b){
		red = r;
		green = g;
		blue = b;
	}
	
	/**
	 * setRGB() changes the red, green and blue intensities of this
	 * to the specified values.
	 * 
	 * @param r the specified red intensity ranging between 0 and 255.
	 * @param g the specified green intensity ranging between 0 and 255.
	 * @param b the specified blue intensity ranging between 0 and 255.
	 */
	public void setRGB(short r, short g, short b){
		red = r;
		green = g;
		blue = b;
	}
	
	/**
	 * getRGB() returns the RGB values (in the order red, green, blue)
	 * of this as an array.
	 * 
	 * @return An array of the red, green and blue intensities.
	 */
	public short[] getRGB(){
		short [] rgb = {red, green, blue};
		return rgb;
	}
	
	/**
	 * equals() returns true if this is identical to the 
	 * specified RGB object.
	 * 
	 * @param p the specified RGB object.
	 * @return true if the RGB object is identical to this.
	 */
	public boolean equals(RGB p){
		return (red == p.red) && 
				(green == p.green) &&
				(blue == p.blue);
	}
	
	/**
	 * toString() returns a String representation in the format 
	 * (red, green, blue) of this RGB object.
	 * 
	 * @return A String representation of this RGB object.
	 */
	public String toString(){
		return "(" + red + "," + green + "," + blue + ")";
	}
	
	/**
	 * add() adds the respective red, green and blue intensities 
	 * of the two specified RGB objects.
	 * 
	 * @param p1 The first specified RGB object.
	 * @param p2 The second specified RGB object.
	 * @return A new RGB object having the sums of the respective 
	 * RGB values.
	 */
	public static RGB add(RGB p1, RGB p2){
		short r = (short) (p1.getRGB()[0]+p2.getRGB()[0]);
		short g = (short) (p1.getRGB()[1]+p2.getRGB()[1]);
		short b = (short) (p1.getRGB()[2]+p2.getRGB()[2]);
		return new RGB(r,g,b);
	}
	
	/**
	 * divide() divides the respective red, green and blue intensities 
	 * of the specified RGB objects by the specified integer.
	 * 
	 * @param p1 The specified RGB object.
	 * @param d The specified divisor.
	 * @return A new RGB object having the division of the respective 
	 * RGB values.
	 */
	public static RGB divide(RGB p1, int d){
		short r = (short)(p1.getRGB()[0]/d);
		short g = (short)(p1.getRGB()[1]/d);
		short b = (short)(p1.getRGB()[2]/d);
		return new RGB(r,g,b);
	}
}
