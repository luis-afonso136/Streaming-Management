package model;

import interfaces.Classifiable;
import interfaces.Captionable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a TV Series in the streaming platform.
 * Implements Classifiable (ratings) and Captionable (subtitles).
 */
public class Series implements Classifiable, Captionable, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private int yearRelease;
    private int duration;
    private String ageRestriction;
    private int noSeasons;
    private int epPerSeason;
    private boolean inProduction;
    private List<Integer> ratings;
    private List<String> captions;

    /**
     * Constructs a Series with full parameter validation.
     * 
     * @param id             must have at least 4 characters, not null/empty
     * @param title          cannot be null or empty
     * @param yearRelease    must be between 1888 and 2026
     * @param duration       must be greater than 0
     * @param ageRestriction must be one of: G, PG, PG-13, R, NC-17
     * @param noSeasons      minimum value: 1
     * @param epPerSeason    minimum value: 1
     * @param inProduction   true if still being produced
     */
    public Series(String id, String title, int yearRelease, int duration,
            String ageRestriction, int noSeasons, int epPerSeason, boolean inProduction) {
        setId(id);
        setTitle(title);
        setYearRelease(yearRelease);
        setDuration(duration);
        setAgeRestriction(ageRestriction);
        setNoSeasons(noSeasons);
        setEpPerSeason(epPerSeason);
        this.inProduction = inProduction;
        this.ratings = new ArrayList<>();
        this.captions = new ArrayList<>();
    }

    /** @return the series ID */
    public String getId() {
        return id;
    }

    /**
     * Sets the series ID.
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

    /** @return the series title */
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

    /** @return episode duration in minutes */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets episode duration.
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

    /** @return number of seasons */
    public int getNoSeasons() {
        return noSeasons;
    }

    /**
     * Sets the number of seasons.
     * 
     * @param noSeasons minimum value: 1
     */
    public void setNoSeasons(int noSeasons) {
        if (noSeasons < 1)
            throw new IllegalArgumentException("Number of seasons must be at least 1.");
        this.noSeasons = noSeasons;
    }

    /** @return episodes per season */
    public int getEpPerSeason() {
        return epPerSeason;
    }

    /**
     * Sets episodes per season.
     * 
     * @param epPerSeason minimum value: 1
     */
    public void setEpPerSeason(int epPerSeason) {
        if (epPerSeason < 1)
            throw new IllegalArgumentException("Episodes per season must be at least 1.");
        this.epPerSeason = epPerSeason;
    }

    /** @return true if the series is still in production */
    public boolean isInProduction() {
        return inProduction;
    }

    /**
     * Sets the production status.
     * 
     * @param inProduction true if still in production
     */
    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    /**
     * Returns total episodes (noSeasons * epPerSeason).
     * 
     * @return total episodes
     */
    public int getTotalEpisodes() {
        return noSeasons * epPerSeason;
    }

    @Override
    public void addRating(int rating) {
        ratings.add(rating);
    }

    @Override
    public double getAverageRating() {
        if (ratings.isEmpty())
            return 0.0;
        int sum = 0;
        for (int r : ratings)
            sum += r;
        return (double) sum / ratings.size();
    }

    @Override
    public int getNumberOfRatings() {
        return ratings.size();
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
        if (!(o instanceof Series))
            return false;
        Series s = (Series) o;
        return Objects.equals(id, s.id) && Objects.equals(title, s.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return String.format(
                "Series{id='%s', title='%s', year=%d, duration=%dmin, age='%s', seasons=%d, ep/season=%d, inProduction=%b, avgRating=%.1f}",
                id, title, yearRelease, duration, ageRestriction, noSeasons, epPerSeason, inProduction,
                getAverageRating());
    }
}