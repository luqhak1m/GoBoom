import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class Player extends subDeck{

    private int turn, score, playerNum, collectedScore=0;
    static ArrayList<Player> trickLeaders=new ArrayList<Player>();

    public Player(int playerNum, TreeSet<Card> deck, int turn){
        super(deck);
        this.turn=turn;
        this.playerNum=playerNum;
        this.score=0;
    }
    public void resetPlayersScore(){
        this.score=0;
    }
    public void resetCollectedScore(){
        this.collectedScore=0;
    }
    public void setPlayersScore(int n){
        this.score+=n;
    }
    public void addCollectedScore(int n){
        this.collectedScore+=n;
    }

    public void setPlayerNum(int n){
        this.playerNum=n;    
    }
    
    public void setPlayerTurn(int n){
        this.turn=n;
    }

    public int getPlayerNum(){
        return playerNum;
    }

    public int getPlayerTurn(){
        return turn;
    }

    public int getPlayerScore(){
        return score;
    }
    public int getCollectedScore(){
        return collectedScore;
    }
    public static ArrayList<Player> getTrickLeaders(){
        return trickLeaders;
    }

    //save ALL trick leaders for EVERY round
    public void saveTrickLeaders(String fileName, ArrayList<Player> trickLeaders) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Player player : trickLeaders) {
                writer.write(player.getPlayerNum() + "\n");
            }
            writer.close();
            System.out.println("Trick leaders saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the trick leaders.");
            e.printStackTrace();
        }
    }
}
