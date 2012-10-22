package edu.rosehulman.dots.model;

import android.graphics.Color;

public class Square {
	int player;
	
	int left, top;
	
	public Square(int x, int y, int col){
		left = x;
		top = y;
		player = col;
	}	
	
	public Square(Line a, Line b, Line c, Line d, int playerNum){
		player = playerNum;
		Line[] lines = new Line[]{a,b,c,d};
		left = a.getA().ordX;
		for (Line l : lines){
			if (l.getA().ordX < left)
				left = l.getA().ordX;
			if (l.getB().ordX < left)
				left = l.getB().ordX;
		}
		top = a.getA().ordY;
		for (Line l : lines){
			if (l.getA().ordY < top)
				top = l.getA().ordY;
			if (l.getB().ordY < top)
				left = l.getB().ordY;
		}
	}

}
