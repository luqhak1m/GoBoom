// import java.util.ArrayList;

// public class Player {
//     private ArrayList<Card> hand;

//     public Player(){
//         hand = new ArrayList<Card>();
//     }

//     public ArrayList<Card> getHand(){
//         return hand;
//     }

//     public void addCard(Card card){
//         hand.add(card);
//     }

//     public Card playCard(int index){
//         return hand.remove(index);
//     }
// }

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private int score;

    public Player(String name, ArrayList<Card> cards) {
        this.name = name;
        this.cards = cards;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}