package fr.esipe.game.escapeir;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import fr.esipe.game.util.Utils;
public class ImportActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Level Name");
		alert.setMessage("Enter your level name");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				importLevel(value);
				Intent intent = new Intent(ImportActivity.this, MenuActivity.class);
				startActivity(intent);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});

		alert.show();
		// test du type de contenu que l'on pourrait recevoir (en fonction de l'URI des données envoyées)
		
	}

	private void importLevel(String value) {
		String scheme = getIntent().getScheme();
		if (("content".equals(scheme)) || ("file".equals(scheme))) {
			try {
				// Tentative d'ouverture du flux de données
				InputStream attachment = getContentResolver().openInputStream(getIntent().getData());

				
				System.out.println(value);
				//String nameLevel = fileName.) ;
				System.out.println(getDir("level",Context.MODE_PRIVATE).getAbsolutePath()+File.separator+value);
				if(Utils.extractAll(attachment, getDir("level",Context.MODE_PRIVATE).getAbsolutePath()+File.separator+value))			
					Toast.makeText(this, "level import", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(this, "level already exist", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(this, "error while opening the file.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}