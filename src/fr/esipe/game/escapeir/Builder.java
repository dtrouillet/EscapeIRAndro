package fr.esipe.game.escapeir;

import android.app.Activity;
import android.os.Bundle;

public class Builder extends Activity {
 @Override
protected void onCreate(Bundle savedInstanceState) {

	 super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_builder);
}
	@Override
		public void onBackPressed() {
			finish();
		}
}
