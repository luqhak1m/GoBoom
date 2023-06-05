
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

public class Deck{
    private ArrayList<Card> deck=new ArrayList<>();
    static ArrayList<Card> original52Cards=new ArrayList<>();

    //constructor
    public Deck(ArrayList<Card> deck){
        this.deck=deck;
    }

    // Generate Cards
    public void generateCards(){
        char[] numbers={'2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K', 'A'};
        char[] suits={'d', 'c', 'h', 's'};
        // char[] suits = {'♦', '♣', '♥', '♠'}; 
        HashMap<Integer, String> cardInitial = new HashMap<>();
        for(int k=0; k<13; k++){
            cardInitial.put(k+1, Character.toString(numbers[k]));
        }
        
        for(int i=0; i<suits.length; i++){
            for(int j=0;j<numbers.length;j++){
                Card x=new Card(j+1, numbers[j], suits[i], cardInitial);
                deck.add(x);
                original52Cards.add(x);
            }
        }
    }

    // Return the whole deck. 
    public ArrayList<Card> getDeck(){
        return deck;
    }
    // Return the original deck. 
    public ArrayList<Card> getOriginalDeck(){
        return original52Cards;
    }

    // Print whole deck
    public void printDeck(){
        ArrayList<String> strDeck=new ArrayList<String>();

        for(Card card:this.getDeck()){
            strDeck.add(card.getInitial());
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

    // Check if deck is empty
    public boolean emptyDeck(){
        if(deck.size()==0){return true;}
        return false;
    }
}
