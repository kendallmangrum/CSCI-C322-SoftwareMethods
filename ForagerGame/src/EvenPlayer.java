import java.awt.*;

import static java.awt.Color.*;

public class EvenPlayer extends PlayerType {

    public EvenPlayer(int x,int y, int id){
        super(x,y,id);
    }

    @Override
    public void draw(Graphics2D g2) {
        int i = this.getPositionX();
        int j = this.getPositionY();
        g2.setColor(new Color(0xFF5D0076, true));
        g2.fillOval(i + (10 * i) + 10, j + (10 * j) + 10, 10, 10);
    }
}
