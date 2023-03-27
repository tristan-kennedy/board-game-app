import java.util.ArrayList;

public class Game {

    private final int id;
    private final String thumbnail, fullSizeImage;
    private final String name, description;
    private final int yearPublished;
    private final int minPlayers, maxPlayers;
    private final int playingTime;
    private final ArrayList<String> categoryList, mechanicList;
//    private final ArrayList<Review> reviewList;


    /**
     * Constructs a Game with all of its data
     * @param id the Game's id
     * @param thumbnail the image link to be used as the Game's thumbnail
     * @param fullSizeImage the image link to be used as the Game's full size image
     * @param name the Game's name
     * @param description the Game's description
     * @param yearPublished the year the Game was published
     * @param minPlayers the minimum number of players for the Game
     * @param maxPlayers the maximum number of players for the Game
     * @param playingTime the recommended playing time for the Game
     * @param categoryList the list of all the Game's board game categories
     * @param mechanicList the list of all the Game's playing mechanics
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
//        this.reviewList = new ArrayList<>();
    }

//    public void addReview(Review r) {
//        reviewList.add(r);
//    }

    /**
     * Returns the Game as a multi-line string containing all of its information
     * @return A multi-line string containing all of this Game's information
     */
    @Override
    public String toString() {
        return String.format("id: %d\nthumbnail: %s\nimage: %s\n name: %s\n description: %s\n yearpublished: %d\n minplayers: %d\n maxplayers: %d\n playingTime: %d\n categories: %s\n mechanics: %s\n\n",
                id, thumbnail, fullSizeImage, name, description, yearPublished, minPlayers, maxPlayers, playingTime, categoryList.toString(), mechanicList.toString()
        );
    }

    /**
     * Returns the Game's description as a String
     * @return The game's description as a String
     */
    public String getDescription() {
        return description;
    }

}