package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {

	
	
//Card data fields
	static String[] FACES = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "K", "Q" };
	static int HEIGHT = 130;

	String face = "";

	
	
	public Card(String face) {

		this.face = face;
		setImage(new Image(this.face + ".png"));
		setFitHeight(HEIGHT);
		setPreserveRatio(true);

	}
//Returns the face of card
	public String getFace() {
		return face;
	}
//Returns the value of the card
	public int valueOf() {

		int value = 0;
		try {

			value = Integer.parseInt(getFace());

		} catch (Exception e) {
			if (getFace().equals("A")) {
				value = 11;

			} else
				value = 10;

		}

		return value;
	}
}
