package fr.esipe.game.escapeir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Button jouerButton =  (Button) findViewById(R.id.jouer);
		jouerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					 Intent intent = new Intent(MenuActivity.this, MainActivity.class);
					 MenuActivity.this.startActivity(intent); //intent must be declared
		       	    ((Activity)MenuActivity.this).finish();
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		setResult(0);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode == 0) {
	        finish();
	    }
	}
	
	@Override
	protected void onStop() {
		System.out.println("stopMe");
		//finish();
		super.onStop();
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
		return true;
	}
	
}
