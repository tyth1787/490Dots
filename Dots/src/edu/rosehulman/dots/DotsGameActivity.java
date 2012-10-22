package edu.rosehulman.dots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.rosehulman.dots.model.ComputerPlayer;
import edu.rosehulman.dots.model.GameDrawer;
import edu.rosehulman.dots.model.HumanPlayer;
import edu.rosehulman.dots.model.Player;

public class DotsGameActivity extends Activity {
	int mNumPlayers;
	int mGridSize;
	GameDrawer drawer;
	
	Player[] playerList;
	Integer[] mScores;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dots_game);
		
		//get the extras
		Intent intent = getIntent();
		mNumPlayers = intent.getIntExtra(getString(R.string.key_num_players), 2);
		mGridSize = intent.getIntExtra(getString(R.string.key_grid_size), 5);
		String playerOne = intent.getStringExtra(getString(R.string.key_player_1));
		String playerTwo = intent.getStringExtra(getString(R.string.key_player_2));
		
		Log.d("DOTS", "number of players: " + mNumPlayers + 
				"\n grid size: " + mGridSize + 
				"\n player 1 name: " + playerOne + 
				"\n player 2 name: " + playerTwo);
		
		if (playerOne.equals("")) playerOne = "Player 1";
		if (playerTwo.equals("")) playerTwo = "Player 2";
		
		
		mScores = new Integer[] {0,0};
		if (mNumPlayers == 1){
			playerList = new Player[] {new HumanPlayer(playerOne), new ComputerPlayer(playerTwo)};
		}else{
			playerList = new Player[] {new HumanPlayer(playerOne), new HumanPlayer(playerTwo)};
		}
		
		//create the drawer
		drawer = new GameDrawer(getApplicationContext(), mGridSize, mGridSize);
		
		((LinearLayout) findViewById(R.id.gameArea)).addView(drawer);
		
		
		updateView();
	}
	
	private void updateView(){
		((TextView)findViewById(R.id.playerOneScore)).setText(playerList[0].getName() + ": " + mScores[0]);
		((TextView)findViewById(R.id.playerTwoScore)).setText(playerList[1].getName() + ": " + mScores[1]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}