package fr.esipe.game.weapon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import fr.esipe.game.escapeir.R;
import fr.esipe.game.util.Utils;

/**
 * Singleton of shiboleet weapon with her specifications
 * @author damien
 *
 */
public class Shiboleet extends Weapon{
	public Shiboleet(World pWorld, boolean isEnemy, Context context){
		super(Utils.createImage(context, R.drawable.shibooleet),20,"Shiboleet", isEnemy);
		setWorld(pWorld);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.bullet = true;
		setBodyDef(bodyDef);
		//bodyDef.angularVelocity = 90;

		FixtureDef fixtureDef = new FixtureDef();

		CircleShape circle = new CircleShape();
		circle.m_radius = 25;
		
		fixtureDef.shape = circle;
		
		setFixtureDef(fixtureDef);
		setDimension(new Vec2());
	}
}
