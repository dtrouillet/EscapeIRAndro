package fr.esipe.game.util;



/**
 * With this class you can easily import new level
 * @author damien
 *
 */
public class XmlLoaderOld {
//	private Document document;
//	private final Element racine;
//	private final File fichierDot;
	 
	/**
	 * load your file XML
	 * @param file is the name of your XML file
	 */
//	public XmlLoader(String file){
//		fichierDot = new File(file);
//		SAXBuilder sxb = new SAXBuilder();
//		try{
//			document = sxb.build(fichierDot);
//		}catch(Exception e){
//			System.out.println("erreur");
//		}
//		racine = document.getRootElement();
//	}
	   
	/**
	 * create all your level
	 * @return List of level
	 */
//   public List<Level> xmlToLevel()
//   {	  
//	   ArrayList<Level> levels = new ArrayList<Level>();
//
//	   List<?> levelList = racine.getChildren();
//	   Iterator<?> itLevel = levelList.iterator();
//		ArrayList<Integer> listTime;
//		ArrayList<Integer> listTrack;
//		ArrayList<Integer> listType;
//	   while(itLevel.hasNext())
//	   {   
//		   Element levelE = (Element)itLevel.next();
//		   
//		   String name = levelE.getAttributeValue("name");
//		   String map = levelE.getChildText("map");
//		   Level level = new Level(name,Utils.createImage(map));
//		   
//		   int time = Integer.parseInt(levelE.getChildText("time"));
//		   level.setTime(time);	   
//		
//		   
//		   List<?> listEnemies = levelE.getChild("enemies").getChildren();
//		   Iterator<?> itEnemies = listEnemies.iterator();
//		   while(itEnemies.hasNext())
//		   {
//			   Element enemy = (Element)itEnemies.next();
//			   int typeEnemy = Integer.parseInt(enemy.getAttributeValue("type"));
//			   int trackEnemy = Integer.parseInt(enemy.getAttributeValue("track"));
//			   int nbrEnemy = Integer.parseInt(enemy.getAttributeValue("nbr"));
//			   int timeToApparate = Integer.parseInt(enemy.getValue());
//			   
//			   listTime = new ArrayList<Integer>();
//			   listTrack = new ArrayList<Integer>();
//			   listType = new ArrayList<Integer>();
//				
//			   for(int i = 0; i < nbrEnemy; i++){
//				   listType.add(typeEnemy);
//				   listTrack.add(trackEnemy);
//				   listTime.add(timeToApparate);
//			   }
//			   level.setParamEnemy(listTime, listType, listTrack);
//		   }
//		   int typeHero = Integer.parseInt(levelE.getChild("hero").getAttributeValue("type"));
//		   int lifeHero = Integer.parseInt(levelE.getChild("hero").getAttributeValue("life"));
//		   level.setParamHero(typeHero, lifeHero);
//		   List<?> listWeapon = levelE.getChild("hero").getChildren();
//		   Iterator<?> itWeapon = listWeapon.iterator();
//		   while(itWeapon.hasNext())
//		   {
//			   Element weapon = (Element)itWeapon.next();
//			   int nbrAmmo = Integer.parseInt(weapon.getAttributeValue("ammo"));
//			   int typeWeapon = Integer.parseInt(weapon.getText());
//			   level.addParamWeapon(typeWeapon, nbrAmmo);
//		   }
//		  		
//		levels.add(level);
//	   	}
//		 return levels;
//	 }
   
}
