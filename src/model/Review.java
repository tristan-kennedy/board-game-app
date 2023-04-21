package src.model;

public class Review {

    private final Integer rating;
    private String reviewText;
    private String userName;
    private final Integer gameId;

    /**
     * Parameterized constructor for a review which contains text and rating
     *
     * @param rating   rating of game /10
     * @param text     String text of review
     * @param gameId   ID of game reviewed
     * @param userName String username of reviewer
     */
    public Review(Integer rating, String text, Integer gameId, String userName) {
        this.rating = rating;
        this.reviewText = text;
        this.gameId = gameId;
        this.userName = userName;
    }

    /**
     * Parameterized constructor for a review which contains only a rating
     *
     * @param rating   rating of game /10
     * @param gameId   ID of game reviewed
     * @param userName String username of reviewer
     */
    public Review(Integer rating, Integer gameId, String userName) {
        this.rating = rating;
        this.gameId = gameId;
    }

    /**
     * Gets the rating of the game
     *
     * @return Integer rating of game /10
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Gets the text review of the game
     *
     * @return String reviewText
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     * Gets the userName of the reviewer
     *
     * @return String userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the gameId of the game reviewed
     *
     * @return Integer gameId
     */
    public Integer getGameId() {
        return gameId;
    }


}
