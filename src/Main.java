package src;

import com.formdev.flatlaf.FlatLightLaf;
import src.model.*;
import src.view.FuzzySearchConvertor;
import src.view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        GameDatabaseLoader.initializeFile();
        GameDatabaseLoader.importGameData();

        UserDataManager.initializeFile();
        UserDataManager.loadReviews();

        FuzzySearchConvertor.initializeFuzzySearch();

        FlatLightLaf.setup();
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.showHorizontalLines", true);

        JFrame frame = new JFrame("Board Game App");
        frame.setContentPane(new MainView().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.pack();
        frame.setVisible(true);

    }
}
