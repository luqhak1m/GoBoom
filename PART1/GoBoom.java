import java.util.Scanner;

public class GoBoom {

    public static void main(String[] args) {
        int numOfPlayers = 4; // Four Players.
        int numOfCards = 7; // 7 Cards for each player.
        int turn=1; // Number of turns.

        Turn gameTurn=new Turn(numOfPlayers, numOfCards); // New Turn object to handle the gameplay.

        Scanner input = new Scanner(System.in);

        for(Player player:gameTurn.getPlayers()){ // Set the lead player.
            if(player.getPlayerTurn()==1){
                gameTurn.setCurrentLeadPlayer(player);
            }
        }
        
        // Game starts.
        while(turn>0){ // This will be the tricks.

            if(!gameTurn.getCenter().emptyDeck()){ // Set the Lead Card and the Highest Value Card (at this stage of the game, both are the same).
                gameTurn.setCurrentLeadCard(gameTurn.getCenter().getLeadCard());
                gameTurn.setHighestValCard(gameTurn.getCenter().getLeadCard());
            }

            if (turn==1){
                Welcome();
            }
            
            for(int i=0; i<numOfPlayers; i++){ // For each trick's round.
                for(Player player:gameTurn.getPlayers()){ // For each player in the players array.
                    if(player.getPlayerTurn()==i+1){ // Get player on current turn.
                        gameTurn.turn(i, input, player, gameTurn.getCenter(), gameTurn.getMainDeck(), turn, numOfPlayers, gameTurn.getPlayers());
                        

                        if(gameTurn.getMode()==1){ // If player types 's'.
                            System.out.println("Restarting game.");
                            break;
                        }


                        if(gameTurn.getMode()==2){ // If player types 'x'.
                            System.out.println("Thank you for playing!");
                            break;
                        }
                    }
                }  
                if(gameTurn.getMode()==2 || gameTurn.getMode()==1){ // Break from the for loop.
                    break;
                }  
            }
            if(gameTurn.getMode()==1){ // If player resets the game.
                gameTurn=new Turn(numOfPlayers, numOfCards); // Create a new Turn object on the Previous Turn object.
            }


            else if(gameTurn.getMode()==2){ // If player exits the game.
                break; // Break the while loop.
            }


            else if(gameTurn.getMode()==0){ // After four (4) rounds has ended.
                System.out.println();System.out.println("*** Player " + gameTurn.getCurrentLeadPlayer().getPlayerNum() + " wins Trick #" + turn + " ***"); // Show which player wins the round.
                gameTurn.getCenter().getDeck().clear(); // Clear the deck for new trick.
    
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
                turn++; // Add turn count.
            }

        }
    }

    public static void Welcome(){
        System.out.println("-- Welcome to Go Boom --");
        System.out.print("Press 'c' to see commands: ");
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();

        if(command.equals("c")|| command.equals("C")){
            Turn.Menu();
            System.out.print("Press any key to play: ");
            command = input.nextLine();
        }
    }
}
