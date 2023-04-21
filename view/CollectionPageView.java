//Design Pattern: Composite
package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CollectionPageView {

    private GameCollection currentlyDisplayedCollection;
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JPanel collectionPagePanel;
    private JTable collectionsTable;
    private JTextField collectionName;
    private JButton addCollectionButton;
    private GameListView gameListView;
    private JPanel collectionGameListViewPanel;
    private JButton saveButton;
    private JButton deleteSelectedButton;
    private JButton removeSelectedGameButton;


    public CollectionPageView() {

        tableModel.addColumn("Collection Name");

        addCollectionButton.addActionListener(e -> {
            String name = collectionName.getText();
            collectionName.setText("");

            GameCollection collection = new GameCollection(name);
            if (UserDataManager.currentUser.addCollection(collection))
                tableModel.addRow(new Object[]{name});
        });

        deleteSelectedButton.addActionListener(e -> {
            if (collectionsTable.getSelectedRow() != -1) {
                String name = (String) tableModel.getValueAt(collectionsTable.convertRowIndexToModel(collectionsTable.getSelectedRow()), 0);
                UserDataManager.currentUser.deleteCollection(UserDataManager.currentUser.getGameCollectionByName(name));
                tableModel.removeRow(collectionsTable.getSelectedRow());
            }
        });

        saveButton.addActionListener(e -> UserDataManager.saveGameCollections());

        collectionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                String name = (String) tableModel.getValueAt(collectionsTable.convertRowIndexToModel(collectionsTable.getSelectedRow()), 0);
                currentlyDisplayedCollection = UserDataManager.currentUser.getGameCollectionByName(name);
                gameListView.setTable(currentlyDisplayedCollection);
            }
            }
        });

        removeSelectedGameButton.addActionListener(e -> {
            JTable gameTable = gameListView.getCurrentTable();
            int gameID = (int) gameTable.getModel().getValueAt(gameTable.convertRowIndexToModel(gameTable.getSelectedRow()), 4);
            currentlyDisplayedCollection.remove(GameDatabaseLoader.mainList.getGame(gameID));
            gameListView.setTable(currentlyDisplayedCollection);
        });

        collectionsTable.setModel(tableModel);
        collectionsTable.setRowHeight(60);
    }

    public void setCurrentUser(User user) {

        tableModel.setRowCount(0);

        for (GameCollection c : user.getCollectionList())
            tableModel.addRow(new Object[]{c.getName()});

        gameListView.setTable(new GameList());
    }

    public JPanel getPanel() {
        return collectionPagePanel;
    }

    private void createUIComponents() {
        gameListView = new GameListView(new GameList());
        collectionGameListViewPanel = gameListView.getPanel();
    }

    public void addSwitchTabListener(SwitchTabListener tsl) {
        gameListView.addSwitchTabListener(tsl);
    }

    public void updateTableData(Game g) {
        gameListView.updateTableData(g);
    }
}
