package src.model;

public class GameCollection extends GameList {

    private String name;

    /**
     * Parameterized constructor for GameCollection takes String name
     *
     * @param name String name of Collection
     */
    public GameCollection(String name) {
        this.name = name;
    }

    /**
     * Remove a game from the Collection
     *
     * @param game Game to be removed
     */
    public void remove(Game game) {
        gameList.remove(game);
    }

    /**
     * Rename an already created collection
     *
     * @param name String new name of collection
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the collection
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

}
