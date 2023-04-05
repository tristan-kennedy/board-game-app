import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class GameListView extends JPanel {

    public GameListView(GameList list) {

        setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(1600, 800));
        add(scrollPane);

        JTable jtableView = new JTable();
        scrollPane.setViewportView(jtableView);
        DefaultTableModel table = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addColumn("Thumbnail");
        table.addColumn("Name");
        table.addColumn("Rating");
        table.addColumn("Player Count");

        for (Game game : list) {
            table.addRow(new Object[]{game.getThumbnail(), game.getName(), game.getRating(), game.getMinPlayers() + " - " + game.getMaxPlayers()});
        }

        jtableView.setModel(table);
        jtableView.setRowHeight(200);
        jtableView.getTableHeader().setReorderingAllowed(false);
        jtableView.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
    }

    private class ImageRender extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String photoName = value.toString();
            ImageIcon imageIcon = null;
            try {
                URL url = new URL(photoName);
                Image image = ImageIO.read(url);
                imageIcon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                return new JLabel(imageIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JLabel(imageIcon);
        }

    }

}
