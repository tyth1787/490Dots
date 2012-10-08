package edu.rosehulman.dots.model;

public class GameController {
	private enum GAMESTATES{
		P1TURN,
		P2TURN,
		GAMEOVER
	}
	
	GAMESTATES gameState;
	
	//width and height are in dots, not pixels
	int width;
	int height;
	
	GameDrawer drawer;
	
	public GameController(int w, int h){
		gameState = GAMESTATES.P1TURN;
		width = w;
		height = h;
		drawer = new GameDrawer(w, h);
	}

}
