package interfaces;

import java.util.List;

/**
 * Interface for entities that support captions/subtitles.
 */
public interface Captionable {

    /**
     * Adds a caption language.
     * 
     * @param caption the caption language to add
     */
    void addCaption(String caption);

    /**
     * Returns all available captions.
     * 
     * @return list of caption languages
     */
    List<String> getCaptions();

    /**
     * Checks if a specific caption is available.
     * 
     * @param caption the caption to check
     * @return true if the caption is available
     */
    boolean hasCaption(String caption);
}