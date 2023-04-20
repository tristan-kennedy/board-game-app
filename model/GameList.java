package model;

import java.util.ArrayList;
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
    public boolean addGame(Game g) {
        if(hasGame(g))
            return false;
        gameList.add(g);
        gameMap.put(g.getID(), g);
        return true;
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

    public boolean hasGame(Game testGame) {
        for(Game game : gameList)
            if(game == testGame)
                return true;
        return false;
    }

}
