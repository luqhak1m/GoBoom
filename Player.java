public class Player {

    private int playerNum;
    private String[] playerCards;

    public Player(){
    }

    public String getPlayerNum(){
        return "Player" + playerNum;
    }

    public String[] getPlayerCards(){
        return playerCards;
    }

    public void setPlayerNum(int playerNum){
        this.playerNum = playerNum;
    }

    public void setPlayerCards(String[] playerCards){
        this.playerCards = playerCards;
    }
}
