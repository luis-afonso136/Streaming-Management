package tree;

/**
 * Generic node for the Binary Search Tree.
 * 
 * @param <T> type of value stored in this node
 */
public class Node<T> {

    /** The value stored in this node. */
    T value;

    /** Left child node. */
    Node<T> left;

    /** Right child node. */
    Node<T> right;

    /**
     * Constructs a Node with the given value.
     * 
     * @param value the value to store
     */
    public Node(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the value stored in this node.
     * 
     * @return the node value
     */
    public T getValue() {
        return value;
    }
}