package fr.esipe.game.weapon;

import org.jbox2d.dynamics.World;

import android.content.Context;

/**
 * this class is for you facilitated the creation of a new weapon type of your choice
 * @author damien
 *
 */
public class WeaponFactory {
	private final World world;
	
	/**
	 * @param pWorld is the world of your game
	 */
	public WeaponFactory(World pWorld){
		world = pWorld;
	}
	
	/**
	 * get the type of weapon you want with a defined number of ammunition
	 * @param type is the type of your new weapon
	 * @param nbrAmmo is the number of ammo
	 * @param isEnemy true if the weapon is an enemy, false otherwise
	 * @return your new weapon
	 */
	public Weapon getWeapon(int type, int nbrAmmo, boolean isEnemy, Context context){
		Weapon weapon = null;
		switch (type) {
		case 1:
			weapon = new Missile(world, isEnemy, context);
			weapon.setNbrAmmo(nbrAmmo);
			break;
		case 2:
			weapon = new FireBall(world, isEnemy, context);
			weapon.setNbrAmmo(nbrAmmo);
			break;
		case 3:
			weapon = new Shiboleet(world, isEnemy, context);
			weapon.setNbrAmmo(nbrAmmo);
			break;
		}
		return weapon;
	}
}
