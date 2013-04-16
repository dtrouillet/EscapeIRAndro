package fr.esipe.game.ship;

import java.util.HashMap;

import fr.esipe.game.weapon.Weapon;

/**
 * Information allows to know in real time the score and life points of a body. It must be added to an object body
 * @author damien
 *
 */
public class Information {
	private int degats;
	private boolean destroy;
	private int life;
	private int score = 0;
	private final boolean scoreUp;
	private Bonus bonus;
	private HashMap<String,Weapon> listWeapon;
	public Information(int pDegats, int pPoint, boolean pScoreUp){
		degats = pDegats;
		life = pPoint;
		destroy = false;
		scoreUp = pScoreUp;
	}
	
	public int getDamages() {
		return degats;
	}
	
	public void setDegats(int degats) {
		this.degats = degats;
	}
	
	public boolean isDestroy() {
		return destroy;
	}
	
	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
	
	public int getPoint() {
		return life;
	}
	
	public void setPoint(int point) {
		life = point;
		if(point <= 0)
			destroy = true;
	}
	
	public boolean isScoreUp(){
		return scoreUp;
	}
	
	public void addScore(int pScore){
		score += pScore;
	}
	
	public int getScore(){
		return score;
	}

	public void getBonus(HashMap<String,Weapon> listWeapon){
		bonus.getAmmo(listWeapon);
	}


	
	public void setWeapon(HashMap<String,Weapon> pListWeapon){
		listWeapon = pListWeapon;
	}

	public HashMap<String, Weapon> getWeapon() {
		return listWeapon;
	}
	
	public void setBonus(Bonus pBonus){
		bonus = pBonus;
	}

	public Bonus getBonus() {
		return bonus;
	}
}
