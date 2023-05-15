import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        String[] ranks = {"A", "K", "Q", "J", "10", "9", "8", 
                          "7", "6", "5", "4", "3", "2"};
        String[] suits = {"c", "d", "h", "s"};
        cards = new ArrayList<Card>();

        for (String suit : suits){
            for (String rank : ranks){
                cards.add(new Card(rank,suit));
            }
        }

        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
}
