package view;

import model.Game;
import model.GameCollection;
import model.Review;
import model.UserDataManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class GameDataView extends JPanel {

    private DefaultTableModel tableModel =  new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private Game currentGameOnScreen;
    private HashMap<Integer, Image> loadedGameImages;

    private JPanel gameDataPanel;
    private JLabel gameName;
    private JScrollPane scrollPane;
    private JLabel imageLabel;
    private JTextPane infoTextPane;
    private JTable reviewTable;
    private JSlider ratingSlider;
    private JTextArea ratingTextBox;
    private JButton ratingSubmitButton;
    private JButton addGameToCollectionButton;
    private JLabel ratingValue;
    private JComboBox<String> collectionList;

    public GameDataView() {
        loadedGameImages = new HashMap<>();

        tableModel.addColumn("Rating");
        tableModel.addColumn("Review");
        tableModel.addColumn("User");

        reviewTable.setModel(tableModel);

        reviewTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        reviewTable.getColumnModel().getColumn(0).setCellRenderer(new RatingCellRenderer());
        reviewTable.setRowHeight(30);
        reviewTable.getTableHeader().setReorderingAllowed(false);

        ratingSubmitButton.addActionListener(e -> {
            int rating = ratingSlider.getValue();
            String reviewText = ratingTextBox.getText();

            ratingSlider.setValue(10);
            ratingTextBox.setText("");

            Review review = new Review(rating, reviewText, currentGameOnScreen.getID(), UserDataManager.currentUser.getUserName());

            //Add review to game object
            currentGameOnScreen.addReview(review);

            //Add review to screen
            tableModel.addRow(new Object[]{(float) rating, reviewText, UserDataManager.currentUser.getUserName()});

            //Save review to XML
            UserDataManager.saveReview(review);

            //Update on-screen rating
            ratingValue.setIcon(new RatingIcon(currentGameOnScreen.getRating()));

        });

        addGameToCollectionButton.addActionListener(e -> {
            String collectionSelection = (String) collectionList.getSelectedItem();
            UserDataManager.currentUser.getGameCollectionByName(collectionSelection).addGame(currentGameOnScreen);
        });
    }

    public JPanel getPanel() {
        return gameDataPanel;
    }

    public void setGame(Game g) {

        tableModel.setRowCount(0);

        currentGameOnScreen = g;

        gameName.setText(g.getName());
        ratingValue.setIcon(new RatingIcon(g.getRating()));

        ImageIcon imageIcon = null;
        if (loadedGameImages.containsKey(g.getID()))
            imageIcon = new ImageIcon(loadedGameImages.get(g.getID()));
        else {
            try {
                URL url = new URL(g.getFullSizeImage());
                Image scaledImage = ImageIO.read(url).getScaledInstance(-1, 450, Image.SCALE_FAST);
                loadedGameImages.put(g.getID(), scaledImage);
                imageIcon = new ImageIcon(scaledImage);
            }

            catch (IllegalArgumentException e) {
                try {
                    URL url = new URL(g.getThumbnail());
                    Image scaledImage = ImageIO.read(url).getScaledInstance(-1, 450, Image.SCALE_FAST);
                    loadedGameImages.put(g.getID(), scaledImage);
                    imageIcon = new ImageIcon(scaledImage);
                }
                catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        infoTextPane.setText("Player Count: " + g.getMinPlayers() + " - " + g.getMaxPlayers() + "\nPlaying Time: " + g.getPlayingTime() + " min" + "\nYear Published: " + g.getYearPublished());

        imageLabel.setIcon(imageIcon);

        for (Review r : g.getReviewList()) {
            tableModel.addRow(new Object[]{(float) r.getRating(), r.getReviewText(), r.getUserName()});
        }

        collectionList.removeAllItems();
        for (GameCollection c : UserDataManager.currentUser.getCollectionList())
            collectionList.addItem(c.getName());
    }

    public void updateCollectionsMenu() {
        collectionList.removeAllItems();
        for (GameCollection c : UserDataManager.currentUser.getCollectionList())
            collectionList.addItem(c.getName());
    }
}

