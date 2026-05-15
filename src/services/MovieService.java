package services;

import model.Movie;
import tree.BinarySearchTree;

import java.io.*;
import java.util.*;

/**
 * Service responsible for managing Movie entities.
 * Uses a HashMap (key: ID) and a Binary Search Tree (ordered by yearRelease +
 * title).
 */
public class MovieService {

    private Map<String, Movie> movieMap;
    private BinarySearchTree bst;

    /**
     * Constructs a new MovieService with empty collections.
     */
    public MovieService() {
        this.movieMap = new HashMap<>();
        this.bst = new BinarySearchTree();
    }

    /**
     * Creates and stores a new Movie. Prevents duplicate ID or title.
     * 
     * @param id             movie ID (min 4 chars)
     * @param title          movie title
     * @param yearRelease    release year (1888-2026)
     * @param duration       duration in minutes (> 0)
     * @param ageRestriction G, PG, PG-13, R, or NC-17
     * @param director       director name
     * @param country        country of production
     * @param budget         production budget (>= 0)
     * @return the created Movie
     * @throws IllegalArgumentException if ID or title already exists
     */
    public Movie createMovie(String id, String title, int yearRelease, int duration,
            String ageRestriction, String director, String country, double budget) {
        if (movieMap.containsKey(id))
            throw new IllegalArgumentException("A Movie with ID '" + id + "' already exists.");
        if (findByTitle(title) != null)
            throw new IllegalArgumentException("A Movie with title '" + title + "' already exists.");
        Movie movie = new Movie(id, title, yearRelease, duration, ageRestriction, director, country, budget);
        movieMap.put(id, movie);
        bst.insert(movie);
        return movie;
    }

    /**
     * Finds a Movie by ID.
     * 
     * @param id the movie ID
     * @return the Movie, or null if not found
     */
    public Movie findById(String id) {
        return movieMap.get(id);
    }

    /**
     * Finds a Movie by title (case-insensitive sequential search).
     * 
     * @param title the movie title
     * @return the Movie, or null if not found
     */
    public Movie findByTitle(String title) {
        for (Movie m : movieMap.values())
            if (m.getTitle().equalsIgnoreCase(title))
                return m;
        return null;
    }

    /**
     * Deletes a Movie by ID.
     * 
     * @param id the movie ID
     * @return true if deleted, false if not found
     */
    public boolean deleteById(String id) {
        Movie movie = movieMap.remove(id);
        if (movie != null) {
            bst.remove(movie.getYearRelease(), movie.getTitle());
            return true;
        }
        return false;
    }

    /**
     * Deletes a Movie by title.
     * 
     * @param title the movie title
     * @return true if deleted, false if not found
     */
    public boolean deleteByTitle(String title) {
        Movie movie = findByTitle(title);
        if (movie != null) {
            movieMap.remove(movie.getId());
            bst.remove(movie.getYearRelease(), movie.getTitle());
            return true;
        }
        return false;
    }

    /**
     * Returns all movies ordered by duration ascending (Quick Sort).
     * 
     * @return sorted list
     */
    public List<Movie> getAllOrderedByDuration() {
        List<Movie> list = new ArrayList<>(movieMap.values());
        quickSortByDuration(list, 0, list.size() - 1);
        return list;
    }

    /**
     * Returns all movies ordered by year via BST in-order traversal.
     * 
     * @return list oldest to newest
     */
    public List<Movie> getAllOrderedByYear() {
        return bst.inOrder();
    }

    /**
     * Returns movies from a specific release year.
     * 
     * @param year the year to filter
     * @return list of matching movies
     */
    public List<Movie> getByYear(int year) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : bst.inOrder())
            if (m.getYearRelease() == year)
                result.add(m);
        return result;
    }

    /**
     * Returns movies within a release year interval.
     * 
     * @param startYear start of interval (inclusive)
     * @param endYear   end of interval (inclusive)
     * @return list of matching movies
     */
    public List<Movie> getByYearInterval(int startYear, int endYear) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : bst.inOrder())
            if (m.getYearRelease() >= startYear && m.getYearRelease() <= endYear)
                result.add(m);
        return result;
    }

    /**
     * Checks if any movie has a budget greater than n.
     * 
     * @param n the budget threshold
     * @return true if any movie exceeds that budget
     */
    public boolean hasBudgetHigherThan(double n) {
        for (Movie m : movieMap.values())
            if (m.getBudget() > n)
                return true;
        return false;
    }

    /**
     * Returns all movies as a list.
     * 
     * @return list of all movies
     */
    public List<Movie> getAll() {
        return new ArrayList<>(movieMap.values());
    }

    /**
     * Returns the total number of movies.
     * 
     * @return count
     */
    public int count() {
        return movieMap.size();
    }

    /**
     * Returns the BST instance.
     * 
     * @return the BinarySearchTree
     */
    public BinarySearchTree getBST() {
        return bst;
    }

    // ==================== Quick Sort by Duration ====================

    private void quickSortByDuration(List<Movie> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSortByDuration(list, low, pi - 1);
            quickSortByDuration(list, pi + 1, high);
        }
    }

    private int partition(List<Movie> list, int low, int high) {
        int pivot = list.get(high).getDuration();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getDuration() <= pivot) {
                i++;
                Movie temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        Movie temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }

    // ==================== Optimized Bubble Sort by Title ====================

    /**
     * Sorts a list of movies by title using Optimized Bubble Sort.
     * 
     * @param list the list to sort
     * @return sorted list
     */
    public List<Movie> bubbleSortByTitle(List<Movie> list) {
        int n = list.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getTitle().compareToIgnoreCase(list.get(j + 1).getTitle()) > 0) {
                    Movie temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
        return list;
    }

    // ==================== Export ====================

    /**
     * Exports all movies to a .txt file ordered by year.
     * 
     * @param filePath path to the output file
     * @throws IOException if an I/O error occurs
     */
    public void exportToTxt(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Movie m : getAllOrderedByYear()) {
                bw.write(m.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Exports all movies to a .json file ordered by year.
     * 
     * @param filePath path to the output file
     * @throws IOException if an I/O error occurs
     */
    public void exportToJson(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            List<Movie> movies = getAllOrderedByYear();
            bw.write("[\n");
            for (int i = 0; i < movies.size(); i++) {
                Movie m = movies.get(i);
                bw.write(String.format(
                        "  {\"id\":\"%s\",\"title\":\"%s\",\"yearRelease\":%d,\"duration\":%d," +
                                "\"ageRestriction\":\"%s\",\"director\":\"%s\",\"country\":\"%s\",\"budget\":%.2f}",
                        m.getId(), m.getTitle(), m.getYearRelease(), m.getDuration(),
                        m.getAgeRestriction(), m.getDirector(), m.getCountry(), m.getBudget()));
                if (i < movies.size() - 1)
                    bw.write(",");
                bw.newLine();
            }
            bw.write("]");
        }
    }

    // ==================== Persistence ====================

    /**
     * Saves all movies to a binary serialized file.
     * 
     * @param filePath path to save
     * @throws IOException if an I/O error occurs
     */
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(movieMap.values()));
        }
    }

    /**
     * Loads movies from a binary serialized file.
     * 
     * @param filePath path to load from
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if class not found during deserialization
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Movie> movies = (List<Movie>) ois.readObject();
            movieMap.clear();
            bst = new BinarySearchTree();
            for (Movie m : movies) {
                movieMap.put(m.getId(), m);
                bst.insert(m);
            }
        }
    }
}