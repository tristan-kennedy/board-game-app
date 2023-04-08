import model.*;
import view.MainView;

import javax.swing.*;
import java.util.Random;

public class MainTest {
    public static void main(String[] args) {

        GameDatabaseLoader databaseLoader = new GameDatabaseLoader("bgg90Games.xml");
        UserDataManager userDataManager = new UserDataManager("outputxml.xml");

        GameList mainList = new GameList();

        databaseLoader.importGameData(mainList);

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++)
            for (Game g : mainList)
                g.addReview(new Review(rand.nextInt(10), "", g.getID(), ""));


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
        frame.setSize(800,800);
        frame.setVisible(true);

    }
}
