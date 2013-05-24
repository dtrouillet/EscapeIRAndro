package fr.esipe.game.util;

import android.graphics.drawable.Drawable;

public class MapLvl {

	public MapLvl(Drawable image2) {
		super();
		this.image = image2;
	}

	private Drawable image;

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

}
