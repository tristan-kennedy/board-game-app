package view;

import model.Game;
import model.GameDatabaseLoader;
import model.UserDataManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainView implements TabSwitchListener {

    private GameListView gameListView;
    private GameDataView gameDataView;
    private CollectionPageView collectionPageView;
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel gameListViewPanel;
    private JPanel gameDataViewPanel;
    private JPanel loginViewPanel;
    private JPanel collectionsViewPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        gameListView = new GameListView(GameDatabaseLoader.mainList);
        gameListViewPanel = gameListView.getPanel();
        gameListView.addTabSwitchListener(this);

        gameDataView = new GameDataView();
        gameDataViewPanel = gameDataView.getPanel();

        loginViewPanel = new LoginView().getPanel();

        collectionPageView = new CollectionPageView();
        collectionsViewPanel = collectionPageView.getPanel();
        collectionPageView.addTabSwitchListener(this);
    }

    public MainView() {
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (UserDataManager.currentUser.getUserName() != "Guest") {
                    tabbedPane.setEnabledAt(3, true);
                    collectionPageView.setCurrentUser(UserDataManager.currentUser);
                } else {
                    tabbedPane.setEnabledAt(3, false);
                }
            }
        });
    }

    @Override
    public void switchTab(int switchTabTo, Game g) {
        tabbedPane.setEnabledAt(1, true);
        gameDataView.setGame(g);
        tabbedPane.setSelectedIndex(switchTabTo);
    }
}




