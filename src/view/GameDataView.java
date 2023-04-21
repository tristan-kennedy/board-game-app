package src.view;

import src.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

public class GameDataView {

    private ReviewListener revListener;
    private SwitchTabListener stListener;

    private final HashMap<Integer, Image> loadedGameImages;
    private Game currentGameOnScreen;

    private final ReviewTableModel tableModel;
    private JPanel gameDataPanel;
    private JLabel gameName;
    private JScrollPane scrollPane;
    private JLabel imageLabel;
    private JTextPane infoTextPane;
    private JTable reviewTable;
    private JSlider ratingSlider;
    private JTextArea reviewTextBox;
    private JButton ratingSubmitButton;
    private JButton addGameToCollectionButton;
    private JLabel ratingValue;
    private JComboBox<String> collectionMenu;
    private JPanel headerPanel;
    private JScrollPane reviewScrollPane;
    private JLabel leaveReviewLabel;
    private JLabel ratingLabel;
    private JPanel reviewPanel;
    private JPanel collectionButtonPanel;

    public GameDataView() {
        loadedGameImages = new HashMap<>();

        // Header panel
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        gameName.setFont(gameName.getFont().deriveFont(Font.BOLD, 72f));

        // Review Table
        tableModel = new ReviewTableModel();
        reviewTable.setModel(tableModel);

        TableColumnModel tcm = reviewTable.getColumnModel();

        tcm.getColumn(0).setMinWidth(100);
        tcm.getColumn(0).setMaxWidth(100);
        tcm.getColumn(0).setCellRenderer(new RatingCellRenderer());

        tcm.getColumn(1).setMinWidth(800);
        tcm.getColumn(1).setCellRenderer(new ReviewRenderer());

        reviewTable.getTableHeader().setReorderingAllowed(false);

        // Game info text
        infoTextPane.setFont(infoTextPane.getFont().deriveFont(20f));
        StyledDocument doc = infoTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Leave review section
        leaveReviewLabel.setFont(leaveReviewLabel.getFont().deriveFont(Font.BOLD, 20));
        leaveReviewLabel.setText("Leave Review:");
        leaveReviewLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.gray));
        ratingLabel.setFont(ratingLabel.getFont().deriveFont(Font.BOLD, 20));
        ratingLabel.setText("Rating:");
        ratingLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.gray));
        ratingSubmitButton.setFont(ratingSubmitButton.getFont().deriveFont(20f));
        ratingSubmitButton.setText("Add Review");

        // Add review behavior
        ratingSubmitButton.addActionListener(e -> {
            int rating = ratingSlider.getValue();
            String reviewText = reviewTextBox.getText().trim();

            // Reset slider and clear review box
            ratingSlider.setValue(10);
            reviewTextBox.setText("");

            Review review = new Review(rating, reviewText, currentGameOnScreen.getID(), UserDataManager.currentUser.getUserName());

            //Add review to game object
            currentGameOnScreen.addReview(review);

            //Add review to review table
            tableModel.addReview(review);

            //Save review to XML
            UserDataManager.saveReview(review);

            //Update on-screen rating
            ratingValue.setIcon(new RatingIcon(currentGameOnScreen.getRating(), 100, 100));

            // Notify game tables to update review data for this game
            revListener.updateTableData(currentGameOnScreen);
        });

        // Add game to collection button
        addGameToCollectionButton.setFont(addGameToCollectionButton.getFont().deriveFont(20f));
        addGameToCollectionButton.setText("Login to Use Collections");
        addGameToCollectionButton.addActionListener(e -> stListener.switchTab(2, currentGameOnScreen));

        // Collection menu
        collectionMenu.setFont(collectionMenu.getFont().deriveFont(16f));
        collectionMenu.setPreferredSize(addGameToCollectionButton.getPreferredSize());
    }

    public void setGame(Game g) {
        currentGameOnScreen = g;

        // Set table data
        tableModel.setTableData(g);

        // Un-sort the reviews
        reviewTable.getRowSorter().setSortKeys(null);

        // Set game rating icon
        ratingValue.setIcon(new RatingIcon(g.getRating(), 100, 100));

        // Set title game text
        gameName.setText(g.getName());

        // Adjust font size to comfortably fit screen
        float fontSize = 72;
        while (gameName.getPreferredSize().width > 1200)
            gameName.setFont(gameName.getFont().deriveFont(--fontSize));

        // Set game Image
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
                if (scaledImage.getWidth(null) > 600)
                    scaledImage = scaledImage.getScaledInstance(600, -1, Image.SCALE_FAST);
                loadedGameImages.put(g.getID(), scaledImage);
                imageIcon = new ImageIcon(scaledImage);
            }

            // Occurs on a few of the game's full size images
            catch (IllegalArgumentException e) {
                // Load the game's thumbnail and scale it up instead
                try {
                    URL url = new URL(g.getThumbnail());
                    Image scaledImage = ImageIO.read(url).getScaledInstance(-1, 450, Image.SCALE_FAST);
                    if (scaledImage.getWidth(null) > 600)
                        scaledImage = scaledImage.getScaledInstance(600, -1, Image.SCALE_FAST);
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

        scrollPane.setPreferredSize(new Dimension(-1, imageIcon.getIconHeight()));
        scrollPane.setMinimumSize(new Dimension(-1, imageIcon.getIconHeight()));
        scrollPane.setMaximumSize(new Dimension(-1, imageIcon.getIconHeight()));
        // Calculate icon width
        int iconWidth = (imageIcon == null) ? 100 : imageIcon.getIconWidth();

        // Calculate max height of info pane
        String infoText = "Player Count: " + g.getMinPlayers() + " - " + g.getMaxPlayers() + "\nPlaying Time: " + g.getPlayingTime() + " min" + "\nYear Published: " + g.getYearPublished();
        JTextPane dummyPane = new JTextPane();
        dummyPane.setSize(iconWidth, Short.MAX_VALUE);
        dummyPane.setFont(infoTextPane.getFont());
        dummyPane.setText(infoText);
        infoTextPane.setMinimumSize(new Dimension(iconWidth, dummyPane.getPreferredSize().height));
        infoTextPane.setPreferredSize(new Dimension(iconWidth, dummyPane.getPreferredSize().height));
        infoTextPane.setMaximumSize(new Dimension(iconWidth, dummyPane.getPreferredSize().height));
        infoTextPane.setText(infoText);

        // Enable/disable the add to collection button and populate the dropdown with collections
        updateCollectionsPanel();

    }

    public void updateCollectionsPanel() {
        // Empty Collection List
        collectionMenu.removeAllItems();

        // Clear all menu listeners
        for (ActionListener l : collectionMenu.getActionListeners())
            collectionMenu.removeActionListener(l);

        // When clicking on different collections in the menu, update to button to show whether they've already been added or not
        collectionMenu.addActionListener(e -> {
            String collectionSelection = (String) collectionMenu.getSelectedItem();
            if (collectionSelection == null) { addGameToCollectionButton.setEnabled(false); } // Disable add button if the selection is null
            // Selection is not null
            else {
                // Game is already in the collection
                if (UserDataManager.currentUser.getGameCollectionByName(collectionSelection).hasGame(currentGameOnScreen)) {
                    addGameToCollectionButton.setText("Already in Collection");
                    addGameToCollectionButton.setEnabled(false);
                }
                // Game is not in collection
                else {
                    addGameToCollectionButton.setText("Add To Collection");
                    addGameToCollectionButton.setEnabled(true);
                }
            }
        });

        // Clear all button listeners
        for (ActionListener l : addGameToCollectionButton.getActionListeners())
            addGameToCollectionButton.removeActionListener(l);

        User u = UserDataManager.currentUser;
        addGameToCollectionButton.setEnabled(true);

        // Not logged in - Change button to switch to login page
        if (u.getUserName().equals("Guest")) {
            collectionMenu.setEnabled(false);
            addGameToCollectionButton.setEnabled(true);
            addGameToCollectionButton.setText("Login to Use Collections");
            addGameToCollectionButton.addActionListener(e -> stListener.switchTab(2, currentGameOnScreen));
        }
        // Logged in - Change button to default functionality
        else {
            addGameToCollectionButton.setText("Add Game To Collection");
            // User has at least 1 collection
            if (u.getCollectionList().size() > 0) {
                collectionMenu.setEnabled(true);
                for (GameCollection c : u.getCollectionList())
                    collectionMenu.addItem(c.getName());
                addGameToCollectionButton.addActionListener(e -> {
                    String collectionSelection = (String) collectionMenu.getSelectedItem();
                    UserDataManager.currentUser.getGameCollectionByName(collectionSelection).addGame(currentGameOnScreen);
                    addGameToCollectionButton.setText("Game Added!");
                    addGameToCollectionButton.setEnabled(false);
                });
            }
            // User has no collections
            else {
                collectionMenu.setEnabled(false);
                addGameToCollectionButton.setText("Manage Collections");
                addGameToCollectionButton.addActionListener(e -> stListener.switchTab(3, currentGameOnScreen));
            }
        }
    }

    public JPanel getPanel() {
        return gameDataPanel;
    }

    public void addSwitchTabListener(SwitchTabListener stl) {
        stListener = stl;
    }

    public void addReviewListener(ReviewListener rl) {
        revListener = rl;
    }
}

