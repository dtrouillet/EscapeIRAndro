package fr.esipe.game.util;
import java.util.List;

import fr.esipe.game.escapeir.MapBuilder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MapListAdapter extends ArrayAdapter<MapLvl> {

	private int				resource;
	private LayoutInflater	inflater;
	private Context 		context;
	private int x=0;
	private int y=0;
	public MapListAdapter ( Context ctx, int resourceId, List<MapLvl> objects) {
		
		super( ctx, resourceId, objects );
		resource = resourceId;
		inflater = LayoutInflater.from( ctx );
		context=ctx;
	}
	
	private OnTouchListener otl=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			x=(int) event.getX();
			y=(int) event.getY();
			return false;
		}
	};
	
	
	private OnClickListener ocl= new OnClickListener() {
		

		@Override
		public void onClick(View v) {
			ImageView selectedShip=((MapBuilder)context).getSelectedShip();
			if(selectedShip!=null){
				RelativeLayout fl = (RelativeLayout) v;
				MapLvl map = getItem( (Integer) fl.getTag() );
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(selectedShip.getWidth(), selectedShip.getHeight());
				params.leftMargin=x-(selectedShip.getWidth()/2);
				params.topMargin=y-(selectedShip.getHeight()/2);
				ImageView iv = new ImageView(context);
				iv.setImageDrawable(selectedShip.getDrawable());
				int h=fl.getHeight();
				int w=fl.getWidth();
				fl.addView(iv,params);
				map.addSpaceships(iv.getDrawable(),params);
				fl.getLayoutParams().height=h;
				fl.getLayoutParams().width=w;

			}
		}
	};
	
	@Override
	public View getView ( int position, View convertView, ViewGroup parent ) {
		if(convertView==null){
			Log.d("TAGTESTNULLITEM", "null ");
		}else{
			convertView.setTag(position);
			if(convertView.getTag()!=null){
				Log.d("TAGPOS", ""+((Integer)convertView.getTag())+"");
			}
		}
		convertView = ( RelativeLayout ) inflater.inflate( resource, null );
		
		if(convertView.getTag()!=null){
			Log.d("TAGPOSINFLATE", ""+((Integer)convertView.getTag())+"");
		}
		if(ocl!=null&&otl!=null){
		convertView.setOnTouchListener(otl);
		convertView.setOnClickListener(ocl);
		}
		MapLvl map = getItem( position );
	    ImageView iv=(ImageView) ((RelativeLayout) convertView).getChildAt(0);
	    
	    iv.setImageBitmap(map.getImage());
	    int nbspace=map.getCountSpacehipOnMap();
	    for(int i=0;i<nbspace;i++){
	    	ImageView ship =new ImageView(context);
	    	ship.setImageDrawable(map.getSpaceshipOnMap(i));
	    	(( RelativeLayout )convertView).addView(ship,map.getSpaceshipparam(i));
	    }
		return convertView;

	}
}

