package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

public class ImageCellRenderer extends DefaultTableCellRenderer {

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

        return new JLabel(imageIcon);
    }
}
