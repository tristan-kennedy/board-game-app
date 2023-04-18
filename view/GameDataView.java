package view;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

public class GameDataView extends JPanel {

    private SwitchTabListener listener;

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

        addGameToCollectionButton.setText("Login to Use Collections");
        addGameToCollectionButton.addActionListener(e -> listener.switchTab(2, currentGameOnScreen));
    }

    public void setGame(Game g) {

        tableModel.setRowCount(0);

        currentGameOnScreen = g;

        gameName.setText(g.getName());
        ratingValue.setIcon(new RatingIcon(g.getRating()));

        // Set Game Image
        ImageIcon imageIcon = null;

        // Game image has already been loaded
        if (loadedGameImages.containsKey(g.getID()))
            imageIcon = new ImageIcon(loadedGameImages.get(g.getID()));
        // Game image needs to be loaded
        else {
            // Load full-size image
            try {
                URL url = new URL(g.getFullSizeImage());
                Image scaledImage = ImageIO.read(url).getScaledInstance(-1, 450, Image.SCALE_FAST);
                loadedGameImages.put(g.getID(), scaledImage);
                imageIcon = new ImageIcon(scaledImage);
            }

            // Occurs on a few of the game's full size images
            catch (IllegalArgumentException e) {
                // Load the game's thumbnail and scale it up instead
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
        imageLabel.setIcon(imageIcon);

        // Set the game info text
        infoTextPane.setText("Player Count: " + g.getMinPlayers() + " - " + g.getMaxPlayers() + "\nPlaying Time: " + g.getPlayingTime() + " min" + "\nYear Published: " + g.getYearPublished());

        // Populate Reviews
        for (Review r : g.getReviewList())
            tableModel.addRow(new Object[]{(float) r.getRating(), r.getReviewText(), r.getUserName()});

        // Enable/disable the add to collection button and populate the dropdown with collections
        updateCollectionsMenu();
    }

    public void updateCollectionsMenu() {
        // Empty Collection List
        collectionList.removeAllItems();

        // Clear all button listeners
        for (ActionListener l : addGameToCollectionButton.getActionListeners())
            addGameToCollectionButton.removeActionListener(l);

        User u = UserDataManager.currentUser;
        // Not logged in - Change button to switch to login page
        if (u.getUserName().equals("Guest")) {
            collectionList.setEnabled(false);
            addGameToCollectionButton.setText("Login to Use Collections");
            addGameToCollectionButton.addActionListener(e -> listener.switchTab(2, currentGameOnScreen));
        }
        // Logged in - Change button to default functionality
        else {
            for (GameCollection c : u.getCollectionList())
                collectionList.addItem(c.getName());
            collectionList.setEnabled(true);
            addGameToCollectionButton.setText("Add To Collection");
            addGameToCollectionButton.addActionListener(e -> {
                String collectionSelection = (String) collectionList.getSelectedItem();
                UserDataManager.currentUser.getGameCollectionByName(collectionSelection).addGame(currentGameOnScreen);
            });
        }
    }

    public JPanel getPanel() {
        return gameDataPanel;
    }

    public void addSwitchTabListener(SwitchTabListener stl) {
        listener = stl;
    }
}

