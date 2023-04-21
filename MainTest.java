// import com.formdev.flatlaf.FlatLightLaf;
import model.*;
import view.FuzzySearchConvertor;
import view.MainView;

import javax.swing.*;

public class MainTest {
    public static void main(String[] args) {

        GameDatabaseLoader.initializeFile();
        GameDatabaseLoader.importGameData();

        UserDataManager.initializeFile();
        UserDataManager.loadReviews();

        FuzzySearchConvertor.initializeFuzzySearch();

//        FlatLightLaf.setup();
//        UIManager.put("Table.showVerticalLines", true);
//        UIManager.put("Table.showHorizontalLines", true);

        JFrame frame = new JFrame("Board Game App");
        frame.setContentPane(new MainView().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);

    }
}
