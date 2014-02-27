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
public class Pipe {

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

	public Pipe(Point startPoint, Point endPoint, double width, double height) {
		/**
		 * Mathematical setup of the object
		 */
		this.width = width;
		this.height = height;
		Calculator calc = new Calculator();
		length = calc.distance(startPoint, endPoint);
		angle = calc.angle(startPoint, endPoint);
		this.startPoint = startPoint;
		this.endPoint = endPoint;

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
		image = Bitmap.createBitmap((int) width, (int) length,
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(image);
		c.drawLine(0, 0, 0, (float) length, linePaint);
		c.drawLine((float) width, 0, (float) width, (float) length, linePaint);
	}
}
