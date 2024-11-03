package assignment2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CipherTest {
    @Test
    public void testShuffle() {
        Deck deck = new Deck(5, 2);
        Deck.Card[] groundTruth = DeckTest.deckToArray(deck);

        Deck.gen.setSeed(10);
        deck.shuffle();
        Deck.gen.setSeed(10);
        for (int i = deck.numOfCards - 1; i > 0; i--) {
            int j = Deck.gen.nextInt(i + 1);
            Deck.Card _b = groundTruth[i];
            groundTruth[i] = groundTruth[j];
            groundTruth[j] = _b;
        }
        DeckTest.checkDeckAgainstTruth(deck, groundTruth);
        assertEquals("[3C, 3D, AD, 5C, BJ, 2C, 2D, 4D, AC, RJ, 4C, 5D]", Arrays.toString(DeckTest.deckToArray(deck)));
    }

    @Test
    public void testDeckRecreate() {
        Deck deck = new Deck();
        makeCardsFromDeck(deck, "[3C, 3D, AD, 5C, BJ, 2C, 2D, 4D, AC, RJ, 4C, 5D]");
        assertEquals("[3C, 3D, AD, 5C, BJ, 2C, 2D, 4D, AC, RJ, 4C, 5D]", Arrays.toString(DeckTest.deckToArray(deck)));
    }

    @Test
    public void testEncode() {
        Deck deck = new Deck(5, 2);
        Deck.gen.setSeed(10);
        deck.shuffle();
        SolitaireCipher cipher = new SolitaireCipher(deck);
        assertEquals("MWIKDVZCKSFP", cipher.encode("Is that you, Bob?"));
    }

    @Test
    public void testDecode() {
        Deck deck = new Deck(5, 2);
        Deck.gen.setSeed(10);
        deck.shuffle();
        SolitaireCipher cipher = new SolitaireCipher(deck);
        assertEquals("ISTHATYOUBOB", cipher.decode("MWIKDVZCKSFP"));
    }

    @Test
    public void testKeyStream() {
        Deck deck = new Deck(5, 2);
        Deck.gen.setSeed(10);
        deck.shuffle();
        SolitaireCipher cipher = new SolitaireCipher(deck);
        assertArrayEquals(new int[]{4, 4, 15, 3, 3, 2, 1, 14, 16, 17, 17, 14}, cipher.getKeystream(12));
    }

    public void makeCardsFromDeck(Deck deck, String cards) {
        char[] c = cards.replaceAll("[^RBAJQKCDHS0-9]", "").toCharArray();
        assertEquals(c.length % 2, 0);
        for (int i = 0; i < c.length; i += 2) {
            char v = c[i];
            char s = c[i + 1];
            if (s == 'J') deck.addCard(deck.new Joker(v == 'R' ? "red" : "black"));
            else {
                String suit = Arrays.stream(Deck.suitsInOrder).filter(ps -> ps.toUpperCase().charAt(0) == s).findAny().get();
                int value = '2' <= v && v <= '9' ? v - '0' : v == 'J' ? 11 : v == 'Q' ? 12 : v == 'K' ? 13 : v == 'A' ? 1 : -1;
                deck.addCard(deck.new PlayingCard(suit, value));
            }
        }
    }
}