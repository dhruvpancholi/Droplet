/**
 * 
 */
package com.example.droplet;

import java.util.LinkedList;

import android.graphics.Point;

/**
 * @author Dhruv Pancholi
 * 
 *         Contains the common method, which will be used in the game
 * 
 */
public class Calculator {
	public Calculator() {

	}

	/**
	 * Used to calculate the distance between two points
	 * 
	 * @param p1
	 *            Point 1
	 * @param p2
	 *            Point 2
	 * @return Returns the distance between the two points
	 */
	public double distance(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y));
	}

	/**
	 * Used to calculate the angle of the line connecting two points
	 * 
	 * @param p1
	 *            Point 1
	 * @param p2
	 *            Point 2
	 * @return Returns the angle of the line connecting two points
	 */
	public double angle(Point p1, Point p2) {
		if (p1.x==p2.x) {
			return Math.PI/2;
		} else {
			return Math.atan((p1.y - p2.y) / (p1.x - p2.x));
		}
	}
	/**
	 * 
	 * @param p Point of which magnitude needs to be calculated
	 * @return Magnitude of the point p
	 */
	protected double magnitude(Point p){
		return Math.sqrt(p.x*p.x+p.y*p.y);
	}
	/**
	 * Resizes the flow rate and calculates the required flow rate accordingly
	 * 
	 * @return The flow rate in the circuit for the given screen size
	 */
	public double flowRate() {
		return GameView.FLOW_RATE;
	}

	public void checkSegmentChange(LinkedList<Bubble> bubbleList,
			LinkedList<Object> segmentList) {
		for (int i = 0; i < bubbleList.size(); i++) {
			continue;
		}
	}
}
