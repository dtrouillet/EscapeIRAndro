package fr.esipe.game.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;
import fr.esipe.game.escapeir.R;

public class XmlLoader {	

	public XmlLoader() {

	}

	public static ArrayList<LevelXml> getFeeds(Context context){
		// On passe par une classe factory pour obtenir une instance de sax
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parseur = null;
		ArrayList<LevelXml> entries = null;
		try {
			// On "fabrique" une instance de SAXParser
			parseur = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}


		/*
		 * Le handler sera gestionnaire du fichier XML c'est à dire que c'est lui qui sera chargé
		 * des opérations de parsing. On vera cette classe en détails ci après.
		*/
		DefaultHandler handler = new ParserXmlHandler();
		try {
			
			// On parse le fichier XML
			InputStream input = context.getResources().openRawResource(R.raw.earth);
			if(input==null)
				Log.e("erreur android","null");
			else{
				parseur.parse(input, handler);
				// On récupère directement la liste des feeds
				entries = ((ParserXmlHandler) handler).getData();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On la retourne l'array list
		return entries;
	}

}