import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GoboomGUI extends Application{   // inheritance

    private Button button1, button2, button3, button4;
    private Text text;
    //private HBox cardContainer;
    private Thread mainThread;
    

    
    //Turn turn;
    // private static final int CARD_WIDTH = 80;
    // private static final int CARD_HEIGHT = 120;
    // Image getImage;
    //private Object gameTurn;

    public static void main(String[] args) {
      launch(args); // launch method invokes start method.
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

      //root
      Pane mainPane = new Pane();
      mainPane.setPrefSize(1000, 600);

      //root layout
      HBox rootLayout = new HBox();
      rootLayout.setPrefSize(1000, 600);
      rootLayout.setPadding(new Insets(0, 0, 0, 0));
      
      
      //card
      // cardContainer = new HBox(10);
      // cardContainer.setPadding(new Insets(CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT, CARD_WIDTH));

      Scene scene = new Scene(mainPane);//set mainPane of the scene
      

      //Text on the page
      text = new Text();
      text.setText("WELCOME TO" +"\n" + "  GOBOOM!!" );
      text.setVisible(true);
      text.setFont(Font.font(50));
      text.setFill(Color.WHITE);

      //Button
      button1 = new Button("New Game");
      button1.setOnAction(e -> ButtonClickHandler(e));
      button1.setPrefSize(200, 100);

      button2 = new Button("Restart Game");
      button2.setOnAction(e -> ButtonClickHandler(e));
      button2.setPrefSize(200, 100);

      button3 = new Button("Resume Game");
      button3.setOnAction(e -> ButtonClickHandler(e));
      button3.setPrefSize(200, 100);

      button4 = new Button("Quit Game");
      button4.setOnAction(e -> ButtonClickHandler(e));
      button4.setPrefSize(200, 100);
      

      //Box for text
      VBox textBox = new VBox(text);
      textBox.setPrefSize(800, 600);
      textBox.setAlignment(Pos.CENTER_RIGHT);
      textBox.setMargin(text, new Insets(0,250,0,50));
      textBox.setPadding(new Insets(0, 20, 0, 0));
      Rectangle gameBg = new Rectangle(800, 600);
      gameBg.setFill(Color.DARKGREEN);

      //textBox.getChildren().add(gameBg);

      //new box
      // VBox newGameBox = new VBox();
      // newGameBox.setPrefSize(800, 600);
      // newGameBox.setAlignment(Pos.CENTER_RIGHT);
      // newGameBox.setMargin(text, new Insets(0,250,0,50));
      // newGameBox.setPadding(new Insets(0, 20, 0, 0));
      // Rectangle newgameBg = new Rectangle(800, 600);
      // newgameBg.setFill(Color.DARKGREEN);


      //box for button
      VBox buttonBox = new VBox(button1, button2, button3, button4);
      buttonBox.setPrefSize(200, 600);
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.setMargin(buttonBox, new Insets(0,50,0,80));
      buttonBox.setPadding(new Insets(0, 20, 0, 20));
      Rectangle buttonBg = new Rectangle(200, 600);
      buttonBg.setFill(Color.LIGHTGREY);

   
      //Display on game screen
      rootLayout.getChildren().addAll(new StackPane(buttonBg,buttonBox),new StackPane(gameBg,textBox));
      mainPane.getChildren().add(rootLayout);
      mainPane.setPrefWidth(1000);
      mainPane.setPrefHeight(600);
      mainPane.setPadding(new Insets(0,0, 0, 0));

      Image icon = new Image("pokercard.jpg");//will display pokercard icon when run
      primaryStage.getIcons().add(icon);
      primaryStage.setTitle("GoBoom");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      //primaryStage.setScene();
      primaryStage.show();
      primaryStage.setOnCloseRequest(event -> {
        stopMainThread();
      });
    }
  
  private void ButtonClickHandler(ActionEvent e){
    
    // @Override
    // public void handle(ActionEvent event) {
    text.setVisible(false);

      if (e.getSource()==button1){
        System.out.println("New Game");
      }
      else if(e.getSource()==button2){
          System.out.println("Restart Game");
      }
      else if(e.getSource()==button3){
          System.out.println("Resume Game");
      }
      else if(e.getSource()==button4){
          System.out.println("Quit Game");
          stopMainThread();
          Platform.exit();
      }
        
    //}
  }

  private void stopMainThread(){
    if (mainThread != null && mainThread.isAlive()) {
          mainThread.interrupt();}
  }

  public void setMainThread(Thread mainThread){
    this.mainThread = mainThread;
  }

  public void play(){
    GoBoom.playGame();
  }
    
}





