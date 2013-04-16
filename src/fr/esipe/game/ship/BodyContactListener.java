package fr.esipe.game.ship;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;


/**
 * The BodyContactListener is called every time a contact is detected in the world. It must be applied to a world.
 * @author damien
 *
 */
public class BodyContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Information infoA = (Information) contact.getFixtureA().getBody().getUserData();
		Information infoB = (Information) contact.getFixtureB().getBody().getUserData();
		if(infoA == null || infoB == null)
			return;
		
		int damageA = infoA.getDamages();
		int damageB = infoB.getDamages();
		infoA.setPoint(infoA.getPoint() - damageB);
		infoB.setPoint(infoB.getPoint() - damageA);
		if(infoB.isScoreUp()){
			infoA.addScore(damageB);
			contact.getFixtureA().getBody().setAwake(false);
		}if(infoA.isScoreUp()){
			infoB.addScore(damageA);
			contact.getFixtureB().getBody().setAwake(false);
		}
		if(infoA.getBonus() != null){
			infoA.getBonus(infoB.getWeapon());
			infoA.getBonus().setDestroy(true);
		}else if(infoB.getBonus() != null){
			infoB.getBonus(infoA.getWeapon());
			infoB.getBonus().setDestroy(true);
		}
	}

	@Override
	public void endContact(Contact arg0) {
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
	}

}