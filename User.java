import java.util.ArrayList;

public class User {

    private ArrayList<GameCollection> collectionList;
    private String userName;
    private String password;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        collectionList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<GameCollection> getCollectionList() {
        return collectionList;
    }

    public void addCollection(GameCollection collection) {
        collectionList.add(collection);
    }

    public void deleteCollection(GameCollection collection) {
        collectionList.remove(collection);
    }
}
