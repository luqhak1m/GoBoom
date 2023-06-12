
import java.util.ArrayList;
import java.util.TreeSet;

public class Player extends subDeck{

    private int turn, score, playerNum, collectedScore;
    static ArrayList<Player> trickLeaders=new ArrayList<Player>();

    public Player(int playerNum, TreeSet<Card> deck, int turn){
        super(deck);
        this.turn=turn;
        this.playerNum=playerNum;
        this.score=0;
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
}
