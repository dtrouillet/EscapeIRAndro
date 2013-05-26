package fr.esipe.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class MapLvl {

	private Bitmap image;
	private List<Drawable> spaceships;
	private List<RelativeLayout.LayoutParams> params;

	public MapLvl(String image2) {
		super();
		this.image = decodeFile(new File(image2));
		spaceships = new LinkedList<Drawable>();
		params = new LinkedList<RelativeLayout.LayoutParams>();
	}
	
	public void addSpaceships(Drawable spaceship,RelativeLayout.LayoutParams param){
		spaceships.add(spaceship);
		params.add(param);
	}
	public int getCountSpacehipOnMap(){
		return spaceships.size();
	}
	public Drawable getSpaceshipOnMap(int index){
		return spaceships.get(index);
	}
	
	private Bitmap decodeFile(File f){
		try {
			//decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f),null,o);

			//Find the correct scale value. It should be the power of 2.
			// final int REQUIRED_SIZE=100;
			// int width_tmp=o.outWidth, height_tmp=o.outHeight;
			int scale=1;
			//while(true){
			//if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
			//break;
			//width_tmp/=2;
			//height_tmp/=2;
			//scale*=2;
			//}

			//decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {}
		return null;
	}



	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public LayoutParams getSpaceshipparam(int i) {
		return params.get(i);
	}

}
