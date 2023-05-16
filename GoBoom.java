
import java.util.ArrayList;
//import java.util.*;

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
        for (int i = 0; i < numOfPlayers; i++) {

            ArrayList<Card> playerCards = new ArrayList<>();
            char[] ranks = {'A', '5', '9', 'K', '2', '6', 'X', '3', '7', 'J', '4', '8', 'Q'};
            char leadCardNum=center.getLeadCard().getNumber();

            // Add cards to the playersCard Array List.
            for (int j = 0; j < numOfCards; j++){
                Card cardToBeAdded=mainDeck.getCardAtIndex(0);
                playerCards.add(cardToBeAdded);
                mainDeck.removeCardAtIndex(0);
            }

            // Set each players' turn.
            for(int k=0; k<ranks.length;k++){
                if(leadCardNum==ranks[k]){
                    int currentPlayerIndex = (k + i) % numOfPlayers;

                    // Create players.
                    players[i] = new Player(i+1, playerCards, currentPlayerIndex+1);
                }
            }

        }

        // Display cards, deck, center deck, score and turn
        int turn=0;
        printRoundsDetails(numOfPlayers, players, center, mainDeck, turn);
    

        //gameplay

        //Scanner scanner = new Scanner(System.in);
        //input.nextInt();


        //check if the card is eligible to play
        boolean hasEligibleCard = false;
        for (Card card : players[turn].getDeck()){
            //players[turn].printDeck();
            if(card.getSuit() == (center.getLeadCard().getSuit()) || card.getNumber()==center.getLeadCard().getNumber()){
                hasEligibleCard = true;
                System.out.println(("There is an eligible card : ") + card.getSuit() + card.getNumber());
                break;
            }
            else{
                continue;
            }
        }
        if (hasEligibleCard){
            //System.out.println("Enter command: ");
        }

        else{
            //player doesnt have eligible card, so need to draw from deck
            System.out.println("There is no eligible card");

            //if (players[turn].getDeck() != (center.getLeadCard().getSuit()) && card.getNumber()!=center.getLeadCard().getNumber()){
                if (mainDeck.getDeck().size()>0){
                    Card drawCard = mainDeck.getCardAtIndex(0);
                    System.out.println(("Card drawn : " + drawCard.getSuit() + drawCard.getNumber()));
                    players[turn].addCard(drawCard);
                    mainDeck.removeCardAtIndex(0);

                    do{
                        //check if the drawn cards is eligible or not
                        if(drawCard.getSuit() == (center.getLeadCard().getSuit()) || drawCard.getNumber()==center.getLeadCard().getNumber()){
                            System.out.println(("It is an eligible card : ") + drawCard.getSuit() + drawCard.getNumber());
                            break;
                        }
                        else{
                            System.out.println("It is still not eligible");
                            continue;
                        }
                    } while(mainDeck.length!=0);
                    

                }
                else{
                    System.out.println("No cards left in the deck!");
                    //break; 
                }
        }        
        }
        
    public static void printRoundsDetails(int numOfPlayers, Player[] players, Deck center, Deck mainDeck, int turn){
         // print the players' cards
         for(int i=0; i<numOfPlayers; i++){
             System.out.print("Player " + players[i].getPlayerNum() + ": ");
             players[i].printDeck();
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
 
         // print current player's turn
         System.out.println("Turn: " + turn);
    }
    
}
