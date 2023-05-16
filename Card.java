
public class Card{
    private int value;
    private char number, suit;

    public Card(int value, char number, char suits){
        this.number=number;
        this.suit=suits;
        this.value=value;
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

    public void printCurrentCardDetailed(){
        System.out.println("value is : " + this.getValue() + ", number is: " + this.getNumber() + ", suit is  : " + this.getSuit());
    }
    public void printCurrentCard(){
        System.out.printf("%c%c", this.getNumber(), this.getSuit());
    }
}
