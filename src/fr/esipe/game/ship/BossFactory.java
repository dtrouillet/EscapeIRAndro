package fr.esipe.game.ship;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;
import fr.esipe.game.escapeir.R;
import fr.esipe.game.util.Utils;
import fr.esipe.game.weapon.FireBall;
import fr.esipe.game.weapon.Missile;
import fr.esipe.game.weapon.Weapon;



/**
 * This factory creates the hero of each level with the characteristics of selected spaceship.
 * @author damien
 *
 */
public class BossFactory {
	private final World world;
	public BossFactory(World pWorld){
		world = pWorld;
	}
	
	/**
	 * Returns a enemy spaceship
	 * @param type corresponds to the type of spaceship involved, 1 being the lowest and 3 the highest.
	 * @return spaceship
	 */	
	public SpaceShip getBoss(int type, int life, Context context){
		Random rand = new Random();

		SpaceShip boss = null;
		switch (type) {
		case 1:
			boss = new Boss1(world, null, true,context);
			Weapon missile = new Missile(world, true, context);
			missile.setNbrAmmo(Integer.MAX_VALUE);
			boss.addWeapon(missile);
			boss.setWeapon(missile.getName());
			break;
		case 2:
			boss = new Boss2(world,null,true, context);
			Weapon fireball = new FireBall(world, true, context);
			fireball.setNbrAmmo(Integer.MAX_VALUE);
			boss.addWeapon(fireball);
			boss.setWeapon(fireball.getName());
			break;
		case 3:
			boss = new Boss3(world,null,true, context);
			Weapon missile2 = new Missile(world, true,context);
			missile2.setNbrAmmo(Integer.MAX_VALUE);
			boss.addWeapon(missile2);
			boss.setWeapon(missile2.getName());
			break;
		}
		boss.setPosition(new Vec2(rand.nextInt(600), 900));
		if(rand.nextInt(4) == 1){
			Bonus bonus = new Bonus(world,boss.getWeaponCurrent().getName(), rand.nextInt(50), Utils.createImage(context,R.drawable.fireball),context);
			boss.setBonus(bonus);
		}
		return boss;
	}
}
