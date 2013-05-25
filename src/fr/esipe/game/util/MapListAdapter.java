package fr.esipe.game.util;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MapListAdapter extends ArrayAdapter<MapLvl> {

	private int				resource;
	private LayoutInflater	inflater;
	private Context 		context;
	
	public MapListAdapter ( Context ctx, int resourceId, List<MapLvl> objects) {
		
		super( ctx, resourceId, objects );
		resource = resourceId;
		inflater = LayoutInflater.from( ctx );
		context=ctx;
	}


	@Override
	public View getView ( int position, View convertView, ViewGroup parent ) {

		convertView = ( LinearLayout ) inflater.inflate( resource, null );
		MapLvl city = getItem( position );
	    ImageView iv=(ImageView) ((LinearLayout) convertView).getChildAt(0);
	    
	    iv.setImageBitmap(city.getImage());
		return convertView;

	}
}

