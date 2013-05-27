package fr.esipe.game.escapeir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.esipe.game.util.MapListAdapter;
import fr.esipe.game.util.MapListView;
import fr.esipe.game.util.MapLvl;

public class MapBuilder extends Activity {
	private ImageView selectedShip=null;
	private MapListAdapter mla;
	private View menuSelected=null;
	private OnClickListener btlmenu = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			XmlSerializer xml = Xml.newSerializer();
			try {
				xml.setOutput(new FileOutputStream((String)getIntent().getExtras().get("mappath")+File.separator+"map.xml"), "UTF-8");
				xml.startDocument("UTF-8", true);
				xml.startTag("", "level");
				xml.startTag("", "time").text(getIntent().getExtras().get("time").toString()).endTag("", "time");
				xml.startTag("", "enemies");
				mla.setxml(xml);
				
				
				xml.endTag("", "enemies");
				xml.endTag("", "level");
				xml.endDocument();
				xml.flush();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_builder);
		((Button)findViewById(R.id.button1)).setOnClickListener(btlmenu);
		List<MapLvl> listmaplvl= new ArrayList<MapLvl>();
		String path=getIntent().getExtras().get("mappath")+File.separator+"map.png";
		int time=(Integer) getIntent().getExtras().get("time");
		int nbmap=(time/3)+1;
		//Drawable image = Drawable.createFromPath(path);
		MapLvl lvl=new MapLvl(path);
		for(int i=0;i<nbmap;i++){
			//MapLvl lvl=new MapLvl(path);
			listmaplvl.add(lvl);
		}
		final ListView listView = ( ListView ) findViewById( R.id.listView1);
		((MapListView)listView).setTextView((TextView) findViewById(R.id.textView1));
		mla= new MapListAdapter(this, R.layout.map_row_item, listmaplvl );
		listView.setAdapter( mla );
		listView.setSelection(listView.getCount()-1);
		listView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mla.onTouch(v, event);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mla.onClick(arg1, arg2,listView.getCount());
			}
		});
		RelativeLayout l=(RelativeLayout) findViewById(R.id.Linear);

		OnClickListener ocl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(v.getTag()!=null)
					if(((String)v.getTag()).contains("spaceship")){
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
					}}
		};




		for(int i=0;i<l.getChildCount();i++){
			if (l.getChildAt(i) instanceof ImageView) {
				ImageView iv = (ImageView) l.getChildAt(i);
				iv.setOnClickListener(ocl);

			};
		}

	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		menuSelected=v;
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Modify Ship");
		menu.add(menu.NONE, 1, Menu.NONE, "Delete Ship");
		menu.add(menu.NONE, 2, Menu.NONE, "Add move");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		String viewinfo = menuSelected.getTag().toString();
		String[] shipinfo = viewinfo.split("/");
		switch (item.getItemId()) {
		case 1:
			mla.DeleteShip(Integer.parseInt(shipinfo[0]), Integer.parseInt(shipinfo[1]));
			((RelativeLayout) menuSelected.getParent()).removeView(menuSelected);
			break;
		case 2:
			mla.setMoveMode(true, Integer.parseInt(shipinfo[0]), Integer.parseInt(shipinfo[1]));
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	public ImageView getSelectedShip() {
		return selectedShip;
	}
}
