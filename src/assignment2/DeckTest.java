package assignment2;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void testDeckConstructor() {
        Deck deck = new Deck(13, 4);
        assertNotNull(deck);
        assertEquals(13 * 4 + 2, deck.numOfCards);
        checkDeckValidity(deck);
    }

    @Test
    public void testDeckConstructor2() {
        Deck deck = new Deck(4, 3);
        StringBuilder s = new StringBuilder();
        Deck.Card c = deck.head;
        for (int i = 0; i < deck.numOfCards; i++, c = c.next) s.append(c).append(' ');
        assertEquals("AC 2C 3C 4C AD 2D 3D 4D AH 2H 3H 4H RJ BJ ", s.toString());
        checkDeckValidity(deck);
    }

    @Test
    public void testCopyConstructor() {
        Deck originalDeck = new Deck(13, 4);
        Deck copiedDeck = new Deck(originalDeck);
        assertNotNull(copiedDeck);
        assertEquals(originalDeck.numOfCards, copiedDeck.numOfCards);
        Deck.Card originalCard = originalDeck.head;
        Deck.Card copiedCard = copiedDeck.head;

        do {
            assertEquals(originalCard.toString(), copiedCard.toString());
            originalCard = originalCard.next;
            copiedCard = copiedCard.next;
        } while (originalCard != originalDeck.head && copiedCard != copiedDeck.head);
        assertEquals(originalDeck.numOfCards, copiedDeck.numOfCards);
    }

    @Test
    public void testAddCard() {
        // Create a new deck
        Deck deck = new Deck();

        // Add some cards to the deck
        Deck.Card card1 = deck.new PlayingCard("H", 1); // Example card
        Deck.Card card2 = deck.new PlayingCard("D", 2); // Example card
        Deck.Card card3 = deck.new PlayingCard("S", 3); // Example card

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        assertEquals(3, deck.numOfCards);

        assertEquals(card1, deck.head);
        assertEquals(card2, deck.head.next);
        assertEquals(card3, deck.head.next.next);
        assertEquals(card3, deck.head.prev);
        assertEquals(card2, deck.head.prev.prev);
        assertEquals(card1, deck.head.prev.prev.prev);
        assertEquals(card1, deck.head.prev.next);

        assertEquals(card1, deck.head.prev.prev.prev);
        assertEquals(card2, deck.head.next.next.next.next);
        assertEquals(card3, deck.head.prev.prev.prev.prev);
        assertEquals(card3, deck.head.next.next.next.next.next);
        checkDeckValidity(deck);
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck(13, 4);
        final int N = deck.numOfCards;
        final int SEED = 69;
        Deck.Card[] groundTruth = deckToArray(deck);
        Deck.gen.setSeed(SEED);
        for (int i = N - 1; i > 0; i--) {
            int j = Deck.gen.nextInt(i + 1);
            Deck.Card _b = groundTruth[i];
            groundTruth[i] = groundTruth[j];
            groundTruth[j] = _b;
        }
        Deck.gen.setSeed(SEED);
        deck.shuffle();
        checkDeckAgainstTruth(deck, groundTruth);

        deck.shuffle();
        Deck.Card c = deck.head;
        for (int i = 0; i < N; i++, c = c.next) assertNotEquals(c.toString(), groundTruth[i].toString());
    }

    @Test
    public void testLocateJoker() {
        Deck deck = new Deck();
        Deck.Joker redJoker = deck.new Joker("red");
        Deck.Joker blackJoker = deck.new Joker("black");
        deck.addCard(redJoker);
        deck.addCard(blackJoker);
        assertEquals(redJoker, deck.locateJoker("red"));
        assertEquals(blackJoker, deck.locateJoker("black"));
        assertNull(deck.locateJoker("blue"));
        Deck emptyDeck = new Deck();
        assertNull(emptyDeck.locateJoker("red"));
        checkDeckValidity(deck);
    }

    @Test
    public void testMoveCard() {
        Deck deck = new Deck();
        Deck.Card card1 = deck.new PlayingCard("H", 1);
        Deck.Card card2 = deck.new PlayingCard("D", 2);
        Deck.Card card3 = deck.new PlayingCard("S", 3);
        Deck.Card card4 = deck.new PlayingCard("C", 4);

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(card4);

        deck.moveCard(card3, 2);
        checkDeckValidity(deck);
        checkDeckAgainstTruth(deck, new Deck.Card[]{card1, card3, card2, card4});

        deck.moveCard(card3, 2);
        checkDeckAgainstTruth(deck, new Deck.Card[]{card1, card2, card4, card3});

        deck.moveCard(card3, 1);
        checkDeckAgainstTruth(deck, new Deck.Card[]{card1, card3, card2, card4});
    }

    @Test
    public void testTripleCut() {
        int[][] tests = new int[][]{
                {0, 1},
                {1, 2},
                {4, 20},
                {0, 20},
                {0, 13 * 4 + 1},
                {20, 13 * 4 + 1},
                {13 * 4, 13 * 4 + 1},
        };
        for (int[] p : tests) {
            Deck deck1 = new Deck(13, 4);
            Deck.Card[] arr1 = deckToArray(deck1);
            tripleCut(arr1, p[0], p[1]);
            Deck deck2 = new Deck(13, 4);
            Deck.Card[] arr2 = deckToArray(deck2);
            deck2.tripleCut(arr2[p[0]], arr2[p[1]]);
            checkDeckAgainstTruth(deck2, arr1);
        }
    }

    @Test
    public void testCountCut() {
        Deck deck = new Deck(13, 4);
        Deck.Card[] arr = deckToArray(deck);
        countCut(arr);
        deck.countCut();
        checkDeckAgainstTruth(deck, arr);
        for (int i = 0; i < 10; i++) {
            deck.shuffle();
            arr = deckToArray(deck);
            countCut(arr);
            deck.countCut();
            checkDeckAgainstTruth(deck, arr);
        }
    }

    // Array-based implementation of triple cut
    public static void tripleCut(Deck.Card[] deck, int i1, int i2) {
        Deck.Card[] temp = new Deck.Card[i2 + 1];
        System.arraycopy(deck, 0, temp, 0, i2 + 1);
        System.arraycopy(deck, i2 + 1, deck, 0, deck.length - i2 - 1);

        System.arraycopy(temp, 0, deck, deck.length - i1, i1);
        System.arraycopy(temp, i1, deck, deck.length - i2 - 1, i2 - i1 + 1);
    }

    public static void countCut(Deck.Card[] deck) {
        int p = deck[deck.length - 1].getValue();
        // Swap the sections of the deck array based on the positions of the first and second cards
        Deck.Card[] temp = new Deck.Card[p];
        System.arraycopy(deck, 0, temp, 0, p);
        System.arraycopy(deck, p, deck, 0, deck.length - p - 1);
        System.arraycopy(temp, 0, deck, deck.length - p - 1, p);
    }


    public static void checkDeckValidity(Deck deck) {
        Deck.Card c = deck.head;
        for (int i = 0; i < deck.numOfCards; i++, c = c.next) {
            assertEquals(c, c.next.prev, "c.next.prev != c at i = " + i);
            assertEquals(c, c.prev.next, "c.prev.next != c at i = " + i);
        }
    }

    public static void checkDeckAgainstTruth(Deck deck, Deck.Card[] groundTruth) {
        System.out.println("TRUTH: " + Arrays.toString(groundTruth));
        System.out.println("GOT:   " + Arrays.toString(deckToArray(deck)));
        assertEquals(groundTruth.length, deck.numOfCards);
        checkDeckValidity(deck);
        int N = groundTruth.length;
        Deck.Card c = deck.head;
        for (int i = 0; i < N; i++, c = c.next) {
            assertEquals(groundTruth[i].toString(), c.toString());
            assertEquals(groundTruth[(i + 1) % N].toString(), c.next.toString());
            assertEquals(groundTruth[(i - 1 + N) % N].toString(), c.prev.toString());
        }
        assertEquals(deck.head, c);
    }

    public static Deck.Card[] deckToArray(Deck deck) {
        Deck.Card[] arr = new Deck.Card[deck.numOfCards];
        Deck.Card c = deck.head;
        for (int i = 0; i < deck.numOfCards; i++, c = c.next) arr[i] = c;
        return arr;
    }
}