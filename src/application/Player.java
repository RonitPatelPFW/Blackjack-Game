package application;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	
//Data fields for the player class
	ArrayList<Card> hand = new ArrayList<Card>(12);
	int value = 0;

	int i = 0;

	static Deck deck = new Deck();

	Player() {
		hit();
	}

//Returns the hand ArrayList
	public ArrayList<Card> getHand() {

		return hand;

	}

//Returns the value of hand
	public int valueOfHand() {

		value += hand.get(i).valueOf();
		i++;

		return value;
	}
//Clears the hand ArrayList
	public void clearHand() {
		hand.clear();
	}

//Method for the dealer whether to stand or not
	public boolean stand(int otherPlayerValue) {
		Random r = new Random();
		boolean stand = true;

		if (value < otherPlayerValue) {
			stand = true;
		} else if (value <= 16) {
			float chance = r.nextFloat();
			if (chance == 0.50f) {

				stand = true;
			}

		}
		if (value <= 19) {
			stand = true;
		} else
			stand = false;

		return stand;

	}

//Adds a card from the deck to the hand
	public void hit() {

		for (int i = 0; i < 12; i++) {
			hand.add(deck.dealCard());
		}

	}
//Checks whether the user or non-user player has busted or not
	public boolean bust() {

		boolean bust = true;
		if (value > 21) {
			bust = true;
		}

		else
			bust = false;

		return bust;
	}
}
