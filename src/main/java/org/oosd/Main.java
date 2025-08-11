package org.oosd;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private StackPane root;      // Main container
    private Scene scene;
    private AnimationTimer timer;

    private double dx = 3;       // X velocity
    private double dy = 3;       // Y velocity

    private final double fieldWidth = 400;
    private final double fieldHeight = 300;

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        scene = new Scene(root, fieldWidth, fieldHeight);

        showMainScreen();

        primaryStage.setTitle("JavaFX Multi-Screen Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainScreen() {
        VBox mainScreen = new VBox(10);
        mainScreen.setPadding(new Insets(20));

        Label label = new Label("Main Screen");
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> showGameScreen());

        Button configButton = new Button("Configuration");
        configButton.setOnAction(e -> showConfigScreen());


        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e->System.exit(0));

        mainScreen.getChildren().addAll(label,startButton, configButton, exitButton);
        root.getChildren().setAll(mainScreen);
    }

    private void showConfigScreen(){
        VBox configScreen = new VBox(10);
        configScreen.setPadding(new Insets(20));

        Label label = new Label("Configuration");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainScreen());

        configScreen.getChildren().addAll(label, backButton);
        root.getChildren().setAll(configScreen);
    }

    private void showGameScreen() {
        Pane gamePane = new Pane();

        // Create field border
        Rectangle field = new Rectangle(0, 0, fieldWidth, fieldHeight);
        field.setFill(Color.TRANSPARENT);
        field.setStroke(Color.BLACK);

        // Create red ball
        Circle ball = new Circle(10, Color.RED);
        ball.setCenterX(fieldWidth / 2);
        ball.setCenterY(fieldHeight / 2);

        Button backButton = new Button("Back");
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setOnAction(e -> {
            timer.stop();
            showMainScreen();
        });

        gamePane.getChildren().addAll(field, ball, backButton);

        // Key control
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                dx = 0;
                dy = -3;
            } else if (e.getCode() == KeyCode.DOWN) {
                dx = 0;
                dy = 3;
            } else if (e.getCode() == KeyCode.LEFT) {
                dx = -3;
                dy = 0;
            } else if (e.getCode() == KeyCode.RIGHT) {
                dx = 3;
                dy = 0;
            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double nextX = ball.getCenterX() + dx;
                double nextY = ball.getCenterY() + dy;

                // Bounce off edges
                if (nextX - ball.getRadius() < 0 || nextX + ball.getRadius() > fieldWidth) {
                    dx = -dx;
                }
                if (nextY - ball.getRadius() < 0 || nextY + ball.getRadius() > fieldHeight) {
                    dy = -dy;
                }

                ball.setCenterX(ball.getCenterX() + dx);
                ball.setCenterY(ball.getCenterY() + dy);
            }
        };

        timer.start();

        root.getChildren().setAll(gamePane);
        gamePane.requestFocus();  // Ensure pane gets key input
    }

    public static void main(String[] args) {
        launch(args);
    }
}
