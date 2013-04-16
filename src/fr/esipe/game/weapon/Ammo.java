package fr.esipe.game.weapon;

import org.jbox2d.dynamics.Body;

import fr.esipe.game.ship.Information;

/**
 * A weapon ammunition requirements to operate an ammunition can inflict damage to an enemy
 * @author damien
 *
 */
public class Ammo {
	private final Body body;
	private final int damage;
	private final Information info;
	private final boolean isEnemy;
	
	public Ammo(Body pBody, int pDamage, boolean pIsEnemy){
		body = pBody;
		damage = pDamage;
		isEnemy = pIsEnemy;
		info = new Information(damage, 1, !isEnemy);
		body.setUserData(info);
	}

	/**
	 * Get body of ammo
	 * @return Body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * get the damage that can be inflicted by this ammunition
	 * @return damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * get the Information object
	 * @return Information
	 */
	public Information getInfo() {
		return info;
	}
}
