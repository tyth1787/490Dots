package edu.rosehulman.dots.model;

public class Point {
	int x;
	int y;
	public int ordX;
	public int ordY;
	
	public Point(int xpos, int ypos, int pixelX, int pixelY){
		x = xpos;
		y = ypos;
		ordX = pixelX;
		ordY = pixelY;
	}

	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}

}
