package com.authorwjf.deal;

import java.util.ArrayList;
import java.util.List;

public class Cards {
	
	private List<String> cards;
	private List<String> previousCards = new ArrayList<String>();

	
	public static int deckSize = 52;
	public static int suitSize = 14;
	
	public static boolean isBackOfCardShowing = true;
	public static boolean secondPack = false;
	
	public List<String> cardsToArray(){
		
		cards = new ArrayList<String>();
		
		for(int i = 1; i <= suitSize; i++) {
				cards.add("c"+i);
				cards.add("d"+i);
				cards.add("h"+i);
				cards.add("s"+i);
		}
		return cards;
	}
	

	public List<String> getCards() {
		return cards;
	}

	public void setCards(List<String> cards) {
		this.cards = cards;
	}

	public List<String> getPreviousCards() {
		return previousCards;
	}
	public int getPreviousCardsSize() {
		return previousCards.size();
	}

	public void setPreviousCards(List<String> previousCards) {
		this.previousCards = previousCards;
	}


	public static void setDeckSize(int deckSize) {
		Cards.deckSize = deckSize;
	}


	public static void setSuitSize(int suitSize) {
		Cards.suitSize = suitSize;
	}

}
