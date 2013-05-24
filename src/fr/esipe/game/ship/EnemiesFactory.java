package fr.esipe.game.ship;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import fr.esipe.game.escapeir.R;
import fr.esipe.game.util.Constant;
import fr.esipe.game.util.Utils;
import fr.esipe.game.weapon.FireBall;
import fr.esipe.game.weapon.Missile;
import fr.esipe.game.weapon.Weapon;



/**
 * This factory creates the hero of each level with the characteristics of selected spaceship.
 * @author damien
 *
 */
public class EnemiesFactory {
	private final World world;
	public EnemiesFactory(World pWorld){
		world = pWorld;
	}
	
	/**
	 * Returns a enemy spaceship
	 * @param type corresponds to the type of spaceship involved, 1 being the lowest and 3 the highest.
	 * @return spaceship
	 */	
	public SpaceShip getEnemie(int type, Track t, Context context){
		Random rand = new Random();

		SpaceShip enemy = null;
		Bitmap image = null;
		switch (type) {
		case 1:
			enemy = new SpaceShipLow(world, t, true,context);
			Weapon missile = new Missile(world, true, context);
			missile.setNbrAmmo(Integer.MAX_VALUE);
			enemy.addWeapon(missile);
			enemy.setWeapon(missile.getName());
			image = Utils.createImage(context, R.drawable.weaponmissile);
			//.getSubimage(80, 0, 80, 80);
			break;
		case 2:
			enemy = new SpaceShipMiddle(world,t,true,context);
			Weapon fireball = new FireBall(world, true, context);
			fireball.setNbrAmmo(Integer.MAX_VALUE);
			enemy.addWeapon(fireball);
			enemy.setWeapon(fireball.getName());
			image = Utils.createImage(context, R.drawable.weaponfireball);
			//.getSubimage(80, 0, 80, 80);
			break;
		case 3:
			enemy = new SpaceShipHigh(world,t,true,context);
			Weapon missile2 = new Missile(world, true, context);
			missile2.setNbrAmmo(Integer.MAX_VALUE);
			enemy.addWeapon(missile2);
			enemy.setWeapon(missile2.getName());
			image = Utils.createImage(context, R.drawable.weaponshibooleet);
			//.getSubimage(80, 0, 80, 80);
			break;
		}
		enemy.setPosition(new Vec2(rand.nextInt(Constant.WIDTH) - 100, 100));
		if(true){//rand.nextInt(10) == 1
			Bonus bonus = new Bonus(world,enemy.getWeaponCurrent().getName(), rand.nextInt(50), image,context);
			enemy.setBonus(bonus);
		}
		return enemy;
	}
}
