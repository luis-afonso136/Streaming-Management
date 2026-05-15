package interfaces;

import java.util.List;

/**
 * Interface for entities that can receive and track awards.
 */
public interface Awardable {

    /**
     * Adds an award to the entity.
     * 
     * @param award the award name
     */
    void addAward(String award);

    /**
     * Returns all awards received.
     * 
     * @return list of award names
     */
    List<String> getAwards();

    /**
     * Checks if the entity has received at least one award.
     * 
     * @return true if the entity has awards
     */
    boolean isAwarded();
}