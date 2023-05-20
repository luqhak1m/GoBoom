
import java.util.Scanner;

public class Turn {
    Card currentLeadCard, currentPlayedCard, leadCenterDeck;
    Player currentLeadPlayer;
    int mode;

    public Turn(){};

    public void printTurnDetails(int numOfPlayers, Player[] players, Deck center, Deck mainDeck, int turn){ // Construct a new turn each round
        System.out.println();
        System.out.println("Trick #" + turn);

        for(int i=0; i<numOfPlayers; i++){
            for(Player player:players){
                if(player.getPlayerTurn()==i+1){
                    System.out.print("Player" + player.getPlayerNum() + ": ");
                    player.printDeck();
                }
            }
        }

         // print the lead card
         System.out.print("Center: ");
         center.printDeck();
 
         // print the remaining deck
         System.out.print("Deck: ");
         mainDeck.printDeck();
 
         // print the score
         System.out.print("Score: ");
         for(int i=0; i<numOfPlayers; i++){
            System.out.print("Player " + players[i].getPlayerNum() + " = " + players[i].getPlayerScore());
            if(i!=3){ // there should be 
                System.out.print(" | ");
            }
            else{
                System.out.println();
            }
         }
    }

    public void playedCardMainMethod(int i, Player player, Deck center, String userInput){
        for(int j=0; j<player.getDeck().size(); j++){
            if(i+1!=1){
                leadCenterDeck=center.getLeadCard(); // Get the lead card on center.
            }
            currentPlayedCard=player.getCardAtIndex(j); // Get the currently played card.
            if(checkHigherVal(j, currentLeadCard, currentPlayedCard, currentLeadPlayer, player, center, returnPlayedSuit(userInput), returnPlayedNum(userInput))){ // If the played card's value is bigger than lead card value.
                currentLeadCard=currentPlayedCard;
                currentLeadPlayer=player;
            }
        }
    }

    public static boolean checkHigherVal(int j, Card currentLeadCard, Card currentPlayedCard, Player currentLeadPlayer, Player player, Deck center, char playedCardSuit, char playedCardNum){

        if(currentPlayedCard.getSuit()==playedCardSuit && currentPlayedCard.getNumber()==playedCardNum){ // If the played card is valid.
            center.addCard(currentPlayedCard);  
            player.removeCardAtIndex(j);

            if(currentPlayedCard.getValue()>currentLeadCard.getValue()){ // If the played card's value is bigger than lead card value.
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public static char returnPlayedSuit(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[0];
    }

    public static char returnPlayedNum(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[1];
    }

    public void setCurrentLeadCard(Card card){
        currentLeadCard=card;
    }
    public void setCurrentPlayedCard(Card card){
        currentPlayedCard=card;
    }
    public void setLeadCenterDeck(Card card){
        leadCenterDeck=card;
    }
    public void setCurrentLeadPlayer(Player player){
        currentLeadPlayer=player;
    }

    public Card getCurrentLeadCard(){
        return currentLeadCard;
    }
    public Card getCurrentPlayedCard(){
        return currentPlayedCard;
    }
    public Card getLeadCenterDeck(){
        return leadCenterDeck;
    }
    public Player getCurrentLeadPlayer(){
        return currentLeadPlayer;
    }

    public void showEligibleCard(Player player, Deck center, Deck mainDeck){
        boolean hasEligibleCard = false;
        for (Card card : player.getDeck()){
            //players[turn].printDeck();
            if(!center.emptyDeck()){
                if(card.getSuit()==(center.getLeadCard().getSuit()) || card.getNumber()==center.getLeadCard().getNumber()){
                    hasEligibleCard=true;
                    System.out.print(("There is an eligible card : "));card.printCurrentCard();System.out.println();
                    break;
                }
                else{
                    continue;
                }
            }
        }
        if(!hasEligibleCard){
            //player doesnt have eligible card, so need to draw from deck
            System.out.println("There is no eligible card");

            //if (players[turn].getDeck() != (center.getLeadCard().getSuit()) && card.getNumber()!=center.getLeadCard().getNumber()){
            do{
                Card drawCard = mainDeck.getCardAtIndex(0);
                System.out.println(("Card drawn : " + drawCard.getSuit() + drawCard.getNumber()));
                player.addCard(drawCard);
                mainDeck.removeCardAtIndex(0);

                    //check if the drawn cards is eligible or not
                    if(!center.emptyDeck()){
                        if(drawCard.getSuit() == (center.getLeadCard().getSuit()) || drawCard.getNumber()==center.getLeadCard().getNumber()){
                            System.out.println(("It is an eligible card : ") + drawCard.getSuit() + drawCard.getNumber());
                            break;
                        }
                        else{
                            System.out.println("It is still not eligible");
                            continue;
                        }
                    }
                } while(mainDeck.getDeck().size()!=0);

            if(mainDeck.getDeck().size()==0){
                System.out.println("No cards left in the deck!");
                //break; 
            }    
        }  
    }

    public void turn(int i, Scanner input, Player player, Deck center, Deck mainDeck, int turn, int numOfPlayers, Player[] players){
        // Print round details
        printTurnDetails(numOfPlayers, players, center, mainDeck, turn);
        System.out.println("Turn: Player" + player.getPlayerNum());
        
        if(!center.emptyDeck()){
            showEligibleCard(player, center, mainDeck);
        }

        // Check for played card
        System.out.print(">"); 
        String userInput=input.next();

        if(userInput.length()==2){
            playedCardMainMethod(i, player, center, userInput);
        }
    }

    public void setMode(String modeStr){
        if(modeStr.equals("s")){
            mode=1;
        }
        if(modeStr.equals("x")){
            mode=2;
        }
        if(modeStr.equals("card")){
            mode=3;
        }
    }

}
