
public class mainTest {
    public static void main(String[] args){

        GameDatabaseLoader databaseLoader = new GameDatabaseLoader("bgg90Games.xml");
        UserDataManager userDataManager = new UserDataManager("outputxml.xml");

        GameList mainList = new GameList();

        databaseLoader.importGameData(mainList);

        userDataManager.createAccount("TestUser", "abc123");

        userDataManager.login("TestUser", "abc123", mainList);

        userDataManager.loadReviews(mainList);

    }
}
