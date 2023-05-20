
import java.util.ArrayList;
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
        Scanner input = new Scanner(System.in);

        // Game starts.
        Turn gameTurn=new Turn();
        for(Player player:players){
            if(player.getPlayerTurn()==1){
                gameTurn.setCurrentLeadPlayer(player);
            }
        }
        
        while(turn>0){ // This will be the tricks.
            if(!center.emptyDeck()){
                gameTurn.setCurrentLeadCard(center.getLeadCard());
                gameTurn.setHighestValCard(center.getLeadCard());
            }
            for(int i=0; i<numOfPlayers; i++){
                for(Player player:players){
                    if(player.getPlayerTurn()==i+1){ // Get player on current turn
                        gameTurn.turn(i, input, player, center, mainDeck, turn, numOfPlayers, players);
                    }
                }    
            }
            System.out.println();
            System.out.println("*** Player " + gameTurn.getCurrentLeadPlayer().getPlayerNum() + " wins Trick #" + turn + " ***");
            center.getDeck().clear();

            // Change the remaining user's turn based on the previous trick winner.
            for(int l=0; l<numOfPlayers; l++){
                int currentPlayerNumber=(gameTurn.getCurrentLeadPlayer().getPlayerNum()+l)%4;
                for(Player player:players){
                    if(currentPlayerNumber==0){ // because 4%4==0 but we wanna make it ==4
                        currentPlayerNumber=4;
                    }
                    if(player.getPlayerNum()==currentPlayerNumber){
                        player.setPlayerTurn(l+1);
                    }
                }
            }
            turn++;
        }
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
}
