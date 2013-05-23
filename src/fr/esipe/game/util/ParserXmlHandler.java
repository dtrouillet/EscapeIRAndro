package fr.esipe.game.util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ParserXmlHandler extends DefaultHandler {

	private static final String LEVEL = "level";
	private static final String LEVEL_NAME = "name";
	
	private static final String MAP = "map";
	private static final String TIME = "time";
	
	private static final String ENEMY = "enemy";
	private static final String ENEMY_TYPE = "type";
	private static final String ENEMY_NBR = "nbr";
	private static final String ENEMY_TRACK = "track";
	
	private static final String HERO = "hero";
	private static final String HERO_TYPE = "type";
	private static final String HERO_LIFE = "life";

	private static final String WEAPON = "weapon";
	private static final String WEAPON_AMMO = "ammo";
	
	private int typeEnemy;
	private int nbrEnemy;
	private int trackEnemy;
	private int ammoWeapon;

	// Array list de feeds
	private ArrayList<LevelXml> entries;

	// Boolean permettant de savoir si nous sommes �� l'int��rieur d'un item
	private boolean inItem;

	// Feed courant
	private LevelXml levelXml;

	// Buffer permettant de contenir les donn��es d'un tag XML
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXmlHandler() {
		super();
	}

	// * Cette m��thode est appel��e par le parser une et une seule
	// * fois au d��marrage de l'analyse de votre flux xml.
	// * Elle est appel��e avant toutes les autres m��thodes de l'interface,
	// * �� l'exception unique, ��videmment, de la m��thode setDocumentLocator.
	// * Cet ��v��nement devrait vous permettre d'initialiser tout ce qui doit
	// * l'��tre avant led��but du parcours du document.

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		entries = new ArrayList<LevelXml>();

	}

	/*
	 * Fonction ��tant d��clench��e lorsque le parser trouve un tag XML
	 * C'est cette m��thode que nous allons utiliser pour instancier un nouveau feed
 	*/
	@Override
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		// Nous r��initialisons le buffer a chaque fois qu'il rencontre un item
		buffer = new StringBuffer();		
		for(int i = 0; i < attributes.getLength(); i++){
			Log.d("attribut", attributes.getValue(i));
		}
		// Ci dessous, localName contient le nom du tag rencontr��

		// Nous avons rencontr�� un tag ITEM, il faut donc instancier un nouveau feed
		if (localName.equalsIgnoreCase(LEVEL)){
			this.levelXml = new LevelXml();
			for(int i = 0; i < attributes.getLength(); i++){
				if(attributes.getQName(i).equalsIgnoreCase(LEVEL_NAME)){
					levelXml.setLevelName(attributes.getValue(i));
				}
			}
			inItem = true;
		}
		if (localName.equalsIgnoreCase(MAP)){
			// Nothing to do
		}
		if (localName.equalsIgnoreCase(TIME)){
			// Nothing to do
		}
		if (localName.equalsIgnoreCase(ENEMY)){
			for(int i = 0; i < attributes.getLength(); i++){
				if(attributes.getQName(i).equalsIgnoreCase(ENEMY_TYPE)){
					typeEnemy = Integer.parseInt(attributes.getValue(i));
				}
				if(attributes.getQName(i).equalsIgnoreCase(ENEMY_TRACK)){
					trackEnemy = Integer.parseInt(attributes.getValue(i));
				}
				if(attributes.getQName(i).equalsIgnoreCase(ENEMY_NBR)){
					nbrEnemy = Integer.parseInt(attributes.getValue(i));
				}
			}
		}
		if (localName.equalsIgnoreCase(HERO)){
			for(int i = 0; i < attributes.getLength(); i++){
				if(attributes.getQName(i).equalsIgnoreCase(HERO_LIFE)){
					levelXml.setLife(Integer.parseInt(attributes.getValue(i)));
				}
				if(attributes.getQName(i).equalsIgnoreCase(HERO_TYPE)){
					levelXml.setHeroType(Integer.parseInt(attributes.getValue(i)));
				}
			}
		}
		if(localName.equalsIgnoreCase(WEAPON)){
			for(int i = 0; i < attributes.getLength(); i++){
				if(attributes.getQName(i).equalsIgnoreCase(WEAPON_AMMO)){
					ammoWeapon = Integer.parseInt(attributes.getValue(i));
				}
			}
		}
		
	}

	// * Fonction ��tant d��clench��e lorsque le parser �� pars��
	// * l'int��rieur de la balise XML La m��thode characters
	// * a donc fait son ouvrage et tous les caract��re inclus
	// * dans la balise en cours sont copi��s dans le buffer
	// * On peut donc tranquillement les r��cup��rer pour compl��ter
	// * notre objet currentFeed

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {		

		if(buffer != null)
			Log.d("XML", "element trouvé "+buffer.toString()+" le local name est "+localName+" name = "+name);
		
		
		if (localName.equalsIgnoreCase(MAP)){
			if(inItem){
				this.levelXml.setMapName(buffer.toString());
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(TIME)){
			if(inItem){
				this.levelXml.setTime(Integer.parseInt(buffer.toString()));
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(ENEMY)){
			if(inItem){		
				for(int i = 0; i < nbrEnemy; i++){
					this.levelXml.addEnemyTime(Integer.parseInt(buffer.toString()));
					this.levelXml.addEnemyTrack(trackEnemy);
					this.levelXml.addEnemyType(typeEnemy);
				}
				
				trackEnemy = 0;
				typeEnemy = 0;
				nbrEnemy = 0;
				buffer = null;
			}
		}
		
		if (localName.equalsIgnoreCase(WEAPON)){
			if(inItem){	
				levelXml.setAmoWeaponHero(ammoWeapon);
				levelXml.setWeaponTypeHero(Integer.parseInt(buffer.toString()));
				buffer = null;
			}
		}
		
		if(localName.equalsIgnoreCase(LEVEL)){
			entries.add(levelXml);
		}
	}

	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * int��grante d'un tag, d��clenche la lev��e de cet ��v��nement.
	// * En g��n��ral, cet ��v��nement est donc lev�� tout simplement
	// * par la pr��sence de texte entre la balise d'ouverture et
	// * la balise de fermeture

	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
		if(buffer != null) buffer.append(lecture);
	}

	// cette m��thode nous permettra de r��cup��rer les donn��es
	public ArrayList<LevelXml> getData(){
		return entries;
	}
}