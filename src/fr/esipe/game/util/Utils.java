package fr.esipe.game.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * This class includes all useful methods to the whole game
 * @author damien
 *
 */
public class Utils {
	 
	/**
	 * gives the square of a number
	 * @param a is a double
	 * @return double
	 */
    static public double sqr(double a) {
        return a*a;
    }
 
    /**
     * gives the distance between two point
     * @param first is the first point
     * @param second is the second point
     * @return the distance between these points
     */
    static public int distance(final Vec2 first, final Vec2 second){
    	return (int)Math.sqrt(sqr(second.y - first.y) + sqr(second.x - first.x));
    }
    
    /**
     * load an image
     * @param file is the name of your image (in ressources/img folder)
     * @return BufferedImage
     */
    static public Bitmap createImage(Context context,int id){
			return BitmapFactory.decodeResource(context.getResources(), id);
    }
    
    /**
     * rotates 180 degrees to your image
     * @param bufferedImage is your image
     * @return a new buffered image
     */
    public static Bitmap rotate(Bitmap bitmap) {
    	Matrix matrix = new Matrix();
    	matrix.postRotate(180);
    	Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    	return rotated;
      }
    
    public static boolean extractAll(InputStream is, String path) throws IOException {
 
		// Create folder(s)
		
		File file = new File(path);
		if(file.exists())
			return false;
		
		File folder = new File(path);
		folder.mkdirs();
		
		final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
 
		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
 
			final OutputStream output = new FileOutputStream(new File(folder,ze.getName()));
			final byte data[] = new byte[1024];
 
			int count;
			while ((count = zis.read(data)) != -1)
				output.write(data, 0, count);
 
			output.flush();
			output.close();
		}
 
		zis.close();
		return true;
	}
 
}
