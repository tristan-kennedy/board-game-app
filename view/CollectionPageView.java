package view;

import model.Game;
import model.GameCollection;
import model.User;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;


import javax.swing.*;
import java.util.ArrayList;

public class CollectionPageView {
    private ArrayList<GameCollection> collections;
    private JPanel collectionPagePanel;
    private JTable collTable;
    private JScrollPane pane;

    public  int j;
    private String[] collum;
    Object[] collData;



    public CollectionPageView(User u) {
        collections=u.getCollectionList();
        collectionPagePanel = new JPanel();
        collum = new String[] {"collections"};
        collData=collections.toArray();
        collTable = new JTable();
        pane = new JScrollPane(collTable);
    }
    public JPanel getPanel() { return collectionPagePanel; }

}
