package fr.esipe.game.weapon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import fr.esipe.game.escapeir.R;
import fr.esipe.game.util.Utils;

/**
 * Singleton of fireball weapon with her specifications
 * @author damien
 *
 */
public class FireBall extends Weapon{
	public FireBall(World pWorld, boolean isEnemy, Context context){
		super(Utils.createImage(context,R.drawable.fireball),2,"FireBall", isEnemy);
		setWorld(pWorld);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = false;

		setBodyDef(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(6, 6);
		fixtureDef.shape = polygon;
		setFixtureDef(fixtureDef);
		setDimension(new Vec2(12,12));
	}
}
