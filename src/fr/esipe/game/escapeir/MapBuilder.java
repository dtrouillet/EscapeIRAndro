package fr.esipe.game.escapeir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.R.color;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.esipe.game.util.MapListAdapter;
import fr.esipe.game.util.MapListView;
import fr.esipe.game.util.MapLvl;

public class MapBuilder extends Activity {
	private ImageView selectedShip=null;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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

		listView.setSelection(listView.getCount()-1);
		RelativeLayout l=(RelativeLayout) findViewById(R.id.Linear);

		OnClickListener ocl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(selectedShip==null){
					selectedShip=(ImageView) v;
				}
				else {
					selectedShip.setBackgroundColor(0);
					if(v.getId()==selectedShip.getId()){
						selectedShip=null;
						return;
					}
					selectedShip=(ImageView) v;
				}
				v.setBackgroundColor(Color.GREEN);
			}
		};


		for(int i=0;i<l.getChildCount();i++){
			if (l.getChildAt(i) instanceof ImageView) {
				ImageView iv = (ImageView) l.getChildAt(i);
				iv.setOnClickListener(ocl);

			};
		}

	}

	public ImageView getSelectedShip() {
		return selectedShip;
	}
}
