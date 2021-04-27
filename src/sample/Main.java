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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main extends Application {

  private boolean setColorForPane(GridPane gridPane, int col, int row, String color) {
    for (Node node : gridPane.getChildren()) {
      if (GridPane.getColumnIndex(node) == null || GridPane.getRowIndex(node) == null){
        continue;
      }
      if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
        node.setStyle("-fx-background-color:" + color);
        return true;
      }
    }
    return false;
  }

  private int[][] getRandomElement(){
    Random r = new Random();
    return BlockTypes.blockTypes[r.nextInt(BlockTypes.blockTypes.length)];
  }

  private int[][] renderRandomElement(String color, GridPane elementContainer){
    int[][] block = getRandomElement();
    for(int i = 0; i < block.length; i++){
      for(int j = 0; j < block[i].length; j++){
        if(block[i][j] == 1){
          setColorForPane(elementContainer, i, j, color);
        }
      }
    }
    return block;
  }


  private String[][] getPlayArea(GridPane elementContainer) {
    String[][] block = new String[10][10];
    int i = 0;
    for (Node node : elementContainer.getChildren()) {
      if(i >= 100){
        break;
      }
      block[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node.getStyle();
      i++;
    }
    return block;
  }

  private boolean renderGreyElementIfPossible(GridPane gp, int[][] block, int x, int y){
    boolean isPossible = true;
    List<Point> coordMap = mapBlockToCoords(block,x , y);
    for(Node node : gp.getChildren()){
      try{
        int actualX = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : -1;
        int actualY = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : -1;
        if(node.getStyle().contains("#b81111") && coordMap.get(actualX) != null){
          if(coordMap.get(actualX) == actualY){
            isPossible = false;
          }
        }
      }catch(Exception e){
        isPossible = false;
      }
    }

    System.out.println(coordMap);

    if(isPossible){
      coordMap.forEach((i, j) -> {
        setColorForPane(gp, i, j, "#acafb0");
      });
    }

    return isPossible;
  }
  private List<Point> mapBlockToCoords(int[][] block, int x, int y){
    List<Point> result = new List<>();
    for(int i = 0; i < 5; i++){
      for(int j =0; j < 5; j++){
        if(block[i][j] == 1){
          result.put(i + x, j + y);
        }
      }
    }
    return result;
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("jebanza");
    Scene scene = new Scene(root, 1000, 770);
    GridPane gameArea = (GridPane) scene.lookup("#gameArea");
    for(Node node : gameArea.getChildren()){
      node.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
        }
      });
    }
    GridPane nextElement1 = (GridPane) scene.lookup("#nextElement1");
    GridPane nextElement2 = (GridPane) scene.lookup("#nextElement2");
    GridPane nextElement3 = (GridPane) scene.lookup("#nextElement3");
    int[][] newBlock1 = renderRandomElement("#b81111", nextElement1);
    int[][] newBlock2 = renderRandomElement("#b81111", nextElement2);
    int[][] newBlock3 = renderRandomElement("#b81111", nextElement3);

    nextElement1.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        int[][] actualBlock = newBlock1;
        String[][] s = getPlayArea(gameArea);
        for(Node node : gameArea.getChildren()){
          node.setOnMouseEntered(e -> {
            renderGreyElementIfPossible(gameArea, newBlock1, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50);
          });
        }
      }
    });

    boolean isColored = setColorForPane(gameArea, 8, 0, "#b81111");
    System.out.println(isColored);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
