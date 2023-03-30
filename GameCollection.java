
public class GameCollection extends GameList {

    private String name;

    public GameCollection(String name) {
        this.name = name;
    }

    void remove(Game g) {
        gameList.remove(g);
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

}
