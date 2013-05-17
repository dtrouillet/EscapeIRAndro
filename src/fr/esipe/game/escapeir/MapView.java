package fr.esipe.game.escapeir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * The bitmap game surface.
 */
public class MapView extends SurfaceView implements Callback {

    /**
     * The game thread.
     */
    private GameThread thread;
    public boolean finish;
    private Context context;
    /**
     * Constructor.
     * 
     * @param context
     *            The context
     * @param attrs
     *            The attributes
     */
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SurfaceHolder holder = getHolder();
        this.thread = new GameThread(holder, context);
        holder.addCallback(this);
        setFocusable(true);
    }

    
    /**
     * Surface ready to serve.
     * 
     * @param holder
     *            The surface holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("surface", "Surface created");
        this.thread.setRunning(true);
        this.thread.start();
    }

    /**
     * Injects surface size.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("surface", "Surface changed, width = [" + width + "], height = [" + height + "]");
        this.thread.setSurfaceSize(width, height);
    }

    /**
     * Inform of surface deletion
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("surface", "Surface destroyed");
        thread.setRunning(false);
        boolean alive = true;
        while (alive) {
            try {
                thread.join();
                alive = false;
            } catch (InterruptedException e) {
            }
        }
        ((Activity)context).setResult(0);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	    	for(int i = 0; i < event.getPointerCount(); i++){
	    		thread.setPosition(event.getX(i),event.getY(i), event.getAction());
	    	}
    	return true;
    }  
}