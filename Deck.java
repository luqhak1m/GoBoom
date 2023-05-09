import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<String> deck;

    //constructor
    public Deck() {
        deck = new ArrayList<String>();
    }

    public void addCards() {

    //add cards into arrayList
    deck.add("sA");
    deck.add("s2");
    deck.add("s3");
    deck.add("s4");
    deck.add("s5");
    deck.add("s6");
    deck.add("s7");
    deck.add("s8");
    deck.add("s9");
    deck.add("sJ");
    deck.add("sX");
    deck.add("sK");
    deck.add("sQ");

    deck.add("cA");
    deck.add("c2");
    deck.add("c3");
    deck.add("c4");
    deck.add("c5");
    deck.add("c6");
    deck.add("c7");
    deck.add("c8");
    deck.add("c9");
    deck.add("cJ");
    deck.add("cX");
    deck.add("cK");
    deck.add("cQ");

    deck.add("dA");
    deck.add("d2");
    deck.add("d3");
    deck.add("d4");
    deck.add("d5");
    deck.add("d6");
    deck.add("d7");
    deck.add("d8");
    deck.add("d9");
    deck.add("dJ");
    deck.add("dX");
    deck.add("dK");
    deck.add("dQ");

    deck.add("hA");
    deck.add("h2");
    deck.add("h3");
    deck.add("h4");
    deck.add("h5");
    deck.add("h6");
    deck.add("h7");
    deck.add("h8");
    deck.add("h9");
    deck.add("hJ");
    deck.add("hX");
    deck.add("hK");
    deck.add("hQ");
    }

    public void randomiseDeck() {
        Collections.shuffle(deck, new Random());
    }

    public String getCardByIndex(int index) {
        return deck.get(index);
    }

    public String getLeadCard() {
        return deck.get(0);
    }

    public ArrayList<String> getDeck() {
        return deck;
    }
}
