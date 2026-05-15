package services;

import model.Series;
import utilities.Stack;

import java.io.*;
import java.util.*;

/**
 * Service responsible for managing Series entities.
 * Uses a HashMap (key: ID) and a custom LIFO Stack for creation order tracking.
 */
public class SeriesService {

    private Map<String, Series> seriesMap;
    private Stack<Series> creationStack;

    /**
     * Constructs a new SeriesService with empty collections.
     */
    public SeriesService() {
        this.seriesMap = new HashMap<>();
        this.creationStack = new Stack<>();
    }

    /**
     * Creates and stores a new Series. Prevents duplicate ID or title.
     * 
     * @param id             series ID (min 4 chars)
     * @param title          series title
     * @param yearRelease    release year (1888-2026)
     * @param duration       episode duration in minutes (> 0)
     * @param ageRestriction G, PG, PG-13, R, or NC-17
     * @param noSeasons      number of seasons (>= 1)
     * @param epPerSeason    episodes per season (>= 1)
     * @param inProduction   whether the series is still in production
     * @return the created Series
     * @throws IllegalArgumentException if ID or title already exists
     */
    public Series createSeries(String id, String title, int yearRelease, int duration,
            String ageRestriction, int noSeasons, int epPerSeason, boolean inProduction) {
        if (seriesMap.containsKey(id))
            throw new IllegalArgumentException("A Series with ID '" + id + "' already exists.");
        if (findByTitle(title) != null)
            throw new IllegalArgumentException("A Series with title '" + title + "' already exists.");
        Series series = new Series(id, title, yearRelease, duration, ageRestriction, noSeasons, epPerSeason,
                inProduction);
        seriesMap.put(id, series);
        creationStack.push(series);
        return series;
    }

    /**
     * Finds a Series by ID.
     * 
     * @param id the series ID
     * @return the Series, or null if not found
     */
    public Series findById(String id) {
        return seriesMap.get(id);
    }

    /**
     * Finds a Series by title (case-insensitive sequential search).
     * 
     * @param title the series title
     * @return the Series, or null if not found
     */
    public Series findByTitle(String title) {
        for (Series s : seriesMap.values())
            if (s.getTitle().equalsIgnoreCase(title))
                return s;
        return null;
    }

    /**
     * Returns all series with more than N total episodes.
     * 
     * @param n minimum episode threshold
     * @return list of matching series
     */
    public List<Series> findWithMoreThanNEpisodes(int n) {
        List<Series> result = new ArrayList<>();
        for (Series s : seriesMap.values())
            if (s.getTotalEpisodes() > n)
                result.add(s);
        return result;
    }

    /**
     * Returns all series with more than N seasons.
     * 
     * @param n minimum season threshold
     * @return list of matching series
     */
    public List<Series> findWithMoreThanNSeasons(int n) {
        List<Series> result = new ArrayList<>();
        for (Series s : seriesMap.values())
            if (s.getNoSeasons() > n)
                result.add(s);
        return result;
    }

    /**
     * Returns all series currently in production.
     * 
     * @return list of series in production
     */
    public List<Series> getInProduction() {
        List<Series> result = new ArrayList<>();
        for (Series s : seriesMap.values())
            if (s.isInProduction())
                result.add(s);
        return result;
    }

    /**
     * Returns all series in creation order (most recently created first).
     * 
     * @return list from newest to oldest
     */
    public List<Series> getAllByCreationOrder() {
        return creationStack.toList();
    }

    /**
     * Returns the last N series created.
     * 
     * @param n number of series to return
     * @return list of the last N created
     */
    public List<Series> getLastNCreated(int n) {
        List<Series> all = creationStack.toList();
        return all.subList(0, Math.min(n, all.size()));
    }

    /**
     * Returns all series as a list.
     * 
     * @return list of all series
     */
    public List<Series> getAll() {
        return new ArrayList<>(seriesMap.values());
    }

    /**
     * Returns the total number of series.
     * 
     * @return count
     */
    public int count() {
        return seriesMap.size();
    }

    // ==================== Insertion Sort by Title ====================

    /**
     * Sorts a list of series by title using Insertion Sort.
     * 
     * @param list the list to sort
     * @return sorted list
     */
    public List<Series> insertionSortByTitle(List<Series> list) {
        for (int i = 1; i < list.size(); i++) {
            Series key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
        return list;
    }

    // ==================== Persistence ====================

    /**
     * Saves all series to a binary serialized file.
     * 
     * @param filePath path to save
     * @throws IOException if an I/O error occurs
     */
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(seriesMap.values()));
        }
    }

    /**
     * Loads series from a binary serialized file.
     * 
     * @param filePath path to load from
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if class not found during deserialization
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Series> list = (List<Series>) ois.readObject();
            seriesMap.clear();
            creationStack = new Stack<>();
            List<Series> reversed = new ArrayList<>(list);
            Collections.reverse(reversed);
            for (Series s : reversed) {
                seriesMap.put(s.getId(), s);
                creationStack.push(s);
            }
        }
    }
}