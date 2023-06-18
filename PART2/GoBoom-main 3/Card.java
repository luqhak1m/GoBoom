
import java.util.HashMap;

public class Card implements Comparable<Card>{
    private int value;
    private char number, suit;
    private String initial;
    static HashMap<Character, Integer> cardScore;

    public Card(int value, char number, char suits, HashMap<Integer, String> cardInitial){
        this.number=number;
        this.suit=suits;
        this.value=value;
        
        String tempCard=String.valueOf(suits)+cardInitial.get(value);
        this.initial=tempCard;

        cardScore=new HashMap<>();

        char[] mapKey={'A', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'J', 'Q', 'K'};

        for(int i=0; i<mapKey.length; i++){
            char suitKey=mapKey[i];
            switch(suitKey){
                case 'A':
                    cardScore.put(suitKey, i+1); // A=1
                    break;
                case 'J': case 'Q': case 'K': case 'X':
                    cardScore.put(suitKey, 10);
                    break;
                default:
                    cardScore.put(suitKey, Character.getNumericValue(suitKey));
                    break;
            }
        }
    }

    public void setValue(int n){
        this.value=n;
    };
    public void setNumber(char x){
        this.number=x;
    };
    public void setSuit(char x){
        this.suit=x;
    };
    public int getValue(){
        return value;
    }
    public char getNumber(){
        return number;
    }
    public char getSuit(){
        return suit;
    }
    public String getInitial(){
        return initial;
    }
    public static HashMap<Character, Integer> getCardScore(){
        return cardScore;
    }

    public void printCurrentCardDetailed(){
        System.out.println("value is : " + this.getValue() + ", number is: " + this.getNumber() + ", suit is  : " + this.getSuit());
    }
    public void printCurrentCard(){
        System.out.printf(this.initial);
    }

    @Override
    public int compareTo(Card card2) {
        if(this.value==card2.value){
            return this.suit-card2.suit;
        }
        else{
            return Integer.compare(this.value, card2.value);
        }
    }

    
}
