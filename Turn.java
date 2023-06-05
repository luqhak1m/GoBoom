
import java.util.Scanner;
import java.util.ArrayList;

public class Turn {
    Deck mainDeck, center;
    Player players[];
    
    Card currentPlayedCard, highestValCard;
    Player currentLeadPlayer;
    int trickNumber, roundNumber; // Each trick will have four rounds (four players)
    static int mode=0;
    // 1=Start new game, 2=Exit, 3=Skip turn
    
    public void setMainDeck(ArrayList<Card> deck){
        mainDeck=new Deck(deck);
    }
    public void setPlayers(){
        players=new Player[4];
    }
    public void setCenter(ArrayList<Card> deck){
        center=new Deck(deck);
    }
    public void setTrickNum(int n){
        trickNumber=n;
    }
    public void setRoundNum(int n){
        roundNumber=n;
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
    public int getTrickNum(){
        return trickNumber;
    }
    public int getRoundNum(){
        return roundNumber;
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
        return Turn.mode;
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
    };
    
    public void turn(Scanner input, Player player, int numOfPlayers){
        // Print round details
        printTurnDetails(numOfPlayers, center, mainDeck);
        System.out.println("Turn: Player" + player.getPlayerNum());
        
        if(!center.emptyDeck()){
            showEligibleCard(player, center, mainDeck);
        }
        if(!checkEligibility(player)&&mainDeck.emptyDeck()){ // skip turn if no eligible card
            System.out.println("Main deck is EMPTY and player " + player.getPlayerNum() + " does not have any card to be played. Player " + player.getPlayerNum() + " skips.");
            return;
        }

        System.out.println();System.out.print("[Player"+getCurrentLeadPlayer().getPlayerNum()+" is the current trick leader with ");getHighestValCard().printCurrentCard(); System.out.print("]"); System.out.println();

        
        boolean validInput=false;
        while(!validInput){

            System.out.print(">"); 
            String userInput=input.next();
            boolean help=false;

            switch (userInput) {
                case "card":
                System.out.println("You haven't played a card yet.");
                break;
                case "d":
                System.out.println("(This action will also be performed automatically when player don't have any cards to play)");
                break;
                case "s":
                this.setMode(1);
                validInput=true;
                break;
                case "x":
                this.setMode(2);
                validInput=true;
                break;
                case "skip":
                this.setMode(3);
                validInput=true;
                break;
                case "help":
                help=true;
                Menu();
            }
            if(userInput.length()==2 && checkPlayedCard(userInput)){
                for(Card card:player.getDeck()){
                    if(userInput.equals(card.getInitial())){
                        currentPlayedCard=card;
                        validInput=true;
                    }
                }
            }

            if(!validInput&&!help){System.out.println("Invalid input my friend.");};
        }
        
        if(mode==1||mode==2 || mode==3){
            if(mode==3){
                System.out.println("Turn Skipped.");
            }
            return;
        }
        playedCardMainMethod(player); 
       
    }

    
    public void playedCardMainMethod(Player player){
            if(roundNumber==1 && trickNumber!=1){  // If first trick of the first round (because the original value for highest value card on the trick is NULL)
                setHighestValCard(currentPlayedCard);
            }
            
            center.addCard(currentPlayedCard);  
            player.getDeck().remove(currentPlayedCard);
            
            if(currentPlayedCard.getValue()>highestValCard.getValue()){ // If the played card's value is bigger than lead card value.
            setHighestValCard(currentPlayedCard);
            setCurrentLeadPlayer(player);
        }
    }

    public void printTurnDetails(int numOfPlayers, Deck center, Deck mainDeck){ // Construct a new turn each round
        System.out.println(); System.out.println("Trick #" + trickNumber); // Print trick number.
        
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
        System.out.println();System.out.print("Center: ");
        center.printDeck();
        
        // Print the remaining deck
        System.out.print("Deck: ");
        mainDeck.printDeck();
        
        // Print the score
        System.out.println();
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
    }

    public boolean checkEligibility(Player player){ // If player has card that can be played
        if(!center.emptyDeck()){
            for(Card card:player.getDeck()){
                if(card.getSuit()==(center.getLeadCard().getSuit()) || card.getNumber()==center.getLeadCard().getNumber()){
                    return true;
                }
            }
        }
        else if(center.emptyDeck()&&getRoundNum()==1){return true;}
        return false;
    }
    public boolean checkPlayedCard(String userInput){ // If player has card that can be played
        if(!center.emptyDeck()){
            if(userInput.charAt(0)==center.getLeadCard().getSuit() || userInput.charAt(1)==center.getLeadCard().getNumber()){
                return true;
            }
        }
        else if(center.emptyDeck()){
            return true;
        }
        return false;
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
            
            while(mainDeck.getDeck().size()!=0){
                Card drawCard = mainDeck.getCardAtIndex(0);
                System.out.println(("Card drawn : " + drawCard.getSuit() + drawCard.getNumber()));
                player.addCard(drawCard);
                mainDeck.removeCardAtIndex(0);
                
                //check if the drawn cards is eligible or not
                if(!center.emptyDeck()){
                    if(drawCard.getSuit() == (center.getLeadCard().getSuit()) || drawCard.getNumber()==center.getLeadCard().getNumber()){
                        System.out.println(("It is an eligible card : ") + drawCard.getSuit() + drawCard.getNumber());
                        mode=1;
                        break;
                    }
                    else{
                        System.out.println("It is still not eligible");
                        continue;
                    }
                }
            } 
            
            if(mainDeck.getDeck().size()==0){
                System.out.println("No cards left in the deck!");
                mode=4;
                //break; 
            }    
        }  
    }

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

    public static void Menu(){
        System.out.println();
        System.out.println("Disclaimers: \nTo play a card, input the card suit followed by its rank.\nFor example: c9");
        System.out.println();
        System.out.println("Cards will be automatically drawn if player does not have any eligible cards to play");
        System.out.println();
        System.out.println("Commands:\nx = quit game\ns = restart game\nskip = skip turn\nhelp = show menu");
        System.out.println();
    }
    
    public void setLeadPlayer(){
        for(Player player:this.getPlayers()){ // Set the lead player.
            if(player.getPlayerTurn()==1){
                this.setCurrentLeadPlayer(player);
            }
        }
    }

    public void Welcome(){
        Scanner input = new Scanner(System.in);
        
        System.out.println("-- Welcome to Go Boom --");
        Turn.Menu();
        System.out.print("Press enter to play: ");
        String command = input.nextLine();

    }
}
