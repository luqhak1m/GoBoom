import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CardImage extends Application {

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    public static final Node main = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        

        // Create a label for each card
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 13; col++) {
                Label cardLabel = createCardLabel(row, col);
                gridPane.add(cardLabel, col, row);
            }
        }

        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("GoBoom Card Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createCardLabel(int suit, int rank) {
        // Create a label with the card's suit and rank as text
        Label label = new Label(getCardText(suit, rank));
        label.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        label.setStyle("-fx-background-color: white; -fx-border-color: black;");
        return label;
    }

    private String getCardText(int suit, int rank) {
    
        String[] suits = {"♠", "♣", "♦", "♥"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        return ranks[rank] + suits[suit];
    }

    public Node start() {
        return null;
    }
}