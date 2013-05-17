package fr.esipe.game.escapeir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("MenuActivity","onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Button jouerButton =  (Button) findViewById(R.id.jouer);
		Button builderButton =  (Button) findViewById(R.id.builder);
		Button quitterButton = (Button) findViewById(R.id.quitter);
		OnClickListener menuListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent =null;
				switch(arg0.getId()){
				case R.id.jouer:  intent = new Intent(MenuActivity.this, MainActivity.class);break;
				case R.id.builder:  intent = new Intent(MenuActivity.this, Builder.class);break;
				case R.id.quitter: finish();return;
				default: return;
				}

				MenuActivity.this.startActivity(intent); //intent must be declared
				//((Activity)MenuActivity.this).finish();
				//finish();
			}
		};
		jouerButton.setOnClickListener(menuListener);
		builderButton.setOnClickListener(menuListener);
		quitterButton.setOnClickListener(menuListener);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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

//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//		return true;
//	}

}
