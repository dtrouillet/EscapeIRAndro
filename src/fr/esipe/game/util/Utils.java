package fr.esipe.game.util;


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
}
