import model.*;
import view.MainView;

import javax.swing.*;
import java.util.Random;

public class MainTest {
    public static void main(String[] args) {

        GameDatabaseLoader.initializeFile();
        GameDatabaseLoader.importGameData();

        UserDataManager.initializeFile();

        /*
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++)
            for (Game g : mainList)
                g.addReview(new Review(rand.nextInt(10), "", g.getID(), ""));
         */

        UserDataManager.createAccount("TestUser", "abc123");

        UserDataManager.login("TestUser", "abc123");

        UserDataManager.loadReviews();

//        MainView main = new MainView(mainList);
//
//        main.changeGameView(mainList.getGame(381247));
//        main.changeGameView(mainList.getGame(374173));

        JFrame frame = new JFrame("Board Game App");
        frame.setContentPane(new MainView(GameDatabaseLoader.mainList).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);

    }
}
