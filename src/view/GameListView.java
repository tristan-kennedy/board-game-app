package src.view;

import src.model.Game;
import src.model.GameDatabaseLoader;
import src.model.GameList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * View class which displays a list of games and allows for search and sort functionality
 */
public class GameListView {

    private SwitchTabListener listener;
    private JPanel gameListPanel;
    private JTable gameTable;
    private GameTableModel tableModel;
    private JTextField searchBox;
    private JComboBox<String> searchFilter;
    private JScrollPane scrollPane;
    private JButton clearSearch;
    private final GameList gList;

    private void createUIComponents() {
        searchBox = new JTextField("Search");
        searchFilter = new JComboBox<>(new String[]{"Name", "Category", "Mechanic"});
        tableModel = new GameTableModel(gList);
        gameTable = new JTable(tableModel);
    }

    /**
     * Constructs a GameListView and its sorted table from a gameList
     *
     * @param gameList the gameList to create the table from
     */
    public GameListView(GameList gameList) {
        gList = gameList;

        gameTable.setMinimumSize(new Dimension(750, 750));
        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getTableHeader().setResizingAllowed(false);
        gameTable.setSelectionBackground(ImageCellRenderer.GREY_SELECTED);
        gameTable.setFont(UIManager.getFont("Table.font").deriveFont(Font.PLAIN, 16));

        // Put the table columns in a list
        ArrayList<TableColumn> columns = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            columns.add(gameTable.getColumnModel().getColumn(i));

        // Thumbnail column
        columns.get(0).setMinWidth(200);
        columns.get(0).setCellRenderer(new ImageCellRenderer());

        // Name column
        columns.get(1).setMinWidth(400);
        columns.get(1).setCellRenderer(new GameNameRenderer());

        // Rating column
        columns.get(2).setMinWidth(100);
        columns.get(2).setMaxWidth(100);
        columns.get(2).setCellRenderer(new RatingCellRenderer());

        // Players columns
        DefaultTableCellRenderer centeredTextRenderer = new DefaultTableCellRenderer();
        centeredTextRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.get(3).setCellRenderer(centeredTextRenderer);

        // Adjust row height to be slightly more than the tallest image
        gameTable.setRowHeight(160);

        // Double click row functionality, takes you to the game's details page
        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int gameID = (int) tableModel.getValueAt(gameTable.convertRowIndexToModel(gameTable.getSelectedRow()), 4);
                    Game g = GameDatabaseLoader.mainList.getGame(gameID); // Uses master list
                    listener.switchTab(1, g);
                }
            }
        });
        defaultSort();

        // Clear search wipes the sorter and empties the search box
        clearSearch.addActionListener(e -> {
            searchBox.setForeground(Color.GRAY);
            searchBox.setText("Search");
            searchFilter.setSelectedIndex(0);
            TableRowSorter<GameTableModel> sorter = (TableRowSorter<GameTableModel>) gameTable.getRowSorter();
            sorter.setRowFilter(null);
        });

        // Add "Search" placeholder text
        searchBox.setText("Search");
        searchBox.setForeground(Color.GRAY);
        searchBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBox.getText().equals("Search")) {
                    searchBox.setText("");
                    searchBox.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBox.getText().isEmpty()) {
                    searchBox.setForeground(Color.GRAY);
                    searchBox.setText("Search");
                }
            }
        });
        // Search functionality
        searchBox.addActionListener(e -> {
            String filter = (String) searchFilter.getSelectedItem();
            String text = searchBox.getText().toLowerCase();

            String fuzzyText = FuzzySearchConvertor.fuzzyCorrect(text); // Converts search string to "fuzzy" string

            // Ignore case for matching
            Pattern pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
            Pattern fuzzyPattern = Pattern.compile(fuzzyText, Pattern.CASE_INSENSITIVE);

            RowFilter<GameTableModel, Integer> rf = null;

            switch (filter) {
                // Name option selected
                case "Name" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        String name = entry.getModel().getValueAt(entry.getIdentifier(), 1).toString();
                        if (pattern.matcher(name).find()) return true;
                        else if (fuzzyPattern.matcher(name).find()) return true;
                        return false;
                    }
                };
                // Category option selected
                case "Category" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        Game g = gList.getGame((int) entry.getModel().getValueAt(entry.getIdentifier(), 4));
                        for (String category : g.getCategoryList()) {
                            if (pattern.matcher(category).find()) return true;
                            else if (fuzzyPattern.matcher(category).find()) return true;
                        }
                        return false;
                    }
                };
                // Mechanic option selected
                case "Mechanic" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        Object[] tableRow = entry.getModel().getRow(entry.getIdentifier());
                        Game g = gList.getGame((int) tableRow[4]);
                        for (String mechanic : g.getMechanicList()) {
                            if (pattern.matcher(mechanic).find()) return true;
                            else if (fuzzyPattern.matcher(mechanic).find()) return true;
                        }
                        return false;
                    }
                };
            }
            TableRowSorter<GameTableModel> sorter = (TableRowSorter<GameTableModel>) gameTable.getRowSorter();
            sorter.setRowFilter(rf);
        });
    }

    /**
     * Returns the gameListPanel
     *
     * @return the gameListPanel
     */
    public JPanel getPanel() {
        return gameListPanel;
    }

    /**
     * Adds a switch tab listener
     *
     * @param stl the switch tab listener to be added
     */
    public void addSwitchTabListener(SwitchTabListener stl) {
        listener = stl;
    }

    /**
     * Sets the table to a different gameList. Can also be used with the same gameList to refresh the table
     *
     * @param gameList the gameList to be shown in the table
     */
    public void setTable(GameList gameList) {
        tableModel.setTableData(gameList);
    }

    /**
     * Returns the currently used JTable
     *
     * @return the currently used JtTable
     */
    public JTable getCurrentTable() {
        return gameTable;
    }

    /**
     * Updates the table data for a specific game (usually for after a new review is added)
     *
     * @param g the game whose table data should be updated
     */
    public void updateTableData(Game g) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int gameID = (int) tableModel.getValueAt(i, 4);
            if (g.getID() == gameID) {
                tableModel.setValueAt(g.getRating(), i, 2);
                break;
            }
        }
        defaultSort();
    }

    /**
     * Sorts the table first by rating and then lexicographically
     */
    public void defaultSort() {
        TableRowSorter<GameTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setSortable(0, false);
        gameTable.setRowSorter(sorter);

        // Default Sort: Rating (High-Low), Name (Alphabetical)
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

}
