package fr.esipe.game.util;

/**
 * This class contains all the constants of the game
 * @author damien
 *
 */
public class Constant {
	public static int WIDTH = 700;
	public static int HEIGHT = 800;
	public static final byte CATEGORY_PLAYER = 0x0001; 		// 0000000000000001 in binary
	public static final byte CATEGORY_ENEMY = 0x0002; 		// 0000000000000010 in binary
	public static final byte CATEGORY_AMMO_ENEMY = 0x0004; 	// 0000000000000100 in binary
	public static final byte CATEGORY_AMMO_HERO = 0x0008; 	// 0000000000001000 in binary
	public static final byte CATEGORY_WALL = 0x0016;
	public static final byte MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_AMMO_ENEMY | CATEGORY_WALL; 
	public static final byte MASK_ENEMY = CATEGORY_PLAYER | CATEGORY_AMMO_HERO; 
	public static final byte MASK_AMMO_ENEMY = CATEGORY_PLAYER; 
	public static final byte MASK_AMMO_HERO = CATEGORY_ENEMY; 
	public static final byte MASK_WALL = CATEGORY_PLAYER;
}
