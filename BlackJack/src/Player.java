
import java.util.ArrayList;
import java.util.List;

public class Player {
	Dealer dealer = BlackJack.dealer;

	List<String> playersHand = new ArrayList<String>();
	List<String> handTwoOnSplit;
	String cardsOnHand = "";
	boolean firstChoiceInRound = true;

	int playersHandsValue = 0;
	int playersAce11to1 = 1;
	int handTwoValue;
	int handTwoAce11to1;
	float money = 1000f;
	float bet;

	CardDeck deck = BlackJack.deck;

	public void getInitials() {
		for (int i = 0; i < 2; i++) {
			int cardIndex = (int) (Math.random() * deck.deckList.size());
			playersHand.add(deck.deckList.get(cardIndex));
			deck.deckList.remove(cardIndex);
			playersHandsValue += deck.defineValueOfCard(playersHand.get(i));

		}
		String initialCardsText = "Your initials are: ";
		for (String card : playersHand) {
			initialCardsText += card + ", ";
		}
		System.out.println(initialCardsText.substring(0, initialCardsText.length() - 2));
		if (playersHandsValue == 22) { // if initials are two Aces
			playersAce11to1--;
			playersHandsValue -= 10; // one of the aces becomes 1 instead of 11
		} else if (playersHandsValue < 21)
			System.out.println("Total value of player's hand: " + playersHandsValue + "\n");
	}

	public void makeChoice() {
		String choiceText = "Make you decision please, type H to Hit, S to Stand";
		boolean sameNominals = deck.defineValueOfCard(playersHand.get(0)) == deck.defineValueOfCard(playersHand.get(1));

		if (playersHand.size() == 2 && sameNominals && firstChoiceInRound) { // possible only on initial cards
			choiceText += ", D for double-down, SP for split";
			System.out.println(choiceText);
			while (true) {
				String decision = BlackJack.sc.nextLine().trim().toLowerCase();
				if (decision.equals("h")) {
					hit();
					break;
				} else if (decision.equals("s")) {
					break;
				} else if (decision.equals("d")) {
					doubleDown();
					break;
				} else if (decision.equals("sp")) {
					split();
					break;
				}
			}
		} else if (playersHand.size() == 2 && firstChoiceInRound) { // possible only on iniial cards
			choiceText += ", D for double-down";
			System.out.println(choiceText);
			while (true) {
				String decision = BlackJack.sc.nextLine().trim().toLowerCase();
				if (decision.equals("h")) {
					hit();
					break;
				} else if (decision.equals("s")) {
					break;
				} else if (decision.equals("d")) {
					doubleDown();
					break;
				}
			}
		} else {
			System.out.println(choiceText);
			while (true) {
				String decision = BlackJack.sc.nextLine().trim().toLowerCase();
				if (decision.equals("h")) {
					hit();
					break;
				} else if (decision.equals("s")) {
					break;
				}
			}
		}
	}

	public void hit() {
		takeCard();
		if (playersHandsValue < 21) {
			makeChoice();
		}
	}

	public void doubleDown() {
		money -= bet;
		takeCard();
		if (playersHandsValue <= 21) {
			if (playersHandsValue > dealer.dealersHandsValue || dealer.dealersHandsValue > 21) {
				money += bet;
			}
		}
	}

	public void takeCard() {

		int cardIndex = (int) (Math.random() * deck.deckList.size());
		playersHand.add(deck.deckList.get(cardIndex));
		deck.deckList.remove(cardIndex);
		playersHandsValue += deck.defineValueOfCard(playersHand.get(playersHand.size() - 1));

		String cardsOnHand = "You took " + playersHand.get(playersHand.size() - 1) + ". \nCards on your hand are: ";
		for (String card : playersHand) {
			cardsOnHand += card + ", ";
		}
		System.out.println(cardsOnHand.substring(0, cardsOnHand.length() - 2) + "\n");

		if (playersHandsValue > 21) {
			if (cardsOnHand.contains("Ace") && playersAce11to1 == 1) { // if there is an Ace that is 11, make it 1
				playersAce11to1--;
				playersHandsValue -= 10;
				System.out.println("Total value of your hand now is: " + playersHandsValue);
			}
		} else if (playersHandsValue == 21) {
			System.out.println("You got to 21, no more cards for you");
		} else if (playersHandsValue < 21) {
			System.out.println("Total value of your hand now is: " + playersHandsValue);
		}
	}

	public void split() {
		
		money -= bet;
		firstChoiceInRound = false;
		handTwoAce11to1 = 1; // giving hand two chance to turn one ace from 11 to 1

		handTwoOnSplit = new ArrayList<String>(); // initialising hand 2
		handTwoOnSplit.add(playersHand.get(1)); // moving second card from initial hand to hand 2
		playersHand.remove(1);

		playersHandsValue = deck.defineValueOfCard(playersHand.get(0)); // value of first card on hand 1
		handTwoValue = deck.defineValueOfCard(handTwoOnSplit.get(0)); // value of first card on hand 2

		System.out.print("For hand one: ");
		takeCard(); // adding card to hand 1
		if (playersHandsValue != 21)
			makeChoice(); // working on hand 1
		if (playersHandsValue > 21)
			System.out.println("Result is " + playersHandsValue + ", too many for hand one");

		System.out.print("\nFor hand two: ");
		int cardIndex = (int) (Math.random() * deck.deckList.size());
		handTwoOnSplit.add(deck.deckList.get(cardIndex));
		deck.deckList.remove(cardIndex);
		handTwoValue += deck.defineValueOfCard(handTwoOnSplit.get(handTwoOnSplit.size() - 1)); // adding card to hand 2
		
		String cardsOnHand = "You took " + handTwoOnSplit.get(handTwoOnSplit.size() - 1) + ". \nCards on your hand are: ";
		cardsOnHand = "";
		for (String card : handTwoOnSplit) {
			cardsOnHand += card + ", ";
		}
		System.out.println(cardsOnHand.substring(0, cardsOnHand.length() - 2) + "\n");
		
		if (handTwoValue < 21) {
			System.out.println("Total value of hand two is " + handTwoValue);
			makeChoiceHandTwo(); // working on hand 2
		}
		// System.out.println("Total value of hand two is " + handTwoValue);
		if (handTwoValue > 21)
			System.out.println("Too many for hand two");
	}

	public void makeChoiceHandTwo() {
		System.out.println("Make you decision please, type H to Hit, S to Stand");
		while (true) {
			String decision = BlackJack.sc.nextLine().trim().toLowerCase();
			if (decision.equals("h")) {
				takeCardHandTwo();
				break;
			} else if (decision.equals("s")) {
				break;
			}
		}
	}
	
	public void takeCardHandTwo() {
		int cardIndex = (int) (Math.random() * deck.deckList.size());
		handTwoOnSplit.add(deck.deckList.get(cardIndex));
		deck.deckList.remove(cardIndex);
		handTwoValue += deck.defineValueOfCard(handTwoOnSplit.get(handTwoOnSplit.size() - 1));

		String cardsOnHand = "You took " + handTwoOnSplit.get(handTwoOnSplit.size() - 1) + ". \nCards on your hand are: ";
		for (String card : handTwoOnSplit) {
			cardsOnHand += card + ", ";
		}
		System.out.println(cardsOnHand.substring(0, cardsOnHand.length() - 2) + "\n");

		if (handTwoValue > 21 && cardsOnHand.contains("Ace") && handTwoAce11to1 == 1) {
			 // if there is an Ace that is 11, make it 1
				handTwoAce11to1--;
				handTwoValue -= 10;
				// System.out.println("Total value of your hand now is: " + handTwoValue);
			
		}
		if (handTwoValue == 21) {
			System.out.println("You got to 21, no more cards for you");
		} 
		if (handTwoValue < 21) {
			System.out.println("Total value of your hand now is: " + handTwoValue);
			makeChoiceHandTwo();
		}
	}
	
	public void placeBet() {
		System.out.println("Total money: " + money +". Please place your initial bet");
		bet = BlackJack.sc.nextFloat();
		while (true) {
			if (bet <= money / 2) {
				money -= bet;
				System.out.println("Money left: " + money);
				break;
			} else {
				System.out.println("You can't place a bet, that is greater than half of your bank! Try again");
				bet = BlackJack.sc.nextFloat();
			}
		}
	}
}
