package app;

import model.Movie;
import model.Series;
import model.Documentary;
import services.MovieService;
import services.SeriesService;
import services.DocumentaryService;

import java.util.List;

/**
 * DemoApp — demonstrates all platform features without manual input.
 * Covers Simple, Medium, and Complex cases for the presentation.
 */
public class DemoApp {

    private static final MovieService movieService = new MovieService();
    private static final SeriesService seriesService = new SeriesService();
    private static final DocumentaryService documentaryService = new DocumentaryService();

    /**
     * Runs the full automated demo.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        header("STREAMING PLATFORM — DEMO");
        setupData();
        simpleCases();
        mediumCases();
        complexCases();
        System.out.println("\n✅ Demo completed successfully.");
    }

    // ==================== SETUP ====================

    private static void setupData() {
        header("SETUP: Creating Sample Data");

        movieService.createMovie("MOV1", "Inception", 2010, 148, "PG-13", "Christopher Nolan", "USA", 160000000);
        movieService.createMovie("MOV2", "The Godfather", 1972, 175, "R", "Francis Ford Coppola", "USA", 6000000);
        movieService.createMovie("MOV3", "Parasite", 2019, 132, "R", "Bong Joon-ho", "South Korea", 11400000);
        movieService.createMovie("MOV4", "Spirited Away", 2001, 125, "PG", "Hayao Miyazaki", "Japan", 19000000);
        movieService.createMovie("MOV5", "Alien", 1979, 117, "R", "Ridley Scott", "UK", 11000000);
        Movie inc = movieService.findById("MOV1");
        inc.addRating(9);
        inc.addRating(10);
        inc.addRating(8);
        inc.addCaption("Portuguese");
        inc.addCaption("English");

        seriesService.createSeries("SER1", "Breaking Bad", 2008, 47, "R", 5, 13, false);
        seriesService.createSeries("SER2", "Stranger Things", 2016, 50, "PG-13", 4, 9, true);
        seriesService.createSeries("SER3", "The Crown", 2016, 58, "PG-13", 6, 10, false);
        seriesService.createSeries("SER4", "House of the Dragon", 2022, 60, "R", 2, 8, true);
        seriesService.createSeries("SER5", "Chernobyl", 2019, 60, "R", 1, 5, false);

        documentaryService.createDocumentary("DOC1", "Blue Planet II", 2017, 50, "G", "Nature", "David Attenborough");
        documentaryService.createDocumentary("DOC2", "The Last Dance", 2020, 50, "PG", "Sports", "Mike Tollin");
        documentaryService.createDocumentary("DOC3", "13th", 2016, 100, "PG-13", "Social Justice", "Ava DuVernay");
        documentaryService.createDocumentary("DOC4", "Apollo 11", 2019, 93, "G", "Space", "Todd Douglas Miller");
        Documentary bp = documentaryService.findById("DOC1");
        bp.addAward("BAFTA Best Factual Series");
        bp.addAward("Emmy Award");

        print("✅ 5 Movies, 5 Series, 4 Documentaries created.");
    }

    // ==================== SIMPLE CASES ====================

    private static void simpleCases() {
        header("SIMPLE CASES");

        print("1. Entities created successfully in setup.\n");

        print("2. Attempting duplicate Movie ID 'MOV1':");
        try {
            movieService.createMovie("MOV1", "Other Movie", 2020, 90, "PG", "Dir", "PT", 0);
        } catch (IllegalArgumentException e) {
            print("   ✅ Rejected: " + e.getMessage());
        }

        print("\n3. Attempting duplicate Series title 'Chernobyl':");
        try {
            seriesService.createSeries("SER9", "Chernobyl", 2021, 45, "PG", 2, 6, false);
        } catch (IllegalArgumentException e) {
            print("   ✅ Rejected: " + e.getMessage());
        }

        print("\n4. Search Movie by ID 'MOV3':");
        print("   " + movieService.findById("MOV3"));

        print("\n5. Search Movie by Title 'Inception':");
        Movie m = movieService.findByTitle("Inception");
        print("   " + m);
        print("   Average Rating: " + m.getAverageRating() + " (" + m.getNumberOfRatings() + " ratings)");
        print("   Captions: " + m.getCaptions());
    }

    // ==================== MEDIUM CASES ====================

    private static void mediumCases() {
        header("MEDIUM CASES");

        print("1. All Movies ordered by duration (Quick Sort):");
        movieService.getAllOrderedByDuration()
                .forEach(mv -> print("   " + mv.getDuration() + " min  →  " + mv.getTitle()));

        print("\n2. Series currently in production:");
        seriesService.getInProduction().forEach(s -> print("   " + s.getTitle()));

        print("\n3. Documentaries with age restriction 'G':");
        documentaryService.getByAgeRestriction("G").forEach(d -> print("   " + d.getTitle()));

        print("\n4. Last 3 Series created (via Stack):");
        seriesService.getLastNCreated(3).forEach(s -> print("   " + s.getTitle()));

        print("\n5. Series with more than 50 total episodes:");
        seriesService.findWithMoreThanNEpisodes(50)
                .forEach(s -> print("   " + s.getTitle() + " → " + s.getTotalEpisodes() + " episodes"));

        print("\n6. Any Movie with budget > 100,000,000: " + movieService.hasBudgetHigherThan(100_000_000));

        print("\n7. Blue Planet II awards:");
        Documentary bp = documentaryService.findById("DOC1");
        print("   isAwarded: " + bp.isAwarded());
        bp.getAwards().forEach(a -> print("   - " + a));
    }

    // ==================== COMPLEX CASES ====================

    private static void complexCases() {
        header("COMPLEX CASES");

        print("1. BST In-Order (oldest → newest):");
        movieService.getAllOrderedByYear().forEach(mv -> print("   " + mv.getYearRelease() + "  " + mv.getTitle()));

        print("\n2. BST Pre-Order:");
        movieService.getBST().preOrder().forEach(mv -> print("   " + mv.getYearRelease() + "  " + mv.getTitle()));

        print("\n3. BST Post-Order:");
        movieService.getBST().postOrder().forEach(mv -> print("   " + mv.getYearRelease() + "  " + mv.getTitle()));

        print("\n4. BST Stats:");
        print("   Minimum: " + movieService.getBST().minimum().getTitle() + " ("
                + movieService.getBST().minimum().getYearRelease() + ")");
        print("   Maximum: " + movieService.getBST().maximum().getTitle() + " ("
                + movieService.getBST().maximum().getYearRelease() + ")");
        print("   Height:  " + movieService.getBST().height());
        print("   Size:    " + movieService.getBST().size());

        print("\n5. Removing 'Alien' (1979) from BST...");
        movieService.deleteByTitle("Alien");
        print("   BST size after removal: " + movieService.getBST().size());
        print("   In-Order after removal:");
        movieService.getAllOrderedByYear().forEach(mv -> print("   " + mv.getYearRelease() + "  " + mv.getTitle()));

        print("\n6. Export Movies to JSON...");
        try {
            movieService.exportToJson("movies_export.json");
            print("   ✅ Exported to movies_export.json");
        } catch (Exception e) {
            print("   ❌ Error: " + e.getMessage());
        }

        print("\n7. Export Documentaries to TXT...");
        try {
            documentaryService.exportToTxt("docs_export.txt");
            print("   ✅ Exported to docs_export.txt");
        } catch (Exception e) {
            print("   ❌ Error: " + e.getMessage());
        }

        print("\n8. All content sorted by title:");
        List<Movie> movies = movieService.getAll();
        movieService.bubbleSortByTitle(movies);
        List<Series> series = seriesService.getAll();
        seriesService.insertionSortByTitle(series);
        System.out.print("   Movies:        ");
        movies.forEach(mv -> System.out.print(mv.getTitle() + "; "));
        System.out.println();
        System.out.print("   Series:        ");
        series.forEach(s -> System.out.print(s.getTitle() + "; "));
        System.out.println();
        System.out.print("   Documentaries: ");
        documentaryService.getAllOrderedByTitle().forEach(d -> System.out.print(d.getTitle() + "; "));
        System.out.println();

        print("\n9. Movies between 2000 and 2020:");
        movieService.getByYearInterval(2000, 2020)
                .forEach(mv -> print("   " + mv.getYearRelease() + "  " + mv.getTitle()));

        print("\n10. Total counts:");
        print("    Movies: " + movieService.count() + " | Series: " + seriesService.count() + " | Documentaries: "
                + documentaryService.count());
    }

    // ==================== HELPERS ====================

    private static void header(String title) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf("║  %-36s║%n", title);
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void print(String msg) {
        System.out.println(msg);
    }
}