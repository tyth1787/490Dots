package edu.rosehulman.dots.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public abstract class Player {
	private String name;
	private int score;
	private Color color;

	public Player(String playerName) {
		name = playerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addToScore() {
		score++;
	}

	public List<Line> move() {

		return new ArrayList<Line>();
	}

}
