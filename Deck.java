
import java.util.Collections;
import java.util.ArrayList;

public class Deck{
    private ArrayList<Card> deck=new ArrayList<>();
    private Card leadCard;

    //constructor
    public Deck(ArrayList<Card> deck){
        this.deck=deck;
    }

    // Generate Cards
    public void generateCards(){
        char[] numbers={'2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K', 'A'};
        char[] suits={'d', 'c', 'h', 's'};
        // char[] suits = {'♦', '♣', '♥', '♠'}; 
        
        for(char suit:suits){
            for(int i=0;i<13;i++){
                Card x=new Card(i+1, numbers[i], suit);
                deck.add(x);
            }
        }
    }

    // Return the whole deck. 
    public ArrayList<Card> getDeck(){
        return deck;
    }

    // Print whole deck
    public void printDeck(){
        ArrayList<String> strDeck=new ArrayList<String>();

        for(Card card:this.getDeck()){
            char suit=card.getSuit();
            char number=card.getNumber();
            String strCard=Character.toString(suit) + Character.toString(number);
            strDeck.add(strCard);
        }
        System.out.println(strDeck.toString());
    }

    // Return the top card from the deck.
    public Card getLeadCard(){
        return deck.get(0);
    }

    // Shuffle the whole deck. 
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    // Get card from the deck.
    public Card getCardAtIndex(int n){
        return deck.get(n);
    }

    // Remove card from the deck.
    public Card removeCardAtIndex(int n){
        return deck.remove(n);
    }

    // Add card to deck
    public void addCard(Card card){
        deck.add(card);
    }

    // Set lead card
    public void setLeadCard(Card card){
        this.leadCard=card;
    }
}
