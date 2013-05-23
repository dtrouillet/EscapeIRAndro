package fr.esipe.game.escapeir;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

	//FPS
	public int targetFPS = 30;
	public float timeStep = 1.0f / targetFPS;
    /** The surface width and height. */
    //private int width = 0;
    public boolean gameOver = false;
    //private int height = 0;
    
    private float positionEventX = -1;
    private float positionEventY = -1;
    //private final Level level;
    
    /** The position */
    //private int positionX = 0;
    private int positionY = 0;
    
    //GESTION DU TIR
    private boolean onHero = false;
    
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
    private Bitmap weaponMissileActivate;
    private Bitmap weaponMissileDesactivate;

    private Bitmap weaponFireBallActivate;
    private Bitmap weaponFireBallDesactivate;

    private Bitmap weaponShibooleetActivate;
    private Bitmap weaponShibooleetDesactivate;

   // private Bitmap lifeMiddle;
    //private Bitmap lifeLow;
    private Bitmap underLife;
    private Bitmap life;
    private ArrayList<SpaceShip> listEnemies = new ArrayList<SpaceShip>();
	private final Random rand = new Random();
	private SpaceShip hero;
	private World world;
	private AtomicInteger test = new AtomicInteger(0);


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
        this.weaponMissileActivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponmissilea);
        this.weaponMissileDesactivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponmissiled);

        this.weaponFireBallActivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponfireballa);
        this.weaponFireBallDesactivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponfireballd);

        this.weaponShibooleetActivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponshibooleeta);
        this.weaponShibooleetDesactivate = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.weaponshibooleetd);

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
            //this.width = width;
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

		org.jbox2d.common.Settings.maxTranslation = 10.0f;	
		
        Log.d("thread", "Game thread started");
        ArrayList<LevelXml> listLevel = XmlLoader.getFeeds(context);
        //Log.d("XML","taille listLevel = "+listLevel.size());
        
        Level level = listLevel.get(0).xmlToLevel(context);;
        world = level.getWorld();
		hero = level.getHero();
		
		world.setContactListener(new BodyContactListener());
		int start = (int) System.currentTimeMillis();
		
		double current_time = 0;
        double last_time = 0;
        int n = 0;
        int fps = n;
        
        while (running) {
            n++;
            current_time = System.currentTimeMillis();

            if( (current_time - last_time) >= 1000 )
            {
                // nombre de frames par seconde
                fps = n;
                n = 0;
                last_time = current_time;
                Log.d("fps",fps+"");
            }
        	
        	if(hero.getLife() > 0 && !level.finish(listEnemies)){
	            synchronized (world) {
	        		world.step(timeStep,2,2);
				}
	            //Log.d("step","step");
           		updateState();
	            Canvas canvas = null;
	            try {
					level.updatePos(listEnemies);
	               // Log.d("life"," = "+hero.getLife());
					int current = (int) System.currentTimeMillis();
					level.addEnemyInList(listEnemies, (current-start)/1000);
	                canvas = holder.lockCanvas(null);

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
        				drawResult(canvas, hero.getLife() <= 0);
        				break;
        			}
        		}finally{
        			if (canvas != null) {
	                    holder.unlockCanvasAndPost(canvas);
	                }
        		}

          	   // ((Activity)context).finish();
          	   
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
     * Draw bitmaps. life and weapon
     * 
     * @param canvas
     *            The canvas
     */
    private void draw(Canvas canvas) {
    	if(canvas != null){
	        screenRect.set(80, 0, 160, 80);
	        RectF dst = new RectF(10, 10, 80, 80);
	        canvas.drawBitmap(weaponMissileActivate, null, dst, null);
	        
	        dst.set(100, 10, 170, 80);
	        canvas.drawBitmap(weaponFireBallActivate, null, dst, null);
	        
	        dst.set(190, 10, 260, 80);
	        canvas.drawBitmap(weaponShibooleetActivate, null, dst, null);
	        //Affichage LIFE AND SCORE
	        //graphics.drawImage(Utils.createImage("underLife.png"), 410, 10, 270, 35, null);
			
			dst = new RectF(Constant.WIDTH - 200, 10, Constant.WIDTH-10, 45);
	        canvas.drawBitmap(underLife, null, dst, null);
			
	        int lifeH = hero.getLife();
			if(lifeH > 100){
				dst = new RectF(Constant.WIDTH - 197, 13, Constant.WIDTH-13, 42);
		        canvas.drawBitmap(life, null, dst, null);
			}else{
				dst = new RectF(Constant.WIDTH - 197, 13, Constant.WIDTH-(13+(100-lifeH/2)), 42);
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
 	        screenRect.set(0, positionY, Constant.WIDTH, positionY+2000);
 	        canvas.drawBitmap(tiles, null, screenRect, null);
 	        screenRect.set(0, positionY - 2000, Constant.WIDTH, positionY);
 	        canvas.drawBitmap(tiles, null, screenRect, null);
     	}      
     }

     private void drawHero(Canvas canvas) {
    	 if(canvas != null){
    		//Log.d("hero", "x = "+hero.getPosition().x+" y = "+hero.getPosition().y);
 	        screenRect.set((int)hero.getPosition().x, (int)hero.getPosition().y, (int)hero.getPosition().x+80, (int)hero.getPosition().y+80);
 	        canvas.drawBitmap(hero.getImage(), null, screenRect, null);
 	       
 	        String text = hero.getScore()+"";
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(50);
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText(text, (canvas.getWidth() - bounds.width() - 50), 100, paint);
			
     	}      
     }

	public void setPosition(final float x, final float y, final int actionEvent) {
		Log.d("hero","setPosition");

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(test.get() != 0)
					return;
				test.incrementAndGet();
				if(actionEvent == MotionEvent.ACTION_UP){
					positionEventX = -1;
					positionEventY = -1;
					if(onHero){
						int xInt = (int)(x - hero.getPosition().x);
						int yInt = (int)(y - hero.getPosition().y);
						System.out.println("x ="+xInt+" y ="+yInt);
						if((x - hero.getPosition().x > 100 || x - hero.getPosition().x < -100) || (y - hero.getPosition().y > 100 || y - hero.getPosition().y < -100)){
							hero.shoot(new Vec2(x-hero.getPosition().x,y-hero.getPosition().y));
						}
					}
					onHero = false;
				}
				
				if(actionEvent == MotionEvent.ACTION_DOWN){
					if((hero.getLife() > 0) && (x > hero.getPosition().x) && (x < hero.getPosition().x + 80) && (y > hero.getPosition().y) && (y < hero.getPosition().y + 80)){
						onHero = true;
					}
				}
						
				if(actionEvent == MotionEvent.ACTION_MOVE && !onHero){
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
				
				test.decrementAndGet();
			}
		}).start();
			
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
			for(Ammo myAmmo : removeAmmo)
				weapon.removeAmmo(myAmmo);
		}
		
		//Suppression des enemis inutiles
		for(SpaceShip ennemis : removeEnemy){
			world.destroyBody(ennemis.getBody());
			if(ennemis.getWeaponCurrent().getAllAmmo().size() == 0 && (ennemis.getBonus() == null || ennemis.getBonus().isDestroy())){
				listEnemies.remove(ennemis);
				Log.d("enemy","detruit");
			}
		}
//		PositionTask positionTask = new PositionTask();
//		positionTask.execute(canvas);
		
	}
	
	private void drawResult(Canvas canvas, boolean isOver){
		if(canvas == null)
			return;
		
		String text;
		int color;
		if(isOver){
			text = "GAME OVER";
			color = Color.RED;
		}else{
			text = "WELL DONE";
			color = Color.BLUE;
		}
		
		canvas.drawColor(color);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(100);
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		canvas.drawText(text, (canvas.getWidth() - bounds.width()) / 2, (canvas.getHeight() - bounds.height()) / 2, paint);
		
		text = "Press back";
		paint.getTextBounds(text, 0, text.length(), bounds);
		canvas.drawText(text, (canvas.getWidth() - bounds.width()) / 2, (canvas.getHeight() - bounds.height()) / 2 + 100, paint);
	}
}
