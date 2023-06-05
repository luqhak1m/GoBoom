
import java.util.Scanner;

public class GoBoom {

    public static void main(String[] args) {
        int numOfPlayers = 4; // Four Players.
        int numOfCards = 7; // 7 Cards for each player.
        int trick=1; // Number of turns.

        Turn gameTurn=new Turn(numOfPlayers, numOfCards); // New Turn object to handle the gameplay.
        Scanner input = new Scanner(System.in);

        // Game starts.
        while(trick>0){ // This will be the tricks.
            
            if(trick==1){
                gameTurn.Welcome();
            }
            Deck centerDeck=gameTurn.center;
            gameTurn.setLeadPlayer(); // Set the lead player

            if(!centerDeck.emptyDeck()){ // Set the Lead Card and the Highest Value Card (at this stage of the game, both are the same).
                Card centerLeadCard=centerDeck.getLeadCard();
                gameTurn.setHighestValCard(centerLeadCard);
            }

            // // This is the rounds.

            for(int i=0; i<numOfPlayers; i++){ // For each trick's round.
                gameTurn.setTrickNum(trick);
                gameTurn.setRoundNum(i+1);
                if(gameTurn.getMode()==1||gameTurn.getMode()==2){
                    break;
                }
                int currentRoundNumber=gameTurn.getRoundNum();
                for(Player player:gameTurn.getPlayers()){ // For each player in the players array.
                    if(player.getPlayerTurn()==currentRoundNumber){ // Get player on current round.
                        gameTurn.turn(input, player, numOfPlayers);
                    }
                }  
            } 
            
            // // End of 4 Rounds.

            if(gameTurn.getMode()==1||gameTurn.getMode()==2){
                if(gameTurn.getMode()==1){
                    System.out.println();
                    System.out.println("Starting a new game...");
                    System.out.println();
                    gameTurn=new Turn(numOfPlayers, numOfCards); // create new game
                    trick=1;
                    gameTurn.setMode(0);
                    continue;
                }
                else{
                    System.out.println();
                    System.out.println("Thank You for playing!");
                    break;
                }
            }

            gameTurn.getCurrentLeadPlayer().addPlayersScore(); //Add trick winner's score
            System.out.println();System.out.println("*** Player " + gameTurn.getCurrentLeadPlayer().getPlayerNum() + " wins Trick #" + gameTurn.getTrickNum() + " ***"); // Show which player wins the round.
            gameTurn.getCenter().getDeck().clear(); // Clear the center deck for new trick.

            // Change the remaining user's turn based on the previous trick winner.
            for(int l=0; l<numOfPlayers; l++){
                int currentPlayerNumber=(gameTurn.getCurrentLeadPlayer().getPlayerNum()+l)%4;
                for(Player player:gameTurn.getPlayers()){
                    if(currentPlayerNumber==0){ // Because 4%4==0. We are aiming for 4 not 0.
                        currentPlayerNumber=4;
                    }
                    if(player.getPlayerNum()==currentPlayerNumber){ 
                        player.setPlayerTurn(l+1); // Each player's turn is now reassigned
                    }
                }
            }
            trick++; // Add turn count.
        }   
    }
}
