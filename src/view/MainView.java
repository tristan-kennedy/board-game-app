//Design Pattern: Observer
package src.view;

import src.model.Game;
import src.model.GameDatabaseLoader;
import src.model.UserDataManager;

import javax.swing.*;

public class MainView implements SwitchTabListener, LoginLogoutListener, ReviewListener {

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

    private void createUIComponents() {
        // GameListView tab
        gameListView = new GameListView(GameDatabaseLoader.mainList);
        gameListViewPanel = gameListView.getPanel();
        gameListView.addSwitchTabListener(this);

        // GameDataView tab
        gameDataView = new GameDataView();
        gameDataViewPanel = gameDataView.getPanel();
        gameDataView.addSwitchTabListener(this);
        gameDataView.addReviewListener(this);

        // LoginView tab
        loginView = new LoginView();
        loginView.addLoginLogoutListener(this);
        loginViewPanel = loginView.getPanel();

        // Collections tab
        collectionPageView = new CollectionPageView();
        collectionsViewPanel = collectionPageView.getPanel();
        collectionPageView.addSwitchTabListener(this);
    }

    /**
     * Constructs a MainView with the Game and Collections tabs disabled by default
     */
    public MainView() {
        // Disable the game detail tab and collections tab by default
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(3, false);

        // Update necessary tabs on switch
        tabbedPane.addChangeListener(e -> {
            switch (tabbedPane.getSelectedIndex()) {
                case 1 -> gameDataView.updateCollectionsMenu();
                case 3 -> collectionPageView.refreshTable();
            }
            loginView.clearText();
        });
    }

    /**
     * Used by individual tab classes to request tab changes
     * @param switchTabTo the index of the tab to switch to
     * @param g the game to set the game details page to
     */
    @Override
    public void switchTab(int switchTabTo, Game g) {
        tabbedPane.setEnabledAt(1, true);
        gameDataView.setGame(g);
        tabbedPane.setSelectedIndex(switchTabTo);
    }

    /**
     * Called when the login tab registers a login event
     * Enables collection page
     */
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

    /**
     * Called when the login tab registers a logout event
     * Disables collection page
     */
    @Override
    public void onLogout() {
        // Disable the collections tab
        tabbedPane.setEnabledAt(3, false);
    }

    /**
     * Updates all gameListView tables' data on the specified game, usually as a response to a new review being created
     * @param g the game whose data should be updated
     */
    @Override
    public void updateTableData(Game g) {
        gameListView.updateTableData(g);
        collectionPageView.updateTableData(g);
    }

    /**
     * Returns the main view panel to be added to a JFrame
     * @return the main view panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}




