package org.oosd.UI.sprite;

import javafx.scene.Node;

public class StarSprite implements Sprite{
    private final Node star;
    public StarSprite(){star = new Star();}

    @Override
    public Node getNode(){return star;}

    @Override
    public void setXY(double x, double y){
        star.setTranslateX(x);
        star.setTranslateY(y);
    }
}
