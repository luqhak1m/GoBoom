
import java.util.Scanner;
import java.util.ArrayList;

public class Turn {
    Deck mainDeck, center;
    Player players[];
    
    Card currentLeadCard, currentPlayedCard, highestValCard;
    Player currentLeadPlayer;
    int mode;
    
    public void setMainDeck(ArrayList<Card> deck){
        mainDeck=new Deck(deck);
    }
    public void setPlayers(){
        players=new Player[4];
    }
    public void setCenter(ArrayList<Card> deck){
        center=new Deck(deck);
    }
    
    public Deck getMainDeck(){
        return mainDeck;
    }
    public Deck getCenter(){
        return center;
    }
    public Player[] getPlayers(){
        return players;
    }
    
    public void setCurrentLeadCard(Card card){
        currentLeadCard=card;
    
    }
    public void setCurrentPlayedCard(Card card){
        currentPlayedCard=card;
    }
    public void setCurrentLeadPlayer(Player player){
        currentLeadPlayer=player;
    }
    public void setHighestValCard(Card card){
        highestValCard=card;
    }
    public void setMode(int modeInt){
        mode=modeInt;
    }
    
    public Card getCurrentLeadCard(){
        return currentLeadCard;
    }
    public Card getCurrentPlayedCard(){
        return currentPlayedCard;
    }
    public Card getHighestValCard(){
        return highestValCard;
    }
    public Player getCurrentLeadPlayer(){
        return currentLeadPlayer;
    }
    public int getMode(){
        return this.mode;
    }

    public Turn(int numOfPlayers, int numOfCards){

        // Two empty ArrayLists to be passed on to the two Deck objects constructor (mainDeck and center).
        ArrayList<Card> deck=new ArrayList<>();
        ArrayList<Card> emptyDeck=new ArrayList<>();


        setMainDeck(deck); 
        setCenter(emptyDeck);
        setPlayers(); // Create an array of players (four players).

        mainDeck.generateCards(); // Generate all of the card's Suit, Number and Value.
        mainDeck.shuffleDeck(); // Shuffle the mainDeck

        Card leadMainDeck=mainDeck.removeCardAtIndex(0); // Take the first card from the deck.
        center.addCard(leadMainDeck); // Make it the lead card.

        for (int i=0; i<numOfPlayers; i++) {
            ArrayList<Card> playerCards = new ArrayList<>(); // Arraylist for each player.

            // Add cards to each player's deck.
            for (int j = 0; j < numOfCards; j++){
                Card cardToBeAdded=mainDeck.getCardAtIndex(0);
                playerCards.add(cardToBeAdded);
                mainDeck.removeCardAtIndex(0);
            }
            int currentPlayerNumber=(getFirstPlayerIndex(center.getLeadCard())+i)%4; // To determine each player's turn.

            if(currentPlayerNumber==0){ 
                currentPlayerNumber=4;
            }
            players[i] = new Player(currentPlayerNumber, playerCards, i+1); // The players constructor.
        }
        mode=0; // Default mode. Play mode.
    };

    private int getFirstPlayerIndex(Card leadCard) {
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

    public void printTurnDetails(int numOfPlayers, Player[] players, Deck center, Deck mainDeck, int turn){ // Construct a new turn each round
        System.out.println(); System.out.println("Trick #" + turn); // Print trick number.

        // Print each player's deck
        for(int i=0; i<numOfPlayers; i++){
            for(Player player:players){
                if(player.getPlayerNum()==i+1){
                    System.out.print("Player" + player.getPlayerNum() + ": "); 
                    player.printDeck();
                }
            }
        }

         // Print the lead card
         System.out.print("Center: ");
         center.printDeck();
 
         // Print the remaining deck
         System.out.print("Deck: ");
         mainDeck.printDeck();
 
         // Print the score
         for(int i=0; i<numOfPlayers; i++){
            for(Player player:players){
                if(player.getPlayerNum()==i+1){
                    System.out.print("Player" + player.getPlayerNum() + " = " + player.getPlayerScore()); // Print each player's deck
                }
            }

            // Design
            if(i!=3){ 
                System.out.print(" | ");
            }
            else{
                System.out.println();
            }

        }
        // System.out.print("Player"+getCurrentLeadPlayer().getPlayerNum()+" is now current the trick leader with ");getHighestValCard().printCurrentCard(); System.out.println();
    }

    public void playedCardMainMethod(int i, Player player, Deck center, String userInput, int turn){
        for(int j=0; j<player.getDeck().size(); j++){
            currentPlayedCard=player.getCardAtIndex(j); // Get the currently played card.
            if(currentPlayedCard.getSuit()==returnPlayedSuit(userInput) && currentPlayedCard.getNumber()==returnPlayedNum(userInput)){
                if(i==0 && turn!=1){ 
                    setHighestValCard(currentPlayedCard);
                }
                center.addCard(currentPlayedCard);  
                player.removeCardAtIndex(j);

                if(currentPlayedCard.getValue()>highestValCard.getValue()){ // If the played card's value is bigger than lead card value.
                    setHighestValCard(currentPlayedCard);
                    setCurrentLeadPlayer(player);
                }
            }
        }
    }

    public static char returnPlayedSuit(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[0];
    }

    public static char returnPlayedNum(String userInput){
        char[] playedCardArr = userInput.toCharArray();
        return playedCardArr[1];
    }


    public void showEligibleCard(Player player, Deck center, Deck mainDeck){
        boolean hasEligibleCard = false;
        for (Card card : player.getDeck()){
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

        while(userInput.equals("card")){
            System.out.println("You haven't played a card yet."); // The currentPlayedCard can't be shown if the player hasn't play any card yet.
            System.out.print(">"); 
            userInput=input.next();
            if(userInput.length()==2){
                break;
            }
        }
        while(userInput.equals("d")){
            System.out.println("(This action will also be performed automatically when player don't have any cards to play)");
            showEligibleCard(player, center, mainDeck);
            System.out.print(">"); 
            userInput=input.next();
        }
        if(userInput.length()==2){
            playedCardMainMethod(i, player, center, userInput, turn);
        }
        else if(userInput.equals("x")){
            setMode(2);
        }
        else if(userInput.equals("s")){
            setMode(1);
        }
    }

    public static void Menu(){
        System.out.println();
        System.out.println("Disclaimers: \nTo play a card, input the card suit followed by its rank.\nFor example: c9");
        System.out.println();
        System.out.println("Cards will be automatically drawn if player does not have any eligible cards to play");
        System.out.println();
        System.out.println("Commands:\nx = quit game\ns = restart game");
        System.out.println();
}

}
