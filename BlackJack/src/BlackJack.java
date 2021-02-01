
import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack {

	static Scanner sc = new Scanner(System.in);
	static CardDeck deck = new CardDeck();
	static Dealer dealer = new Dealer();
	static Player player = new Player();
	String finalLine = "";

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.playGame();
	}

	public void playGame() {
		deck.fillTheDeck();
		player.placeBet();
		player.getInitials();
		dealer.getAllCards();

		if (player.playersHandsValue == 21) { // if 21 is on initials = blackjack
			finalLine += "You got BlackJack!\n";
			if (dealer.dealersHandsValue == 21 && dealer.dealersHand.size() == 2) { // checking BJ for dealer
				finalLine += "Dealer also has BlackJack, round ends with push ";
				player.money += player.bet;
			} else { // if no BJ for dealer and BJ for player
				finalLine += "Dealer has no BlackJack, you won with BJ! ";
				player.money += 2.5f * player.bet;
			}
		} else { // if there is no BJ for player

			if (dealer.dealersHandsValue == 21 && dealer.dealersHand.size() == 2) {
				finalLine += "Dealer has BlackJack, dealer wins";
			} else {
				player.makeChoice();
				if (player.handTwoOnSplit != null) {
					splitWasChosen();
				} else {
					if (player.playersHandsValue <= 21) {
						finalLine += "Dealers cards are " + dealer.cardsOnHand;
						finalLine += "Dealer's result is " + dealer.dealersHandsValue + "\n";
					}
					if (player.playersHandsValue > 21) {
						finalLine += "Your result is " + player.playersHandsValue + ". Too many! You lose!";
					} else if (dealer.dealersHandsValue > 21) {
						player.money += 2 * player.bet;
						finalLine += "Dealer busts, you win!";
					} else if (player.playersHandsValue == dealer.dealersHandsValue) {
						player.money += player.bet;
						finalLine += "Round ends with push";
					} else if (player.playersHandsValue > dealer.dealersHandsValue) {
						player.money += 2 * player.bet;
						finalLine += "Your result is higher, You win!";
					} else if (player.playersHandsValue < dealer.dealersHandsValue) {
						finalLine += "Dealer gets higher result, You lose!";
					}

				}
			}
		}
		System.out.println(finalLine);
		playAgain();
	}

	public void splitWasChosen() {
		if (player.playersHandsValue > 21 && player.handTwoValue > 21) {
			finalLine += "Both hands bust, you lose";
		} else if (player.playersHandsValue <= 21 && player.handTwoValue <= 21) {
			if (dealer.dealersHandsValue > 21) {
				player.money += 4 * player.bet;
				finalLine += "Dealer busts with " + dealer.dealersHandsValue + ", you won with both hands!";
			} else {
				finalLine += ("Dealers final result is " + dealer.dealersHandsValue + "\n");
				if (player.playersHandsValue == dealer.dealersHandsValue) {
					player.money += player.bet;
					finalLine += "Hand one pushes. ";
				}
				if (player.playersHandsValue > dealer.dealersHandsValue || dealer.dealersHandsValue > 21) {
					player.money += 2 * player.bet;
					finalLine += "Hand one wins. ";
				}
				if (player.playersHandsValue < dealer.dealersHandsValue && dealer.dealersHandsValue <= 21) {
					finalLine += "Hand one loses. ";
				}
				if (player.handTwoValue == dealer.dealersHandsValue) {
					player.money += player.bet;
					finalLine += "Hand two pushes. ";
				}
				if (player.handTwoValue > dealer.dealersHandsValue || dealer.dealersHandsValue > 21) {
					player.money += 2 * player.bet;
					finalLine += "Hand two wins. ";
				}
				if (player.handTwoValue < dealer.dealersHandsValue && dealer.dealersHandsValue <= 21) {
					finalLine += "Hand two loses. ";
				}
			}
		} else if (player.playersHandsValue > 21) {
			finalLine += ("Dealers final result is " + dealer.dealersHandsValue + "\n");
			finalLine += ("Dealers cards are " + dealer.cardsOnHand);
			finalLine += "Hand one loses. ";
			if (player.handTwoValue == dealer.dealersHandsValue) {
				player.money += player.bet;
				finalLine += "Hand two pushes. ";
			}
			if (player.handTwoValue > dealer.dealersHandsValue || dealer.dealersHandsValue > 21) {
				player.money += 2 * player.bet;
				finalLine += "Hand two wins. ";
			}
			if (player.handTwoValue < dealer.dealersHandsValue && dealer.dealersHandsValue <= 21) {
				finalLine += "Hand two loses. ";
			}
		} else if (player.handTwoValue > 21) {
			finalLine += ("Dealers final result is " + dealer.dealersHandsValue + "\n");
			finalLine += ("Dealers cards are " + dealer.cardsOnHand);
			if (player.playersHandsValue == dealer.dealersHandsValue) {
				player.money += player.bet;
				finalLine += "Hand one pushes. ";
			}
			if (player.playersHandsValue > dealer.dealersHandsValue || dealer.dealersHandsValue > 21) {
				player.money += 2 * player.bet;
				finalLine += "Hand one wins. ";
			}
			if (player.playersHandsValue < dealer.dealersHandsValue && dealer.dealersHandsValue <= 21) {
				finalLine += "Hand one loses. ";
			}
			finalLine += "Hand two loses. ";
		}

	}

	public void playAgain() {
		System.out.println("\nWould you like to play again? Type y if yes, n if no");
		while (true) {
			String decision = BlackJack.sc.nextLine().trim().toLowerCase();
			if (decision.equals("y")) {
				finalLine = "";
				deck.deckList = new ArrayList<String>(); // reinitialising all variables from the start
				dealer.dealersHand = new ArrayList<String>();
				dealer.dealersHandsValue = 0;
				dealer.dealersAce11to1 = 1;
				dealer.cardsOnHand = "";
				dealer.deck = BlackJack.deck;
				player.playersHand = new ArrayList<String>();
				player.cardsOnHand = "";
				player.firstChoiceInRound = true;
				player.playersHandsValue = 0;
				player.playersAce11to1 = 1;
				player.handTwoOnSplit = null;
				player.handTwoValue = 0;
				player.handTwoAce11to1 = 1;
				player.deck = BlackJack.deck;
				playGame();
				break;
			} else if (decision.equals("n")) {
				System.out.println("Thank you for the game. Your final balance is " + player.money + ". Bye!");
				break;
			}
		}
	}
}
