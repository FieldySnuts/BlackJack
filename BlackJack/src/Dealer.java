import java.util.ArrayList;
import java.util.List;

public class Dealer {

	List<String> dealersHand = new ArrayList<String>();

	int dealersHandsValue = 0;

	int dealersAce11to1 = 1;
	String cardsOnHand = "";
	CardDeck deck = BlackJack.deck;

	public void getAllCards() { // filling dealers hand right from the start

		while (dealersHandsValue <= 16) { // dealers draws until he has 17 or more
			int cardIndex = (int) (Math.random() * deck.deckList.size());
			dealersHand.add(deck.deckList.get(cardIndex));
			deck.deckList.remove(cardIndex);
			dealersHandsValue += deck.defineValueOfCard(dealersHand.get(dealersHand.size() - 1));
		}
		boolean dealersBJ = (dealersHandsValue == 21 && dealersHand.size() == 2);
		if (!dealersBJ) {
		System.out.println("Dealer's open cards is: " + dealersHand.get(0) + //
				", value = " + deck.defineValueOfCard(dealersHand.get(0)) + "\n");
		} 

		for (String card : dealersHand) {
			cardsOnHand += card + ", ";
		}
		cardsOnHand = cardsOnHand.substring(0, cardsOnHand.length() - 2) + "\n";
	}

}
