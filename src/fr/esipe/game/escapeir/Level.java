package fr.esipe.game.escapeir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import fr.esipe.game.ship.EnemiesFactory;
import fr.esipe.game.ship.HeroFactory;
import fr.esipe.game.ship.SpaceShip;
import fr.esipe.game.ship.Track;
import fr.esipe.game.weapon.WeaponFactory;



public class Level {
	private final Bitmap image;
	private final String name;
	private World world;
	private final EnemiesFactory enemiesFactory;
	private final HeroFactory heroFactroy;
	private final WeaponFactory weaponFactory;
//	private int time;
	private int typeHero;
	private final Context context;
	private int lifeHero;
	private List<Integer> listTime = new ArrayList<Integer>();
	//private List<Integer> listTrack;
	private List<Integer> listType = new ArrayList<Integer>();
	private List<Integer> listTypeWeapon = new ArrayList<Integer>();
	private List<Integer> listNbrAmmo = new ArrayList<Integer>();
	private int nbrEnemies = 0;
	
	public Level(String pName, Bitmap pImage, Context pContext){
		name = pName;
		image = pImage;
		context = pContext;
//		AABB worldAABB = new AABB();
//        worldAABB.lowerBound.set(new Vec2((float) -100.0, (float) -100.0));
//        worldAABB.upperBound.set(new Vec2((float) 100.0, (float) 300.0));
        
        Vec2 gravity    = new Vec2((float) 0.0, (float)00.0);
        boolean doSleep = true;
        
		world = new World(gravity,doSleep);
		enemiesFactory = new EnemiesFactory(world);
		heroFactroy = new HeroFactory(world);
		weaponFactory = new WeaponFactory(world);
	}
	
	public World getWorld(){
		return world;
	}
	
	public Bitmap getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}

	public void addEnemyInList(ArrayList<SpaceShip> listEnemy, int time){
		Track t = new Track(new Vec2(200,0));
		t.add(new Vec2(10, 400));
		t.add(new Vec2(200, 400));
		t.add(new Vec2(500,500));
		t.setTrackLoop(1);
		for(int i = nbrEnemies; i < listTime.size(); i++){
			if(listTime.get(i) <= time){
				listEnemy.add(enemiesFactory.getEnemie(listType.get(i),t,context));
				nbrEnemies++;
			
			}
		}
	}

	public void updatePos(ArrayList<SpaceShip> listEnemy){
		for (SpaceShip spaceShip : listEnemy) {
			SpaceShip spa = spaceShip;
			spa.updatePos();
		}
	}
	

	public SpaceShip getHero(){
		SpaceShip hero = heroFactroy.getHero(typeHero, lifeHero,context);
		for(int i = 0; i < listTypeWeapon.size(); i++){
			hero.addWeapon(weaponFactory.getWeapon(listTypeWeapon.get(i), listNbrAmmo.get(i), false,context));
		}
		hero.setWeapon(weaponFactory.getWeapon(listTypeWeapon.get(0), listNbrAmmo.get(0), false,context).getName());
		return hero;
	}
	
	public void setParamHero(int pType, int pLife){
		typeHero = pType;
		lifeHero = pLife;
	}

	//TODO mettre en place le temps pour le niveau
	public void setTime(int pTime){
		//time = pTime;
		//TODO mettre en place le temps
	}
	
	public void setParamEnemy(List<Integer> pListTime, List<Integer> pListType, List<Integer> pListTrack){
		listTime.addAll(pListTime);
		listType.addAll(pListType);
		Collections.sort(listTime);
		Collections.sort(listType);
		//listTrack = pListTrack;
		//TODO mettre en place les circuits
	}
	
	public boolean finish(ArrayList<SpaceShip> listEnemy){
		return (nbrEnemies == listType.size()) && (listEnemy.size() == 0);
	}
	
	public void addParamWeapon(int pTypeWeapon, int pNbrAmmo){
		listTypeWeapon.add(pTypeWeapon);
		listNbrAmmo.add(pNbrAmmo);
	}
}
