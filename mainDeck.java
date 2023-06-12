import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class mainDeck implements deckTemplate<LinkedList<Card>> {

    private LinkedList<Card> deck;

    public mainDeck(LinkedList<Card> d){
        this.deck=d;
    }

    // Generate Cards
    public void generateCard(){
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
            }
        }
    }

    
    public Card getLeadCard() {
        return deck.peek();
    }
    
    public Card removeLeadCard(){
        return deck.pop();
    }
    
    public void shuffleDeck() {
        Collections.shuffle(deck);
        
    }
    
    @Override
    public LinkedList<Card> getDeck() {
        return deck;
    }

    @Override
    public void setDeck(LinkedList<Card> d) {
        deck=d;
    }

    @Override
    public void printDeck() {
        ArrayList<String> strDeck=new ArrayList<String>();

        for(Card card:this.getDeck()){
            strDeck.add(card.getInitial());
        }
        System.out.println(strDeck.toString());
    }

    @Override
    public void addCard(Card card) {
        deck.add(card);
    }

    @Override
    public boolean emptyDeck() {
        if(deck.size()==0){
            return true;
        }
        return false;
    }
    
}
