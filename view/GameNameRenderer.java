package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameNameRenderer implements TableCellRenderer {
    private static final Pattern YEAR_PATTERN = Pattern.compile("\\(");
    private static final int COLUMN_WIDTH = 400;
    private static final int ROW_HEIGHT = 160;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel wrapper = new JPanel();
        JTextPane titlePane = new JTextPane();
        JTextPane yearPane = new JTextPane();

        // Split the string into its name and year components
        String s = (String) value;
        Matcher m = YEAR_PATTERN.matcher(s);
        String titleString, yearString;
        if (m.find()) {
            titleString = s.substring(0, m.start());
            yearString = s.substring(m.start());
        }
        else {
            titleString = s;
            yearString = "";
        }

        // Center title text
        StyledDocument doc = titlePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Set font and text
        Font titleFont = table.getFont().deriveFont(Font.BOLD, 24);
        titlePane.setFont(titleFont);
        titlePane.setText(titleString);

        // Center year text
        doc = yearPane.getStyledDocument();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Set font and text
        Font yearFont = table.getFont().deriveFont(Font.PLAIN, 12);
        yearPane.setFont(yearFont);
        yearPane.setText(yearString);

        // Set title pane dimensions
        Dimension titleSize = new Dimension(COLUMN_WIDTH, getEffectiveHeight(titleFont, titleString));
        titlePane.setMinimumSize(titleSize);
        titlePane.setPreferredSize(titleSize);
        titlePane.setMaximumSize(titleSize);

        // Set year pane dimensions
        Dimension yearSize = new Dimension(COLUMN_WIDTH, getEffectiveHeight(yearFont, yearString));
        yearPane.setMaximumSize(yearSize);
        yearPane.setPreferredSize(yearSize);
        yearPane.setMinimumSize(yearSize);

        // Setup wrapper panel
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(titlePane);
        wrapper.add(yearPane);
        wrapper.add(Box.createVerticalGlue());

        // Add background color-change and border hiding on selection
        if (isSelected) {
            wrapper.setBackground(ImageCellRenderer.GREY_SELECTED);
            titlePane.setBackground(ImageCellRenderer.GREY_SELECTED);
            yearPane.setBackground(ImageCellRenderer.GREY_SELECTED);
            wrapper.setBorder(null);
        }
        else {
            wrapper.setBackground(UIManager.getColor("Table.background"));
            titlePane.setBackground(UIManager.getColor("Table.background"));
            yearPane.setBackground(UIManager.getColor("Table.background"));
            wrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, UIManager.getColor("Table.gridColor")));
        }
        return wrapper;
    }

    private int getEffectiveHeight(Font font, String text) {
        JTextPane dummyPane = new JTextPane();
        dummyPane.setSize(COLUMN_WIDTH, ROW_HEIGHT);
        dummyPane.setFont(font);
        dummyPane.setText(text);
        return dummyPane.getPreferredSize().height;
    }

}
