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

    public JPanel getPanel() { return loginPanel; }

    public LoginView(){
        loginButton.addActionListener(e -> {

            errorLabel.setText("");

            String userName = usernameTextField.getText();
            String password = passwordTextField.getText();

            usernameTextField.setText("");
            passwordTextField.setText("");

            if(!(UserDataManager.login(userName, password))){
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Incorrect Login Info");
            }
            else{
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

            if(!(UserDataManager.createAccount(userName, password))){
                errorLabel.setForeground(Color.RED);
                errorLabel.setText("Username Already Taken");
            }
            else{
                errorLabel.setForeground(Color.GREEN);
                errorLabel.setText("Account Created");
            }

        });

        logoutButton.addActionListener(e -> {
            UserDataManager.logout();
            errorLabel.setForeground(Color.GREEN);
            errorLabel.setText("Logged Out");
        });
    }
}
