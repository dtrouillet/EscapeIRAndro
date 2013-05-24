package fr.esipe.game.weapon;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;
import fr.esipe.game.util.Constant;

/**
 * this class define the weapon and her action
 * @author damien
 *
 */
public abstract class Weapon{
	
	public static final String FIREBALL = "FireBall";
	public static final String MISSILE = "Missile";
	public static final String SHIBOLEET = "Shiboleet";

	
	private final Bitmap bufferedImage;
	private final int damage;
	private final boolean isEnemy;
	private final String name;
	private int nbrAmmo = 0;
	private final ArrayList<Ammo> ammo = new ArrayList<Ammo>();
	private Vec2 positionSpaceShip;
	private FixtureDef fixtureDef;
	private World world;
	private BodyDef bodyDef;
	private  Body body;
	private Vec2 dimension;
	
	public Vec2 getDimension() {
		return dimension;
	}

	public void setDimension(Vec2 dimension) {
		this.dimension = dimension;
	}

	/**
	 * @param pBufferedImage is the image of your weapon / ammo
	 * @param pDamage is the damage that will be inflicted on the enemy
	 * @param pName is the name of the weapon
	 * @param pIsEnemy true if the weapon is an enemy, false otherwise
	 */
	public Weapon(Bitmap pBufferedImage, int pDamage,String pName, boolean pIsEnemy){
		bufferedImage = pBufferedImage;
		damage = pDamage;
		name = pName;
		isEnemy = pIsEnemy;
	}
	
	public Weapon(Weapon initWeapon){
		bufferedImage = initWeapon.bufferedImage;
		damage = initWeapon.damage;
		name = initWeapon.name;
		isEnemy = initWeapon.isEnemy;
		setWorld(initWeapon.world);
	}
		
	/**
	 * get the image of the weapon
	 * @return
	 */
	public Bitmap getImage() {
		return bufferedImage;
	}
	
	/**
	 * get the damage of ammo
	 * @return damage
	 */
	public int getDamage(){
		return damage;
	}

	/**
	 * set the initial position of new ammo when you shoot
	 * @param position
	 */
	public void setPosition(Vec2 position) {
		positionSpaceShip = position;
	}
	

	/**
	 * the weapon can fire if it has ammo, otherwise it does nothing
	 * @param direction of shoot
	 */
	public void shoot(Vec2 direction){
		if(nbrAmmo <= 0)
			return;
		body = world.createBody(bodyDef);
		if(isEnemy){
			fixtureDef.filter.categoryBits = Constant.CATEGORY_AMMO_ENEMY;
			fixtureDef.filter.maskBits = Constant.MASK_AMMO_ENEMY;
		}else{
			fixtureDef.filter.categoryBits = Constant.CATEGORY_AMMO_HERO;
			fixtureDef.filter.maskBits = Constant.MASK_AMMO_HERO;
		}

		if(body != null){
			body.createFixture(fixtureDef);
			body.setTransform(positionSpaceShip, 0);
			body.setLinearVelocity(direction);
	
			Ammo myAmmo = new Ammo(body, damage, isEnemy);
			ammo.add(myAmmo);
			nbrAmmo--;
		}
	}

	/**
	 * get the weapon name
	 * @return weapon name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * get all ammo which were learned and which are still active
	 * @return ArrayList<Ammo>
	 */
	public ArrayList<Ammo> getAllAmmo(){
		return ammo;
	}
	
	/**
	 * destroy the ammo
	 * @param munition to destruct
	 */
	public void removeAmmo(Ammo myAmmo){
		ammo.remove(myAmmo);
		world.destroyBody(myAmmo.getBody());
	}
	
	protected void setWorld(World pWorld) {
		world = pWorld;
	}
	
	protected void setBodyDef(BodyDef pBodyDef) {
		bodyDef = pBodyDef;
	}
	
	protected void setFixtureDef(FixtureDef pFixtureDef) {
		fixtureDef = pFixtureDef;
	}

	
	/**
	 * get the score and destroy munition
	 * @return score
	 */
	public int clean() {
		int score = 0;
		ArrayList<Ammo> listAmmo = new ArrayList<Ammo>();
		for(Ammo myAmmo : ammo){
			if(myAmmo.getInfo().isDestroy()){
				score += myAmmo.getInfo().getScore(); 
				world.destroyBody(myAmmo.getBody());
				listAmmo.add(myAmmo);
			}
		}
		
		for(Ammo myAmmo : listAmmo)
			ammo.remove(myAmmo);
		
		return score;
	}
	
	/**
	 * get the number of remaining ammo
	 * @return int
	 */
	public int getNbrAmmo(){
		return nbrAmmo;
	}
	
	/**
	 * set the number of remaining ammo
	 * @param number of ammo
	 */
	public void setNbrAmmo(int pAmmo){
		nbrAmmo = pAmmo;
	}

	/**
	 * add ammo for the weapon
	 * @param number of ammo to add
	 */
	public void addAmmo(int pNbrAmmo) {
		nbrAmmo += pNbrAmmo;
	}
}
