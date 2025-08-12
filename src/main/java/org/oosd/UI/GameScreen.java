package org.oosd.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.oosd.model.Game;

public class GameScreen implements ScreenWithGame {
    private final GamePane gamePane;
    private final Frame parent;
    private BorderPane borderPane;
    private Screen mainScreen;

    public GameScreen(Frame frame){
        parent = frame;
        gamePane = new GamePane();
        buildScreen();

        gamePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                gamePane.startGame();
            }
        });
    }

    private StackPane getBottomPane() {
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("menu-button");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(e -> {
            gamePane.stopGame();
            parent.showScreen(mainScreen);
        });

        StackPane bottomPane = new StackPane(backButton);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(0, 0, 20, 0));
        bottomPane.setFocusTraversable(false);
        return bottomPane;
    }

    private void buildScreen(){
        borderPane = new BorderPane();
        StackPane playFieldWrapper = new StackPane(gamePane);
        playFieldWrapper.setPadding(new Insets(10, 0, 10, 10));
        playFieldWrapper.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(playFieldWrapper);
        borderPane.setBottom(getBottomPane());
    }

    @Override
    public Node getScreen(){return borderPane;}

    @Override
    public void setGame(Game game) {gamePane.setGame(game);}

    @Override
    public void setRoute (String path, Screen screen){
        if ("back".equals(path)){
            mainScreen = screen;
        }
    }
}