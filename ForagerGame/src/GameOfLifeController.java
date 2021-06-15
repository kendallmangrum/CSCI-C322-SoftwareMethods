
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public class GameOfLifeController extends Observable {

    RMI model;
    GameOfLifeView view;
    int[][] grid;
    HashMap<Integer, Pair<Integer,Integer>> playerPositions;
    Random rd;
    Player p;

    public GameOfLifeController(RMI m) throws RemoteException{
        super();
        this.model = m;
        rd = new Random();
        this.grid = model.getResources();
        addPlayer();
        this.playerPositions =  getOtherPlayerPositions(p.getID());
        view = new GameOfLifeView(this,p,playerPositions,grid);
    }


    public void addPlayer() throws RemoteException{
        while(true) {
            int index = model.generatePlayerID();
            if (!getOtherPlayerPositions(-1).containsKey(index)) {
                int x = rd.nextInt(grid.length);
                int y = rd.nextInt(grid[0].length);
                p = PlayerFactory.makePlayer(x,y,index);
                model.sendPlayerPosition(p.getID(),p.getPositionX(),p.getPositionY());
                break;
            }else if(getOtherPlayerPositions(-1).size() == 3){
                System.out.println("Too many client");
                break;
            }
        }
    }

    HashMap<Integer, Pair<Integer,Integer>> getOtherPlayerPositions(int except) throws RemoteException{
        playerPositions = model.getPlayerPositions(except);
        setChanged();
        notifyObservers(this);
        return playerPositions;
    }

    public void sendPlayerPosition() throws RemoteException {
        this.model.sendPlayerPosition(this.p.getID(), this.p.getPositionX(), this.p.getPositionY());
        setChanged();
        notifyObservers(this);
    }

    public void getResources() throws RemoteException {
        int[][] temp = model.getResources();
        this.grid = temp;
        setChanged();
        notifyObservers(this);
    }

    public void gatherResource() throws RemoteException {
        int num = model.gatherResource(p.getID());
        this.grid = model.getResources();
        view.grid = grid;
        p.setResources(num);
        view.showScore();
    }

    public void run() throws RemoteException {
        getResources();
        sendPlayerPosition();
        getOtherPlayerPositions(p.getID());
    }

    public static void main(String[] args) throws RemoteException {

        try {
            Remote robj = Naming.lookup("//localhost/GameOfLifeModel");
            RMI modelServer = (RMI) robj;
            GameOfLifeController c = new GameOfLifeController(modelServer);

            //Access the services provided by the remote object.
            while(true){
                try {
                    c.run();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}