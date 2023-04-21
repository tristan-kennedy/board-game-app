package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * TableCellRenderer which allows for the custom review icons to be displayed
 */
public class RatingCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        RatingIcon icon = new RatingIcon((float) value, 50, 50);
        setIcon(icon);
        if (isSelected) setBackground(ImageCellRenderer.GREY_SELECTED);
        else setBackground(UIManager.getColor("Table.background"));
        setHorizontalAlignment(JLabel.CENTER);
        return this;
    }
}
