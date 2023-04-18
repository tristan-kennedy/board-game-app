package view;

import model.Game;
import model.GameDatabaseLoader;
import model.UserDataManager;

import javax.swing.*;

public class MainView implements SwitchTabListener, LoginLogoutListener {

    private GameListView gameListView;
    private GameDataView gameDataView;
    private LoginView loginView;
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
        gameListView.addSwitchTabListener(this);

        gameDataView = new GameDataView();
        gameDataViewPanel = gameDataView.getPanel();

        loginView = new LoginView();
        loginView.addLoginLogoutListener(this);
        loginViewPanel = loginView.getPanel();

        collectionPageView = new CollectionPageView();
        collectionsViewPanel = collectionPageView.getPanel();
        collectionPageView.addSwitchTabListener(this);
    }

    public MainView() {
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.addChangeListener(e -> {
            loginView.clearText();
        });
    }

    @Override
    public void switchTab(int switchTabTo, Game g) {
        tabbedPane.setEnabledAt(1, true);
        gameDataView.setGame(g);
        tabbedPane.setSelectedIndex(switchTabTo);
    }

    @Override
    public void onLogin() {
        // If the logged-in user isn't a guest, enable the collections tab
        if (!UserDataManager.currentUser.getUserName().equals("Guest")) {
            tabbedPane.setEnabledAt(3, true);
            collectionPageView.setCurrentUser(UserDataManager.currentUser);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    @Override
    public void onLogout() {
        // Disable the collections tab
        tabbedPane.setEnabledAt(3, false);
    }
}




