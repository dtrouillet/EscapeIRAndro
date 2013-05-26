package fr.esipe.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class MapLvl {

	private Bitmap image;

	public MapLvl(String image2) {
		super();
		this.image = decodeFile(new File(image2));
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

}
