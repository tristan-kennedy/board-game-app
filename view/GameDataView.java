package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Game;

import java.awt.*;
import java.net.URL;

public class GameDataView extends JPanel {

    private JPanel gameDataPanel;
    private JLabel gameName;
    private JScrollPane scrollPane;
    private JLabel imageLabel;
    private JTextPane infoTextPane;
    private JTable table1;

    public GameDataView() {

    }

    public JPanel getPanel() {
        return gameDataPanel;
    }

    public void setGame(Game g) {

        gameName.setText(g.getName());

        ImageIcon imageIcon = null;
        try {
            URL url = new URL(g.getFullSizeImage());
            Image image = ImageIO.read(url).getScaledInstance(-1, 600, Image.SCALE_FAST);
            imageIcon = new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        infoTextPane.setText("Player Count: " + g.getMinPlayers() + " - " + g.getMaxPlayers() + "\nPlaying Time: " + g.getPlayingTime() + " min" + "\nYear Published: " + g.getYearPublished());

        imageLabel.setIcon(imageIcon);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Hello");
        tableModel.addColumn("Test");
        table1.setModel(tableModel);
    }
}
