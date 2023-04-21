package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class ReviewRenderer implements TableCellRenderer {
    private static final int MIN_ROW_HEIGHT = 80;
    private static final int COLUMN_WIDTH = 800;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel wrapper = new JPanel();
        JTextPane usernamePane = new JTextPane();
        JTextPane reviewPane = new JTextPane();

        String username = (String) table.getModel().getValueAt(table.convertRowIndexToModel(row), 2);
        String review = (String) value;

        // Left-align username
        StyledDocument doc = usernamePane.getStyledDocument();
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        doc.setParagraphAttributes(0, doc.getLength(), left, false);

        // Set font and text
        Font usernameFont = table.getFont().deriveFont(Font.BOLD, 16);
        usernamePane.setFont(usernameFont);
        usernamePane.setText(username);

        // Left-align review text
        doc = reviewPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), left, false);

        // Set font and text
        Font reviewFont = table.getFont().deriveFont(Font.PLAIN, 12);
        reviewPane.setFont(reviewFont);
        reviewPane.setText(review);

        // Set username pane dimensions
        int usernameHeight = getEffectiveHeight(usernameFont, username);
        Dimension usernameSize = new Dimension(COLUMN_WIDTH, usernameHeight);
        usernamePane.setMinimumSize(usernameSize);
        usernamePane.setPreferredSize(usernameSize);
        usernamePane.setMaximumSize(usernameSize);

        // Set review pane dimensions
        int reviewHeight = getEffectiveHeight(reviewFont, review);
        Dimension reviewSize = new Dimension(COLUMN_WIDTH, reviewHeight);
        reviewPane.setMinimumSize(reviewSize);
        reviewPane.setPreferredSize(reviewSize);
        reviewPane.setMaximumSize(reviewSize);



        // Setup wrapper panel
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(usernamePane);
        wrapper.add(reviewPane);
        wrapper.add(Box.createVerticalGlue());

        // Set the table row height based on the length of the review
        table.setRowHeight(row, Math.max(usernameHeight + reviewHeight, MIN_ROW_HEIGHT));

        wrapper.setBackground(UIManager.getColor("Table.background"));

        return wrapper;
    }

    private int getEffectiveHeight(Font font, String text) {
        JTextPane dummyPane = new JTextPane();
        dummyPane.setSize(COLUMN_WIDTH, Short.MAX_VALUE);
        dummyPane.setFont(font);
        dummyPane.setText(text);
        return dummyPane.getPreferredSize().height;
    }

}