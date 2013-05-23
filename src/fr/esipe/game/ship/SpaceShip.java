package fr.esipe.game.ship;

import java.util.HashMap;
import java.util.Iterator;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;
import android.util.Log;
import fr.esipe.game.util.Utils;
import fr.esipe.game.weapon.Weapon;


public abstract class SpaceShip {
	private Bitmap image;
	private final String name;
	private final boolean enemy;
	private final boolean boss;
	private int life;
	private Bonus bonus;
	private Vec2 direction;
	private Information info;
	private  Iterator<Vec2> trackIterator;
	private Vec2 dest=null;
	private boolean validate=false;
	protected final World world;
	protected Body body;
	protected final HashMap<String,Weapon> weapon = new HashMap<String,Weapon>();
	private Weapon weaponSelected = null;
	
	public Vec2 getPosition() {
		return new Vec2(body.getPosition());
	}
	
	public void setPosition(final Vec2 newPosition){
		if(newPosition == null || body == null){
			Log.d("setPosition","position est a null");
			return;
		}
		synchronized (this) {
			body.setTransform(newPosition, 0);
		}
	}

	public SpaceShip(Bitmap pImage, String pNom, boolean pEnnemi, boolean pBoss, int pLife, World pWorld){
		image = pImage;
		name = pNom;
		enemy = pEnnemi;
		boss = pBoss;
		life = pLife;
		world = pWorld;
		info = new Information(10, pLife, true);
		if(!enemy){
			info.setWeapon(weapon);
			info.setDegats(Integer.MAX_VALUE);
		}
	}
	
	public void setBonus(Bonus pBonus){
		bonus = pBonus;
	}
	
	public Bonus getBonus(){
		return bonus;
	}
	
	public Bitmap getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public boolean isEnnemi() {
		return enemy;
	}

	public boolean isBoss() {
		return boss;
	}

	public int getLife() {
		life = info.getPoint();
		return life;
	}


	public int getScore() {
		return info.getScore();
	}

	public void rotateImage(){
		image = Utils.rotate(image);
	}

	public void decLife(int damage){
		if(damage < life){
			life = life - damage;
		}else{
			life = 0;
			world.destroyBody(body);

		}
		info.setPoint(life);
	}
	
	
	public Body getBody(){
		return body;
	}
	
	public void addWeapon(Weapon pWeapon){
		weapon.put(pWeapon.getName(), pWeapon);
		if(!enemy){
			info.setWeapon(weapon);
		}
	}
	
	public Weapon getWeaponCurrent(){
		return weaponSelected;
	}
	
	public Weapon getWeapon(String weaponName){
		return weapon.get(weaponName);
	}
	
	public void shoot(Vec2 direction){
		if(direction != null){
			weaponSelected.setPosition(body.getPosition());
			weaponSelected.shoot(direction);
		}
	}
	
	public boolean setWeapon(String pWeapon){
		Weapon myWeapon = weapon.get(pWeapon);
		if(myWeapon != null){
			weaponSelected = myWeapon;
			return true;
		}
		return false;
	}
	
	public void setDirection(Vec2 pDirection){
		direction = pDirection;
	}
	
	public void move(){
		body.setLinearDamping(0.7f);
		body.setLinearVelocity(direction);
	}
	
	public Information getInformation(){
		return info;
	}

	public void cleanAmmo() {
		info.addScore(weaponSelected.clean());
	}
	
	public void setLife(int pLife){
		life = pLife;
		info.setPoint(pLife);
	}
	
	public void updatePos() {
		if(dest==null)dest=trackIterator.next();
		if(trackIterator.hasNext()&&Utils.distance(dest, body.getWorldCenter())<=20){
			dest=trackIterator.next();
			validate=false;
		}
		if(validate==false){
			body.setLinearVelocity(dest.sub(body.getWorldCenter()).mul(0.25f));
			validate=true;
		}
		
	}
	
	public void setTrackIterator(Iterator<Vec2> pTrackIterator){
		trackIterator = pTrackIterator;
	}

}
