package fr.esipe.game.util;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import fr.esipe.game.escapeir.MapBuilder;

public class MapListAdapter extends ArrayAdapter<MapLvl> {

	private int				resource;
	private LayoutInflater	inflater;
	private Context 		context;
	private int x=0;
	private int y=0;
	private Map<Integer, List<ShipOnMap>> ship;
	public MapListAdapter ( Context ctx, int resourceId, List<MapLvl> objects) {
		
		super( ctx, resourceId, objects );
		resource = resourceId;
		inflater = LayoutInflater.from( ctx );
		context=ctx;
		ship = new HashMap<Integer, List<ShipOnMap>>();
	}
	
	private OnTouchListener otl=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			x=(int) event.getX();
			y=(int) event.getY();
			return false;
		}
	};
	
	
	
	private class ShipOnMap {
		public Drawable ship;
		public RelativeLayout.LayoutParams param;
	}

		

		public void onClick(View v,int position) {
			ImageView selectedShip=((MapBuilder)context).getSelectedShip();
			if(selectedShip!=null){
				RelativeLayout fl = (RelativeLayout) v;
				MapLvl map = getItem( position);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(selectedShip.getWidth(), selectedShip.getHeight());
				params.leftMargin=x-(selectedShip.getWidth()/2);
				params.topMargin=y-(selectedShip.getHeight()/2);
				ImageView iv = new ImageView(context);
				iv.setImageDrawable(selectedShip.getDrawable());
				int h=fl.getHeight();
				int w=fl.getWidth();
				if(!ship.containsKey(position)){
					ship.put(position, new LinkedList<ShipOnMap>());
				}
				ShipOnMap som= new ShipOnMap();
				som.ship=iv.getDrawable();
				som.param=params;
				List<ShipOnMap> lship=ship.get(position);
				lship.add(som);
				iv.setTag(position+"/"+(lship.size()-1));
				((MapBuilder)context).registerForContextMenu(iv);
				fl.addView(iv,params);
				fl.getLayoutParams().height=h;
				fl.getLayoutParams().width=w;

			}
		}
	
	@Override
	public View getView ( int position, View convertView, ViewGroup parent ) {
	
		convertView = ( RelativeLayout ) inflater.inflate( resource, null );
		
		if(/*ocl!=null&&*/otl!=null){
		convertView.setOnTouchListener(otl);
		}
		MapLvl map = getItem( position );
	    ImageView iv=(ImageView) ((RelativeLayout) convertView).getChildAt(0);
	    iv.setImageBitmap(map.getImage());
	    if(ship.containsKey(position)){
	    	List<ShipOnMap> lsom=ship.get(position);
	    int nbspace=lsom.size();
	    for(int i=0;i<nbspace;i++){
	    	ImageView ship =new ImageView(context);
	    	ship.setImageDrawable(lsom.get(i).ship);
	    	ship.setTag(position+"/"+i);
	    	(( RelativeLayout )convertView).addView(ship,lsom.get(i).param);
	    	((MapBuilder)context).registerForContextMenu(ship);
	    }}
		return convertView;

	}
	
	public void DeleteShip(int position, int index){
		List<ShipOnMap>lship =ship.get(position);
		if(lship!=null){
			lship.remove(index);
			if(lship.size()==0)
				ship.remove(position);
		}
	}

}

