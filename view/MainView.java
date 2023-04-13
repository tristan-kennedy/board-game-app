package view;

import model.Game;
import model.GameList;
import model.User;
import model.UserDataManager;

import javax.swing.*;

public class MainView implements TabSwitchListener {

    private GameListView gameListView;
    private GameDataView gameDataView;

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel gameListViewPanel;
    private JPanel gameDataViewPanel;
    private JPanel loginViewPanel;
    private JPanel collectionsViewPanel;

    public UserDataManager um;

    public JPanel getMainPanel() { return mainPanel; }

    private void createUIComponents() {
        gameListView = new GameListView();
        gameListViewPanel = gameListView.getPanel();
        gameListView.addTabSwitchListener(this);

        gameDataView = new GameDataView();
        gameDataView.setGame(gameListView.getTopRatedGame());
        gameDataViewPanel = gameDataView.getPanel();

        loginViewPanel = new LoginView().getPanel();

        collectionsViewPanel = new CollectionPageView(um.currentUser).getPanel();
    }


    @Override
    public void switchTab(int switchTabTo, Game g) {
        gameDataView.setGame(g);
        tabbedPane.setSelectedIndex(switchTabTo);
    }
}




