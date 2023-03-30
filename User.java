import java.util.ArrayList;

public class User {

    //private ArrayList<GameCollection> collectionList;
    private String userName;
    private String password;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public  String getUserName(){
        return userName;
    }

    /*
    public void createCollection(String name){
        collectionList.add(GameCollection(name));
    }

    public void deleteCollection(){
        //Depends on how we implement the collections worry about later
    }

     */
}
