import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.*;

import static java.awt.Color.*;

public class GameOfLifeView extends JComponent implements KeyListener, Observer {

    JFrame aJFrame;
    JFrame scoreJFrame;
    JLabel sl;
    int[][] grid;
    HashMap<Integer, Pair<Integer,Integer>> playerPositions;
    Graphics2D g2;
    Player p;
    GameOfLifeController c;

    public GameOfLifeView(GameOfLifeController controller, Player p, HashMap<Integer, Pair<Integer,Integer>> playerPositions , int[][] grid) throws RemoteException {
        scoreJFrame = new JFrame();
        scoreJFrame.setSize(300,100);
        scoreJFrame.setBackground(WHITE);
        scoreJFrame.setResizable(false);
        scoreJFrame.setVisible(true);
        sl = new JLabel("Your Score");
        sl.setFont(new Font("Helvetica", Font.BOLD, 20));
        sl.setBounds(10,10, 75, 40);
        scoreJFrame.add(sl);
        aJFrame = new JFrame();
        aJFrame.add(this);
        aJFrame.setLocation(400,10);
        aJFrame.setSize(800, 800);
        aJFrame.setBackground(BLACK);
        aJFrame.setVisible(true);
        aJFrame.addKeyListener(this);
        aJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.c = controller;
        this.grid = grid;
        this.playerPositions = playerPositions;
        this.p = p;
        controller.addObserver(this);
        run(controller);
    }

    public void display(int[][] grid){
        Graphics g = getGraphics();
        g2 = (Graphics2D) g;
        for(int i = 0; i < 68; i++) {
            for (int j = 0; j < 68; j++) {
                Boolean playerSquare = false;
                for(Map.Entry<Integer, Pair<Integer,Integer>> pl : playerPositions.entrySet()) {
                    if(pl.getValue().getA() == i && pl.getValue().getB() == j) {
                        playerSquare = true;
                    }
                }
                if(p.getPositionX() == i && p.getPositionY() == j) {
                    playerSquare = true;
                }
                if (grid[i][j] == 0 && !playerSquare) {
                    g2.setColor(WHITE);
                    g2.fillRect(i + (10 * i) + 10, j + (10 * j) + 10, 10, 10);
                }else if(grid[i][j] == 1 && !playerSquare){
                    g2.setColor(new Color(105, 255, 105));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 2 && !playerSquare){
                    g2.setColor(new Color(26, 200, 26));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 3 && !playerSquare){
                    g2.setColor(new Color(0, 150, 0));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 4 && !playerSquare){
                    g2.setColor(new Color(0, 100, 0));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 5 && !playerSquare){
                    g2.setColor(new Color(0, 92, 0));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 6 && !playerSquare){
                    g2.setColor(new Color(1, 73, 1));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else if(grid[i][j] == 7 && !playerSquare) {
                    g2.setColor(new Color(0, 45, 0));
                    g2.fillRect(i+(10*i)+10, j+(10*j)+10, 10, 10);
                }else{
                    continue;
                }
            }
        }

        //draw other player
        for (Map.Entry<Integer, Pair<Integer,Integer>> e : playerPositions.entrySet()){
            g2.setColor(new Color(0x90520E));
            int i = e.getValue().getA();
            int j = e.getValue().getB();
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(i+(10*i)+10+5, j+(10*j)+10+2,i+(10*i)+10+5, j+(10*j)+10+8);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(i+(10*i)+10+2, j+(10*j)+10+5,i+(10*i)+10+8, j+(10*j)+10+5);
        }

        p.draw(g2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //87 'W'
        if (e.getKeyCode() == 87 && (p.getPositionY() - 1) >= 0) {
            boolean b1 = true;
            for (Map.Entry<Integer, Pair<Integer,Integer>> e1 : playerPositions.entrySet()) {
                int i = e1.getValue().getA();
                int j = e1.getValue().getB();
                int x = p.getPositionX();
                int y = p.getPositionY()-1;
                if(i==x && j==y){
                    b1 = false;
                }
            }
            if(b1){
                p.moveUp();
                try {
                    c.gatherResource();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        }
        //83 'S'
        else if (e.getKeyCode() == 83 && (p.getPositionY() + 1) < 68) {
            boolean b1 = true;
            for (Map.Entry<Integer, Pair<Integer,Integer>> e1 : playerPositions.entrySet()) {
                int i = e1.getValue().getA();
                int j = e1.getValue().getB();
                int x = p.getPositionX();
                int y = p.getPositionY()+1;
                if(i==x && j==y){
                    b1 = false;
                }
            }
            if(b1){
                p.moveDown();
                try {
                    c.gatherResource();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        }
        //65 'A'
        else if (e.getKeyCode() == 65 && (p.getPositionX() - 1) >= 0) {
            boolean b1 = true;
            for (Map.Entry<Integer, Pair<Integer,Integer>> e1 : playerPositions.entrySet()) {
                int i = e1.getValue().getA();
                int j = e1.getValue().getB();
                int x = p.getPositionX()-1;
                int y = p.getPositionY();
                if(i==x && j==y){
                    b1 = false;
                }
            }
            if(b1){
                p.moveLeft();
                try {
                    c.gatherResource();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        }
        //68 'D'
        else if (e.getKeyCode() == 68 && (p.getPositionX() + 1) < 68) {
            boolean b1 = true;
            for (Map.Entry<Integer, Pair<Integer,Integer>> e1 : playerPositions.entrySet()) {
                int i = e1.getValue().getA();
                int j = e1.getValue().getB();
                int x = p.getPositionX()+1;
                int y = p.getPositionY();
                if(i==x && j==y){
                    b1 = false;
                }
            }
            if(b1){
                p.moveRight();
                try {
                    c.gatherResource();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        }
    }
    public void showScore() {
        sl.setText("Your Score: "+p.getResources());
        scoreJFrame.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            run((GameOfLifeController) arg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void run(GameOfLifeController c) throws RemoteException {
        this.grid = c.grid;
        this.playerPositions = c.playerPositions;
        this.p = c.p;
        display(grid);
    }
}
