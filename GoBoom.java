
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GoBoom {

    public static void main(String[] args) {
        
        // //   All cards should be faced up to facilitate checking and Start a new game with randomized 52 cards.

        // Generating the main deck of card
        ArrayList<Card> deck=new ArrayList<>();
        Deck mainDeck = new Deck(deck);
        mainDeck.generateCards();

        // Shuffling the generated deck of cards
        mainDeck.shuffleDeck();

        // Deal cards to four players
        int numOfPlayers = 4;
        int numOfCards = 7;

        // Creating an array of players
        Player[] players=new Player[4];



        // // The first card in the deck is the first lead card and is placed at the center.

        // Create a 'center' Array List and insert the lead card from the deck.
        ArrayList<Card> centerDeck=new ArrayList<>();
        Deck center=new Deck(centerDeck);

        Card leadMainDeck=mainDeck.removeCardAtIndex(0); // lead card is removed from the deck
        center.addCard(leadMainDeck); // removed lead card added to the center



        // //   The first lead card determines the first player and Deal 7 cards to each of the 4 players.

        // Adding cards to the new player deck
        for (int i=0; i<numOfPlayers; i++) {

            ArrayList<Card> playerCards = new ArrayList<>();
            
            // Add cards to the playersCard Array List.
            for (int j = 0; j < numOfCards; j++){
                Card cardToBeAdded=mainDeck.getCardAtIndex(0);
                playerCards.add(cardToBeAdded);
                mainDeck.removeCardAtIndex(0);
            }
            
            int currentPlayerNumber=(getFirstPlayerIndex(center.getLeadCard())+i)%4;
            if(currentPlayerNumber==0){ // because 4%4==0 but we wanna make it ==4
                currentPlayerNumber=4;
            }
            
            players[i] = new Player(currentPlayerNumber, playerCards, i+1);
        }
                
        int turn=1;
        boolean stopGame=false;
        Scanner input = new Scanner(System.in);

        Card leadCenterDeck=center.getLeadCard();
        Card currentLeadCard=leadCenterDeck;
        Card currentPlayedCard=players[turn-1].getCardAtIndex(turn-1); // just a placeholder
        Player currentLeadPlayer=players[turn-1]; // just a placeholder

        // Game starts.
        while(turn>0 && !stopGame){ // This will be the tricks.
            for(int i=0; i<numOfPlayers; i++){ // This will be each trick's turn, and since there's four players there will only be 4 turns.
                if(turn==1){
                    for(Player player:players){
                        if(player.getPlayerTurn()==i+1){ // Get player on current turn
                            if(i+1==1){ // If it's the first round of the first trick, this will make the first player moves.
                                System.out.print("At the beginning of a game. The first lead card ");
                                leadCenterDeck.printCurrentCard();
                                System.out.print(" is placed at the center. Player" + player.getPlayerNum() + " is the first player because of "); 
                                leadCenterDeck.printCurrentCard();
                                currentLeadPlayer=player;
                                currentLeadCard=leadCenterDeck;
                            }

                            // Print round details
                            printRoundsDetails(numOfPlayers, players, center, mainDeck, turn);
                            System.out.println("Turn: Player" + player.getPlayerNum());
                            
                            //check if the card is eligible to play
                            showEligibleCard(player, center, mainDeck);
                            
                            // Check for played card
                            String userInput=getUserInput(input);

                            // This can be a function
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
                    }
                }
                else if(turn>1){ // For trick > 1.
                    for(Player player:players){
                        if(i+1==1){ // If it's the first round of the trick.
                            if(currentLeadPlayer==player){ // Leader of previous trick will move first.

                                // Print round details
                                printRoundsDetails(numOfPlayers, players, center, mainDeck, turn);
                                System.out.println("Turn: Player" + player.getPlayerNum());
                                
                                // We don't check for eligible card because center is empty.
                                
                                // Check for played card
                                String userInput=getUserInput(input);

                                // This can be a function
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
                        }
                        else{ // If it's not the first round of the trick (2,3,4,...n).

                            // Since the player with turn #1 is now different, we need to change for other as well.

                            if(player.getPlayerTurn()==i+1){ 
                                // Print round details
                                printRoundsDetails(numOfPlayers, players, center, mainDeck, turn);
                                System.out.println("Turn: Player" + player.getPlayerNum());
                                
                                //check if the card is eligible to play
                                showEligibleCard(player, center, mainDeck);
                                
                                // Check for played card
                                String userInput=getUserInput(input);

                                // This can be a function
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
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("*** Player " + currentLeadPlayer.getPlayerNum() + " wins Trick #" + turn + " ***");
            center.getDeck().clear();

            // Change the remaining user's turn based on the previous trick winner.


            turn++;
        }

        //gameplay

       

             
        }

    private static int getFirstPlayerIndex(Card leadCard) {
        char rank = leadCard.getNumber();
        switch(rank){
            case 'A': case '5': case '9': case 'K':{
                return 1; // Player1 goes first
            }
            case '2': case '6': case 'X':{
                return 2; // Player2 goes first
            }
            case '3': case '7': case 'J':{
                return 3; // Player3 goes first
            }
            case '4': case '8': case 'Q':{
                return 4; // Player4 goes first
            }
            default: {
                return 0; // Default to Player1
            }
        }
    }
        
    public static void printRoundsDetails(int numOfPlayers, Player[] players, Deck center, Deck mainDeck, int turn){
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

    public static Boolean checkEligibleCard(Card card, Deck center){
        if(card.getSuit()==(center.getLeadCard().getSuit()) || card.getNumber()==center.getLeadCard().getNumber()){
            return true;
        }
        else{
            return false;
        }
    }

    public static void showEligibleCard(Player player, Deck center, Deck mainDeck){
        boolean hasEligibleCard = false;
        for (Card card : player.getDeck()){
            //players[turn].printDeck();
            if(checkEligibleCard(card, center)){
                hasEligibleCard=true;
                System.out.print(("There is an eligible card : "));card.printCurrentCard();System.out.println();
                break;
            }
            else{
                continue;
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
                    if(drawCard.getSuit() == (center.getLeadCard().getSuit()) || drawCard.getNumber()==center.getLeadCard().getNumber()){
                        System.out.println(("It is an eligible card : ") + drawCard.getSuit() + drawCard.getNumber());
                        break;
                    }
                    else{
                        System.out.println("It is still not eligible");
                        continue;
                    }
                } while(mainDeck.getDeck().size()!=0);

            if(mainDeck.getDeck().size()==0){
                System.out.println("No cards left in the deck!");
                //break; 
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
    

    public static String getUserInput(Scanner input){
        System.out.print(">"); 
        String playerComm=input.next();
        return playerComm;
    }
    
    // public static void checkUserInput(String userInput){
    //     if(userInput.length()==2){
    //         char[] playedCardArr = userInput.toCharArray();
    //         playedCardSuit=playedCardArr[0];
    //         playedCardNum=playedCardArr[1];
    //     }
    // }

    public static char returnPlayedSuit(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[0];
    }
    public static char returnPlayedNum(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[1];
    }
    

}
