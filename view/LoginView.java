package view;

import model.UserDataManager;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JLabel loginLabel;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel errorLabel;
    private JButton logoutButton;

    public JPanel getPanel() {
        return loginPanel;
    }

    public LoginView() {
        loginButton.addActionListener(e -> {

            errorLabel.setText("");

            String userName = usernameTextField.getText();
            String password = passwordTextField.getText();

            usernameTextField.setText("");
            passwordTextField.setText("");

            if (!(UserDataManager.login(userName, password))) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Incorrect Login Info");
            } else {
                errorLabel.setForeground(Color.GREEN);
                errorLabel.setText("Login Successful");
            }

        });

        createAccountButton.addActionListener(e -> {

            errorLabel.setText("");

            String userName = usernameTextField.getText();
            String password = passwordTextField.getText();

            usernameTextField.setText("");
            passwordTextField.setText("");

            if (!(UserDataManager.createAccount(userName, password))) {
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Username Already Taken");
            } else {
                errorLabel.setForeground(Color.GREEN);
                errorLabel.setText("Account Created");
            }

        });

        logoutButton.addActionListener(e -> {
            if (!(UserDataManager.currentUser.getUserName() == "Guest")) {
                JFrame frame = new JFrame();
                String message = "Would you like to save your collections changes?";
                int answer = JOptionPane.showConfirmDialog(frame, message);
                if (answer == JOptionPane.YES_OPTION){
                    UserDataManager.saveGameCollections();
                    UserDataManager.logout();
                    errorLabel.setForeground(Color.GREEN);
                    errorLabel.setText("Logged Out");
                }
                else if (answer == JOptionPane.NO_OPTION) {
                    UserDataManager.logout();
                    errorLabel.setForeground(Color.GREEN);
                    errorLabel.setText("Logged Out");
                }
            }
        });
    }
}
