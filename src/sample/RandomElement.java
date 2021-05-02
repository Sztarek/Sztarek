package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class RandomElement extends GridPaneElement {
  private String color;
  public RandomElement(Scene scene, String id, String color) {
    super(scene, id);
    this.color = color;
    renderRandomElement();
  }

  public int[][] renderRandomElement(){
    color = BlockTypes.getRandomCoolor();
    int[][] block = BlockTypes.getRandomElement();
    for(int i = 0; i < block.length; i++){
      for(int j = 0; j < block[i].length; j++){
        if(block[i][j] == 1){
          setColorForPane(i, j, color);
        }else{
          setColorForPane(i, j, "#ffffff");
        }
      }
    }
    return block;
  }

  public String getColor() {
    return color;
  }

  int getScoreForElement(){
    int score = 0;
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        if(getElementInArray()[i][j] == 1){
          score++;
        }
      }
    }
    return score;
  }

  public boolean checkIfBlank(){
    System.out.println("checking if blank: " + this.color);
    for(Node node : this.elementArea.getChildren()){
      int idx = 0;
      if(!node.getStyle().contains("#ffffff")){
        idx++;
        System.out.println("It doesnt contain white: " + idx);
        return false;
      }
    }
    return true;
  }
}
