package assignment2;

// TODO: check what would happen if when we move something up two, it is in the head position


public class Test {
    public static void main(String[] args){

        Deck d = new Deck(5, 2);
        Deck.gen.setSeed(10);
        d.shuffle();
        SolitaireCipher s = new SolitaireCipher(d);
        String encoded = s.encode("Is that you, Bob?");
        String decoded = s.decode(encoded);

   //     System.out.println(d.generateNextKeystreamValue());


//
//        d.generateNextKeystreamValue();
//        d.generateNextKeystreamValue();
//        d.generateNextKeystreamValue();

        //String encoded = s.encode("Is that you, Bob?");
       // String decoded = s.decode(encoded);

//
       // int [] keyStream = s.getKeystream(12);
        //System.out.println(java.util.Arrays.toString(keyStream));
        //System.out.println(encoded);
//        System.out.println("begin");
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move red joker");
//        d.moveCard(d.locateJoker("red"),1);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move black joker");
//        d.moveCard(d.locateJoker("black"),2);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("triplecut");
//        d.tripleCut(d.locateJoker("black"), d.locateJoker("red"));
//        d.printAllCards();
//        d.printAllCardsback();
//        d.countCut();
//        //System.out.println(d.lookUpCard());
//        System.out.println("countcut");
//        d.printAllCards();
//        d.printAllCardsback();
//
//        System.out.println("begin");
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move red joker");
//        d.moveCard(d.locateJoker("red"),1);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move black joker");
//        d.moveCard(d.locateJoker("black"),2);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("triplecut");
//        d.tripleCut(d.locateJoker("red"), d.locateJoker("black"));
//        d.printAllCards();
//        d.printAllCardsback();
//        d.countCut();
//        //System.out.println(d.lookUpCard());
//        System.out.println("countcut");
//        d.printAllCards();
//        d.printAllCardsback();
//
//
//        System.out.println("begin");
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move red joker");
//        d.moveCard(d.locateJoker("red"),1);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move black joker");
//        d.moveCard(d.locateJoker("black"),2);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("triplecut");
//        d.tripleCut(d.locateJoker("black"), d.locateJoker("red"));
//        d.printAllCards();
//        d.printAllCardsback();
//        d.countCut();
//        //System.out.println(d.lookUpCard());
//        System.out.println("countcut");
//        d.printAllCards();
//        d.printAllCardsback();
//
//
//        System.out.println("begin");
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move red joker");
//        d.moveCard(d.locateJoker("red"),1);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("move black joker");
//        d.moveCard(d.locateJoker("black"),2);
//        d.printAllCards();
//        d.printAllCardsback();
//        System.out.println("triplecut");
//        d.tripleCut(d.locateJoker("red"), d.locateJoker("black"));
//        d.printAllCards();
//        d.printAllCardsback();
//        d.countCut();
//        //System.out.println(d.lookUpCard());
//        System.out.println("countcut");
//        d.printAllCards();
//        d.printAllCardsback();

     //  System.out.println(d.generateNextKeystreamValue());

    }
}
