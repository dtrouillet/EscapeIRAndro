package fr.esipe.game.ship;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import fr.esipe.game.escapeir.R;
import fr.esipe.game.util.Constant;
import fr.esipe.game.util.Utils;

/**
 * Singleton that represents a type of vessel
 * @author damien
 *
 */
public class SpaceShipMiddle extends SpaceShip {
	public SpaceShipMiddle(World world, Track track, boolean isEnemy, Context context) {
		super(Utils.createImage(context, R.drawable.spaceship2), "spaceship middle", isEnemy, false, 20, world);
		BodyDef bodyDef = new BodyDef();
		if(isEnemy){
			setTrackIterator(track.getIterator());
			//TODO Origine true
			bodyDef.bullet = false;
		}else{
			bodyDef.position.set(Constant.WIDTH/2-40, Constant.HEIGHT - 100);
			rotateImage();
		}
		bodyDef.type = BodyType.DYNAMIC;

		body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(40, 40);

		fixtureDef.shape = polygon;
		if(isEnemy){
			fixtureDef.filter.categoryBits = Constant.CATEGORY_ENEMY;
			fixtureDef.filter.maskBits = Constant.MASK_ENEMY;
		}else{
			fixtureDef.filter.categoryBits = Constant.CATEGORY_PLAYER;
			fixtureDef.filter.maskBits = Constant.MASK_PLAYER;
		}

		
		body.createFixture(fixtureDef);
		body.setUserData(getInformation());
	}
}
