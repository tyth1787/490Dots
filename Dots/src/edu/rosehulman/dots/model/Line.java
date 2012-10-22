package edu.rosehulman.dots.model;

public class Line {
	
	Point a;
	Point b;
	
	public Line (Point x, Point y){
		a = x;
		b = y;
	}
	
	public Point getA(){
		return a;
	}
	public Point getB(){
		return b;
	}
}
