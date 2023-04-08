package view;

import model.Game;
import model.GameList;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GameListView {

    private TabSwitchListener listener;
    private GameList gList;

    private JPanel gameListPanel;
    private JTable gameTable;
    private JTextField searchBox;
    private JComboBox<String> searchFilter;
    private JScrollPane scrollPane;

    private void createUIComponents() {
        searchBox = new JTextField();
        searchFilter = new JComboBox<>(new String[]{"Name", "Category", "Mechanic"});

        GameTableModel tableModel = new GameTableModel(gList);
        gameTable = new JTable(tableModel);

        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getColumnModel().getColumn(0).setCellRenderer(new ImageCellRenderer());

        gameTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int gameID = (int) tableModel.getValueAt(gameTable.convertRowIndexToModel(gameTable.getSelectedRow()), 4);
                    Game g = gList.getGame(gameID);
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

    public GameListView(GameList gList) {
        this.gList = gList;
    }

    public JPanel getPanel() {
        return gameListPanel;
    }

    public void addTabSwitchListener(TabSwitchListener tsl) {
        listener = tsl;
    }


}
