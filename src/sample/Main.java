package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableIntegerArray;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

  private int geScore(Scene scene){
    Label scoreView = (Label) scene.lookup("#showScore");
    return Integer.parseInt(scoreView.getText());
  }



  private void setScore(Scene scene, int score){
    Label scoreView = (Label) scene.lookup("#showScore");
    scoreView.setText(String.valueOf(score));
  }


  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("jebanza");
    Scene scene = new Scene(root, 1000, 770);


    System.out.println("jebanza");
    RandomElement nextElement1 = new RandomElement(scene, "nextElement1", BlockTypes.getRandomCoolor());
    RandomElement nextElement2 = new RandomElement(scene, "nextElement2", BlockTypes.getRandomCoolor());
    RandomElement nextElement3 = new RandomElement(scene, "nextElement3", BlockTypes.getRandomCoolor());

    List<RandomElement> randomElements = new ArrayList<>();
    randomElements.add(nextElement1);
    randomElements.add(nextElement2);
    randomElements.add(nextElement3);
    GameAreaController gameAreaController = new GameAreaController(scene);
    gameAreaController.setMouseEvents(scene, randomElements);


    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
