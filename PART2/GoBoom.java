import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class GoBoom {

    public static void main(String[] args) {
        play();
    }
    public static void play() {
        int numOfPlayers = 4; // Four Players.
        int numOfCards = 7; // 7 Cards for each player.
        int trick=1; // Number of turns.

        Turn gameTurn=new Turn(numOfPlayers, numOfCards); // New Turn object to handle the gameplay.
        Scanner input = new Scanner(System.in);

        HashMap<Integer, Integer> playersScore=new HashMap<Integer, Integer>();
        for(int i=1; i<=4; i++){
            playersScore.put(i, 0);
        }

        gameTurn.Welcome();
        // Game starts.
        while(trick>0){ // This will be the tricks.
        
            mainDeck centerDeck=gameTurn.center;
            gameTurn.setLeadPlayer(); // Set the lead player

            if(!centerDeck.emptyDeck()){ // Set the Lead Card and the Highest Value Card (at this stage of the game, both are the same).
                Card centerLeadCard=centerDeck.getLeadCard();
                gameTurn.setHighestValCard(centerLeadCard);
            }

            if(trick==1){
                for(Player p:gameTurn.getPlayers()){
                    int currentPlayerScore=playersScore.get(p.getPlayerNum());
                    p.addCollectedScore(currentPlayerScore);
                }   
            }

            // // This is the rounds.

            for(int i=0; i<numOfPlayers; i++){ // For each trick's round.
                gameTurn.setTrickNum(trick);
                gameTurn.setRoundNum(i+1);
                if(gameTurn.getMode()==1||gameTurn.getMode()==2||gameTurn.getMode()==4){
                    break;
                }
                int currentRoundNumber=gameTurn.getRoundNum();
                for(Player player:gameTurn.getPlayers()){ // For each player in the players array.
                    if(player.getPlayerTurn()==currentRoundNumber){ // Get player on current round.
                        gameTurn.turn(input, player, numOfPlayers);
                        
                        if(player.emptyDeck()){
                            System.out.println();
                            System.out.println("Player " + player.getPlayerNum() + " Wins!");
                            System.out.println();

                            try {
                                FileWriter w = new FileWriter("roundWinner.txt", true);
                                w.write(String.valueOf(player.getPlayerNum()));
                                w.close();
                            } catch (IOException e) {
                                System.out.println("An error occurred while saving round #" +currentRoundNumber+ "winner.");
                                e.printStackTrace();
                            }
                            for(Player p:gameTurn.players){
                                gameTurn.countScore(p);
                                int updatedScore=playersScore.get(p.getPlayerNum())+p.getPlayerScore();
                                playersScore.put(p.getPlayerNum(),updatedScore);

                            }
                            gameTurn.setMode(4);
                            break;
                        }
                    }
                }  
            } 
            
            // // End of 4 Rounds.
            Player.getTrickLeaders().add(gameTurn.getCurrentLeadPlayer());
            // System.out.println(Player.getTrickLeaders());

            if(gameTurn.getMode()==1||gameTurn.getMode()==2||gameTurn.getMode()==4){
                if(gameTurn.getMode()==1){
                    System.out.println();System.out.println("Starting a new game...");System.out.println();
                    gameTurn=new Turn(numOfPlayers, numOfCards); // create new game
                    trick=1;
                    gameTurn.setMode(0);
                    for(Player p:gameTurn.getPlayers()){
                        p.resetCollectedScore();
                        // System.out.println(p.getCollectedScore());
                    }
                    for(int i=1; i<=4; i++){
                        playersScore.put(i, 0);
                    }
                    continue;
                }
                else if (gameTurn.getMode() == 2){
                    System.out.println();
                    System.out.println("Game has ended. Thank You for playing!");
                    break;
                }
                if(gameTurn.getMode()==4){
                    System.out.println();System.out.println("Deck emptied. Starting a new game...");System.out.println();
                    gameTurn=new Turn(numOfPlayers, numOfCards); // create new game
                    trick=1;
                    gameTurn.setMode(0);
                    
                    continue;
                }
            }

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

