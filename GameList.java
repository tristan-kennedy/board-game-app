import java.util.ArrayList;
import java.util.Iterator;

public class GameList implements Iterable<Game> {

    private final ArrayList<Game> gameList;

    /**
     * Constructs an empty GameList
     */
    public GameList() { gameList = new ArrayList<>(); }

    /**
     * Adds a Game to the GameList
     * @param g the Game to be added
     */
    public void addGame(Game g) {
        gameList.add(g);
    }

    /**
     * Allows for using foreach loops over the GameList
     * @return an iterator to the interal ArrayList
     */
    @Override
    public Iterator<Game> iterator() {
        return gameList.iterator();
    }

}
