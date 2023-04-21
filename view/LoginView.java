package view;

import model.UserDataManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginView {

    private static final Color GREEN = new Color(15, 92, 35);
    private static final Color RED = new Color(196, 29, 29);

    private ArrayList<LoginLogoutListener> logListeners;
    private JPanel loginPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JLabel loginLabel;
    private JButton loginButton;
    private JButton createAccountButton;
    private JLabel errorLabel;
    private JButton logoutButton;

    public JPanel getPanel() {
        return loginPanel;
    }

    public LoginView() {
        clearText(); // Forces the error text to take up space on the screen
        logListeners = new ArrayList<>();

        loginButton.addActionListener(e -> {
            String userName = usernameTextField.getText();
            String password = new String(passwordTextField.getPassword());

            usernameTextField.setText("");
            passwordTextField.setText("");

            if (isNotEmpty(userName, password)) {
                if (UserDataManager.login(userName, password)) {
                    // Login successful
                    errorLabel.setForeground(GREEN);
                    errorLabel.setText("Login Successful");
                    for (LoginLogoutListener logListener : logListeners)
                        logListener.onLogin();
                    updateButtons();
                } else {
                    // Login failed
                    errorLabel.setForeground(RED);
                    errorLabel.setText("Incorrect Login Info");
                }
            }
        });

        createAccountButton.addActionListener(e -> {
            String userName = usernameTextField.getText();
            String password = new String(passwordTextField.getPassword());

            usernameTextField.setText("");
            passwordTextField.setText("");

            if (isNotEmpty(userName, password)) {
                if (UserDataManager.createAccount(userName, password)) {
                    errorLabel.setForeground(GREEN);
                    errorLabel.setText("Account Created");
                } else {
                    errorLabel.setForeground(RED);
                    errorLabel.setText("Username Already Taken");
                }
            }
        });

        logoutButton.addActionListener(e -> {
            JFrame frame = new JFrame();
            String message = "Would you like to save your collections changes?";
            int answer = JOptionPane.showConfirmDialog(frame, message);
            switch (answer) {
                case JOptionPane.YES_OPTION:
                    UserDataManager.saveGameCollections();
                case JOptionPane.NO_OPTION:
                    UserDataManager.logout();
                    for (LoginLogoutListener logListener : logListeners)
                        logListener.onLogout();
                    errorLabel.setForeground(GREEN);
                    errorLabel.setText("Logged Out");
                    updateButtons();
                    break;
            }
        });
    }

    public void addLoginLogoutListener(LoginLogoutListener logListener) {
        logListeners.add(logListener);
    }

    public void clearText() {
        errorLabel.setText(" ");
    }

    private void updateButtons() {
        boolean isLoggingIn = loginButton.isEnabled();
        // Login & Create Account buttons should always be opposite the Logout button
        loginButton.setEnabled(!isLoggingIn);
        createAccountButton.setEnabled(!isLoggingIn);

        logoutButton.setEnabled(isLoggingIn);
    }

    private boolean isNotEmpty(String username, String password) {
        if (username.length() == 0) {
            errorLabel.setForeground(RED);
            errorLabel.setText("Username cannot be blank");
            return false;
        }
        if (password.length() == 0) {
            errorLabel.setForeground(RED);
            errorLabel.setText("Password cannot be blank");
            return false;
        }
        return true;
    }
}
