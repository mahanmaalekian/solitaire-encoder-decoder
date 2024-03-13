package assignment2;

import java.util.Random;

public class Deck {
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();

	public int numOfCards; // contains the total number of cards in the deck
	public Card head; // contains a pointer to the card on the top of the deck

	/* 
	 * TODO: Initializes a Deck object using the inputs provided
	 */
	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		/**** ADD CODE HERE ****/
		if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13)
		{
			throw new IllegalArgumentException("Number of cards per suit not in range");
		}
		if (numOfSuits < 1 || numOfSuits > suitsInOrder.length)
		{
			throw new IllegalArgumentException("Number of suits not in range");
		}
		numOfCards = 0;
		for (int i = 0; i < numOfSuits; i++){
			for (int j = 1; j <= numOfCardsPerSuit; j++) {
				Card card = new PlayingCard(suitsInOrder[i],j);
				this.addCard(card);
			}
		}
		Card redJoker = new Joker("red");
		this.addCard(redJoker);
		Card blackJoker = new Joker("black");
		this.addCard(blackJoker);
	}

	/* 
	 * TODO: Implements a copy constructor for Deck using Card.getCopy().
	 * This method runs in O(n), where n is the number of cards in d.
	 */
	public Deck(Deck d) {
		/**** ADD CODE HERE ****/
		Card tmp = d.head;
		for (int i = 0; i < d.numOfCards; i++) {
			addCard(tmp.getCopy());
			tmp = tmp.next;
		}
	}

	/*
	 * For testing purposes we need a default constructor.
	 */
	public Deck() {}

	/* 
	 * TODO: Adds the specified card at the bottom of the deck. This 
	 * method runs in $O(1)$. 
	 */
	public void addCard(Card c) {
		/**** ADD CODE HERE ****/
		//is the head.next the next card?
		if (numOfCards == 0){
			head = c;
			head.next = c;
			head.prev = c;
		}
		else {
			c.next = head;
			c.prev = head.prev;
			head.prev.next = c;
			head.prev = c;
		}
		numOfCards++;
	}

	/*
	 * TODO: Shuffles the deck using the algorithm described in the pdf. 
	 * This method runs in O(n) and uses O(n) space, where n is the total 
	 * number of cards in the deck.
	 */
	public void shuffle() {
		/**** ADD CODE HERE ****/
		Card [] cardArr = new Card[numOfCards];
		Card tmp = head;
		for (int i = 0; i < numOfCards; i++){
			cardArr[i] = tmp; //tmp.getcopy?
			tmp = tmp.next;
		}
		for (int i = numOfCards - 1; i > 0; i--){
			int j = gen.nextInt(i+1);
			Card holder = cardArr[i];
			cardArr[i] = cardArr[j];
			cardArr[j] = holder;
		}
		int tmpNumOfCards = numOfCards;
		numOfCards = 0;
		for (int i = 0; i < tmpNumOfCards; i++){
			this.addCard(cardArr[i]);
		}

	}

	/*
	 * TODO: Returns a reference to the joker with the specified color in 
	 * the deck. This method runs in O(n), where n is the total number of 
	 * cards in the deck. 
	 */
	public Joker locateJoker(String color) {
		/**** ADD CODE HERE ****/
//		if (!(color.equalsIgnoreCase("red")) || !color.equalsIgnoreCase("black")){
//			throw new IllegalArgumentException("Not a valid color");
//		}
		Card tmp = head;
		for (int i = 0; i < numOfCards; i++) {
			if (tmp instanceof Joker && ((Joker) tmp).getColor().equalsIgnoreCase(color)){
				return (Joker) tmp;
			}
			tmp = tmp.next;
		}
		return null;
	}

	/*
	 * TODO: Moved the specified Card, p positions down the deck. You can 
	 * assume that the input Card does belong to the deck (hence the deck is
	 * not empty). This method runs in O(p).
	 */
	public void moveCard(Card c, int p) {
		/**** ADD CODE HERE ****/
		if (p == 0) return;
		Card tmp = c;
		for (int i = 0; i < p; i++){
			tmp = tmp.next;
		}
		if (c == head) {
			System.out.println("head reached");
			Card tmpNext = tmp.next;
			Card tmpHeadNext = head.next;
			Card tmpHeadPrev = head.prev;


			tmp.next = head;
			head.prev = tmp;

			head.next = tmpNext;
			tmpNext.prev = head;

			tmpHeadNext.prev = tmpHeadPrev;
			tmpHeadPrev.next = tmpHeadNext;
			return;
		}
		//TODO: add case for if the next is head as Yun said
		c.prev.next = c.next;
		c.next.prev = c.prev;
		c.prev = tmp;
		c.next = tmp.next;
		tmp.next.prev = c;
		tmp.next = c;
	}

	/*
	 * TODO: Performs a triple cut on the deck using the two input cards. You 
	 * can assume that the input cards belong to the deck and the first one is 
	 * nearest to the top of the deck. This method runs in O(1)
	 */
	public void tripleCut(Card firstCard, Card secondCard) {
		/**** ADD CODE HERE ****/
		if (firstCard == head && secondCard == head.prev) return;
		if (secondCard == head.prev){
			head = firstCard;
			return;
		}
		if (firstCard == head) {
			head = secondCard.next;
			return;
		}

		Card tmpFirstCardPrev = firstCard.prev;
		Card tmpSecondCardNext = secondCard.next;
		firstCard.prev = head.prev;
		head.prev.next = firstCard;

		secondCard.next = head;
		head.prev = secondCard;

		tmpSecondCardNext.prev = tmpFirstCardPrev;
		tmpFirstCardPrev.next = tmpSecondCardNext;

		head = tmpSecondCardNext;
	}

	/*
	 * TODO: Performs a count cut on the deck. Note that if the value of the 
	 * bottom card is equal to a multiple of the number of cards in the deck, 
	 * then the method should not do anything. This method runs in O(n).
	 */
	public void countCut() {
		/**** ADD CODE HERE ****/
		int limit = head.prev.getValue() % numOfCards;
		if ((numOfCards - limit) == 1 || limit == 0 ) return;
		Card tmp = head;
		for (int i = 0; i < limit-1; i++){
			tmp = tmp.next;
		}
		Card tmpNext = tmp.next;
		Card tmpheadPrevPrev = head.prev.prev;
		Card tmpHeadPrev = head.prev;

		tmp.next = head.prev;
		head.prev.prev = tmp;

		tmpheadPrevPrev.next = head;
		head.prev = tmpheadPrevPrev;

		tmpHeadPrev.next = tmpNext;
		tmpNext.prev = tmpHeadPrev;
		head = tmpNext;
	}

	/*
	 * TODO: Returns the card that can be found by looking at the value of the 
	 * card on the top of the deck, and counting down that many cards. If the 
	 * card found is a Joker, then the method returns null, otherwise it returns
	 * the Card found. This method runs in O(n).
	 */
	public Card lookUpCard() {
		/**** ADD CODE HERE ****/
		int value = head.getValue();
		Card tmp = head;
		for (int i = 0; i < value; i++){
			tmp = tmp.next;
		}
		if (tmp instanceof Joker) return null;
		return tmp;
	}

	/*
	 * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
	 * using this deck. This method runs in O(n).
	 */
	public int generateNextKeystreamValue() {
		/**** ADD CODE HERE ****/
		Card keyStreamCard = null;
		while (keyStreamCard == null)
		{
			System.out.println("jello ");
			printAllCards();
			Card redJoker = locateJoker("red");
			Card blackJoker = locateJoker("black");
			System.out.println(redJoker);
			moveCard(locateJoker("red"), 1);
			moveCard(locateJoker("black"),2);
			printAllCards();

			int distanceRed = 0;
			int distanceBlack = 0;
			Card redJokerTmp = locateJoker("red");
			Card blackJokerTmp = locateJoker("black");
			while (redJokerTmp.prev != head && redJokerTmp != head){
				redJokerTmp = redJokerTmp.prev;
				distanceRed++;
			}
			while (blackJokerTmp.prev != head && blackJokerTmp != head){
				blackJokerTmp = blackJokerTmp.prev;
				distanceBlack++;
			}

			if (distanceBlack > distanceRed){
				tripleCut(locateJoker("red"), locateJoker("black"));
			}
			else {
				tripleCut(locateJoker("black"), locateJoker("red"));
			}
			System.out.println("triple");
			printAllCards();
			countCut();
			System.out.println("count");
			printAllCards();
			keyStreamCard = lookUpCard();
		}
		return keyStreamCard.getValue();
	}


	public abstract class Card { 
		public Card next;
		public Card prev;

		public abstract Card getCopy();
		public abstract int getValue();

	}

	public class PlayingCard extends Card {
		public String suit;
		public int rank;

		public PlayingCard(String s, int r) {
			this.suit = s.toLowerCase();
			this.rank = r;
		}

		public String toString() {
			String info = "";
			if (this.rank == 1) {
				//info += "Ace";
				info += "A";
			} else if (this.rank > 10) {
				String[] cards = {"Jack", "Queen", "King"};
				//info += cards[this.rank - 11];
				info += cards[this.rank - 11].charAt(0);
			} else {
				info += this.rank;
			}
			//info += " of " + this.suit;
			info = (info + this.suit.charAt(0)).toUpperCase();
			return info;
		}

		public PlayingCard getCopy() {
			return new PlayingCard(this.suit, this.rank);   
		}

		public int getValue() {
			int i;
			for (i = 0; i < suitsInOrder.length; i++) {
				if (this.suit.equals(suitsInOrder[i]))
					break;
			}

			return this.rank + 13*i;
		}

	}

	public class Joker extends Card{
		public String redOrBlack;

		public Joker(String c) {
			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
				throw new IllegalArgumentException("Jokers can only be red or black"); 

			this.redOrBlack = c.toLowerCase();
		}

		public String toString() {
			//return this.redOrBlack + " Joker";
			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
		}

		public Joker getCopy() {
			return new Joker(this.redOrBlack);
		}

		public int getValue() {
			return numOfCards - 1;
		}

		public String getColor() {
			return this.redOrBlack;
		}
	}

	public void printAllCards() {
		Card c = head;
		for (int i = 0; i < numOfCards; i++){
			System.out.print(c + " ");
			c = c.next;
		}
		System.out.println();
	}
	public void printAllCardsback() {
		Card c = head.prev;
		for (int i = 0; i < numOfCards; i++){
			System.out.print(c + " ");
			c = c.prev;
		}
		System.out.println();
	}

}
