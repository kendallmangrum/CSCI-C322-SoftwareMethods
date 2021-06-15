import java.awt.*;

public interface Player {
    int getID();
    int getPositionX();
    int getPositionY();
    void setPositionX(int x);
    void setPositionY(int y);
    void setPosition(int positionX, int positionY);
    Pair<Integer, Integer> getPosition();
    void setResources(int resourceLevel);
    int getResources();
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void draw(Graphics2D g);
}
