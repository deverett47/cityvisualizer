package gfx;

/**
 * Welcome to the beginning of your very own graphics package!
 * This graphics package will be used in most of your assignments from 
 * now on.
 *
 * This should look A LOT like the code you've seen in lecture (HINT HINT).
 * At first glance this class looks really dense, but don't worry most of
 * the methods you have to fill in wont be very long.
 *
 * REMEMBER most of the code you will write here will be code you've seen 
 * before (WINK WINK).
 *
 * All of the accessor methods return a dummy value so that this file can 
 * be compiled from the start. Make sure to change these to make them
 * actually do something!
 *
 * Feel free to add other functionality, but keep in mind this is a shape 
 * and shouldn't have capabilities that are more specific to say 
 * bees or something.
 *
 * Some bells and whistles you might want to add:
 *  - set transparency (look at java.awt.Color in the Javadocs)
 *  - anti aliasing (getting rid of jagged edges)
 *
 *
 *
 *
 * I tried to turn on anti aliasing using the RenderingHints information I found in the Javadocs for Graphics2D.
 * I'm not sure if I actually notice a difference in the edge quality of the shapes in my Shape Viewer
 * but I figured I would leave it in to see if you thought I did it correctly! I explained my process
 * for turning on anti aliasing in comments where necessary.
 *
 * I also made an Ellipse class and a Polygon class to use in Cartoon.
 *
 * @author deverett (Dylan Everett)
 */
public abstract class Shape {

	/** Used to store some geometric data for this shape. */
	private java.awt.geom.RectangularShape _shape;

	/** Reference to containing subclass of JPanel. */
	private javax.swing.JPanel _panel;

	/** Border and Fill Colors. */
	private java.awt.Color _borderColor, _fillColor;

	/** Rotation (in radians). */
	private double _rotationAngle;

	/** Border Width */
	private int _borderWidth;

	/** Indicates whether or not the shape should wrap. */
	private boolean _wrapping;

	/** Whether or not the shape should paint itself. */
	private boolean _isVisible;

	private java.awt.RenderingHints _hints; // creates an instance of RenderingHints, which will later be passed to the brush's addRenderingHints(...) method


	/** 
	 * Initialize all instance variables here.  You'll need to store the
	 * containing subclass of JPanel to deal with wrapping and some of the
	 * extra credit stuff.
	 */



	public Shape(javax.swing.JPanel container, 
		java.awt.geom.RectangularShape s) {
		    _shape = s;
		    _panel = container;
                    /** Stores a new RenderingHints in _hints, with the anti-aliasing keys I want to use passed in the constructor */
                    _hints = new java.awt.RenderingHints(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
 		    _shape.setFrame (50, 50, 100, 100);
		    this.setBorderColor(java.awt.Color.BLACK);
		    this.setFillColor(java.awt.Color.BLACK);
	}


	/**
	 * Should return the x location of the top left corner of 
	 * shape's bounding box.
	 */
	public double getX() {
		return _shape.getX();
	}


	/** 
	 * Should return the y location of the top left corner of 
	 * shape's bounding box. 
	 */
	public double getY() {
		return _shape.getY();
	}


	/** Should return height of shape's bounding box. */
	public double getHeight() {
		return _shape.getHeight();
	}


	/** Should return width of shape's bounding box. */
	public double getWidth() {
		return _shape.getWidth();
	}


	/** Should return the border color you are storing. */
	public java.awt.Color getBorderColor() {
		return _borderColor;
	}


	/** Should return the fill color you are storing. */
	public java.awt.Color getFillColor() {
		return _fillColor;
	}


	/** Should return the rotation you are storing. Should return the angle in degrees. */
	public double getRotation() {
		return _rotationAngle*180/Math.PI;
	}


	/** 
	 * Should return the width of the brush stroke for 
	 * the outline of your shape.
	 */
	public int getBorderWidth() {
		return _borderWidth;
	}


	/** Should return whether or not the shape is wrapping. */
	public boolean getWrapping() {
		return _wrapping;
	}


	/** Should return whether or not the shape is visible. */
	public boolean getVisible() {
		return _isVisible;
	}



	/** 
	 * Set the location of shape. Make sure to wrap if the wrap 
	 * boolean is true. Refer to the lecture slides to see
	 * how wrapping is done.
	 */
	public void setLocation(double x, double y) {
	     if (this.getWrapping()){     // if wrapping is turned on, this block of code will be excuted; if not, the else clause will be
                 double dpWidth = _panel.getWidth(); // stores the container's width
                 double dpHeight = _panel.getHeight(); // stores the container's height
                 double newX = ((x % dpWidth) + dpWidth) % dpWidth; // calculates new X position with wrapping turned on
                 double newY = ((y % dpHeight) + dpHeight) % dpHeight; // calculates new Y position with wrapping turned on
		_shape.setFrame (newX, newY, _shape.getWidth(), _shape.getHeight());  
	     }
	     else {
		_shape.setFrame (x, y, _shape.getWidth(), _shape.getHeight());
	     }
	}


	/** Set the size of shape. */
	public void setSize(double width, double height) {
		_shape.setFrame(_shape.getX(), _shape.getY(), width, height);
	}


	/** Set the border color. */
	public void setBorderColor(java.awt.Color c) {
		_borderColor = c;

	}


	/** Set the fill color. */
	public void setFillColor(java.awt.Color c) {
		_fillColor = c;
	}


	/** Set the color of the whole shape (border and fill). */
	public void setColor(java.awt.Color c) {
		_fillColor = c;
		_borderColor = c;
	}


	/**
	 * Set the rotation of the shape. Refer to the lecture to see
	 * how this should be done
	 */
	public void setRotation(double degrees) {
		    _rotationAngle = degrees*Math.PI/180;
	}

	/** Set how thick the shapes outline will be. */
	public void setBorderWidth(int width) {
		    _borderWidth = width;
	}

	/** Set whether or not the shape should wrap. */
	public void setWrapping(boolean wrap) {
		    _wrapping = wrap;
	}

	/** Set whether or not the shape should paint itself. */
	public void setVisible(boolean visible) {
		    _isVisible = visible;
	}

	/**
	 * This method is best explained in pseudocode:
	 *	If shape is visible
	 *	    rotate graphics
	 *	    set the brush stroke (width) of the graphics
	 *	    set the color of the graphics to the border color of the shape
	 *	    draw the shapestore the selected border width
	 *	    set the color of the graphics to the fill color of the shape
	 *	    fill the shape
	 *	    un-rotate the graphics
	 */
	public void paint(java.awt.Graphics2D brush) {
             if (this.getVisible()) {     // if this is true, the shape will be painted; if not, no code will be executed	
                  brush.addRenderingHints(_hints); // use the addRenderingHints method of the Graphics2D class, which should instruct my brush on how to render the shape it creates
		  brush.rotate(_rotationAngle, _shape.getCenterX(), _shape.getCenterY());
		  java.awt.BasicStroke stroke = new java.awt.BasicStroke(this.getBorderWidth()); //creates a local variable to that takes in the border width
                  brush.setStroke(stroke); //sets the border width, using stroke which had a width passed to it when it was constructed
                  brush.setColor(_borderColor);
		  brush.draw(_shape);
		  brush.setColor(_fillColor);
		  brush.fill(_shape);
		  brush.rotate(-_rotationAngle, _shape.getCenterX(), _shape.getCenterY());
	      }
	}


	/** 
	 * Should return true if the point is within the shape.  
	 * There's a special case for when the shape is rotated,
	 * which we have handled for you.		    
	 *
	 * YOU DO NOT NEED TO TOUCH THIS METHOD.
	 */
	public boolean contains(java.awt.Point p) {
		if (0 != _rotationAngle) {
			double x = _shape.getCenterX();
			double y = _shape.getCenterY();
			java.awt.geom.AffineTransform trans = java.awt.geom.AffineTransform.getRotateInstance(_rotationAngle, x, y);
			java.awt.Shape s = trans.createTransformedShape(_shape);
			return s.contains(p);
		}
		return _shape.contains(p);
	}

	/** 
	 * Returns true if the shape intersects (i.e. overlaps) the imaginary rectangle 
	 * whose upper left corner is at (x,y) and has the specified width
	 * and height.
	 * 
	 * There's a special case for when the shape is rotated,
	 * which we have handled for you.
	 *
	 * YOU DO NOT NEED TO TOUCH THIS METHOD.
	 */
	public boolean intersects(double x, double y, double width, double height) {
		// if the shape has rotation
		if (0 != _rotationAngle) {
			// create the rotation transformation for the shape's rotation angle
			java.awt.geom.AffineTransform trans = java.awt.geom.AffineTransform.getRotateInstance(_rotationAngle, _shape.getCenterX(), _shape.getCenterY());
			// produce the rotated shape by applying the transformation to our underlying, unrotated shape
			java.awt.Shape s = trans.createTransformedShape(_shape);
			// check the rotated shape for intersection
			return s.intersects(x, y, width, height);
		}
		
		return _shape.intersects(x, y, width, height);
	}

	/**
	 * This should be called when the shape is clicked.
	 * You'll want to overwrite this in subclasses to do something useful.
	 * Should stay empty in this class 
	 */

	
	public void react() {}
}
