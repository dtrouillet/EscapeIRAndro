//package fr.esipe.game.escapeir;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.World;
//
//import android.app.Application;
//import android.gesture.Gesture;
//import android.graphics.Color;
//import android.renderscript.Font;
//import android.view.MotionEvent;
//import fr.esipe.game.ship.BodyContactListener;
//import fr.esipe.game.ship.Bonus;
//import fr.esipe.game.ship.Information;
//import fr.esipe.game.ship.SpaceShip;
//import fr.esipe.game.util.Constant;
//import fr.esipe.game.util.Utils;
//import fr.esipe.game.util.XmlLoader;
//import fr.esipe.game.weapon.Ammo;
//import fr.esipe.game.weapon.Weapon;
//
//
//
///**
// * This class handles all the game display
// * @author damien
// *
// */
//public class Display {
//	public static void start(){
//		final XmlLoader xml = new XmlLoader("ressources/xml/level/earth.xml");
//		
//		org.jbox2d.common.Settings.maxTranslation = 5.0f;	
//		final Random rand = new Random();
//
//		//APPLICATION
//		Application.run("EscapeIR DAMIEN-BENJAMIN", Constant.WIDTH, Constant.HEIGHT, new ApplicationCode() {
//			int mapPosition;
//			ArrayList<SpaceShip> listEnemies = new ArrayList<SpaceShip>();
//			List<Vec2> lineGesture;
//			//LANCEMENT APPLICATION
//			@Override
//			public void run(ApplicationContext context) {
//				 Frame.getFrames()[0].setLocationRelativeTo(null);
//
//				//AFFICHAGE DU GAME OVER
//				context.render(new ApplicationRenderCode() {  
//			          @Override
//			          public void render(Graphics2D graphics) {
//			            graphics.setColor(Color.BLACK);
//			            graphics.fill(new Rectangle(0, 0, Constant.WIDTH, Constant.HEIGHT));
//			            graphics.setColor(Color.WHITE);
//			        	graphics.drawImage(Utils.createImage("level.png").getSubimage(0, 0, 120, 119), 500, 100, 120, 119,null);
//						graphics.setFont(new Font("Arial", 1, 20));
//			        	graphics.drawString("EARTH", 530, 240);
//			        	graphics.drawImage(Utils.createImage("level.png").getSubimage(120, 0, 120, 119), 100, 100, 120, 119,null);
//			        	graphics.drawString("JUPITER", 120, 240);
//			        	graphics.drawImage(Utils.createImage("level.png").getSubimage(245, 0, 120, 119), 300, 100, 120, 119,null);
//			        	graphics.drawString("MOON", 335, 240);
//			        	
//						graphics.setFont(new Font("Arial", 1, 100));
//			        	graphics.drawString("ESCAPE IR", Constant.WIDTH/2-270, Constant.HEIGHT/2);
//						graphics.setFont(new Font("Arial", 1, 30));
//						graphics.drawString("Select your level to start", Constant.WIDTH/2-200, Constant.HEIGHT/2+100);
//			          }
//			        });
//				int levelChoice = 0;
//
//				while(levelChoice == 0){
//					MotionEvent motionEvent = context.waitMotion();
//					if(motionEvent.getKind() == Kind.ACTION_UP){
//						int mouseX = motionEvent.getX();
//						int mouseY = motionEvent.getY();
//						//TODO mise en place des level
//						if(mouseX >= 100 && mouseX <= 220 && mouseY >= 100 && mouseY <= 219){
//							levelChoice = 1;
//						}else if(mouseX >= 300 && mouseX <= 420 && mouseY >= 100 && mouseY <= 219){
//							levelChoice = 2;
//						}else if(mouseX >= 500 && mouseX <= 620 && mouseY >= 100 && mouseY <= 219){
//							levelChoice = 3;
//						}
//					}
//				}
//				
//				final Level level = xml.xmlToLevel().get(levelChoice-1);
//				final World world = level.getWorld();
//				final SpaceShip hero = level.getHero();
//				world.setContactListener(new BodyContactListener());
//				
//				final Gesture gesture = new Gesture(context);
//				int start = (int) System.currentTimeMillis();
//				for(;;){
//				    world.step(1/60f, 10, 10);
//
//					//long debut = System.currentTimeMillis();
//					
//					
//					hero.cleanAmmo();
//					int current = (int) System.currentTimeMillis();
//					level.addEnemyInList(listEnemies, (current-start)/1000);
//					level.updatePos(listEnemies);
//
//					lineGesture = gesture.getPositionGesture(hero);
//					
//					try {
//			            Thread.sleep(10);
//					} catch (InterruptedException e) {
//						Thread.currentThread().interrupt();
//					}
//					
//					context.render(new ApplicationRenderCode() {
//						
//						//GRAPHIQUE
//						@Override
//						public void render(Graphics2D graphics) {
//							//AFFICHAGE MAP
//				            graphics.fill(new Rectangle(0, 0, Constant.WIDTH, Constant.HEIGHT));
//
//							graphics.drawImage(level.getImage(), 0,mapPosition-2000,Constant.WIDTH,2000, null);
//							graphics.drawImage(level.getImage(), 0,mapPosition,Constant.WIDTH,2000, null);
//							
//							//AFFICHAGE HERO
//							graphics.drawImage(hero.getImage(), (int)hero.getPosition().x,Constant.HEIGHT - (int)hero.getPosition().y,80,80, null);
//							
//
//							
//							//AFFICHAGE ENNEMIS
//							List<SpaceShip> removeEnemy = new ArrayList<SpaceShip>();
//							for(Ammo myAmmo : hero.getWeaponCurrent().getAllAmmo()){
//								graphics.drawImage(hero.getWeaponCurrent().getImage(), (int)myAmmo.getBody().getPosition().x,Constant.HEIGHT-(int)myAmmo.getBody().getPosition().y, 25,25,null);
//							}
//							
//							for(SpaceShip enemy : listEnemies){
//								List<Ammo> removeAmmo = new ArrayList<Ammo>();
//								Weapon weapon = enemy.getWeaponCurrent();
//
//								if(!enemy.getInformation().isDestroy() && enemy.getBody().getPosition().y > 0 && enemy.getBody().getPosition().x > -80 && enemy.getBody().getPosition().x < 700){
//								graphics.drawImage(enemy.getImage(), (int)enemy.getPosition().x,Constant.HEIGHT - (int)enemy.getPosition().y,80,80, null);
//								
//
//								for(Ammo myAmmo : weapon.getAllAmmo()){
//									Information infoAmmo = (Information)myAmmo.getBody().getUserData();
//									if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y < 0 || myAmmo.getBody().getPosition().x < 0 || myAmmo.getBody().getPosition().x > 700  ){
//										removeAmmo.add(myAmmo);
//									}else{
//										graphics.drawImage(weapon.getImage(), (int)myAmmo.getBody().getPosition().x,Constant.HEIGHT-(int)myAmmo.getBody().getPosition().y, 25,25,null);
//									}
//								}						
//								
//								if(rand.nextInt(20) == 1){
//									enemy.shoot(new Vec2(0, -200));
//								}							
//								
//
//								}else{
//									for(Ammo myAmmo : weapon.getAllAmmo()){
//										Information infoAmmo = (Information)myAmmo.getBody().getUserData();
//										if(infoAmmo.isDestroy() || myAmmo.getBody().getPosition().y < 0 || myAmmo.getBody().getPosition().x < -20 || myAmmo.getBody().getPosition().x > 700  ){
//											removeAmmo.add(myAmmo);
//										}else{
//											graphics.drawImage(weapon.getImage(), (int)myAmmo.getBody().getPosition().x,Constant.HEIGHT-(int)myAmmo.getBody().getPosition().y, (int)weapon.getDimension().x,(int)weapon.getDimension().y,null);
//									
//										}
//									}
//									if(enemy.getBonus() != null){
//										if(enemy.getBonus().isDestroy() == true){
//											world.destroyBody(enemy.getBonus().getBody());
//										}else{
//											Bonus bonus = enemy.getBonus();
//											bonus.setNewPosition(enemy.getPosition());
//											bonus.getBody().setLinearVelocity(new Vec2(0,-800));
//											
//											graphics.drawImage(bonus.getImage(), (int)bonus.getBody().getPosition().x,Constant.HEIGHT-(int)bonus.getBody().getPosition().y, 25,25,null);
//									
//										}
//									}
//									removeEnemy.add(enemy);
//								}
//								//suppression des munitions inutiles
//								for(Ammo myAmmo : removeAmmo)
//									weapon.removeAmmo(myAmmo);
//							}
//
//							//Suppression des enemis inutiles
//							for(SpaceShip ennemis : removeEnemy){
//								world.destroyBody(ennemis.getBody());
// 								if(ennemis.getWeaponCurrent().getAllAmmo().size() == 0 && (ennemis.getBonus() == null || ennemis.getBonus().isDestroy())){
//									listEnemies.remove(ennemis);
//								}
//							}
//							
//							
//							//System.out.println(listEnemies.size());
//
//							//world.step(1/60, 10, 10);
//
//							//AFFICHAGE GESTURE LINE
//							if(gesture.setNextPosition(hero)){
//								graphics.setColor(Color.BLACK);
//								graphics.setFont(new Font("Arial", 1, 50));
//							//	graphics.drawString(gesture.getKindGesture().toString(), 10, 50);
//								graphics.setColor(Color.GREEN);
//							}
//							
//							if(lineGesture != null){
//								Vec2 currentVector;
//								Vec2 previousVector;
//								for(int i = 1; i < lineGesture.size(); i++){
//									currentVector = lineGesture.get(i);
//									previousVector = lineGesture.get(i-1);
//									graphics.drawLine((int)previousVector.x, (int)previousVector.y, (int)currentVector.x, (int)currentVector.y);
//								}
//							}
//							
//							//AFFICHAGE CHOIX ARME
//							graphics.drawImage(Utils.createImage("weaponMissile.png").getSubimage(80, 0, 80, 80), 10, 10, 80, 80, null);
//							graphics.drawImage(Utils.createImage("lifeLow.png"), 13, 80, hero.getWeaponCurrent().getNbrAmmo()/3, 7, null);//max 74*3
//							
//							graphics.drawImage(Utils.createImage("weaponFireball.png").getSubimage(0, 0, 80, 80), 110, 10, 80, 80, null);
//							graphics.drawImage(Utils.createImage("weaponShibooleet.png").getSubimage(0, 0, 80, 80), 210, 10, 80, 80, null);
//							graphics.drawImage(Utils.createImage("weaponMissile.png").getSubimage(0, 0, 80, 80), 310, 10, 80, 80, null);
//							
//							//Affichage LIFE AND SCORE
//							graphics.drawImage(Utils.createImage("underLife.png"), 410, 10, 270, 35, null);
//							
//							int life = hero.getLife();
//							if(life > 100)
//								graphics.drawImage(Utils.createImage("life.png"), 413, 13, (int)(100*2.64), 29, null);
//							else if(life > 40)
//								graphics.drawImage(Utils.createImage("life.png"), 413, 13, (int)(life*2.64), 29, null);
//							else if(life > 20)
//								graphics.drawImage(Utils.createImage("lifeMiddle.png"), 413, 13, (int)(life*2.64), 29, null);
//							else if(life > 0)
//								graphics.drawImage(Utils.createImage("lifeLow.png"), 413, 13, (int)(life*2.64), 29, null);
//							else
//								graphics.drawImage(Utils.createImage("lifeLow.png"), 413, 13, 0, 29, null);
//
//							graphics.drawImage(Utils.createImage("underLife.png"), 410, 55, 270, 35, null);
//							
//							int score = hero.getScore();
//							if(score < 265)
//								graphics.drawImage(Utils.createImage("score.png"), 413, 58, score/10, 29, null);
//							else
//								graphics.drawImage(Utils.createImage("score.png"), 413, 58, 265, 29, null);
//
//							graphics.setColor(Color.BLACK);
//							graphics.setFont(new Font("Arial", 1, 20));
//							if(hero.getLife() > 0)
//								graphics.drawString(life+"", 420, 35);
//							else
//								graphics.drawString("0", 420, 35);
//
//							graphics.drawString(score+"", 420, 80);
//
//						}
//					});
//					mapPosition += 1;
//					if(mapPosition > 2000)
//						mapPosition = 0;
//					//ON SORT DE LA BOUCLE DU JEU SI PV = 0
//					if(hero.getLife() <= 0){
//						break;
//					}
//					
//
//					//System.out.println("temps : "+(System.currentTimeMillis() - debut));
//				}
//				
//				//AFFICHAGE DU GAME OVER
//				context.render(new ApplicationRenderCode() {  
//			          @Override
//			          public void render(Graphics2D graphics) {
//			            graphics.setColor(Color.BLACK);
//
//						graphics.setFont(new Font("Arial", 1, 100));
//						graphics.drawString("GAME OVER", Constant.WIDTH/2-300, Constant.HEIGHT/2);
//			          }
//			        });
//			}
//		});
//	}
//}
