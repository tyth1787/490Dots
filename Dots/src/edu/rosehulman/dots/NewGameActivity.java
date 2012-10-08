package edu.rosehulman.dots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewGameActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		Button newGameButton = (Button) findViewById(R.id.onePlayerEasy);
		newGameButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.onePlayerEasy:
			Intent newIntent = new Intent(this, DotsGameActivity.class);
			startActivity(newIntent);
		}

	}
}