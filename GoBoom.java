import java.util.ArrayList;
import java.util.Collections;

public class GoBoom {
    private static final String[] ranks = {"A", "5", "9", "K", "2", "6", "10", "3", "7", "J", "4", "8", "Q"};
    private static final String[] players = {"Player1", "Player2", "Player3", "Player4"};
    private static final int numOfPlayers = 4;
    private static final int numOfCards = 7;

    public static void main(String[] args) {
        // create a new deck
        ArrayList<Card> deck = new ArrayList<>();

        // create cards with ranks and suits
        String[] suits = {"h", "d", "c", "s"};
        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(rank, suit);
                deck.add(card);
            }
        }

        // shuffle the deck
        Collections.shuffle(deck);

        // get the lead card
        Card leadCard = deck.remove(0);

        // determine the first player based on the lead card
        int firstPlayerIndex = getFirstPlayerIndex(leadCard);

        //create players and deal cards
        ArrayList<Player> playerList = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            int currentPlayerIndex = (firstPlayerIndex + i) % numOfPlayers;
            ArrayList<Card> playerCards = new ArrayList<>();
            for (int j = 0; j < numOfCards; j++) {
                Card card = deck.remove(0);
                playerCards.add(card);
            }
            Player player = new Player(players[currentPlayerIndex], playerCards);
            playerList.add(player);
        }

        // print the players' cards
        printPlayerCards(playerList);

       // print the lead card
       System.out.println("Center: [" + leadCard + "]");

       // print the remaining deck
       System.out.print("Deck: ");
       printCards(deck);

       // print the score
       System.out.println("Score: " + getPlayerScores(playerList));

       // print the current turn
       System.out.println("Turn: " + getCurrentTurn(playerList, firstPlayerIndex));

    }

    private static int getFirstPlayerIndex(Card leadCard) {
        String rank = leadCard.getRank();
        if (rank.equals("A") || rank.equals("5") || rank.equals("9") || rank.equals("K")) {
            return 0; // Player1 goes first
        } else if (rank.equals("2") || rank.equals("6") || rank.equals("10")) {
            return 1; // Player2 goes first
        } else if (rank.equals("3") || rank.equals("7") || rank.equals("J")) {
            return 2; // Player3 goes first
        } else if (rank.equals("4") || rank.equals("8") || rank.equals("Q")) {
            return 3; // Player4 goes first
        } else {
            return 0; // Default to Player1
        }
    }

    private static void printPlayerCards(ArrayList<Player> playerList) {
        for (String playerName : players) {
            for (Player player : playerList) {
                if (player.getName().equals(playerName)) {
                    System.out.print(player.getName() + ": ");
                    printCards(player.getCards());
                    break;
                }
            }
        }
    }

    // private static void printPlayerCards(ArrayList<Player> playerList) {
    //     for (Player player : playerList) {
    //         System.out.print(player.getName() + ": ");
    //         printCards(player.getCards());
    //     }
    // }

    private static void printCards(ArrayList<Card> cards) {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append(", ");
        }
        String cardList = sb.toString().trim();
        if (!cardList.isEmpty()) {
            cardList = cardList.substring(0, cardList.length() - 1);
        }
        System.out.println("[" + cardList + "]");
    }

    private static String getPlayerScores(ArrayList<Player> playerList) {
        StringBuilder sb = new StringBuilder();
        for (Player player : playerList) {
            sb.append(player.getName()).append(" = ").append(player.getScore()).append(" | ");
        }
        String scores = sb.toString().trim();
        if (!scores.isEmpty()) {
            scores = scores.substring(0, scores.length() - 1);
        }
        return scores;
    }

    private static String getCurrentTurn(ArrayList<Player> playerList, int firstPlayerIndex) {
        int currentTurnIndex = firstPlayerIndex % numOfPlayers;
        return playerList.get(currentTurnIndex).getName();
    }
}