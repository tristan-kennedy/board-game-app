package view;

import model.Game;
import model.GameList;

import javax.swing.*;

public class MainView implements TabSwitchListener {

    private GameList gList;

    private GameListView gameListView;
    private GameDataView gameDataView;

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel gameListViewPanel;
    private JPanel gameDataViewPanel;
    private JPanel loginViewPanel;
    private JPanel collectionsViewPanel;

    public JPanel getMainPanel() { return mainPanel; }

    private void createUIComponents() {
        gameListView = new GameListView(gList);
        gameListViewPanel = gameListView.getPanel();
        gameListView.addTabSwitchListener(this);

        gameDataView = new GameDataView();
        gameDataViewPanel = gameDataView.getPanel();

        loginViewPanel = new LoginView().getPanel();

        collectionsViewPanel = new CollectionPageView().getPanel();
    }

    public MainView(GameList gList) {
        this.gList = gList;
    }

    @Override
    public void switchTab(int switchTabTo, Game g) {
        gameDataView.setGame(g);
        tabbedPane.setSelectedIndex(switchTabTo);
    }
}




