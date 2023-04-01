import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class GameList implements Iterable<Game> {

    protected ArrayList<Game> gameList;
    private final HashMap<Integer, Game> gameMap;

    /**
     * Constructs an empty GameList
     */
    public GameList() {
        gameList = new ArrayList<>();
        gameMap = new HashMap<>();
    }

    /**
     * Adds a Game to the GameList
     *
     * @param g the Game to be added
     */
    public void addGame(Game g) {
        gameList.add(g);
        gameMap.put(g.getID(), g);
    }

    /**
     * Returns a new list of Games sorted by either name, rating, or max players
     *
     * @param sortBy   The field to sort the Games by: "name", "rating", or "players"
     * @param ordering 1 for ascending, -1 for descending
     * @return a new, sorted list of Games
     */
    public ArrayList<Game> sort(String sortBy, int ordering) {
        ArrayList<Game> sortedGames = new ArrayList<>(gameList);

        switch (sortBy) {
            case "name" -> {
                if (ordering == 1) // Sort lexicographically
                    sortedGames.sort(Comparator.comparing(Game::getName));
                if (ordering == -1) // Sort reverse lexicographically
                    sortedGames.sort(Comparator.comparing(Game::getName).reversed());
            }
            case "rating" -> {
                if (ordering == 1)
                    sortedGames.sort((a, b) -> Float.compare(a.getRating(), b.getRating()));
                if (ordering == -1)
                    sortedGames.sort((a, b) -> Float.compare(b.getRating(), a.getRating()));
            }
            case "players" -> {
                if (ordering == 1)
                    sortedGames.sort(Comparator.comparingInt(Game::getMaxPlayers));
                if (ordering == -1)
                    sortedGames.sort(Comparator.comparingInt(Game::getMaxPlayers).reversed());
            }
        }
        return sortedGames;
    }

    /**
     * Finds a specific game by ID. Returns null if game not found
     *
     * @param id the Game's ID
     * @return the Game associated with the ID if found, otherwise null
     */
    public Game getGame(int id) {
        return gameMap.getOrDefault(id, null);
    }


    /**
     * Allows for using foreach loops over the GameList
     *
     * @return an iterator to the interal ArrayList
     */
    @Override
    public Iterator<Game> iterator() {
        return gameList.iterator();
    }

}
