package edu.rosehulman.dots.model;

public class Point {
	int x;
	int y;
	public int ordX;
	public int ordY;
	
	public Point(int xpos, int ypos, int ordinalX, int ordinalY){

		x = xpos;
		y = ypos;
		ordX = ordinalX;
		ordY = ordinalY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getDistanceFrom(Point p) {
		return Math.sqrt((ordX - p.ordX) * (ordX - p.ordX) + (ordY - p.ordY)
				* (ordY - p.ordY));
	}
}
