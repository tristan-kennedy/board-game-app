import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainView extends JFrame {

    public MainView() {

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Board Game App");

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Main Game List", new GameListView());
        tabbedPane.addTab("Game", new GameDataView());
        tabbedPane.addTab("Login", new LoginView());
        tabbedPane.addTab("My Collections", new CollectionPageView());

        JLabel label1 = new JLabel();
        label1.setPreferredSize(new Dimension(200, 40));
        label1.setText("Main Game List");
        label1.setIcon(UIManager.getIcon("FileChooser.listViewIcon"));
        tabbedPane.setTabComponentAt(0, label1);

        JLabel label2 = new JLabel();
        label2.setPreferredSize(new Dimension(200, 40));
        label2.setText("Game");
        label2.setIcon(UIManager.getIcon("FileChooser.listViewIcon"));
        tabbedPane.setTabComponentAt(1, label2);

        JLabel label3 = new JLabel();
        label3.setPreferredSize(new Dimension(200, 40));
        label3.setText("Login");
        label3.setIcon(UIManager.getIcon("FileChooser.listViewIcon"));
        tabbedPane.setTabComponentAt(2, label3);

        JLabel label4 = new JLabel();
        label4.setPreferredSize(new Dimension(200, 40));
        label4.setText("My Collections");

        label4.setIcon(UIManager.getIcon("FileChooser.listViewIcon"));
        tabbedPane.setTabComponentAt(3, label4);

        add(tabbedPane);

        setVisible(true);

    }
}
