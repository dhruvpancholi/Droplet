package com.example.droplet;

import android.graphics.Point;

public class Node {
	Point pos;
	Point next;
	Point prev;
	boolean drawn;
	public Node(Point pos) {
		this.pos = pos;
	}
	public Point getNode(){
		return this.pos;
	}
	public void addNext(Point next){
		this.next=next;
	}
	public void addPrevious(Point prev){
		this.prev=prev;
	}
	public boolean isDrawn() {
		return drawn;
	}
}
