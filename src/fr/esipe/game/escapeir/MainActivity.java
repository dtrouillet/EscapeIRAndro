package fr.esipe.game.escapeir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MainActivity", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	protected void onStop() {
		System.out.println("stop");
		super.onStop();
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode == 0) {
	        finish();
	    }
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return true;
	}
	
}
