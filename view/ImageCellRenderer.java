package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

public class ImageCellRenderer extends DefaultTableCellRenderer {

    public static final Color GREY_SELECTED = new Color(200, 200, 200);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String photoName = value.toString();
        ImageIcon imageIcon = null;
        try {
            URL url = new URL(photoName);
            Image image = ImageIO.read(url);
            imageIcon = new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isSelected) setBackground(GREY_SELECTED);
        else setBackground(UIManager.getColor("Table.background"));
        setIcon(imageIcon);
        setHorizontalAlignment(JLabel.CENTER);
        return this;
    }

}
