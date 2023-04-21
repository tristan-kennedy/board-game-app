package src.view;

import src.model.Game;

/**
 * Listener interface which allows for the users tab to be swapped automatically
 */
public interface SwitchTabListener {
    /**
     * //Switches tab
     * @param switchTo int (tab number)
     * @param g Game
     */
    void switchTab(int switchTo, Game g);
}
