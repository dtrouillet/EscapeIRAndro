package fr.esipe.game.escapeir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	private boolean menu = true;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		Log.d("MenuActivity","onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		setListener();
		File myDir = new File(getDir("level",Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "earth"); //pour créer le repertoire dans lequel on va mettre notre fichier
		Boolean success=false;
		
		if (!myDir.exists()) {
		    success = myDir.mkdir(); //On crée le répertoire (s'il n'existe pas!!)
		}
		if (success){				               
		    FileOutputStream output;
			try {
				File myFile = new File(getDir("level",Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "earth","map.xml"); //on déclare notre futur fichier
				output = new FileOutputStream(myFile,false);
				InputStream input = getResources().openRawResource(R.raw.earth);
				int i = 0;
				while ((i = input.read()) != -1) {
				   output.write(i);
				}
				
				myFile = new File(getDir("level",Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "earth","map.png"); //on déclare notre futur fichier
				output = new FileOutputStream(myFile,false);
				input = getResources().openRawResource(R.drawable.earth);
				i = 0;
				while ((i = input.read()) != -1) {
				   output.write(i);
				}
				//System.out.println(myFile.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //le true est pour écrire en fin de fichier, et non l'écraser
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setListener(){
		Button jouerButton =  (Button) findViewById(R.id.jouer);
		Button builderButton =  (Button) findViewById(R.id.builder);
		Button quitterButton = (Button) findViewById(R.id.quitter);
		Button importButton = (Button) findViewById(R.id.importLvl);
		Button exportButton = (Button) findViewById(R.id.exporter);
		
		OnClickListener menuListener = new OnClickListener() {
			ListView listeLvl;
			TextView txtLvl;

			@Override
			public void onClick(View arg0) {
				menu = false;
				Intent intent = null;
				switch(arg0.getId()){
					case R.id.importLvl: 
						Toast.makeText(context, "To import a level go on the file .scir and choose import on EscapeIR", Toast.LENGTH_LONG).show();
						break;
					
					case R.id.exporter:
						intent = new Intent(MenuActivity.this, ExportActivity.class);
						MenuActivity.this.startActivity(intent); //intent must be declared
						break;
					case R.id.jouer:
						setContentView(R.layout.menu_level);
						listeLvl = (ListView)findViewById(R.id.listeLvl);
						txtLvl = (TextView)findViewById(R.id.textLvl);
						File lvlMyDir = new File(getDir("level",Context.MODE_PRIVATE).getAbsolutePath()); //pour créer le repertoire dans lequel on va mettre notre fichier
	
						String[] listeStrings = lvlMyDir.list();
						final ArrayAdapter<String> monAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listeStrings);
						listeLvl.setAdapter(monAdapter);
						txtLvl.setText("Choose your level to play");
						txtLvl.setBackgroundColor(Color.GRAY);
						txtLvl.setTextSize(20);
						txtLvl.setPadding(15, 0, 0, 0);
						listeLvl.setOnItemClickListener(new OnItemClickListener() {
	
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
								Log.d("MenuActivity","lvlChoice = "+arg2+" lvl = "+monAdapter.getItem(arg2));
								Intent myIntent = new Intent(MenuActivity.this, MainActivity.class);
								Bundle b = new Bundle();
								b.putString("pathLevel", monAdapter.getItem(arg2)); //Your id
								myIntent.putExtras(b); //Put your id to your next Intent
								MenuActivity.this.startActivity(myIntent); //intent must be declared
							}
						});
						//intent = new Intent(MenuActivity.this, MainActivity.class);
						break;
						
					case R.id.builder:  
						intent = new Intent(MenuActivity.this, Builder.class);
						MenuActivity.this.startActivity(intent); //intent must be declared
						break;
						
					case R.id.quitter: finish();return;
						
					default: return;
				}

				
				//((Activity)MenuActivity.this).finish();
				//finish();
			}
		};
		jouerButton.setOnClickListener(menuListener);
		builderButton.setOnClickListener(menuListener);
		quitterButton.setOnClickListener(menuListener);
		importButton.setOnClickListener(menuListener);
		exportButton.setOnClickListener(menuListener);
	}

	@Override
	public void onBackPressed() {
		if(menu){
			setResult(0);
			finish();
		}else{
			setContentView(R.layout.activity_menu);
			setListener();
			menu = true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 0) {
			finish();
		}
	}

	@Override
	protected void onStop() {
		Log.d("MenuActivity","stop");
		super.onStop();
	}

//	@Override
//	public boolean onMenuItemSelected(int featureId, MenuItem item) {
//		Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//		return true;
//	}

}
