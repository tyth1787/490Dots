package edu.rosehulman.dots;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class DotsGameActivity extends FragmentActivity implements OnClickListener,
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
	
	private Line addedLine;
	private ArrayList<Square> addedSquares;
	private int lastGameState;

	boolean initialized = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dots_game);

		// listeners
		((Button) findViewById(R.id.resetButton)).setOnClickListener(this);
		((Button) findViewById(R.id.mainMenuButton)).setOnClickListener(this);
		((Button) findViewById(R.id.undoButton)).setOnClickListener(this);
		((Button) findViewById(R.id.help_button)).setOnClickListener(this);
		((Button) findViewById(R.id.undoButton)).setEnabled(false);

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
		Log.d("DOTS", "Initialized = " + initialized);
		if (!initialized) {
			gameState = PLAYER_1_TURN;
			lines = new ArrayList<Line>();

			mScores = new Integer[] { 0, 0 };
			if (mNumPlayers == 1) {
				playerList = new Player[] { new HumanPlayer(playerOne),
						new ComputerPlayer(playerTwo) };
			} else {
				playerList = new Player[] { new HumanPlayer(playerOne),
						new HumanPlayer(playerTwo) };
			}
			addedSquares = new ArrayList<Square>();

			initGame();
			initialized = true;
		}

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
		Log.d("dots",
				"Width = "
						+ ((LinearLayout) findViewById(R.id.gameArea))
								.getWidth());

		mScores = new Integer[] { 0, 0 };
	}

	private void updateView() {
		TextView p1 = ((TextView) findViewById(R.id.playerOneScore));
		TextView p2 = ((TextView) findViewById(R.id.playerTwoScore));
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		p1.setText(playerList[0]
				.getName() + ": " + mScores[0]);
		p2.setText(playerList[1]
				.getName() + ": " + mScores[1]);
		
		//set the color in the view
		int p1Color = Integer.parseInt(prefs.getString(getString(R.string.p1color), "0"));
		int p2Color = Integer.parseInt(prefs.getString(getString(R.string.p2color), "1"));
		
		p1.setBackgroundColor(getResources().getColor(drawer.COLORS[p1Color]));
		p2.setBackgroundColor(getResources().getColor(drawer.COLORS[p2Color]));
		
		//set the colors in the drawer
		drawer.setPlayerColor(0,p1Color);
		drawer.setPlayerColor(1,p2Color);
		
		
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
					addedSquares.add(s);
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
				addedSquares.add(s);
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
					addedSquares.add(s);
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
				addedSquares.add(s);
				drawer.addSquare(s);
				pointsScored++;
			}

		}
		lines.add(l);
		addedLine = l;
		drawer.addLine(l);
		((Button) findViewById(R.id.undoButton)).setEnabled(true);

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
			break;
		case R.id.help_button:
			DialogFragment df = new DialogFragment() {
				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder builder = new AlertDialog.Builder(DotsGameActivity.this);
					builder.setTitle(R.string.help)
					.setMessage(R.string.help_text)
					.setPositiveButton(android.R.string.ok, null);
					return builder.create();
				}
			};
			df.show(getSupportFragmentManager(), "");
			break;
		}
	}

	public void undoTurn() {
		
		this.gameState = lastGameState;
		
		this.lines.remove(addedLine);
		drawer.removeLine(addedLine);
		
		for (Square s : addedSquares){
			squares.remove(s);
		}
		drawer.removeSquares(addedSquares);
		addedSquares = new ArrayList<Square>();
		
		//disable the undo button
		((Button) findViewById(R.id.undoButton)).setEnabled(false);
		updateView();
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
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_settings){
			Intent intent = new Intent(this,PlayerPreferences.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onResume() {
		updateView();
		super.onResume();
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