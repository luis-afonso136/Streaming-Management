package tree;

import model.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree for Movie objects.
 * Ordered primarily by yearRelease, secondarily by title.
 * Supports: insert, search, inOrder, preOrder, postOrder,
 * size, remove, minimum, maximum, height.
 */
public class BinarySearchTree {

    private Node<Movie> root;
    private int size;

    /**
     * Constructs an empty BinarySearchTree.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Compares two Movies for BST ordering (yearRelease first, then title).
     * 
     * @param a first Movie
     * @param b second Movie
     * @return negative, zero, or positive
     */
    private int compare(Movie a, Movie b) {
        if (a.getYearRelease() != b.getYearRelease())
            return Integer.compare(a.getYearRelease(), b.getYearRelease());
        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }

    /**
     * Inserts a Movie into the BST.
     * 
     * @param movie the Movie to insert
     */
    public void insert(Movie movie) {
        root = insertRec(root, movie);
        size++;
    }

    private Node<Movie> insertRec(Node<Movie> node, Movie movie) {
        if (node == null)
            return new Node<>(movie);
        int cmp = compare(movie, node.value);
        if (cmp < 0)
            node.left = insertRec(node.left, movie);
        else if (cmp > 0)
            node.right = insertRec(node.right, movie);
        return node;
    }

    /**
     * Searches for a Movie by yearRelease and title.
     * 
     * @param yearRelease the release year
     * @param title       the movie title
     * @return the found Movie, or null if not found
     */
    public Movie search(int yearRelease, String title) {
        return searchRec(root, yearRelease, title);
    }

    private Movie searchRec(Node<Movie> node, int year, String title) {
        if (node == null)
            return null;
        int yearCmp = Integer.compare(year, node.value.getYearRelease());
        if (yearCmp == 0) {
            int titleCmp = title.compareToIgnoreCase(node.value.getTitle());
            if (titleCmp == 0)
                return node.value;
            else if (titleCmp < 0)
                return searchRec(node.left, year, title);
            else
                return searchRec(node.right, year, title);
        } else if (yearCmp < 0)
            return searchRec(node.left, year, title);
        else
            return searchRec(node.right, year, title);
    }

    /**
     * Returns all Movies in in-order (oldest to newest).
     * 
     * @return sorted list by yearRelease ascending
     */
    public List<Movie> inOrder() {
        List<Movie> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(Node<Movie> node, List<Movie> result) {
        if (node == null)
            return;
        inOrderRec(node.left, result);
        result.add(node.value);
        inOrderRec(node.right, result);
    }

    /**
     * Returns all Movies in pre-order (root, left, right).
     * 
     * @return pre-order list
     */
    public List<Movie> preOrder() {
        List<Movie> result = new ArrayList<>();
        preOrderRec(root, result);
        return result;
    }

    private void preOrderRec(Node<Movie> node, List<Movie> result) {
        if (node == null)
            return;
        result.add(node.value);
        preOrderRec(node.left, result);
        preOrderRec(node.right, result);
    }

    /**
     * Returns all Movies in post-order (left, right, root).
     * 
     * @return post-order list
     */
    public List<Movie> postOrder() {
        List<Movie> result = new ArrayList<>();
        postOrderRec(root, result);
        return result;
    }

    private void postOrderRec(Node<Movie> node, List<Movie> result) {
        if (node == null)
            return;
        postOrderRec(node.left, result);
        postOrderRec(node.right, result);
        result.add(node.value);
    }

    /**
     * Returns the number of nodes in the BST.
     * 
     * @return tree size
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the BST is empty.
     * 
     * @return true if empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Removes a Movie identified by yearRelease and title.
     * 
     * @param yearRelease the release year
     * @param title       the movie title
     */
    public void remove(int yearRelease, String title) {
        root = removeRec(root, yearRelease, title);
    }

    private Node<Movie> removeRec(Node<Movie> node, int year, String title) {
        if (node == null)
            return null;
        int yearCmp = Integer.compare(year, node.value.getYearRelease());
        int titleCmp = (yearCmp == 0) ? title.compareToIgnoreCase(node.value.getTitle()) : yearCmp;

        if (yearCmp < 0 || (yearCmp == 0 && titleCmp < 0)) {
            node.left = removeRec(node.left, year, title);
        } else if (yearCmp > 0 || (yearCmp == 0 && titleCmp > 0)) {
            node.right = removeRec(node.right, year, title);
        } else {
            size--;
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            Node<Movie> successor = findMin(node.right);
            node.value = successor.value;
            size++;
            node.right = removeRec(node.right,
                    successor.value.getYearRelease(), successor.value.getTitle());
        }
        return node;
    }

    /**
     * Returns the Movie with the smallest key (oldest by year).
     * 
     * @return minimum Movie, or null if empty
     */
    public Movie minimum() {
        return (root == null) ? null : findMin(root).value;
    }

    private Node<Movie> findMin(Node<Movie> node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    /**
     * Returns the Movie with the largest key (newest by year).
     * 
     * @return maximum Movie, or null if empty
     */
    public Movie maximum() {
        if (root == null)
            return null;
        Node<Movie> current = root;
        while (current.right != null)
            current = current.right;
        return current.value;
    }

    /**
     * Returns the height of the BST.
     * 
     * @return height (0 if empty)
     */
    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node<Movie> node) {
        if (node == null)
            return 0;
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }
}