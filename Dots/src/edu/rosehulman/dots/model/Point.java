package edu.rosehulman.dots.model;

public class Point{

	public int ordX;
	public int ordY;
	
	public Point(int pixelX, int pixelY){

		ordX = pixelX;
		ordY = pixelY;
	}

	public double getDistanceFrom(Point p) {
		return Math.sqrt((ordX - p.ordX) * (ordX - p.ordX) + (ordY - p.ordY)
				* (ordY - p.ordY));
	}

	public boolean equals(Point another) {
		return (ordX == another.ordX && ordY == another.ordY);
	}
}
