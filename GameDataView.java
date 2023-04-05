import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GameDataView extends JPanel {

    public GameDataView(){

        setLayout( new BorderLayout() );

    }

    public void setGame(Game game){

        GridBagConstraints gbc = new GridBagConstraints();

        removeAll();

        JLabel label = new JLabel(game.getName());
        label.setFont(new Font("Serif", Font.PLAIN, 40));

        JLabel description = new JLabel();
        description.setText("<html>" + game.getDescription() + "</html>");
        description.setFont(new Font("Serif", Font.PLAIN, 12));

        Image image = null;
        try {
            URL url = new URL(game.getFullSizeImage());
            image = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setPreferredSize(new Dimension(400, 400));

        add(picLabel, BorderLayout.PAGE_START);
        //add(label, BorderLayout.CENTER);
        add(description, BorderLayout.CENTER);

    }

}
