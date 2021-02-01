import java.util.ArrayList;
import java.util.List;

public class CardDeck {

	public String[] suits = { "Spades", "Hearts", "Clubs", "Diamonds" }; // 4 suits

	public String[] nominals = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };  // 13 cards of each suit

	public List<String> deckList = new ArrayList<String>(52);  // deck of cards, must be 4 * 13 = 52 cards

	public List<String> fillTheDeck() {
		for (String suit : suits) {
			for (String nominal : nominals) {
				deckList.add(nominal + " of " + suit);    // filling up the deck with 52 cards 
			}
		}
		return deckList;
	}
	
	public int defineValueOfCard(String card) {
		int value;
		if (card.contains("2"))
			value = 2;
		else if (card.contains("3"))
			value = 3;
		else if (card.contains("4"))
			value = 4;
		else if (card.contains("5"))
			value = 5;
		else if (card.contains("6"))
			value = 6;
		else if (card.contains("7"))
			value = 7;
		else if (card.contains("8"))
			value = 8;
		else if (card.contains("9"))
			value = 9;
		else if (card.contains("10"))
			value = 10;
		else if (card.contains("Jack"))
			value = 10;
		else if (card.contains("Queen"))
			value = 10;
		else if (card.contains("King"))
			value = 10;
		else
			value = 11;
		return value;
	}
}
