package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class GameAreaController {
  GameArea gameArea;

  public GameAreaController(Scene scene) {
    this.gameArea = new GameArea(scene, "gameArea");
  }

  public void setMouseEvents(Scene scene, List<RandomElement> listOfRandomElements){
    for(RandomElement randomElement : listOfRandomElements){
      randomElement.getElementArea().setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          for(Node node : gameArea.getElementArea().getChildren()){
            node.setOnMouseEntered(e -> {
              int[][] actualBlock = randomElement.getElementInArray();
              gameArea.renderElementIfPossible(actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, "#d4d0c7");
            });
            node.setOnMouseExited(e -> {
              gameArea.deleteGreyElements();
            });
            node.setOnMouseClicked(e -> {
              int[][] actualBlock = randomElement.getElementInArray();
              boolean isGood = gameArea.renderElementIfPossible(actualBlock, (int)e.getSceneX() / 50, (int)e.getSceneY() / 50, randomElement.getColor());
              if(isGood){
                Label scoreView = (Label) scene.lookup("#showScore");
                scoreView.setText(String.valueOf(Integer.parseInt(scoreView.getText()) + randomElement.getScoreForElement()));
                randomElement.renderBlank();
                checkIfAllBlank(listOfRandomElements);
                gameArea.deleteFilledRowsAndColumns();
              }
            });
          }
        }
      });
    }
  }

  public boolean checkIfAllBlank(List<RandomElement> list){
    int idx = 0;
    for(RandomElement randomElement : list){
      if(!randomElement.checkIfBlank()){
        System.out.println("emptyu: " + idx);
        return false;
      }
    }

    return true;
  }
}
