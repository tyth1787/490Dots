package edu.rosehulman.dots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewGameActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		Button newGameButton = (Button) findViewById(R.id.onePlayerEasy);
		newGameButton.setOnClickListener(this);
		Button oneHardButton = (Button) findViewById(R.id.onePlayerHard);
		oneHardButton.setOnClickListener(this);
		Button twoButton = (Button) findViewById(R.id.twoPlayer);
		twoButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		Intent newIntent = null;

		// get the grid size
		Spinner spinner = ((Spinner) findViewById(R.id.gridSize));
		int gridSize = 0;
		switch (spinner.getSelectedItemPosition()) {
		case 0: // Large
			gridSize = 10;
			break;
		case 1: // Medium
			gridSize = 8;
			break;
		case 2: // Small
			gridSize = 5;
			break;
		}

		newIntent = new Intent(this, DotsGameActivity.class);
		newIntent.putExtra(getString(R.string.key_grid_size), gridSize);
		EditText namePlayerOne = ((EditText) findViewById(R.id.playerName1));
		EditText namePlayerTwo = ((EditText) findViewById(R.id.playerName2));

		Log.d("DOTS", "player 1: " + namePlayerOne.getText() + "\n player 2: "
				+ namePlayerTwo);

		newIntent.putExtra(getString(R.string.key_player_1), namePlayerOne
				.getText().toString());
		newIntent.putExtra(getString(R.string.key_player_2), namePlayerTwo
				.getText().toString());

		switch (v.getId()) {
		case R.id.onePlayerEasy:
			newIntent.putExtra(getString(R.string.key_num_players), 1);
			newIntent.putExtra(getString(R.string.key_difficulty), 0);
			break;
		case R.id.onePlayerHard:
			newIntent.putExtra(getString(R.string.key_num_players), 1);
			newIntent.putExtra(getString(R.string.key_difficulty), 1);
			break;
		case R.id.twoPlayer:
			newIntent.putExtra(getString(R.string.key_num_players), 2);
			break;
		default:
			return;
		}

		startActivity(newIntent);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
		}
	}
}