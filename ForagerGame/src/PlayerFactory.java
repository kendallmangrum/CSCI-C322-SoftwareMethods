public class PlayerFactory {

    public static Player makePlayer (int x, int y, int id) {
        if (id % 2 == 0) {
            return new EvenPlayer(x, y, id);
        } else if (id % 2 == 1) {
            return new OddPlayer(x, y, id);
        }
        return null;
    }
}
