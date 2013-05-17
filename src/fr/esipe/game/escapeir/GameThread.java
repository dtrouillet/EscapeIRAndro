package fr.esipe.game.escapeir;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import fr.esipe.game.ship.BodyContactListener;
import fr.esipe.game.ship.Bonus;
import fr.esipe.game.ship.Information;
import fr.esipe.game.ship.SpaceShip;
import fr.esipe.game.util.Constant;
import fr.esipe.game.util.LevelXml;
import fr.esipe.game.util.XmlLoader;
import fr.esipe.game.weapon.Ammo;
import fr.esipe.game.weapon.Weapon;

/**
 * The game main loop.
 */
public class GameThread extends Thread {

    /** The surface width and height. */
    private int width = 0;
    public boolean gameOver = false;
    //private int height = 0;
    
    private float positionEventX = -1;
    private float positionEventY = -1;
    //private final Level level;
    
    /** The position */
    //private int positionX = 0;
    private int positionY = 0;
    
    private Rect screenRect = new Rect();
    
    /** The running state. */
    private boolean running;
    /** The surface holder. */
    private SurfaceHolder holder;
    // [...]
    /** The context. */
    private Context context;
    /** The tiles. */
    private Bitmap tiles;
    private Bitmap weaponMissile;
    private Bitmap weaponFireBall;
    private Bitmap weaponShibooleet;
   // private Bitmap lifeMiddle;
    //private Bitmap lifeLow;
    private Bitmap underLife;
    private Bitmap life;
    private ArrayList<SpaceShip> listEnemies = new ArrayList<SpaceShip>();
	private final Random rand = new Random();
	private SpaceShip hero;
	private World world;


    /** The level. */
   

    /**
     * Constructor.
     * 
     * @param holder
     *            The surface holder
     * @param context
     *            The context
     */
    public GameThread(SurfaceHolder holder, Context context) {
        this.holder = holder;
        this.context = context;
        this.tiles = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.earth);
        this.weaponMissile = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponmissile);
        this.weaponFireBall = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponfireball);
        this.weaponShibooleet = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponshibooleet);
        this.underLife = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.underlife);
        this.life = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.life);
       // this.lifeLow = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.lifelow);
        //this.lifeMiddle = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.lifemiddle);

        //XmlLoader xml = new XmlLoader("ressources/xml/level/earth.xml");
        //this.level = 
    }

    /**
     * Injects the surface size.
     * 
     * @param width
     *            The width
     * @param height
     *            The height
     */
    public void setSurfaceSize(int width, int height) {
        synchronized (this.holder) {
            this.width = width;
            //this.height = height;
            Constant.WIDTH = width;
            Constant.HEIGHT = height;
        }
    }
    

    /**
     * Set the running state.
     * 
     * @param running
     *            The state
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Game main loop.
     */
    @Override
    public void run() {

		org.jbox2d.common.Settings.maxTranslation = 5.0f;	
		
        Log.d("thread", "Game thread started");
        ArrayList<LevelXml> listLevel = XmlLoader.getFeeds(context);
        //Log.d("XML","taille listLevel = "+listLevel.size());
        
        Level level = listLevel.get(0).xmlToLevel(context);;
        world = level.getWorld();
		hero = level.getHero();
		
		world.setContactListener(new BodyContactListener());
		int start = (int) System.currentTimeMillis();
		int menuCreate = 0;

        
        while (running) {
        	if(hero.getLife() > 0){
        		//world.step(1f, 50, 50);
	            world.step(1/60f, 6,2);
        		updateState();
	            Canvas canvas = null;
	            try {
					level.updatePos(listEnemies);
	                canvas = holder.lockCanvas(null);
	                Log.d("life"," = "+hero.getLife());
					int current = (int) System.currentTimeMillis();
					level.addEnemyInList(listEnemies, (current-start)/1000);
	                synchronized (this.holder) {
	                	//Boucle qui dessine sur le canvas
	                    hero.cleanAmmo();
	                	drawMap(canvas);
	                    drawHero(canvas);
	                    drawEnemy(canvas);
	                    draw(canvas);
	                }
	            } finally {
	                if (canvas != null) {
	                    holder.unlockCanvasAndPost(canvas);
	                }
	            }
        	}else{
        		gameOver = true;
        		Canvas canvas = null;
        		try{
	                canvas = holder.lockCanvas(null);
        			synchronized (this.holder) {
        			// drawGameOver(canvas);
        			}
        		}finally{
        			if (canvas != null) {
	                    holder.unlockCanvasAndPost(canvas);
	                }
        		}
        		
        		if(menuCreate == 0){
        			//Intent intent = new Intent(context, MenuActivity.class);
        			//context.startActivity(intent); //intent must be declared
        			menuCreate++;
        		}
          	    ((Activity)context).finish();
          	   
        	}
        }
    }

    /**
     * Update the game state.
     */
    private void updateState() {
    	this.positionY = positionY + 20;
    	if(positionY > 2000)
    			positionY = 0;
    	//Log.d("mg","Width = "+width+" Height = "+height);
    }

    /**
     * Draw bitmaps.
     * 
     * @param canvas
     *            The canvas
     */
    private void draw(Canvas canvas) {
    	if(canvas != null){
	        screenRect.set(80, 0, 160, 80);
	        RectF dst = new RectF(10, 10, 80, 80);
	        canvas.drawBitmap(weaponMissile, screenRect, dst, null);
	        
	        dst.set(100, 10, 170, 80);
	        canvas.drawBitmap(weaponFireBall, screenRect, dst, null);
	        
	        dst.set(190, 10, 260, 80);
	        canvas.drawBitmap(weaponShibooleet, screenRect, dst, null);
	        //Affichage LIFE AND SCORE
	        //graphics.drawImage(Utils.createImage("underLife.png"), 410, 10, 270, 35, null);
			
			dst = new RectF(Constant.WIDTH - 200, 10, Constant.WIDTH-10, 45);
	        canvas.drawBitmap(underLife, null, dst, null);
			
	        int lifeH = hero.getLife();
			if(lifeH > 100){
				dst = new RectF(Constant.WIDTH - 197, 13, Constant.WIDTH-13, 42);
		        canvas.drawBitmap(life, null, dst, null);
			}else{
				dst = new RectF(Constant.WIDTH - 197, 13, Constant.WIDTH-(13+(100-lifeH)), 42);
		        canvas.drawBitmap(life, null, dst, null);
			}
//				graphics.drawImage(Utils.createImage("life.png"), 413, 13, (int)(100*2.64), 29, null);
//			else if(life > 40)
//				graphics.drawImage(Utils.createImage("life.png"), 413, 13, (int)(life*2.64), 29, null);
//			else if(life > 20)
//				graphics.drawImage(Utils.createImage("lifeMiddle.png"), 413, 13, (int)(life*2.64), 29, null);
//			else if(life > 0)
//				graphics.drawImage(Utils.createImage("lifeLow.png"), 413, 13, (int)(life*2.64), 29, null);
//			else
//				graphics.drawImage(Utils.createImage("lifeLow.png"), 413, 13, 0, 29, null);
//
//			graphics.drawImage(Utils.createImage("underLife.png"), 410, 55, 270, 35, null);
    	}        
    }
    
    /**
     * Draw bitmaps to display map
     * 
     * @param canvas : the canvas
     * 
     */
     private void drawMap(Canvas canvas) {
    	 if(canvas != null){
 	        screenRect.set(0, positionY, width, positionY+2000);
 	        canvas.drawBitmap(tiles, null, screenRect, null);
 	        screenRect.set(0, positionY - 2000, width, positionY);
 	        canvas.drawBitmap(tiles, null, screenRect, null);
     	}      
     }

     private void drawHero(Canvas canvas) {
    	 if(canvas != null){
    		//Log.d("hero", "x = "+hero.getPosition().x+" y = "+hero.getPosition().y);
 	        screenRect.set((int)hero.getPosition().x, (int)hero.getPosition().y, (int)hero.getPosition().x+80, (int)hero.getPosition().y+80);
 	        canvas.drawBitmap(hero.getImage(), null, screenRect, null);
 	     
     	}      
     }

	public void setPosition(float x, float y, int actionEvent) {
		//System.out.println("position");
		if(actionEvent == MotionEvent.ACTION_UP){
			positionEventX = -1;
			positionEventY = -1;
		}
		
		if(actionEvent == MotionEvent.ACTION_DOWN){
			if((hero.getLife() > 0) && (x > hero.getPosition().x) && (x < hero.getPosition().x + 80) && (y > hero.getPosition().y) && (y < hero.getPosition().y + 80)){
				hero.shoot(new Vec2(0, -100));
			}
		}
		
		
		if(actionEvent == MotionEvent.ACTION_MOVE){
			float newX = hero.getPosition().x + (x - positionEventX);

			float newY = hero.getPosition().y + (y - positionEventY);

					
			newY = newY < 0 ? 0 : newY;
			newY = newY > Constant.HEIGHT ? Constant.HEIGHT : newY;
			
			newX = newX < 0 ? 0 : newX;
			newX = newX > Constant.WIDTH ? Constant.WIDTH : newX;
			
			//Log.d("position", "("+newX+" , "+newY+")");
			hero.setPosition(new Vec2(newX,newY));	
		}
		positionEventX = x;
		positionEventY = y;
	}
	
	private void drawEnemy(Canvas canvas){
		if(canvas == null)
			return;
		

		
		for(Ammo myAmmo : hero.getWeaponCurrent().getAllAmmo()){
			screenRect.set((int)myAmmo.getBody().getPosition().x, (int)myAmmo.getBody().getPosition().y, (int)myAmmo.getBody().getPosition().x+25, (int)myAmmo.getBody().getPosition().y+25);
			canvas.drawBitmap(hero.getWeaponCurrent().getImage(), null, screenRect, null);
		}
		

		List<SpaceShip> removeEnemy = new ArrayList<SpaceShip>();


		for(SpaceShip enemy : listEnemies){
			List<Ammo> removeAmmo = new ArrayList<Ammo>();
			Weapon weapon = enemy.getWeaponCurrent();

			if(!enemy.getInformation().isDestroy() && enemy.getBody().getPosition().y > 0 && enemy.getBody().getPosition().x > -80 && enemy.getBody().getPosition().x < 700){
				screenRect.set((int)enemy.getPosition().x, (int)enemy.getPosition().y, (int)enemy.getPosition().x+80, (int)enemy.getPosition().y + 80);
				canvas.drawBitmap(enemy.getImage(), null, screenRect, null);
				//System.out.println("enemy position ("+enemy.getPosition()+")");

			for(Ammo myAmmo : weapon.getAllAmmo()){
				Information infoAmmo = (Information)myAmmo.getBody().getUserData();
				if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y < 0 || myAmmo.getBody().getPosition().x < 0 || myAmmo.getBody().getPosition().x > 700  ){
					removeAmmo.add(myAmmo);
				}else{
					screenRect.set((int)myAmmo.getBody().getPosition().x, (int)myAmmo.getBody().getPosition().y, (int)myAmmo.getBody().getPosition().x+25, (int)myAmmo.getBody().getPosition().y+25);
					canvas.drawBitmap(weapon.getImage(), null, screenRect, null);
					//Log.d("shoot","pan");
				}
			}						
			
			if(rand.nextInt(20) == 1){
				//TODO A VERIFIER
				enemy.shoot(new Vec2(0, 200));
			}							
			

			}else{
				for(Ammo myAmmo : weapon.getAllAmmo()){
					Information infoAmmo = (Information)myAmmo.getBody().getUserData();
					if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y > Constant.HEIGHT || myAmmo.getBody().getPosition().x < -20 || myAmmo.getBody().getPosition().x > Constant.WIDTH  ){
						removeAmmo.add(myAmmo);
					}else{
						screenRect.set((int)myAmmo.getBody().getPosition().x, (int)myAmmo.getBody().getPosition().y, (int)myAmmo.getBody().getPosition().x+25, (int)myAmmo.getBody().getPosition().y+25);
						canvas.drawBitmap(weapon.getImage(), null, screenRect, null);
					}
				}
				if(enemy.getBonus() != null){
					if(enemy.getBonus().isDestroy() == true){
						world.destroyBody(enemy.getBonus().getBody());
					}else{
						Bonus bonus = enemy.getBonus();
						bonus.setNewPosition(enemy.getPosition());
						//TODO A VERIFIER
						bonus.getBody().setLinearVelocity(new Vec2(0,800));
						
						screenRect.set((int)bonus.getBody().getPosition().x, (int)bonus.getBody().getPosition().y, (int)bonus.getBody().getPosition().x+25, (int)bonus.getBody().getPosition().y+25);
						canvas.drawBitmap(bonus.getImage(), null, screenRect, null);
					}
				}
				removeEnemy.add(enemy);
			}
			//suppression des munitions inutiles
			for(Ammo myAmmo : removeAmmo)
				weapon.removeAmmo(myAmmo);
		}
		
		//Suppression des enemis inutiles
		for(SpaceShip ennemis : removeEnemy){
			world.destroyBody(ennemis.getBody());
			if(ennemis.getWeaponCurrent().getAllAmmo().size() == 0 && (ennemis.getBonus() == null || ennemis.getBonus().isDestroy())){
				listEnemies.remove(ennemis);
				Log.d("async","detruit");
			}
		}
//		PositionTask positionTask = new PositionTask();
//		positionTask.execute(canvas);
		
	}
	
//	public  class PositionTask extends AsyncTask<Canvas, Integer, Void> {
//	    private Rect screenRect2 = new Rect();
//
//    	@Override
//    	protected void onPreExecute() {
//    		super.onPreExecute();
//    		//Toast.makeText(context, "debut task position", Toast.LENGTH_SHORT).show();
//    	}
//    	
//    	@Override
//    	protected Void doInBackground(Canvas... params) {    		
//    		List<SpaceShip> removeEnemy = new ArrayList<SpaceShip>();
//
//
//    		for(SpaceShip enemy : listEnemies){
//    			List<Ammo> removeAmmo = new ArrayList<Ammo>();
//    			Weapon weapon = enemy.getWeaponCurrent();
//
//    			if(!enemy.getInformation().isDestroy() && enemy.getBody().getPosition().y > 0 && enemy.getBody().getPosition().x > -80 && enemy.getBody().getPosition().x < 700){
//    				screenRect2.set((int)enemy.getPosition().x, (int)enemy.getPosition().y, (int)enemy.getPosition().x+80, (int)enemy.getPosition().y + 80);
//    			  params[0].drawBitmap(enemy.getImage(), null, screenRect2, null);
//
//    			for(Ammo myAmmo : weapon.getAllAmmo()){
//    				Information infoAmmo = (Information)myAmmo.getBody().getUserData();
//    				if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y < 0 || myAmmo.getBody().getPosition().x < 0 || myAmmo.getBody().getPosition().x > 700  ){
//    					removeAmmo.add(myAmmo);
//    				}else{
//    					screenRect2.set((int)myAmmo.getBody().getPosition().x, (int)myAmmo.getBody().getPosition().y, (int)myAmmo.getBody().getPosition().x+25, (int)myAmmo.getBody().getPosition().y+25);
//    		 	       params[0].drawBitmap(weapon.getImage(), null, screenRect2, null);
//    					Log.d("shoot","pan");
//    				}
//    			}						
//    			
//    			if(rand.nextInt(20) == 1){
//    				//TODO A VERIFIER
//    				enemy.shoot(new Vec2(0, 200));
//    			}							
//    			
//
//    			}else{
//    				for(Ammo myAmmo : weapon.getAllAmmo()){
//    					Information infoAmmo = (Information)myAmmo.getBody().getUserData();
//    					if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y < 0 || myAmmo.getBody().getPosition().x < -20 || myAmmo.getBody().getPosition().x > 700  ){
//    						removeAmmo.add(myAmmo);
//    					}else{
//    						screenRect2.set((int)myAmmo.getBody().getPosition().x, (int)myAmmo.getBody().getPosition().y, (int)myAmmo.getBody().getPosition().x+25, (int)myAmmo.getBody().getPosition().y+25);
//    						params[0].drawBitmap(weapon.getImage(), null, screenRect2, null);
//    					}
//    				}
//    				if(enemy.getBonus() != null){
//    					if(enemy.getBonus().isDestroy() == true){
//    						world.destroyBody(enemy.getBonus().getBody());
//    					}else{
//    						Bonus bonus = enemy.getBonus();
//    						bonus.setNewPosition(enemy.getPosition());
//    						//TODO A VERIFIER
//    						bonus.getBody().setLinearVelocity(new Vec2(0,800));
//    						
//    						screenRect2.set((int)bonus.getBody().getPosition().x, (int)bonus.getBody().getPosition().y, (int)bonus.getBody().getPosition().x+25, (int)bonus.getBody().getPosition().y+25);
//    						params[0].drawBitmap(bonus.getImage(), null, screenRect2, null);
//    					}
//    				}
//    				removeEnemy.add(enemy);
//    			}
//    			//suppression des munitions inutiles
//    			for(Ammo myAmmo : removeAmmo)
//    				weapon.removeAmmo(myAmmo);
//    		}
//    		
//    		//Suppression des enemis inutiles
//    		for(SpaceShip ennemis : removeEnemy){
//    			world.destroyBody(ennemis.getBody());
//    			if(ennemis.getWeaponCurrent().getAllAmmo().size() == 0 && (ennemis.getBonus() == null || ennemis.getBonus().isDestroy())){
//    				listEnemies.remove(ennemis);
//    				Log.d("async","detruit");
//    			}
//    		}
//    		return null;
//    	}
//    	
//    	@Override
//    	protected void onProgressUpdate(Integer... progress) {
//    		super.onProgressUpdate(progress);
//    	}
//    	
//
//    }
}