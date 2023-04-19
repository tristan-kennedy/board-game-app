package view;

import model.Game;
import model.GameDatabaseLoader;
import model.GameList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
        searchFilter = new JComboBox<>(new String[]{"Name", "Category", "Mechanic"});
        tableModel = new GameTableModel(gList);
        gameTable = new JTable(tableModel);
    }

    public GameListView(GameList gameList) {
        gList = gameList;

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

        // Adjust row height to be slightly more than tallest image
        gameTable.setRowHeight(160);

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

        TableRowSorter<GameTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setSortable(0, false);
        gameTable.setRowSorter(sorter);

        // Default Sort: Rating (High-Low), Name (Alphabetical)
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();


        clearSearch.addActionListener(e -> {
            searchBox.setText("");
            sorter.setRowFilter(null);
        });

        searchBox.addActionListener(e -> {
            String filter = searchFilter.getSelectedItem().toString();
            String text = searchBox.getText();

            Pattern pattern = Pattern.compile(text, Pattern.CASE_INSENSITIVE);

            RowFilter<GameTableModel, Integer> rf = null;

            switch (filter) {
                case "Name" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        String name = entry.getModel().getValueAt(entry.getIdentifier(), 1).toString();
                        return pattern.matcher(name).find();
                    }
                };
                case "Category" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        Game g = gList.getGame((int) entry.getModel().getValueAt(entry.getIdentifier(), 4));
                        for (String category : g.getCategoryList())
                            if (pattern.matcher(category).find()) return true;
                        return false;
                    }
                };
                case "Mechanic" -> rf = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends GameTableModel, ? extends Integer> entry) {
                        Object[] tableRow = entry.getModel().getRow(entry.getIdentifier());
                        Game g = gList.getGame((int) tableRow[4]);
                        for (String mechanic : g.getMechanicList())
                            if (pattern.matcher(mechanic).find()) return true;
                        return false;
                    }
                };
            }

            sorter.setRowFilter(rf);
        });
    }

    public JPanel getPanel() {
        return gameListPanel;
    }

    public void addSwitchTabListener(SwitchTabListener stl) {
        listener = stl;
    }

    public void setTable(GameList gameList) {
        tableModel.setTableData(gameList);
    }

    public JTable getCurrentTable() {
        return gameTable;
    }

}
