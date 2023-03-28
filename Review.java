public class Review {

    private float rating;
    private String review_Text;

    /**
     *
     * @param i The rating out of 10
     * @param text User's review of the game
     */
    public Review(int i, String text)
    {
        if(i>10)
        {
            i=10;
        }
        else if (i<0)
        {
            i=0;
        }
        this.rating=i;
        this.review_Text=text;
    }

    /**
     *
     * @param i The rating out of 10
     */
    public Review(int i)
    {
        if(i>10)
        {
            i=10;
        }
        else if (i<0)
        {
            i=0;
        }
        this.rating=i;
    }

    /**
     *
     * @param text User's review of the game
     */
    void setReview(String text)
    {
        this.review_Text=text;
    }

    /**
     *
     * @param i The rating out of 10
     */
    void setRating(int i)
    {
        if(i>10)
        {
            i=10;
        }
        else if (i<0)
        {
            i=0;
        }
        this.rating=i;
    }

}
