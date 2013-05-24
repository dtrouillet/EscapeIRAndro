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
		return hero;
	}
}
