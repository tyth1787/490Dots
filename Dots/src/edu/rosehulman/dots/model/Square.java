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

}
