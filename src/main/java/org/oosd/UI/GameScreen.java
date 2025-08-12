package org.oosd.UI;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.oosd.model.Game;

public class GameScreen implements ScreenWithGame {
    private Game game;
    private final Frame parent;
    private Screen mainScreen;

    // UI
    private BorderPane borderPane;
    private Pane playField;
    private Circle ball;

    // Loop
    private AnimationTimer timer;

    public GameScreen(Frame frame){
        this.parent = frame;
        this.borderPane = new BorderPane();

        borderPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                buildScreen();
                setControl(newScene);
                startGame();
            }
        });
    }

    private void buildScreen(){
        StackPane playFieldWrapper = new StackPane(getGameField());
        playFieldWrapper.setPadding(new Insets(10, 0, 10, 10));
        playFieldWrapper.setAlignment(Pos.TOP_CENTER);
        borderPane.setTop(playFieldWrapper);
        borderPane.setBottom(getBottomPane());
    }

    private Pane getGameField() {
        playField = new Pane();
        playField.setPrefSize(Game.fieldWidth, Game.fieldHeight);
        playField.setMinSize(Game.fieldWidth, Game.fieldHeight);
        playField.setMaxSize(Game.fieldWidth, Game.fieldHeight);

        Rectangle field = new Rectangle(Game.fieldWidth, Game.fieldHeight);
        field.setFill(Color.TRANSPARENT);
        field.setStroke(Color.BLACK);
        field.setStrokeWidth(1.5);

        ball = new Circle(game.getSize(), game.getColor());
        ball.setCenterX(Game.fieldWidth / 2);
        ball.setCenterY(Game.fieldHeight / 2);
        if (game.isHasShadow()){
            DropShadow shadow = new DropShadow();
            shadow.setOffsetX(5);
            shadow.setOffsetY(5);
            ball.setEffect(shadow);
        }

        playField.getChildren().setAll(field, ball);
        return playField;
    }

    private Node getBottomPane() {
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(e -> {
            if (timer != null) timer.stop();
            parent.showScreen(mainScreen);
        });

        StackPane bottomPane = new StackPane(backButton);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(0, 0, 20, 0));
        return bottomPane;
    }

    private void setControl(Scene scene){
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {game.increaseY();}
            else if (e.getCode() == KeyCode.DOWN) {game.decreaseY();}
            else if (e.getCode() == KeyCode.LEFT) {game.decreaseX();}
            else if (e.getCode() == KeyCode.RIGHT) {game.increaseX();}
        });
    }

    private void startGame(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double nextX = ball.getCenterX() + game.getDx();
                double nextY = ball.getCenterY() + game.getDy();

                // Bounce off edges
                if (nextX - ball.getRadius() < 0 || nextX + ball.getRadius() > Game.fieldWidth) {
                    game.setDx (-game.getDx());}
                if (nextY - ball.getRadius() < 0 || nextY + ball.getRadius() > Game.fieldHeight) {
                    game.setDy (-game.getDy());}

                ball.setCenterX(ball.getCenterX() + game.getDx());
                ball.setCenterY(ball.getCenterY() + game.getDy());
            }
        };
        timer.start();
    }

    @Override
    public Node getScreen(){return borderPane;}

    @Override
    public void setGame(Game game) {this.game = game;}

    @Override
    public void setRoute (String path, Screen screen){
        if ("back".equals(path)){
            mainScreen = screen;
        }
    }
}