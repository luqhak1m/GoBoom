
import java.util.HashMap;

public class Card{
    private int value;
    private char number, suit;
    private String initial;

    public Card(int value, char number, char suits, HashMap<Integer, String> cardInitial){
        this.number=number;
        this.suit=suits;
        this.value=value;
        
        String tempCard=String.valueOf(suits)+cardInitial.get(value);
        this.initial=tempCard;
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

    public void printCurrentCardDetailed(){
        System.out.println("value is : " + this.getValue() + ", number is: " + this.getNumber() + ", suit is  : " + this.getSuit());
    }
    public void printCurrentCard(){
        System.out.printf(this.initial);
    }

    
}
