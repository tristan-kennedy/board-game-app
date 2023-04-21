package src.view;

/**
 * Listener interface which listens for when the user logs in or out
 */
public interface LoginLogoutListener {

    /**
     * What happens on login
     */
    void onLogin();

    /**
     * What happens on logout
     */
    void onLogout();
}
