import java.util.Scanner;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.*;

public class Turn {
    mainDeck main, center;
    Player players[];
    
    Card currentPlayedCard, highestValCard;
    Player currentLeadPlayer;
    int trickNumber, roundNumber; // Each trick will have four rounds (four players)
    static int mode=0;
    // 1=Start new game, 2=Exit, 3=Skip turn
    
    public void setPlayers(){
        players=new Player[4];
    }
    public void setTrickNum(int n){
        trickNumber=n;
    }
    public void setRoundNum(int n){
        roundNumber=n;
    }
    
    public mainDeck getMainDeck(){
        return main;
    }
    public mainDeck getCenter(){
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
        main=new mainDeck(new LinkedList<Card>());
        center=new mainDeck(new LinkedList<Card>());
        
        main.generateCard();
        setPlayers(); // Create an array of players (four players).

        main.shuffleDeck(); // Shuffle the mainDeck

        Card leadMainDeck=main.removeLeadCard(); // Take the first card from the deck.
        center.addCard(leadMainDeck); // Make it the lead card.

        for (int i=0; i<numOfPlayers; i++) {
            TreeSet<Card> playersCard=new TreeSet<Card>(); // Arraylist for each player.

            // Add cards to each player's deck.
            for (int j = 0; j < numOfCards; j++){
                Card cardToBeAdded=main.removeLeadCard();
                playersCard.add(cardToBeAdded);
            }
            int currentPlayerNumber=(getFirstPlayerIndex(center.getLeadCard())+i)%4; // To determine each player's turn.
            
            if(currentPlayerNumber==0){ 
                currentPlayerNumber=4;
            }
            players[i] = new Player(currentPlayerNumber, playersCard, i+1); // The players constructor.
            //savePlayerDeck("player" + (i + 1) + "Deck.txt", players[i]);
        }
    }

    //save each player's deck into seperate text files
    public void savePlayerDeck(String fileName, Player player) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Card card : player.getDeck()) {
                String cardInfo = card.getSuit() + String.valueOf(card.getNumber());
                writer.write(cardInfo + ", ");
            }
            writer.close();
            System.out.println("Player " + player.getPlayerNum() + "'s deck saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the player's deck.");
            e.printStackTrace();
        }
    }

    //save center deck (clears file everytime deck is empty/new trick)
    public void saveCenterDeck(String filename) {
        try {
            if(center.emptyDeck()){
                PrintWriter w = new PrintWriter(filename);
                w.print("");
                w.close();
            } 
            else {
                FileWriter w = new FileWriter(filename);
                for (Card card : center.getDeck()){
                    String cardInfo = card.getSuit() + String.valueOf(card.getNumber());
                    w.write(cardInfo+"\n");
                }
                w.close();
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred while saving the center deck.");
            e.printStackTrace();
        }
    }

    //save the trick number
    public void saveTrickNum(String filename) {
        try {
            FileWriter w = new FileWriter(filename);
            String trickNumString = String.valueOf(getTrickNum());
            w.write(trickNumString);
            w.close();
            System.out.println("Trick #" +getTrickNum()+" succesfully saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the trick number.");
            e.printStackTrace();
        }
    }

    // public void saveLatestTrickLeader(String filename) {
    //     try{
    //         FileWriter w = new FileWriter("currentTrickLeader.txt", true);
    //         w.write(String.valueOf(getCurrentLeadPlayer().getPlayerNum()));
    //         w.close();
    //     }catch (IOException e) {
    //         System.out.println("An error occurred while saving the current trick leader.");
    //         e.printStackTrace();
    //     }
    // }
    
    public void turn(Scanner input, Player player, int numOfPlayers){
        // Print round details
        printTurnDetails(numOfPlayers, center, main, player);
        
        if(!checkEligibility(player)&&main.emptyDeck()){ // skip turn if no eligible card
            System.out.println("Main deck is EMPTY and player " + player.getPlayerNum() + " does not have any card to be played. Player " + player.getPlayerNum() + " skips.");
            return;
        }

        boolean validInput=false;
        while(!validInput){

            System.out.print(">"); 
            String userInput=input.next();
            boolean invalidButDontBreak=false;

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
                if(roundNumber==1){
                    System.out.println("You are the trick leader, you cannot skip this turn.");
                    invalidButDontBreak=true;
                }
                else{
                    this.setMode(3);
                    validInput=true;
                }
                break;
                case "help":
                invalidButDontBreak=true;
                Menu();
                break;
                case "draw":
                if(!main.emptyDeck()){
                    drawSingleCard(player);
                }else{System.out.println("Main deck is empty.");}
                invalidButDontBreak=true;
                break;
                case "details":
                printTurnDetails(numOfPlayers, center, main, player);
                invalidButDontBreak=true;
                break;
                case "save":
                main.saveMainDeck("mainDeck.txt");
                saveTrickNum("trickNum.txt");
                for (int i=0; i<numOfPlayers; i++){
                    savePlayerDeck("player" + (i + 1) + "Deck.txt", players[i]);
                }
                player.saveTrickLeaders("trickLeaders.txt", Player.getTrickLeaders());
                validInput=true;
                invalidButDontBreak=true;
                break;
            }

            if(userInput.length()==2 && checkPlayedCard(userInput)){
                for(Card card:player.getDeck()){
                    if(userInput.equals(card.getInitial())){
                        currentPlayedCard=card;
                        validInput=true;
                    }
                }
            }

            if(!validInput&&!invalidButDontBreak) {
                System.out.println("Invalid input my friend.");
            }
        }
        
        if(mode==1||mode==2 || mode==3){
            if(mode==3){
                System.out.println();
                System.out.println("Turn Skipped.");
            }
            mode=0;
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
            saveCenterDeck("centerDeck.txt");
            
            if(currentPlayedCard.getValue()>highestValCard.getValue()){ // If the played card's value is bigger than lead card value.
            setHighestValCard(currentPlayedCard);
            setCurrentLeadPlayer(player);
        }
    }

    public void printTurnDetails(int numOfPlayers, mainDeck center, mainDeck mainDeck, Player player){ // Construct a new turn each round
        System.out.println(); 
        System.out.println("Trick #" + trickNumber); // Print trick number.
        System.out.println();
        
        // Print each player's deck
        for(int i=0; i<numOfPlayers; i++){
            for(Player p:players){
                if(p.getPlayerNum()==i+1){
                    System.out.print("Player" + p.getPlayerNum() + ": "); 
                    p.printDeck();
                }
            }
        }
        
        System.out.println();System.out.print("Note: Player"+getCurrentLeadPlayer().getPlayerNum()+" is the current trick leader with ");getHighestValCard().printCurrentCard();System.out.println();
        // Print the lead card
        System.out.println();System.out.print("Center: ");
        center.printDeck();
        
        // Print the remaining deck
        System.out.print("Deck: ");
        mainDeck.printDeck();
        
        // Print the score
        System.out.println();
        for(int i=0; i<numOfPlayers; i++){
            for(Player p:players){
                if(p.getPlayerNum()==i+1){
                    System.out.print("Player" + p.getPlayerNum() + " = " + p.getCollectedScore()); // Print each player's deck
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

        System.out.println("Turn: Player " + player.getPlayerNum());
        try{
            PrintWriter w = new PrintWriter("currentPlayer.txt");
            w.write(String.valueOf(player.getPlayerNum()));
            System.out.println("Current Player saved successfully.");
            w.close();
        }catch(IOException e){
            System.out.println("An error occurred while saving the current player");
            e.printStackTrace();
        }
        if(!center.emptyDeck()){
            System.out.println();
            showEligibleCard(player, center, mainDeck);
        }
        System.out.println();

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

    public void showEligibleCard(Player player, mainDeck center, mainDeck mainDeck){
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
                Card drawCard=drawSingleCard(player);
                
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
            } 
            
            if(mainDeck.getDeck().size()==0){
                System.out.println("No cards left in the deck!");
                mode=4;
                //break; 
            }
            
        }  
    }

    private Card drawSingleCard(Player player){
        Card drawCard = main.removeLeadCard();
        System.out.println(("Card drawn : " + drawCard.getSuit() + drawCard.getNumber()));
        player.addCard(drawCard);
        return drawCard;
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
        System.out.println("Commands:\nx = quit game\ns = restart game\nskip = skip turn\nhelp = show menu\ndraw = draw a single card from the main deck\ndetails = show turn details\nsave = save the game");
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

    public void countScore(Player player){
        HashMap<Character, Integer> cardScore=Card.getCardScore();
        for(Card c:player.getDeck()){
            char num=c.getNumber();
            player.setPlayersScore(cardScore.get(num));
        }
        System.out.println(player.getPlayerScore());
        //System.out.println("Player " + player.getPlayerNum() + "'s score is " + player.getPlayerScore());

        try {
            FileWriter writer = new FileWriter("playerScores.txt", true);
            writer.write("Player " + player.getPlayerNum() + "'s score: " + player.getPlayerScore() + "\n");
            writer.close();
            System.out.println("Player " + player.getPlayerNum() + "'s score saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the player's score.");
            e.printStackTrace();
        }
    }
}
