import java.util.ArrayList;
public class GameCollection {

    private ArrayList<Game> collection;
    private String name;

    public GameCollection()
    {
        this.name = "New Collection";
    }

    public GameCollection(String n)
    {
        this.name = n;
    }

    public GameCollection(Game g)
    {
        this.add(g);
        this.name = "New Collection";
    }

    public GameCollection(ArrayList<Game> collection, String n) {
        this.collection = collection;
        this.name = n;
    }

    void add(Game g)
    {
        this.collection.add(g);
    }

    void remove(Game g)
    {
        this.collection.remove(g);
    }

    void setName(String n)
    {
        this.name = n;
    }

}
