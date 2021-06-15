import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public interface RMI extends java.rmi.Remote{
     HashMap<Integer, Pair<Integer,Integer>> getPlayerPositions(int except) throws RemoteException;
     void sendPlayerPosition(int playerID, int positionX, int positionY) throws RemoteException;
     int gatherResource(int forPlayer) throws RemoteException;
     int[][] getResources() throws RemoteException;
     int generatePlayerID() throws RemoteException;
}
