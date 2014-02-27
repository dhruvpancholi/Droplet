package com.example.droplet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 
 * @author Dhruv Pancholi
 * 
 *         The bubble class which handles various properties of the object and
 *         allows the usage of
 * 
 * 
 */

public class Bubble {

	/**
	 * @param diameter
	 *            Diameter of the bubble, generally lesser than the width of the
	 *            channel
	 */
	protected double diameter;
	/**
	 * @param position
	 *            Current position of the bubble
	 * @param velocity
	 *            Current velocity of the bubble, which remains fixed and only
	 *            changes with the change in flow rate
	 * @param positionTemp
	 *            Psuedo position, to create the effect of the loop
	 */
	protected Point position, velocity, positionTemp;
	/**
	 * @param SegmentId
	 *            The segment in which the bubble is currently located in
	 * @param SegmentType
	 *            The type of the segment to give the x and y velocity of the
	 *            bubble
	 */
	protected int SegmentId, SegmentType;
	/**
	 * @param BallGraphic
	 *            The bitmap which is stored in the bubble object
	 */
	protected Bitmap ballGraphic;
	/**
	 * @param bubbleType
	 *            Indicates the type of the bubble. There are generally two
	 *            types of bubbles 1. Cell Bubble 2. Drug Bubble
	 */
	protected int bubbleType;
	/**
	 * These two variables are used for the algorithm for the change in segment,
	 * every time in the game loop these is calculated
	 * 
	 * @param segmentLength
	 *            The length of the segment in which the bubble currently is
	 * @param distanceStart
	 *            Distance of the bubble from the start point of the segment
	 */
	protected double segmentLength, distanceStart;
	/**
	 * @param id
	 *            The id of the bubble or is equivalent to the index of the
	 *            bubble in the bubble list
	 */
	protected int id;
	/**
	 * @param segmentId
	 *            ID of the segment in which the bubble currently is. It also
	 *            indicates the index of the segment in the segment array or
	 *            list
	 */
	protected int segmetnId;
	/**
	 * @param velocityX
	 *            Velocity of the bubble in the x direction, actually used in
	 *            the calculation
	 * @param velocityY
	 *            Velocity of the bubble in the y direction, actually used in
	 *            the calculation
	 */
	protected double velocityX, velocityY;
	/**
	 * @param positionX
	 *            Position of the bubble in the x direction, actually used in
	 *            the calculation
	 * @param positionY
	 *            Position of the bubble in the y direction, actually used in
	 *            the calculation
	 */
	protected double positionX, positionY;

	/**
	 * Constructor for the bubble object which contains different properties
	 * 
	 * @param dia
	 *            Diameter of the bubble
	 * @param myContext
	 *            Context of the Activity, here the main game activity
	 * @param type
	 *            Type of the bubble which will be created
	 */
	Calculator calc;
	/**
	 * @param angle
	 *            The direction in which the bubble is currently moving
	 */
	double angle;

	public Bubble(int dia, Context myContext, int type) {
		diameter = dia;
		calc = new Calculator();
		angle = Math.PI/4;
		ballGraphic = Bitmap.createBitmap(dia, dia, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(ballGraphic);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawCircle(dia / 2, dia / 2, dia / 2, paint);
		bubbleType = type;
		positionX = GameThread.screenW / 2;
		positionY = 0;
		velocity = new Point(2, 2);
		position = new Point(GameThread.screenW / 2, 0);
		setVelocity();
	}
	int i=0;
	protected void move() {
		i++;
		if (i==60) {
			if (angle>Math.PI/2) {
				angle-=Math.PI/2;
			}
			else {
				angle+=Math.PI/2;
			}
			i=0;
		}
		//angle+=Math.PI/60;
		positionX += velocityX;
		positionY += velocityY;
		setVelocity();
	}

	protected void setVelocity() {
		double velo = calc.distance(velocity, new Point(0, 0));
		velocityX = velo * Math.cos(angle);
		velocityY = velo * Math.sin(angle);
	}
}
