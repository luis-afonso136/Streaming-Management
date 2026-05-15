package model;

import interfaces.Captionable;
import interfaces.Awardable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Documentary in the streaming platform.
 * Implements Captionable (subtitles) and Awardable (awards).
 * Implements Comparable for TreeSet ordering by title.
 */
public class Documentary implements Captionable, Awardable, Comparable<Documentary>, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private int yearRelease;
    private int duration;
    private String ageRestriction;
    private String theme;
    private String narrator;
    private List<String> captions;
    private List<String> awards;

    /**
     * Constructs a Documentary with full parameter validation.
     * 
     * @param id             must have at least 4 characters, not null/empty
     * @param title          cannot be null or empty
     * @param yearRelease    must be between 1888 and 2026
     * @param duration       must be greater than 0
     * @param ageRestriction must be one of: G, PG, PG-13, R, NC-17
     * @param theme          cannot be null or empty
     * @param narrator       cannot be null or empty
     */
    public Documentary(String id, String title, int yearRelease, int duration,
            String ageRestriction, String theme, String narrator) {
        setId(id);
        setTitle(title);
        setYearRelease(yearRelease);
        setDuration(duration);
        setAgeRestriction(ageRestriction);
        setTheme(theme);
        setNarrator(narrator);
        this.captions = new ArrayList<>();
        this.awards = new ArrayList<>();
    }

    /** @return the documentary ID */
    public String getId() {
        return id;
    }

    /**
     * Sets the documentary ID.
     * 
     * @param id must have at least 4 characters, not null/empty
     */
    public void setId(String id) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("ID cannot be null or empty.");
        if (id.trim().length() < 4)
            throw new IllegalArgumentException("ID must have at least 4 characters.");
        this.id = id.trim();
    }

    /** @return the documentary title */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title cannot be null or empty
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty.");
        this.title = title.trim();
    }

    /** @return the release year */
    public int getYearRelease() {
        return yearRelease;
    }

    /**
     * Sets the release year.
     * 
     * @param yearRelease must be between 1888 and 2026
     */
    public void setYearRelease(int yearRelease) {
        if (yearRelease < 1888 || yearRelease > 2026)
            throw new IllegalArgumentException("Year must be between 1888 and 2026.");
        this.yearRelease = yearRelease;
    }

    /** @return duration in minutes */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration.
     * 
     * @param duration must be greater than 0
     */
    public void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException("Duration must be greater than 0.");
        this.duration = duration;
    }

    /** @return the age restriction */
    public String getAgeRestriction() {
        return ageRestriction;
    }

    /**
     * Sets the age restriction.
     * 
     * @param ageRestriction must be one of: G, PG, PG-13, R, NC-17
     */
    public void setAgeRestriction(String ageRestriction) {
        if (!isValidAge(ageRestriction))
            throw new IllegalArgumentException("Age restriction must be one of: G, PG, PG-13, R, NC-17.");
        this.ageRestriction = ageRestriction;
    }

    /** @return the documentary theme */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme.
     * 
     * @param theme cannot be null or empty
     */
    public void setTheme(String theme) {
        if (theme == null || theme.trim().isEmpty())
            throw new IllegalArgumentException("Theme cannot be null or empty.");
        this.theme = theme.trim();
    }

    /** @return the narrator name */
    public String getNarrator() {
        return narrator;
    }

    /**
     * Sets the narrator.
     * 
     * @param narrator cannot be null or empty
     */
    public void setNarrator(String narrator) {
        if (narrator == null || narrator.trim().isEmpty())
            throw new IllegalArgumentException("Narrator cannot be null or empty.");
        this.narrator = narrator.trim();
    }

    @Override
    public void addCaption(String caption) {
        if (caption != null && !caption.trim().isEmpty())
            captions.add(caption.trim());
    }

    @Override
    public List<String> getCaptions() {
        return new ArrayList<>(captions);
    }

    @Override
    public boolean hasCaption(String caption) {
        return captions.contains(caption);
    }

    @Override
    public void addAward(String award) {
        if (award != null && !award.trim().isEmpty())
            awards.add(award.trim());
    }

    @Override
    public List<String> getAwards() {
        return new ArrayList<>(awards);
    }

    @Override
    public boolean isAwarded() {
        return !awards.isEmpty();
    }

    /**
     * Natural ordering for TreeSet: by title (case-insensitive), then by ID.
     * 
     * @param other the other Documentary to compare
     * @return comparison result
     */
    @Override
    public int compareTo(Documentary other) {
        int c = this.title.compareToIgnoreCase(other.title);
        return c != 0 ? c : this.id.compareToIgnoreCase(other.id);
    }

    private boolean isValidAge(String ar) {
        if (ar == null)
            return false;
        return ar.equals("G") || ar.equals("PG") || ar.equals("PG-13")
                || ar.equals("R") || ar.equals("NC-17");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Documentary))
            return false;
        Documentary d = (Documentary) o;
        return Objects.equals(id, d.id) && Objects.equals(title, d.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return String.format(
                "Documentary{id='%s', title='%s', year=%d, duration=%dmin, age='%s', theme='%s', narrator='%s', awards=%d}",
                id, title, yearRelease, duration, ageRestriction, theme, narrator, awards.size());
    }
}