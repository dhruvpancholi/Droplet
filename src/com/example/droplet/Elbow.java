package com.example.droplet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

/**
 * 
 * @author Dhruv Pancholi
 * 
 *         Creates the object with the given start and the end point, end point
 *         can be calculated from the calculator object given start point and
 *         the angle (orientation)
 * 
 */
public class Elbow {

	/**
	 * @param startPoint
	 *            Midpoint of the entrance of the pipe
	 * @param endPoint
	 *            Midpoint of the exit of the pipe
	 */
	protected Point startPoint, endPoint;

	/**
	 * @param width
	 *            Width of the channel, since it is rectangular
	 * @param height
	 *            Height of the channel, since it is rectangular
	 */
	protected double width, height;
	/**
	 * @param length
	 *            The length of the channel in terms of pixels
	 */
	protected double length;
	/**
	 * @param angle
	 *            Orientation of the object with respect to the screen
	 */
	protected double angle;
	/**
	 * @param id
	 *            ID of each and every bubble dynamically generated in the game
	 *            and is also the index of the bubble in the tracking object
	 */
	protected int id;
	/**
	 * @param image
	 *            Transparent image of the object that is created
	 */
	protected Bitmap image;
	/**
	 * @param linePaint
	 *            Paint used to draw the boundaries of the object, initiated in
	 *            the constructor
	 */
	protected Paint linePaint;
	/**
	 * @param orienatation
	 *            Defines the angle in which the given elbow is oriented
	 */
	protected double orientation;
	/**
	 * Assuming the elbow in the horizontal direction or in other words the
	 * point of the elbow is in the horizontal direction
	 * 
	 * @param horizontalWidthAdder
	 *            The addition to the midpoint of the top-most point of the
	 *            elbow
	 * @param verticalWidthAdder
	 *            The addition to the midpoint of the lower-most point of the
	 *            elbow
	 */
	protected double horizontalWidthAdder, verticalWidthAdder;
	/**
	 * Temporary variables
	 */
	Calculator calc = new Calculator();

	public Elbow(Point startPoint, Point endPoint, double width, double height,
			double angle, double orientation) {
		/**
		 * Mathematical setup of the object
		 */
		this.width = width;
		this.height = height;
		length = calc.distance(startPoint, endPoint) / Math.sin(angle);
		this.angle = angle;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.horizontalWidthAdder = width/2*Math.tan(angle/2);
		this.verticalWidthAdder = width/2*Math.tan(angle/2);

		/**
		 * Setting up the linePaint
		 */
		linePaint = new Paint();
		linePaint.setColor(Color.BLACK);
		linePaint.setAntiAlias(true);
		linePaint.setStyle(Style.FILL_AND_STROKE);
		linePaint.setStrokeWidth(1);

		/**
		 * Necessary methods for the setup of the object
		 */
		createImage();
	}

	/**
	 * @return Image of the object that is created
	 */
	private void createImage() {
		image = Bitmap.createBitmap((int) (calc.distance(startPoint, endPoint)
				/ (2 * Math.tan(angle/2)) + horizontalWidthAdder),
				(int) (calc.distance(startPoint, endPoint) + 2 * verticalWidthAdder),
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(image);
		/**
		 * Drawing the outer shell of the elbow
		 */
		c.drawLine(0, 0, image.getWidth(), image.getHeight()/2, linePaint);
		c.drawLine(image.getWidth(), image.getHeight()/2, 0, image.getHeight(), linePaint);
		/**
		 * Drawing the inner shell of the elbow
		 */
		c.drawLine(0, (float) (2*verticalWidthAdder), (int)(image.getWidth()-2*horizontalWidthAdder), image.getHeight()/2, linePaint);
		c.drawLine((int)(image.getWidth()-2*horizontalWidthAdder), image.getHeight()/2, 0, (float) (image.getHeight()-2*verticalWidthAdder), linePaint);
	}
}
