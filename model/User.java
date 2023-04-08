package model;

import java.util.ArrayList;

public class User {

    private ArrayList<GameCollection> collectionList;
    private String userName;
    private String password;

    /**
     * Parameterized constructor which creates a user via username and password
     *
     * @param userName String username
     * @param password String password
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        collectionList = new ArrayList<>();
    }

    /**
     * Returns the String userName of a user
     *
     * @return String userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the ArrayList of GameCollections that the user has
     *
     * @return ArrayList<GameCollection> collectionList
     */
    public ArrayList<GameCollection> getCollectionList() {
        return collectionList;
    }

    /**
     * Adds a collection to th User's list of collections
     *
     * @param collection GameCollection
     */
    public void addCollection(GameCollection collection) {
        collectionList.add(collection);
    }

    /**
     * Deletes a collection from the User's list of collections
     *
     * @param collection GameCollection
     */
    public void deleteCollection(GameCollection collection) {
        collectionList.remove(collection);
    }
}
