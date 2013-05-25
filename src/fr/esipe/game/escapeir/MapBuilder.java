package fr.esipe.game.escapeir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import fr.esipe.game.util.MapListAdapter;
import fr.esipe.game.util.MapListView;
import fr.esipe.game.util.MapLvl;

public class MapBuilder extends Activity {
 @SuppressWarnings("deprecation")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_builder);
	List<MapLvl> listmaplvl= new ArrayList<MapLvl>();
	String path=getIntent().getExtras().get("mappath")+File.separator+"map.png";
	int time=(Integer) getIntent().getExtras().get("time");
	int nbmap=(time/3)+1;
	//Drawable image = Drawable.createFromPath(path);
	MapLvl lvl=new MapLvl(path);
	for(int i=0;i<nbmap;i++){
		listmaplvl.add(lvl);
	}
	ListView listView = ( ListView ) findViewById( R.id.listView1);
	((MapListView)listView).setTextView((TextView) findViewById(R.id.textView1));
	listView.setAdapter( new MapListAdapter(this, R.layout.map_row_item, listmaplvl ) );
	
 }
 
}
