package edu.rosehulman.dots.model;

public class Line{
	
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
	
	public boolean matchesOrdinal(Line l){
		return ((l.a.ordX == a.ordX && l.b.ordY == b.ordY) || (l.b.ordX == a.ordX && l.a.ordY == b.ordY));
	}
	
	public void swapPoints(){
		Point temp = a;
		a = b;
		b = temp;
	}

	public boolean equals(Line l) {
		return ((l.getA().equals(l.getA()) && l.getB().equals(l.getB())) || (l.getA().equals(l.getB()) && l.getB().equals(l.getA())));

	}
}
