import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

import static java.awt.Color.*;

public class PlayerType implements Player{
    private int id;
    private int positionX;
    private int positionY;
    private int collectedResources;

    public PlayerType(int x,int y, int id){
        this.positionX = x;
        this.positionY = y;
        this.collectedResources = 0;
        this.id = id;
    }
    @Override
    public int getID(){
        return id;
    }
    @Override
    public int getPositionX(){
        return positionX;
    }
    @Override
    public int getPositionY() {
        return positionY;
    }
    @Override
    public void setPositionX(int x){
        this.positionX =x;
    }
    @Override
    public void setPositionY(int y){
       this.positionY=y;
    }
    @Override
    public void setPosition(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
    @Override
    public Pair<Integer, Integer> getPosition(){
         return new Pair<Integer,Integer>(positionX,positionY);
    }
    @Override
    public void setResources(int resourceLevel){
        collectedResources = resourceLevel;
    }
    @Override
    public int getResources(){
        return collectedResources;
    }
    @Override
    public void moveUp(){
        this.setPositionY(this.getPositionY()-1);
    }
    @Override
    public void moveDown(){
        this.setPositionY(this.getPositionY()+1);
    }
    @Override
    public void moveLeft(){
        this.setPositionX(this.getPositionX()-1);
    }
    @Override
    public void moveRight(){
        this.setPositionX(this.getPositionX()+1);
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
