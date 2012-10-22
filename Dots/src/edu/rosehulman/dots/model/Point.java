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

	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}

}
