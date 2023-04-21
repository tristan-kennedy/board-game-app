package src.view;

import src.model.Game;
import src.model.Review;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class ReviewTableModel extends AbstractTableModel {
    private final String[] columnNames = { "Rating", "Review" };
    private ArrayList<Object[]> tableData;

    public ReviewTableModel() {
        tableData = new ArrayList<>();
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

    public void addReview(Review r) {
        tableData.add(0, new Object[]{(float) r.getRating(), r.getReviewText(), r.getUserName()});
        fireTableDataChanged();
    }

    public void setTableData(Game g){
        tableData = new ArrayList<>();
        for (Review r : g.getReviewList())
            tableData.add(new Object[]{(float) r.getRating(), r.getReviewText(), r.getUserName()});
        fireTableDataChanged();
    }
}