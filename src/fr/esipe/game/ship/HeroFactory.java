package fr.esipe.game.ship;

import org.jbox2d.dynamics.World;

import android.content.Context;


/**
 * This factory creates the hero spaceship of each level with the characteristics of selected spaceship.
 * @author damien
 *
 */
public class HeroFactory {
	private final World world;
	public HeroFactory(World pWorld){
		world = pWorld;
	}
	
	/**
	 * Returns a hero spaceship
	 * @param type corresponds to the type of spaceship involved, 1 being the lowest and 3 the highest.
	 * @param life is the hero whose life has early part
	 * @return spaceship
	 */
	public SpaceShip getHero(int type, int life, Context context){
		SpaceShip hero = null;
		switch (type) {
		case 1:
			hero = new SpaceShipLow(world, null, false, context);
			break;
		case 2:
			hero = new SpaceShipMiddle(world,null,false, context);
			break;
		case 3:
			hero = new SpaceShipHigh(world,null,false, context);
			break;
		}
		hero.setLife(life);
		//applyLimit();
		return hero;
	}
	
//	private void applyLimit(){
//		Log.d("applyLimit", "WIDTH = "+Constant.WIDTH+" HEIGHT = "+Constant.HEIGHT);
//		BodyDef bodyDefLeft = new BodyDef();
//		BodyDef bodyDefRight = new BodyDef();
//		BodyDef bodyDefTop = new BodyDef();
//		BodyDef bodyDefBottom = new BodyDef();
//
//		bodyDefLeft.type = BodyType.STATIC;
//		bodyDefRight.type = BodyType.STATIC;
//		bodyDefTop.type = BodyType.STATIC;
//		bodyDefBottom.type = BodyType.STATIC;
//		
//		bodyDefLeft.position = new Vec2(-30,Constant.HEIGHT - (Constant.HEIGHT/3*2));
//		bodyDefRight.position = new Vec2(Constant.WIDTH,Constant.HEIGHT);
//		bodyDefTop.position = new Vec2(0,Constant.HEIGHT - (Constant.HEIGHT/3*2));
//		bodyDefBottom.position = new Vec2(0,0);
//
//		Body bodyLeft = world.createBody(bodyDefLeft);
//		Body bodyRight = world.createBody(bodyDefRight);
//		Body bodyTop = world.createBody(bodyDefTop);
//		Body bodyBottom = world.createBody(bodyDefBottom);
//		
//		FixtureDef fixtureDefLeft = new FixtureDef();
//		FixtureDef fixtureDefRight = new FixtureDef();
//		FixtureDef fixtureDefTop = new FixtureDef();
//		FixtureDef fixtureDefBottom = new FixtureDef();
//
//		PolygonShape polygonLeft = new PolygonShape();
//		PolygonShape polygonRight = new PolygonShape();
//		PolygonShape polygonTop = new PolygonShape();
//		PolygonShape polygonBottom = new PolygonShape();
//
//		polygonLeft.setAsBox(0, Constant.HEIGHT);
//		polygonRight.setAsBox(0, Constant.HEIGHT);
//		polygonTop.setAsBox(Constant.WIDTH, 0);
//		polygonBottom.setAsBox(Constant.WIDTH, 0);
//
//		fixtureDefRight.shape = polygonRight;
//		fixtureDefLeft.shape = polygonLeft;
//		fixtureDefTop.shape = polygonTop;
//		fixtureDefBottom.shape = polygonBottom;
//
//		fixtureDefLeft.filter.categoryBits = Constant.CATEGORY_WALL;
//		fixtureDefRight.filter.categoryBits = Constant.CATEGORY_WALL;
//		fixtureDefTop.filter.categoryBits = Constant.CATEGORY_WALL;
//		fixtureDefBottom.filter.categoryBits = Constant.CATEGORY_WALL;
//
//		fixtureDefLeft.filter.maskBits = Constant.MASK_WALL;
//		fixtureDefRight.filter.maskBits = Constant.MASK_WALL;
//		fixtureDefTop.filter.maskBits = Constant.MASK_WALL;
//		fixtureDefBottom.filter.maskBits = Constant.MASK_WALL;
//
//		bodyLeft.createFixture(fixtureDefLeft);
//		bodyRight.createFixture(fixtureDefRight);
//		bodyTop.createFixture(fixtureDefTop);
//		bodyBottom.createFixture(fixtureDefBottom);
//	}
}
