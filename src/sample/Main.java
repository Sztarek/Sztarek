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

import java.util.*;

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
        }else{
          setColorForPane(elementContainer, i, j, "#ffffff");
        }
      }
    }
    return block;
  }


  private int[][] getPlayArea(GridPane elementContainer) {
    int[][] block = new int[10][10];
    int i = 0;
    for (Node node : elementContainer.getChildren()) {
      if(i >= 100){
        break;
      }
      block[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node.getStyle().contains("#b81111") ? 1 : 0;
      i++;
    }
    return block;
  }

  private boolean checkIfPossible(GridPane gp, int[][] block, int x, int y){
    int[][] bg = getPlayArea(gp);
    List<Point> coords = mapBlockToCoords(block, x, y);
    System.out.println("------------");
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 10; j++){
        System.out.print(bg[i][j] + " ");
      }
      System.out.println();
    }
    for(Point point : coords){
      try{
        if(bg[point.x][point.y] == 1){
          return false;
        }
      }catch (Exception e){
        return false;
      }
    }

    return true;
  }

  private boolean renderElementIfPossible(GridPane gp, int[][] block, int x, int y, String color){
    boolean isPossible = checkIfPossible(gp, block, y, x);
    List<Point> coords = mapBlockToCoords(block, y, x);

    if(isPossible){
      for(Point point : coords){
        setColorForPane(gp, point.y, point.x, color);
      }
    }


    return isPossible;
  }
  private List<Point> mapBlockToCoords(int[][] block, int x, int y){
    List<Point> result = new LinkedList<>();
    for(int i = 0; i < 5; i++){
      for(int j =0; j < 5; j++){
        if(block[i][j] == 1){
          result.add(new Point(i + x, j + y));
        }
      }
    }
    return result;
  }

  private void deleteGreyElements(GridPane gp){
    for(Node node : gp.getChildren()){
      if(node.getStyle().contains("#d4d0c7")){
        setColorForPane(gp, GridPane.getColumnIndex(node), GridPane.getRowIndex(node), "#ffffff");
      }
    }
  }



  private int[][] getRandomElement(GridPane elementContainer) {
    int[][] block = new int[5][5];
    int i = 0;
    for (Node node : elementContainer.getChildren()) {
      if(i >= 25){
        break;
      }
      block[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node.getStyle().contains("#b81111") ? 1 : 0;
      i++;
    }
    return block;
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

    nextElement1.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        for(Node node : gameArea.getChildren()){
          node.setOnMouseEntered(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement1);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement1);
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement1);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              int[][] newActual = getRandomElement();
              renderRandomElement("#b81111", nextElement1);
            }
          });
        }
      }
    });

    nextElement2.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        for(Node node : gameArea.getChildren()){
          node.setOnMouseEntered(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement2);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement2);
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement2);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              int[][] newActual = getRandomElement();
              renderRandomElement("#b81111", nextElement2);
            }
          });
        }
      }
    });

    nextElement3.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        for(Node node : gameArea.getChildren()){
          node.setOnMouseEntered(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement3);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement3);
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] s = getPlayArea(gameArea);
            int[][] actualBlock = getRandomElement(nextElement3);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              int[][] newActual = getRandomElement();
              renderRandomElement("#b81111", nextElement3);
            }
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
