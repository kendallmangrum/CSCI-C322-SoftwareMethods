import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GameOfLifeModel extends UnicastRemoteObject implements RMI {

    int[][] grid2;
    int gridWidthHeight;
    Random rd;
    HashMap<Integer, Player> players;
    int index;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static int numUpdates = 0;
    private static int gameSecondsLeft = 300;


    public GameOfLifeModel(String file_name) throws RemoteException, FileNotFoundException {
        super();
        this.gridWidthHeight = 67;
        grid2 = new int[this.gridWidthHeight+1][this.gridWidthHeight+1];
        //initialize grid
        File f = new File(file_name+".txt");
        Scanner reader = new Scanner(f);
        int l=0;
        int k=0;
        while (reader.hasNextInt()){
            int next = reader.nextInt();
            grid2[l][k] = next;
            k++;
            if(k==68){
                l++;
                k=0;
            }
        }
        players = new HashMap<>();
        this.rd = new Random();
    }

    @Override
    public HashMap<Integer, Pair<Integer,Integer>> getPlayerPositions(int except) throws RemoteException {
        HashMap<Integer, Pair<Integer, Integer>> temp = new HashMap<>();
        for (Map.Entry<Integer, Player> e : players.entrySet()) {
            if(e.getKey() != except) {
                temp.put(e.getKey(), e.getValue().getPosition());
            }
        }
        return temp;
    }

    @Override
    public void sendPlayerPosition(int playerID, int positionX, int positionY)throws RemoteException{

        if(players==null || !players.containsKey(playerID)){
            Player p = PlayerFactory.makePlayer(positionX,positionY,playerID);
            players.put(playerID, p);
        }else {
            players.get(playerID).setPosition(positionX, positionY);
        }
    }

    @Override
    public int gatherResource(int forPlayer) throws RemoteException {
        int x = players.get(forPlayer).getPositionX();
        int y = players.get(forPlayer).getPositionY();
        if(grid2[x][y] != 0) {
            int resourceLevel = grid2[x][y];
            grid2[x][y] = grid2[x][y] - 1;
            int cur = players.get(forPlayer).getResources();
            players.get(forPlayer).setResources(cur + 1);
        }
        int res = players.get(forPlayer).getResources();
        return res;
    }

    @Override
    public int[][] getResources() throws RemoteException{
        return grid2;
    }

    private void updateResources() {
        int[][] temp= new int[68][68];
        for(int f=0;f<=67;f++){
            for(int g= 0;g<=67;g++){
                temp[f][g]=grid2[f][g];
            }
        }
        for (int i = 1; i < grid2.length - 1; i++) {
            for (int j = 1; j < grid2[i].length - 1; j++) {
                if (grid2[i][j] < 7) {
                    float activeAdjacent = (float) 0.0;
                    float growthParam = (float) .0017;
                    float numOfNeighbors = 8;

                    int[] neighbors = new int[8];

                    neighbors[0] = grid2[i - 1][j - 1];
                    neighbors[1] = grid2[i - 1][j];
                    neighbors[2] = grid2[i - 1][j + 1];
                    neighbors[3] = grid2[i][j - 1];
                    neighbors[4] = grid2[i][j + 1];
                    neighbors[5] = grid2[i + 1][j - 1];
                    neighbors[6] = grid2[i + 1][j];
                    neighbors[7] = grid2[i + 1][j + 1];

                    for (int k = 0; k < 8; k++) {
                        if (neighbors[k] > 0) {
                            activeAdjacent++;
                        }
                    }

                    float p = growthParam * (activeAdjacent / numOfNeighbors);

                    Random rand = new Random();
                    float random = rand.nextFloat();

                    if (p > random) {
                        grid2[i][j]++;
                    }
                }
            }
        }
    }

    @Override
    public int generatePlayerID() throws RemoteException {
        rd = new Random();
        index = rd.nextInt(100000);
        return index;
    }



    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        try {
            GameOfLifeModel gameOfLifeModel = new GameOfLifeModel(args[0]);
            Naming.rebind("/GameOfLifeModel", gameOfLifeModel);
            System.out.println("Starting Up Game in 10 Seconds, 5 minutes remaining.");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    gameOfLifeModel.updateResources();
                    numUpdates++;
                    if (numUpdates % 278 == 0) {
                        System.out.println(gameSecondsLeft + " Seconds Left.");
                        gameSecondsLeft -= 5;
                    }
                }
            };
            ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(r, 10000, 18, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
            while(true) {
                Thread.sleep(18);
                if(numUpdates >= 16667) {
                    System.out.println("Game Complete!");
                    scheduledFuture.cancel(true);
                    scheduler.shutdown();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}