package application;

import java.util.ArrayList;

public class Deck {

//Data fields for Deck class
	ArrayList<Card> cards = new ArrayList<Card>(52);

	Deck() {
		reset();
	}

//Resets and adds cards to the cards ArrayList
	public void reset() {
		cards.clear();

		for (int j = 0; j < 4; j++) {

			for (int i = 0; i < Card.FACES.length; i++) {

				cards.add(new Card(Card.FACES[i]));

			}
		}

	}

//returns a card from the deck
	public Card dealCard() {

		int randPos = (int) (Math.random() * cards.size());

		Card rand = cards.get(randPos);
		cards.remove(randPos);
		//cards.trimToSize();

		return rand;

	}
}
