package interfaces;

/**
 * Interface for entities that can receive and manage ratings/classifications.
 */
public interface Classifiable {

    /**
     * Adds a rating to the entity.
     * 
     * @param rating the rating value
     */
    void addRating(int rating);

    /**
     * Returns the average of all ratings.
     * 
     * @return average rating, or 0.0 if no ratings exist
     */
    double getAverageRating();

    /**
     * Returns the total number of ratings.
     * 
     * @return number of ratings
     */
    int getNumberOfRatings();
}