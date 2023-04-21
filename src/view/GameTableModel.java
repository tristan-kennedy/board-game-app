package src.view;

import src.model.Game;
import src.model.GameList;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class GameTableModel extends AbstractTableModel {
    private final String[] columnNames = { "Thumbnail", "Name", "Rating", "Player Count" };
    private ArrayList<Object[]> tableData;

    public GameTableModel(GameList gList) {
        setTableData(gList);
    }

    @Override
    public int getRowCount() {
        return tableData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (tableData.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    public Object[] getRow(int rowIndex) {
        return tableData.get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tableData.get(rowIndex)[columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setTableData(GameList gameList){
        tableData = new ArrayList<>();
        for (Game g : gameList) {
            tableData.add(new Object[]{
                g.getThumbnail(),
                g.getName() + " (" + g.getYearPublished() + ")",
                g.getRating(),
                g.getMinPlayers() + "-" + g.getMaxPlayers(),
                g.getID()
            });
        }
        fireTableDataChanged();
    }
}
