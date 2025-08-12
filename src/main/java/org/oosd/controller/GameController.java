package org.oosd.controller;

import javafx.scene.input.KeyCode;
import org.oosd.model.Game;

public class GameController {
    private final Game game;

    public GameController(Game game) {this.game = game;}

    public void receiveKeyPress(KeyCode key) {
        switch (key) {
            case KeyCode.UP -> game.increaseY();
            case KeyCode.DOWN -> game.decreaseY();
            case KeyCode.LEFT -> game.increaseX();
            case KeyCode.RIGHT -> game.decreaseX();
        }
    }
}
