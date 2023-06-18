import java.util.ArrayList;

public class Player extends Deck{

    private int turn, score, playerNum;

    public Player(int playerNum, ArrayList<Card> playerCards, int turn){
        super(playerCards);
        this.turn=turn;
        this.playerNum=playerNum;
        this.score=0;
    }

    public void setPlayersScore(int n){
        this.score=n;
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
}