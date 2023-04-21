package src.model;

import java.util.ArrayList;

public class Game {

    private final int id;
    private final String thumbnail, fullSizeImage;
    private final String name, description;
    private final int yearPublished;
    private final int minPlayers, maxPlayers;
    private final int playingTime;
    private final ArrayList<String> categoryList, mechanicList;
    private float rating;
    private final ArrayList<Review> reviewList;

    /**
     * Constructs a Game with all of its data
     *
     * @param id            the Game's id
     * @param thumbnail     the image link to be used as the Game's thumbnail
     * @param fullSizeImage the image link to be used as the Game's full size image
     * @param name          the Game's name
     * @param description   the Game's description
     * @param yearPublished the year the Game was published
     * @param minPlayers    the minimum number of players for the Game
     * @param maxPlayers    the maximum number of players for the Game
     * @param playingTime   the recommended playing time for the Game
     * @param categoryList  the list of all the Game's board game categories
     * @param mechanicList  the list of all the Game's playing mechanics
     */
    public Game(int id, String thumbnail, String fullSizeImage, String name, String description, int yearPublished, int minPlayers, int maxPlayers, int playingTime, ArrayList<String> categoryList, ArrayList<String> mechanicList) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.fullSizeImage = fullSizeImage;
        this.name = name;
        this.description = description;
        this.yearPublished = yearPublished;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playingTime = playingTime;
        this.categoryList = categoryList;
        this.mechanicList = mechanicList;
        this.rating = 0;
        this.reviewList = new ArrayList<>();
    }

    /**
     * Adds a Review to the reviewList and updates the Game's rating
     *
     * @param r the Review to be added
     */
    public void addReview(Review r) {
        reviewList.add(r);
        rating = 0;
        for (Review rev : reviewList)
            rating += rev.getRating();
        rating /= (float) reviewList.size();
    }

    /**
     * Returns the Game as a multi-line string containing all of its information
     *
     * @return A multi-line string containing all of this Game's information
     */
    @Override
    public String toString() {
        return String.format("id: %d\nthumbnail: %s\nimage: %s\n name: %s\n description: %s\n yearpublished: %d\n minplayers: %d\n maxplayers: %d\n playingTime: %d\n categories: %s\n mechanics: %s\n\n",
                id, thumbnail, fullSizeImage, name, description, yearPublished, minPlayers, maxPlayers, playingTime, categoryList.toString(), mechanicList.toString()
        );
    }

    /**
     * Returns the Game's ID
     *
     * @return The game's description as a String
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the BGG thumbnail image link
     *
     * @return the BGG thumbnail image link
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Returns the BGG full size image link
     *
     * @return the BGG full size image link
     */
    public String getFullSizeImage() {
        return fullSizeImage;
    }

    /**
     * Returns the Game's name
     *
     * @return the Game's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Game's description as a String
     *
     * @return The game's description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the Game's year of publishing
     *
     * @return the Game's year of publishing
     */
    public int getYearPublished() {
        return yearPublished;
    }

    /**
     * Returns the Game's recommended minimum players
     *
     * @return the Game's recommended minimum players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Returns the Game's recommended maximum players
     *
     * @return the Game's recommended maximum players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the Game's suggested playing time
     *
     * @return the Game's suggested playing time
     */
    public int getPlayingTime() {
        return playingTime;
    }

    /**
     * Returns the Game's list of categories on BGG
     *
     * @return the Game's list of categories on BGG
     */
    public ArrayList<String> getCategoryList() {
        return categoryList;
    }

    /**
     * Returns the Game's list of game mechanics on BGG
     *
     * @return the Game's list of game mechanics on BGG
     */
    public ArrayList<String> getMechanicList() {
        return mechanicList;
    }

    /**
     * Retuns the Game's rating
     *
     * @return Float rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Returns the list of reviews associated with a game
     *
     * @return reviewList ArrayList<Review>
     */
    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public boolean equals(Game testGame) {
        return id == testGame.id;
    }
}