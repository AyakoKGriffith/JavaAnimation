package org.oosd.model;

public abstract class GameEntity {
    protected int x,y;
    protected int dx,dy;
    protected int size;

    boolean isVisible(){
        return false;
    }
    abstract void process();
}
