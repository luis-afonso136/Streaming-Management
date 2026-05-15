package services;

import model.Documentary;

import java.io.*;
import java.util.*;

/**
 * Service responsible for managing Documentary entities.
 * Uses a TreeSet as the only collection — no duplicates, ordered by title.
 */
public class DocumentaryService {

    private TreeSet<Documentary> documentarySet;

    /**
     * Constructs a new DocumentaryService with an empty TreeSet.
     */
    public DocumentaryService() {
        this.documentarySet = new TreeSet<>();
    }

    /**
     * Creates and stores a new Documentary. Prevents duplicates.
     * 
     * @param id             documentary ID (min 4 chars)
     * @param title          documentary title
     * @param yearRelease    release year (1888-2026)
     * @param duration       duration in minutes (> 0)
     * @param ageRestriction G, PG, PG-13, R, or NC-17
     * @param theme          documentary theme
     * @param narrator       narrator name
     * @return the created Documentary
     * @throws IllegalArgumentException if ID or title already exists
     */
    public Documentary createDocumentary(String id, String title, int yearRelease, int duration,
            String ageRestriction, String theme, String narrator) {
        if (findById(id) != null)
            throw new IllegalArgumentException("A Documentary with ID '" + id + "' already exists.");
        if (findByTitle(title) != null)
            throw new IllegalArgumentException("A Documentary with title '" + title + "' already exists.");
        Documentary doc = new Documentary(id, title, yearRelease, duration, ageRestriction, theme, narrator);
        documentarySet.add(doc);
        return doc;
    }

    /**
     * Finds a Documentary by ID.
     * 
     * @param id the documentary ID
     * @return the Documentary, or null if not found
     */
    public Documentary findById(String id) {
        for (Documentary d : documentarySet)
            if (d.getId().equalsIgnoreCase(id))
                return d;
        return null;
    }

    /**
     * Finds a Documentary by title (case-insensitive).
     * 
     * @param title the documentary title
     * @return the Documentary, or null if not found
     */
    public Documentary findByTitle(String title) {
        for (Documentary d : documentarySet)
            if (d.getTitle().equalsIgnoreCase(title))
                return d;
        return null;
    }

    /**
     * Returns all documentaries with the given age restriction.
     * 
     * @param ageRestriction the restriction to filter by
     * @return list of matching documentaries
     */
    public List<Documentary> getByAgeRestriction(String ageRestriction) {
        List<Documentary> result = new ArrayList<>();
        for (Documentary d : documentarySet)
            if (d.getAgeRestriction().equalsIgnoreCase(ageRestriction))
                result.add(d);
        return result;
    }

    /**
     * Returns all documentaries ordered by title (TreeSet natural ordering).
     * 
     * @return sorted list
     */
    public List<Documentary> getAllOrderedByTitle() {
        return new ArrayList<>(documentarySet);
    }

    /**
     * Returns all documentaries as a list.
     * 
     * @return list of all documentaries
     */
    public List<Documentary> getAll() {
        return new ArrayList<>(documentarySet);
    }

    /**
     * Returns the total number of documentaries.
     * 
     * @return count
     */
    public int count() {
        return documentarySet.size();
    }

    // ==================== Selection Sort by Year ====================

    /**
     * Sorts a list of documentaries by release year ascending using Selection Sort.
     * 
     * @param list the list to sort
     * @return sorted list
     */
    public List<Documentary> selectionSortByYear(List<Documentary> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (list.get(j).getYearRelease() < list.get(minIdx).getYearRelease())
                    minIdx = j;
            Documentary temp = list.get(minIdx);
            list.set(minIdx, list.get(i));
            list.set(i, temp);
        }
        return list;
    }

    // ==================== Export ====================

    /**
     * Exports all documentaries to a .txt file ordered by title.
     * 
     * @param filePath path to the output file
     * @throws IOException if an I/O error occurs
     */
    public void exportToTxt(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Documentary d : documentarySet) {
                bw.write(d.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Exports all documentaries to a .json file ordered by title.
     * 
     * @param filePath path to the output file
     * @throws IOException if an I/O error occurs
     */
    public void exportToJson(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            List<Documentary> docs = getAllOrderedByTitle();
            bw.write("[\n");
            for (int i = 0; i < docs.size(); i++) {
                Documentary d = docs.get(i);
                bw.write(String.format(
                        "  {\"id\":\"%s\",\"title\":\"%s\",\"yearRelease\":%d,\"duration\":%d," +
                                "\"ageRestriction\":\"%s\",\"theme\":\"%s\",\"narrator\":\"%s\"}",
                        d.getId(), d.getTitle(), d.getYearRelease(), d.getDuration(),
                        d.getAgeRestriction(), d.getTheme(), d.getNarrator()));
                if (i < docs.size() - 1)
                    bw.write(",");
                bw.newLine();
            }
            bw.write("]");
        }
    }

    // ==================== Persistence ====================

    /**
     * Saves all documentaries to a binary serialized file.
     * 
     * @param filePath path to save
     * @throws IOException if an I/O error occurs
     */
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(documentarySet));
        }
    }

    /**
     * Loads documentaries from a binary serialized file.
     * 
     * @param filePath path to load from
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if class not found during deserialization
     */
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Documentary> docs = (List<Documentary>) ois.readObject();
            documentarySet.clear();
            documentarySet.addAll(docs);
        }
    }
}