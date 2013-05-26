package fr.esipe.game.escapeir;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.esipe.game.util.Utils;
public class ExportActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_level);
		ListView listeLvl = (ListView)findViewById(R.id.listeLvl);
		TextView txtLvl = (TextView)findViewById(R.id.textLvl);
		File lvlMyDir = new File(getDir("level",Context.MODE_PRIVATE).getAbsolutePath()); //pour créer le repertoire dans lequel on va mettre notre fichier

		String[] listeStrings = lvlMyDir.list();
		final ArrayAdapter<String> monAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listeStrings);
		listeLvl.setAdapter(monAdapter);
		txtLvl.setText("Choose your level to export it");
		txtLvl.setBackgroundColor(Color.GRAY);
		txtLvl.setTextSize(20);
		txtLvl.setPadding(15, 0, 0, 0);
		listeLvl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
				Log.d("MenuActivity","lvlChoice = "+arg2+" lvl = "+monAdapter.getItem(arg2));
				
				String level = exportLevel(getDir("level",Context.MODE_PRIVATE).getAbsolutePath()+File.separator+monAdapter.getItem(arg2),monAdapter.getItem(arg2));
				if(level == null){
					finish();
					return;
				}
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("application/octet-stream ");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				intent.putExtra(Intent.EXTRA_SUBJECT,"Share level");
				intent.putExtra(Intent.EXTRA_TEXT, "This is my level for EscapeIr");
				File levelFile = new File(level);
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(levelFile));
				startActivity(Intent.createChooser(intent, "share level"));
			}
		});
		
	}

	private String exportLevel(String value,String levelName) {
		try {
			String dest = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+levelName+".scir";
			System.out.println(dest);
			 Utils.zipFile(value, dest);
			 return dest;
		} catch (Exception e) {
			Toast.makeText(ExportActivity.this, "Error", Toast.LENGTH_SHORT).show();
			return null;
		}
	}
}