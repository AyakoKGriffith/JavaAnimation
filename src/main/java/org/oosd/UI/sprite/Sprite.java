package org.oosd.UI.sprite;

import javafx.scene.Node;

public interface Sprite {
    Node getNode(); // return the Node object that can be displayed on the GamePane
    void setXY(double x, double y); // set the location of the Sprite
}
