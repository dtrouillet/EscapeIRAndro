package fr.esipe.game.ship;

import java.util.HashMap;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.esipe.game.util.Constant;
import fr.esipe.game.weapon.Weapon;

import android.graphics.Bitmap;


public class Bonus {
	private final String typeWeapon;
	private final int nbrAmmo;
	private final Bitmap image;
	private final World world;
	private boolean isDestroy = false;
	private Body body;
	private boolean isInit = false;
	
	public Bonus(World pWorld, String pTypeWeapon, int pNbrAmmo, Bitmap pImage){
		typeWeapon = pTypeWeapon;
		nbrAmmo = pNbrAmmo;
		image = pImage;
		world = pWorld;
		createBody();
	}
	
	private void createBody(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = true;

		body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(40, 40);

		fixtureDef.shape = polygon;
		fixtureDef.filter.categoryBits = Constant.CATEGORY_ENEMY;
		fixtureDef.filter.maskBits = Constant.MASK_ENEMY;

		
		body.createFixture(fixtureDef);
		Information info = new Information(0, 0, false);
		info.setBonus(this);
		
		body.setUserData(info);
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public void getAmmo(HashMap<String,Weapon> listWeapon){
		if(listWeapon.get(typeWeapon) != null)
			listWeapon.get(typeWeapon).addAmmo(nbrAmmo);
	}

	public boolean isDestroy() {
		return isDestroy;
	}
	
	public void setDestroy(boolean pIsDestroy){
		isDestroy = pIsDestroy;
	}

	public Body getBody() {
		return body;
	}

	public void setNewPosition(Vec2 position) {
		if(!isInit){
			body.setTransform(position,10);
			isInit = true;
		}
	}
}
