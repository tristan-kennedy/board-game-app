package view;

import javax.swing.*;

import model.Game;

public class GameDataView extends JPanel {

    private JPanel gameDataPanel;
    private JTextArea textArea1;

    public GameDataView() {
    }

    public JPanel getPanel() { return gameDataPanel; }

    public void setGame(Game g){
        textArea1.setText(g.toString());
    }

}
