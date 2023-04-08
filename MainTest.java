import model.GameDatabaseLoader;
import model.GameList;
import model.UserDataManager;
import view.MainView;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {

        GameDatabaseLoader databaseLoader = new GameDatabaseLoader("bgg90Games.xml");
        UserDataManager userDataManager = new UserDataManager("outputxml.xml");

        GameList mainList = new GameList();

        databaseLoader.importGameData(mainList);

        userDataManager.createAccount("TestUser", "abc123");

        userDataManager.login("TestUser", "abc123", mainList);

        userDataManager.loadReviews(mainList);

//        MainView main = new MainView(mainList);
//
//        main.changeGameView(mainList.getGame(381247));
//        main.changeGameView(mainList.getGame(374173));

        JFrame frame = new JFrame("Board Game App");
        frame.setContentPane(new MainView(mainList).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
