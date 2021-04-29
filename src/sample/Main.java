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


  private boolean setColorForPane(GridPane gridPane, int col, int row, String color) {
    for (Node node : gridPane.getChildren()) {
      if (GridPane.getColumnIndex(node) == null || GridPane.getRowIndex(node) == null){
        continue;
      }
      if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
        setColorForNode(node, color);
        return true;
      }
    }
    return false;
  }

  private int[][] renderRandomElement(String color, GridPane elementContainer){
    int[][] block = BlockTypes.getRandomElement();
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

  private void renderBlank(GridPane element) {
    for(Node node : element.getChildren()){
      node.setStyle("-fx-background-color: #ffffff");
    }
  }

  private boolean checkIfBlank(GridPane e){
    for(Node node : e.getChildren()){
      if(!node.getStyle().contains("#ffffff")){
        return false;
      }
    }
    return true;
  }

  private boolean checkIfAllBlank(GridPane e1, GridPane e2, GridPane e3){
    return checkIfBlank(e1) && checkIfBlank(e2) && checkIfBlank(e3);
  }

  private void deleteGreyElements(GridPane gp){
    for(Node node : gp.getChildren()){
      if(node.getStyle().contains("#d4d0c7")){
        setColorForPane(gp, GridPane.getColumnIndex(node), GridPane.getRowIndex(node), "#ffffff");
      }
    }
  }

  private void setColorForNode(Node node, String color){
    node.setStyle("-fx-background-color: "+color);
  }

  private List<Integer> checkForFilledRowIndecies(GridPane gp){
    int[] rows = new int[10];
    for(Node node : gp.getChildren()){
      if(GridPane.getRowIndex(node) == null){
        continue;
      }
      if(!node.getStyle().contains("#ffffff")){
        rows[GridPane.getRowIndex(node)]++;
      }
    }
    List<Integer> filledRows = new LinkedList<>();
    for(int i = 0; i < 10; i++){
      if(rows[i] == 10){
        filledRows.add(i);
      }
    }

    return filledRows;
  }

  private List<Integer> checkForFilledColumnIndecies(GridPane gp){
    int[] columns = new int[10];
    for(Node node : gp.getChildren()){
      if(GridPane.getColumnIndex(node) == null){
        continue;
      }
      if(!node.getStyle().contains("#ffffff")){
        System.out.println("COL INDEX: " + GridPane.getColumnIndex(node));
        columns[GridPane.getColumnIndex(node)]++;
      }
    }
    List<Integer> filledColumns = new LinkedList<>();
    for(int i = 0; i < 10; i++){
      if(columns[i] == 10){
        filledColumns.add(i);
      }
    }
    System.out.println(filledColumns.toString());
    return filledColumns;
  }

  private void deleteFilledRowsAndColumns(GridPane gp){
    List<Integer> rows = checkForFilledRowIndecies(gp);
    List<Integer> columns = checkForFilledColumnIndecies(gp);


    for(Node node : gp.getChildren()){
      if(rows.contains(GridPane.getRowIndex(node))){
        setColorForNode(node, "#ffffff");
        continue;:x:
      }
      if(columns.contains(GridPane.getColumnIndex(node))){
        setColorForNode(node, "#ffffff");
      }
    }
  }

  private void reRenderAllNextElements(GridPane e1, GridPane e2, GridPane e3){
    System.out.println("rerender checking");
    if(checkIfAllBlank(e1, e2, e3)){
      System.out.println("woah, it's empty");
      renderRandomElement("#b81111", e1);
      renderRandomElement("#b81111", e2);
      renderRandomElement("#b81111", e3);
    }
  }

  private int getScoreForElement(int[][] block){
    int score = 0;
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        if(block[i][j] == 1){
          score++;
        }
      }
    }
    return score;
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
    renderBlank(gameArea);
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

    renderRandomElement("#b81111", nextElement1);
    renderRandomElement("#b81111", nextElement2);
    renderRandomElement("#b81111", nextElement3);


    nextElement1.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        for(Node node : gameArea.getChildren()){
          node.setOnMouseEntered(e -> {
            int[][] actualBlock = getRandomElement(nextElement1);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] actualBlock = getRandomElement(nextElement1);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              Label scoreView = (Label) scene.lookup("#showScore");
              System.out.println(scoreView.toString());
              scoreView.setText(Integer.valueOf(scoreView.getText() + getScoreForElement(actualBlock)).toString());
              renderBlank(nextElement1);
              reRenderAllNextElements(nextElement1, nextElement2, nextElement3);
              deleteFilledRowsAndColumns(gameArea);
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
            int[][] actualBlock = getRandomElement(nextElement2);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] actualBlock = getRandomElement(nextElement2);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              Label scoreView = (Label) scene.lookup("#showScore");
              scoreView.setText(Integer.valueOf(scoreView.getText() + getScoreForElement(actualBlock)).toString());
              renderBlank(nextElement2);
              reRenderAllNextElements(nextElement1, nextElement2, nextElement3);
              deleteFilledRowsAndColumns(gameArea);
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
            int[][] actualBlock = getRandomElement(nextElement3);
            renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
          });
          node.setOnMouseExited(e -> {
            deleteGreyElements(gameArea);
          });
          node.setOnMouseClicked(e -> {
            int[][] actualBlock = getRandomElement(nextElement3);
            boolean isGood = renderElementIfPossible(gameArea, actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#b81111");
            if(isGood){
              Label scoreView = (Label) scene.lookup("#showScore");
              scoreView.setText(Integer.valueOf(scoreView.getText() + getScoreForElement(actualBlock)).toString());
              renderBlank(nextElement3);
              reRenderAllNextElements(nextElement1, nextElement2, nextElement3);
              deleteFilledRowsAndColumns(gameArea);
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
