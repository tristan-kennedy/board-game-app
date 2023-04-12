import model.*;
import view.MainView;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {

        GameDatabaseLoader.initializeFile();
        GameDatabaseLoader.importGameData();

        UserDataManager.initializeFile();

        UserDataManager.createAccount("TestUser", "abc123");

        UserDataManager.login("TestUser", "abc123");

        UserDataManager.loadReviews();

        JFrame frame = new JFrame("Board Game App");
        frame.setContentPane(new MainView(GameDatabaseLoader.mainList).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);

    }
}
