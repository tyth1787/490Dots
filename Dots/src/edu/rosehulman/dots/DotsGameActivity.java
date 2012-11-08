package edu.rosehulman.dots;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.rosehulman.dots.model.ComputerPlayer;
import edu.rosehulman.dots.model.GameDrawer;
import edu.rosehulman.dots.model.HumanPlayer;
import edu.rosehulman.dots.model.Line;
import edu.rosehulman.dots.model.Player;
import edu.rosehulman.dots.model.Point;
import edu.rosehulman.dots.model.Square;

public class DotsGameActivity extends Activity implements OnClickListener,
		OnTouchListener {
	final int PLAYER_1_TURN = 0;
	final int PLAYER_2_TURN = 1;
	final int GAME_OVER = -1;

	int mNumPlayers;
	int mGridSize;
	GameDrawer drawer;

	Player[] playerList;
	Integer[] mScores;
	int gameState;

	List<Line> lines;
	List<Square> squares;
	private List<Point> points;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dots_game);
		
		

		// listeners
		((Button) findViewById(R.id.resetButton)).setOnClickListener(this);
		((Button) findViewById(R.id.mainMenuButton)).setOnClickListener(this);
		((Button) findViewById(R.id.undoButton)).setOnClickListener(this);
		gameState = PLAYER_1_TURN;
		lines = new ArrayList<Line>();

		// get the extras
		Intent intent = getIntent();
		mNumPlayers = intent
				.getIntExtra(getString(R.string.key_num_players), 2);
		mGridSize = intent.getIntExtra(getString(R.string.key_grid_size), 5);
		String playerOne = intent
				.getStringExtra(getString(R.string.key_player_1));
		String playerTwo = intent
				.getStringExtra(getString(R.string.key_player_2));

		Log.d("DOTS", "number of players: " + mNumPlayers + "\n grid size: "
				+ mGridSize + "\n player 1 name: " + playerOne
				+ "\n player 2 name: " + playerTwo);

		if (playerOne.equals(""))
			playerOne = "Player 1";
		if (playerTwo.equals(""))
			playerTwo = "Player 2";

		mScores = new Integer[] { 0, 0 };
		if (mNumPlayers == 1) {
			playerList = new Player[] { new HumanPlayer(playerOne),
					new ComputerPlayer(playerTwo) };
		} else {
			playerList = new Player[] { new HumanPlayer(playerOne),
					new HumanPlayer(playerTwo) };
		}

		initGame();

		updateView();
	}

	private void initGame() {
		// init
		gameState = PLAYER_1_TURN;
		lines = new ArrayList<Line>();
		squares = new ArrayList<Square>();
		// create the drawer
		drawer = new GameDrawer(getApplicationContext(), mGridSize, mGridSize);
		drawer.setOnTouchListener(this);
		points = drawer.getPoints();

		((LinearLayout) findViewById(R.id.gameArea)).addView(drawer);
		Log.d("dots", "Width = " + ((LinearLayout) findViewById(R.id.gameArea)).getWidth());

		mScores = new Integer[] { 0, 0 };
	}

	private void updateView() {
		((TextView) findViewById(R.id.playerOneScore)).setText(playerList[0]
				.getName() + ": " + mScores[0]);
		((TextView) findViewById(R.id.playerTwoScore)).setText(playerList[1]
				.getName() + ": " + mScores[1]);
		((TextView) findViewById(R.id.currentPlayersTurnText))
				.setText(playerList[gameState].getName() + "'s Turn");
		drawer.invalidate();
	}

	private void addLine(Line l) {
		if (inLines(l.getA().ordX, l.getA().ordY, l.getB().ordX, l.getB().ordY) != null) {
			Log.d("DOTS",
					"Line already exists at (" + l.getA().ordX + ", "
							+ l.getB().ordY + ") to (" + l.getB().ordX + ", "
							+ l.getB().ordY + ")");
			return;
		}
		if (l.getA().ordX > l.getB().ordX || l.getA().ordY > l.getB().ordY)
			l.swapPoints();
		// get the ordinal location of the points on the line
		int x1 = l.getA().ordX;
		int y1 = l.getA().ordY;
		int x2 = l.getB().ordX;
		int y2 = l.getB().ordY;

		int pointsScored = 0;

		int xSpace = drawer.getXSpace();
		int ySpace = drawer.getYSpace();

		if (y1 == y2) { // horizontal line
			// top square
			if (y1 > 0) {

				Line a = inLines(x1, y1, x1, y1 - ySpace);
				Line b = inLines(x1, y1 - ySpace, x2, y2 - ySpace);
				Line c = inLines(x2, y2, x2, y2 - ySpace);

				if (a != null && b != null && c != null) {

					Square s = new Square(a, b, c, l, gameState);
					squares.add(s);
					drawer.addSquare(s);
					pointsScored++;
				}
			}
			// bottom square
			Line a = inLines(x1, y1, x1, y1 + ySpace);
			Line b = inLines(x1, y1 + ySpace, x2, y1 + ySpace);
			Line c = inLines(x2, y2, x2, y2 + ySpace);

			if (a != null && b != null && c != null) {
				Square s = new Square(a, b, c, l, gameState);
				squares.add(s);
				drawer.addSquare(s);
				pointsScored++;
			}

		} else { // vertical line

			// left square
			if (x1 > 0) {
				Line a = inLines(x1, y1, x1 - xSpace, y1);
				Line b = inLines(x1 - xSpace, y1, x2 - xSpace, y2);
				Line c = inLines(x2, y2, x2 - xSpace, y2);

				if (a != null && b != null && c != null) {
					Square s = new Square(a, b, c, l, gameState);
					squares.add(s);
					drawer.addSquare(s);
					pointsScored++;
				}
			}
			// right square
			Line a = inLines(x1, y1, x1 + xSpace, y1);
			Line b = inLines(x1 + xSpace, y1, x2 + xSpace, y2);
			Line c = inLines(x2, y2, x2 + xSpace, y2);
			if (a != null && b != null && c != null) {
				Square s = new Square(a, b, c, l, gameState);
				squares.add(s);
				drawer.addSquare(s);
				pointsScored++;
			}

		}
		lines.add(l);
		drawer.addLine(l);

		// add score
		mScores[gameState] += pointsScored;

		// progress player
		if (pointsScored == 0) {
			gameState++;
			if (gameState == mNumPlayers)
				gameState = 0;
		}

		updateView();
		checkGameOver();

	}

	private void checkGameOver() {
		// check for game over
		if (squares.size() == (mGridSize - 1) * (mGridSize - 1)) {
			gameState = GAME_OVER;
			// TODO: disable touching

			// set player win text
			String newText = "";
			int highestScoreIndex = getHighestScoreIndex();
			if (highestScoreIndex != -1) {
				newText = playerList[highestScoreIndex].getName() + " wins!";
			} else {
				newText = "Tie Game!";
			}
			((TextView) findViewById(R.id.currentPlayersTurnText))
					.setText(newText);
		}

	}

	private int getHighestScoreIndex() {
		int index = 0;
		int currentHighest = mScores[0];
		for (int i = 1; i < mScores.length; i++) {
			// need to check for all of them but since only two players at the
			// moment...
			if (mScores[i] == currentHighest) {
				return -1;
			}
			if (mScores[i] > currentHighest) {
				currentHighest = mScores[i];
				index = i;
			}
		}
		return index;
	}

	private Line inLines(int x, int y, int i, int j) {
		for (Line line : lines) {
			if (line.equals(new Line(new Point(x, y), new Point(i, j))))
				return line;
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resetButton:
			initGame();
			updateView();
			break;
		case R.id.mainMenuButton:
			Intent result = new Intent();
			setResult(Activity.RESULT_OK, result);
			finish();
			break;
		case R.id.undoButton:
			undoTurn();
		}
	}

	public void undoTurn() {

	}

	public boolean onTouch(View v, MotionEvent event) {
		// binary search refactor?
		Point clicked = new Point((int) event.getX(), (int) event.getY());
		findClosestPoints(clicked);

		for (Line l : lines)
			Log.d("DOTS",
					"Lines array:: (" + l.getA().ordX + ", " + l.getA().ordY
							+ ") to (" + l.getB().ordX + ", " + l.getB().ordY
							+ ")");

		return false;
	}

	public void findClosestPoints(Point clicked) {
		double shortestDistance = Double.MAX_VALUE;
		Point closestPoint = new Point(0, 0);
		double secondShortestDistance = Double.MAX_VALUE;
		Point secondClosestPoint = new Point(0, 0);
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			double distance = point.getDistanceFrom(clicked);
			if (distance < shortestDistance) {
				secondShortestDistance = shortestDistance;
				secondClosestPoint = closestPoint;
				shortestDistance = distance;
				closestPoint = point;
			} else if (distance < secondShortestDistance) {
				secondShortestDistance = distance;
				secondClosestPoint = point;
			}
		}
		Log.d("dots", "clicked point = " + clicked.ordX + "," + clicked.ordY);
		Log.d("dots", "closest point = " + closestPoint.ordX + ","
				+ closestPoint.ordY);
		Log.d("dots", "second closest point = " + secondClosestPoint.ordX + ","
				+ secondClosestPoint.ordY);
		addLine(new Line(closestPoint, secondClosestPoint));
	}

}