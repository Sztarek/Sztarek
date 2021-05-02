package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;
import java.util.List;

public class GameArea extends GridPaneElement {

  public GameArea(Scene scene, String id) {
    super(scene, id);
  }


  public boolean renderElementIfPossible(int[][] block, int x, int y, String color){
    boolean isPossible = checkIfPossible(block, y, x);
    List<Point> coords = mapBlockToCoords(block, y, x);
    if(isPossible){
      for(Point point : coords){
        setColorForPane(point.y, point.x, color);
      }
    }


    return isPossible;
  }

  public void deleteGreyElements(GridPane gp){
    for(Node node : gp.getChildren()){
      if(node.getStyle().contains("#d4d0c7")){
        setColorForPane(GridPane.getColumnIndex(node), GridPane.getRowIndex(node), "#ffffff");
      }
    }
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

  private boolean checkIfPossible(int[][] block, int x, int y){
    int[][] bg = getElementInArray();
    List<Point> coords = mapBlockToCoords(block, x, y);
    for(Point point : coords){
      try{
        if(bg[point.x][point.y] == 1){
          return false;
        }
      }catch (Exception e){
        System.out.println(e.getMessage());
        return false;
      }
    }

    return true;
  }

  private List<Integer> checkForFilledRowIndecies(){
    int[] rows = new int[10];
    for(Node node : getElementArea().getChildren()){
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

  private List<Integer> checkForFilledColumnIndecies(){
    int[] columns = new int[10];
    for(Node node : getElementArea().getChildren()){
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

  public int deleteFilledRowsAndColumns(){
    List<Integer> rows = checkForFilledRowIndecies();
    List<Integer> columns = checkForFilledColumnIndecies();


    int addedScore = 0;
    for(Node node : getElementArea().getChildren()){
      if(rows.contains(GridPane.getRowIndex(node))){
        System.out.println("deleted element: " + GridPane.getColumnIndex(node) + GridPane.getRowIndex(node));
        setColorForNode(node, "#ffffff");
        addedScore++;
        continue;
      }
      if(columns.contains(GridPane.getColumnIndex(node))){
        setColorForNode(node, "#ffffff");
        addedScore++;
      }
    }
    return addedScore;
  }

  public void deleteGreyElements(){
    for(Node node : this.elementArea.getChildren()){
      if(node.getStyle().contains("#d4d0c7")){
        setColorForPane(GridPane.getColumnIndex(node), GridPane.getRowIndex(node), "#ffffff");
      }
    }
  }
}
