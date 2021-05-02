package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public abstract class GridPaneElement {
  public GridPane getElementArea() {
    return elementArea;
  }

  protected GridPane elementArea;
  private int row;
  private int col;
  public GridPaneElement(Scene scene, String id){
    this.elementArea = (GridPane) scene.lookup("#" + id);
    this.row = elementArea.getRowCount();
    this.col = elementArea.getColumnCount();
    renderBlank();
  }


  public void renderBlank() {
    for (Node node : this.elementArea.getChildren()) {
      node.setStyle("-fx-background-color: #ffffff");
    }
  }

  public int[][] getElementInArray() {
    int[][] block = new int[this.row][this.col];
    int i = 0;
    for (Node node : elementArea.getChildren()) {
      if(i >= this.row * this.col){
        break;
      }
      block[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] =
        !(node.getStyle().contains("#ffffff") || node.getStyle().contains("#d4d0c7"))
        ? 1 : 0;
      i++;
    }
    return block;
  }

  protected void setColorForNode(Node node, String color){
    node.setStyle("-fx-background-color: "+color);
  }





  public void writeArrayToConsole(){
    int[][] arr = getElementInArray();
    for(int i = 0; i < arr.length; i++){
      for(int j = 0; j < arr[i].length; j++){
        System.out.print(arr[i][j] + " ");
      }
      System.out.println();
    }
  }

  protected boolean setColorForPane(int col, int row, String color) {
    for (Node node : this.elementArea.getChildren()) {
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
}
