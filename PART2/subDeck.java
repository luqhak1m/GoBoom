import java.util.ArrayList;
import java.util.TreeSet;

public class subDeck implements deckTemplate<TreeSet<Card>>{

    private TreeSet<Card> deck;
    static TreeSet<Card> original52Cards=new TreeSet<Card>();

    public subDeck(TreeSet<Card> playerCards){
        this.deck=playerCards;
    };

    // Return the original deck. 
    public TreeSet<Card> getOriginalDeck(){
        return original52Cards;
    }

    public boolean getCard(Card card) {
        return deck.contains(card);
    }
    public boolean removeCard(Card card) {
        return deck.remove(card);
    }

    @Override
    public TreeSet<Card> getDeck() {
        return deck;
    }

    @Override
    public void printDeck() {
        ArrayList<String> strDeck=new ArrayList<String>();

        for(Card card:deck){
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

    @Override
    public void setDeck(TreeSet<Card> d) {
        deck=d;
    }


    // getCardAtIndex
    // removeCardAtIndex
    
}
