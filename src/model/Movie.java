package model;

import interfaces.Classifiable;
import interfaces.Captionable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Movie in the streaming platform.
 * Implements Classifiable (ratings) and Captionable (subtitles).
 */
public class Movie implements Classifiable, Captionable, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private int yearRelease;
    private int duration;
    private String ageRestriction;
    private String director;
    private String country;
    private double budget;
    private List<Integer> ratings;
    private List<String> captions;

    /**
     * Constructs a Movie with full parameter validation.
     * 
     * @param id             must have at least 4 characters, not null/empty
     * @param title          cannot be null or empty
     * @param yearRelease    must be between 1888 and 2026
     * @param duration       must be greater than 0
     * @param ageRestriction must be one of: G, PG, PG-13, R, NC-17
     * @param director       cannot be null or empty
     * @param country        cannot be null or empty
     * @param budget         must be >= 0
     */
    public Movie(String id, String title, int yearRelease, int duration,
            String ageRestriction, String director, String country, double budget) {
        setId(id);
        setTitle(title);
        setYearRelease(yearRelease);
        setDuration(duration);
        setAgeRestriction(ageRestriction);
        setDirector(director);
        setCountry(country);
        setBudget(budget);
        this.ratings = new ArrayList<>();
        this.captions = new ArrayList<>();
    }

    /** @return the movie ID */
    public String getId() {
        return id;
    }

    /**
     * Sets the movie ID.
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

    /** @return the movie title */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the movie title.
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

    /** @return the director name */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director.
     * 
     * @param director cannot be null or empty
     */
    public void setDirector(String director) {
        if (director == null || director.trim().isEmpty())
            throw new IllegalArgumentException("Director cannot be null or empty.");
        this.director = director.trim();
    }

    /** @return the country of production */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     * 
     * @param country cannot be null or empty
     */
    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty())
            throw new IllegalArgumentException("Country cannot be null or empty.");
        this.country = country.trim();
    }

    /** @return the production budget */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget.
     * 
     * @param budget must be >= 0
     */
    public void setBudget(double budget) {
        if (budget < 0)
            throw new IllegalArgumentException("Budget must be >= 0.");
        this.budget = budget;
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
        if (!(o instanceof Movie))
            return false;
        Movie m = (Movie) o;
        return Objects.equals(id, m.id) && Objects.equals(title, m.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return String.format(
                "Movie{id='%s', title='%s', year=%d, duration=%dmin, age='%s', director='%s', country='%s', budget=%.2f, avgRating=%.1f}",
                id, title, yearRelease, duration, ageRestriction, director, country, budget, getAverageRating());
    }
}