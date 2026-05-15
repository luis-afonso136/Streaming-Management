package app;

import model.Movie;
import model.Series;
import model.Documentary;
import services.MovieService;
import services.SeriesService;
import services.DocumentaryService;

import java.util.*;

/**
 * Main application entry point for the Streaming Content Management Platform.
 * Provides an interactive text-based menu for managing Movies, Series, and
 * Documentaries.
 */
public class App {

    private static final MovieService movieService = new MovieService();
    private static final SeriesService seriesService = new SeriesService();
    private static final DocumentaryService documentaryService = new DocumentaryService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] VALID_AGE = { "G", "PG", "PG-13", "R", "NC-17" };

    /**
     * Application entry point.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   Streaming Content Management Platform  ║");
        System.out.println("╚══════════════════════════════════════════╝");
        boolean running = true;
        while (running) {
            System.out.println("\n──────────── MAIN MENU ────────────");
            System.out.println("1. Movies");
            System.out.println("2. Series");
            System.out.println("3. Documentaries");
            System.out.println("4. Show All Content (sorted by title)");
            System.out.println("5. Show Counts");
            System.out.println("0. Exit");
            switch (readInt("Choose: ")) {
                case 1 -> movieMenu();
                case 2 -> seriesMenu();
                case 3 -> documentaryMenu();
                case 4 -> showAllContent();
                case 5 -> showCounts();
                case 0 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ==================== MOVIE MENU ====================

    private static void movieMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n──────── MOVIE MENU ────────");
            System.out.println(" 1. Add Movie              2. Search by ID");
            System.out.println(" 3. Search by Title        4. List by duration");
            System.out.println(" 5. List oldest→newest     6. Search by Year");
            System.out.println(" 7. Year Interval          8. Budget > N check");
            System.out.println(" 9. Delete by ID          10. Delete by Title");
            System.out.println("11. BST Pre-Order         12. BST Post-Order");
            System.out.println("13. BST Info              14. Export JSON");
            System.out.println(" 0. Back");
            switch (readInt("Choose: ")) {
                case 1 -> addMovieMenu();
                case 2 -> {
                    Movie m = movieService.findById(readString("ID: "));
                    System.out.println(m != null ? m : "Not found.");
                }
                case 3 -> {
                    Movie m = movieService.findByTitle(readString("Title: "));
                    System.out.println(m != null ? m : "Not found.");
                }
                case 4 -> movieService.getAllOrderedByDuration().forEach(System.out::println);
                case 5 -> movieService.getAllOrderedByYear().forEach(System.out::println);
                case 6 -> {
                    int y = readInt("Year: ");
                    movieService.getByYear(y).forEach(System.out::println);
                }
                case 7 -> {
                    int s = readInt("Start year: ");
                    int e = readInt("End year: ");
                    movieService.getByYearInterval(s, e).forEach(System.out::println);
                }
                case 8 -> {
                    double n = readDouble("Budget N: ");
                    System.out.println("Exists: " + movieService.hasBudgetHigherThan(n));
                }
                case 9 -> System.out.println(movieService.deleteById(readString("ID: ")) ? "Deleted." : "Not found.");
                case 10 ->
                    System.out.println(movieService.deleteByTitle(readString("Title: ")) ? "Deleted." : "Not found.");
                case 11 -> movieService.getBST().preOrder()
                        .forEach(m -> System.out.println(m.getYearRelease() + " - " + m.getTitle()));
                case 12 -> movieService.getBST().postOrder()
                        .forEach(m -> System.out.println(m.getYearRelease() + " - " + m.getTitle()));
                case 13 -> {
                    System.out.println("Min:    "
                            + (movieService.getBST().minimum() != null ? movieService.getBST().minimum().getTitle()
                                    : "empty"));
                    System.out.println("Max:    "
                            + (movieService.getBST().maximum() != null ? movieService.getBST().maximum().getTitle()
                                    : "empty"));
                    System.out.println("Height: " + movieService.getBST().height());
                    System.out.println("Size:   " + movieService.getBST().size());
                }
                case 14 -> {
                    try {
                        movieService.exportToJson(readString("File path (.json): "));
                        System.out.println("Exported.");
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void addMovieMenu() {
        System.out.println("--- Add Movie ---");
        String id = readValidId("ID (min 4 chars): ");
        String title = readNonEmpty("Title: ");
        int year = readIntInRange("Year (1888-2026): ", 1888, 2026);
        int duration = readPositiveInt("Duration (minutes): ");
        String age = readAgeRestriction();
        String director = readNonEmpty("Director: ");
        String country = readNonEmpty("Country: ");
        double budget = readNonNegDouble("Budget: ");
        try {
            System.out.println(
                    "Created: " + movieService.createMovie(id, title, year, duration, age, director, country, budget));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== SERIES MENU ====================

    private static void seriesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n──────── SERIES MENU ────────");
            System.out.println("1. Add Series      2. Search by ID    3. Search by Title");
            System.out.println("4. Episodes > N    5. Seasons > N     6. In Production");
            System.out.println("7. By creation     8. Last 3          0. Back");
            switch (readInt("Choose: ")) {
                case 1 -> addSeriesMenu();
                case 2 -> {
                    Series s = seriesService.findById(readString("ID: "));
                    System.out.println(s != null ? s : "Not found.");
                }
                case 3 -> {
                    Series s = seriesService.findByTitle(readString("Title: "));
                    System.out.println(s != null ? s : "Not found.");
                }
                case 4 -> {
                    int n = readPositiveInt("Min total episodes: ");
                    seriesService.findWithMoreThanNEpisodes(n).forEach(System.out::println);
                }
                case 5 -> {
                    int n = readPositiveInt("Min seasons: ");
                    seriesService.findWithMoreThanNSeasons(n).forEach(System.out::println);
                }
                case 6 -> seriesService.getInProduction().forEach(System.out::println);
                case 7 -> seriesService.getAllByCreationOrder().forEach(System.out::println);
                case 8 -> seriesService.getLastNCreated(3).forEach(System.out::println);
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void addSeriesMenu() {
        System.out.println("--- Add Series ---");
        String id = readValidId("ID (min 4 chars): ");
        String title = readNonEmpty("Title: ");
        int year = readIntInRange("Year (1888-2026): ", 1888, 2026);
        int duration = readPositiveInt("Episode duration (minutes): ");
        String age = readAgeRestriction();
        int seasons = readPositiveInt("Number of seasons: ");
        int ep = readPositiveInt("Episodes per season: ");
        boolean prod = readBoolean("In production? (yes/no): ");
        try {
            System.out.println(
                    "Created: " + seriesService.createSeries(id, title, year, duration, age, seasons, ep, prod));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== DOCUMENTARY MENU ====================

    private static void documentaryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n──────── DOCUMENTARY MENU ────────");
            System.out.println("1. Add    2. Search by ID    3. Search by Title    4. List all");
            System.out.println("5. Filter by age restriction    6. Export TXT    7. Export JSON    0. Back");
            switch (readInt("Choose: ")) {
                case 1 -> addDocumentaryMenu();
                case 2 -> {
                    Documentary d = documentaryService.findById(readString("ID: "));
                    System.out.println(d != null ? d : "Not found.");
                }
                case 3 -> {
                    Documentary d = documentaryService.findByTitle(readString("Title: "));
                    System.out.println(d != null ? d : "Not found.");
                }
                case 4 -> documentaryService.getAllOrderedByTitle().forEach(System.out::println);
                case 5 -> {
                    String age = readString("Age restriction: ");
                    documentaryService.getByAgeRestriction(age).forEach(System.out::println);
                }
                case 6 -> {
                    try {
                        documentaryService.exportToTxt(readString("File path (.txt): "));
                        System.out.println("Exported.");
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
                case 7 -> {
                    try {
                        documentaryService.exportToJson(readString("File path (.json): "));
                        System.out.println("Exported.");
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void addDocumentaryMenu() {
        System.out.println("--- Add Documentary ---");
        String id = readValidId("ID (min 4 chars): ");
        String title = readNonEmpty("Title: ");
        int year = readIntInRange("Year (1888-2026): ", 1888, 2026);
        int duration = readPositiveInt("Duration (minutes): ");
        String age = readAgeRestriction();
        String theme = readNonEmpty("Theme: ");
        String narrator = readNonEmpty("Narrator: ");
        try {
            System.out.println("Created: "
                    + documentaryService.createDocumentary(id, title, year, duration, age, theme, narrator));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== GLOBAL ====================

    private static void showAllContent() {
        List<Movie> movies = movieService.getAll();
        movieService.bubbleSortByTitle(movies);
        List<Series> series = seriesService.getAll();
        seriesService.insertionSortByTitle(series);
        System.out.print("\nMovies:        ");
        movies.forEach(m -> System.out.print(m.getTitle() + "; "));
        System.out.println();
        System.out.print("Series:        ");
        series.forEach(s -> System.out.print(s.getTitle() + "; "));
        System.out.println();
        System.out.print("Documentaries: ");
        documentaryService.getAllOrderedByTitle().forEach(d -> System.out.print(d.getTitle() + "; "));
        System.out.println();
    }

    private static void showCounts() {
        System.out.println("Movies: " + movieService.count()
                + " | Series: " + seriesService.count()
                + " | Documentaries: " + documentaryService.count());
    }

    // ==================== INPUT HELPERS ====================

    private static String readString(String p) {
        System.out.print(p);
        return scanner.nextLine().trim();
    }

    private static String readNonEmpty(String p) {
        String v;
        do {
            v = readString(p);
            if (v.isEmpty())
                System.out.println("Cannot be empty.");
        } while (v.isEmpty());
        return v;
    }

    private static String readValidId(String p) {
        String v;
        do {
            v = readString(p);
            if (v.length() < 4)
                System.out.println("Min 4 characters.");
        } while (v.length() < 4);
        return v;
    }

    private static int readInt(String p) {
        while (true) {
            System.out.print(p);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private static int readIntInRange(String p, int min, int max) {
        int v;
        do {
            v = readInt(p);
            if (v < min || v > max)
                System.out.printf("Must be %d-%d.%n", min, max);
        } while (v < min || v > max);
        return v;
    }

    private static int readPositiveInt(String p) {
        int v;
        do {
            v = readInt(p);
            if (v <= 0)
                System.out.println("Must be > 0.");
        } while (v <= 0);
        return v;
    }

    private static double readDouble(String p) {
        while (true) {
            System.out.print(p);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static double readNonNegDouble(String p) {
        double v;
        do {
            v = readDouble(p);
            if (v < 0)
                System.out.println("Must be >= 0.");
        } while (v < 0);
        return v;
    }

    private static String readAgeRestriction() {
        String v;
        do {
            v = readString("Age restriction (G/PG/PG-13/R/NC-17): ");
            boolean ok = false;
            for (String a : VALID_AGE)
                if (a.equals(v)) {
                    ok = true;
                    break;
                }
            if (!ok)
                System.out.println("Must be one of: G, PG, PG-13, R, NC-17.");
            else
                return v;
        } while (true);
    }

    private static boolean readBoolean(String p) {
        while (true) {
            String v = readString(p).toLowerCase();
            if (v.equals("yes") || v.equals("y") || v.equals("true"))
                return true;
            if (v.equals("no") || v.equals("n") || v.equals("false"))
                return false;
            System.out.println("Enter yes or no.");
        }
    }
}