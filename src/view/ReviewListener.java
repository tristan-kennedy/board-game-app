package src.view;

import src.model.Game;

/**
 * Listener interface which allows for updating table data on review added
 */
public interface ReviewListener {
    /**
     * //Updates table data on review
     * @param g Game
     */
    void updateTableData(Game g);
}
