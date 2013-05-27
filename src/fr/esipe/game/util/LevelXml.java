 package fr.esipe.game.util;

import java.io.File;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import fr.esipe.game.escapeir.Level;
import fr.esipe.game.ship.Track;

public class LevelXml {
	public String mapName;
	public int time;
	public ArrayList<Integer> enemyType = new ArrayList<Integer>();
	public ArrayList<Track> enemyTrack = new ArrayList<Track>();
	public ArrayList<Integer> enemyTime = new ArrayList<Integer>();
	public int startX;

	public Track enemyMove;
	public int heroType;
	public int life;
	public int weaponTypeHero;
	public int amoWeaponHero;
	public String levelName;
	
	
	
	public String getMapName() {
		return mapName;
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void addEnemyStart(int pStartX) {
		enemyMove.startx(new Vec2(startX,0));
	}
	

	
	public ArrayList<Integer> getEnemyType() {
		return enemyType;
	}
	
	public void setEnemyType(ArrayList<Integer> enemyType) {
		this.enemyType = enemyType;
	}
	
	public void addEnemyType(String pEnemyType){
		int type = 1;
		if(pEnemyType.equals("spaceship1")){
			type = 1;
		}
		
		if(pEnemyType.equals("spaceship2")){
			type = 2;
		}
		
		if(pEnemyType.equals("spaceship3")){
			type = 3;
		}
		enemyMove = new Track();
		enemyTrack.add(enemyMove);
		enemyMove.setTrackLoop(0);
		enemyType.add(type);
	}
	
	public Track getEnemyMove() {
		return enemyMove;
	}
	
	public void setEnemyMove(ArrayList<Vec2> enemyMove) {
		if(enemyMove == null || enemyMove.size() == 0)
			enemyMove.add(new Vec2(startX,0));
		this.enemyMove.addAll(enemyMove);
		System.out.println(enemyMove.size());
	}
	
	public void addEnemyMove(Vec2 pEnemyMove){
		enemyMove.add(pEnemyMove);
	}
	
	
	
	public ArrayList<Track> getEnemyTrack() {
		return enemyTrack;
	}
	
	public void setEnemyTrack(ArrayList<Track> enemyTrack) {
		this.enemyTrack = enemyTrack;
	}
	
	public void addEnemyTrack(Track pEnemyTrack){
		enemyTrack.add(pEnemyTrack);
	}
	
	public ArrayList<Integer> getEnemyTime() {
		return enemyTime;
	}
	
	public void setEnemyTime(ArrayList<Integer> enemyTime) {
		this.enemyTime = enemyTime;
	}
	
	public void addEnemyTime(int pEnemyTime){
		enemyTime.add(pEnemyTime);
	}
	
	public int getHeroType() {
		return heroType;
	}
	
	public void setHeroType(int heroType) {
		this.heroType = heroType;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public int getWeaponTypeHero() {
		return weaponTypeHero;
	}
	
	public void setWeaponTypeHero(int weaponTypeHero) {
		this.weaponTypeHero = weaponTypeHero;
	}
	
	public int getAmoWeaponHero() {
		return amoWeaponHero;
	}
	
	public void setAmoWeaponHero(int amoWeaponHero) {
		this.amoWeaponHero = amoWeaponHero;
	}
	
	public String getLevelName() {
		return levelName;
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public Level xmlToLevel(Context context,String nameLevel){
		//int resID = context.getResources().getIdentifier(mapName , "drawable", context.getPackageName());
		Bitmap bitmap = BitmapFactory.decodeFile(context.getDir("level",Context.MODE_PRIVATE).getAbsolutePath()+File.separator+nameLevel+File.separator+"map.png");
		 Level level = new Level(levelName,bitmap,context);
		 level.setTime(time);	 
		 Log.d("init","taille enemyTime = "+enemyTime.size()+" enemyType = "+enemyType.size()+" enemyTrack = "+enemyTrack.size());
		 
		 level.setParamEnemy(enemyTime, enemyType, enemyTrack);
		 level.setParamHero(heroType, life);
		 level.addParamWeapon(weaponTypeHero, amoWeaponHero);
		 return level;
	}


	
	
}
