
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

    public int getPlayerNum(){
        return playerNum;
    }

    public int getPlayerTurn(){
        return turn;
    }

    public int getPlayerScore(){
        return score;
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
