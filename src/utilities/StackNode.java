package utilities;

/**
 * Generic node used by the custom Stack implementation.
 * Holds a value and a reference to the next node.
 * 
 * @param <T> the type of value stored in the node
 */
public class StackNode<T> {

    /** The value stored in this node. */
    T value;

    /** Reference to the next node in the stack. */
    StackNode<T> next;

    /**
     * Constructs a StackNode with the given value.
     * 
     * @param value the value to store
     */
    public StackNode(T value) {
        this.value = value;
        this.next = null;
    }

    /**
     * Returns the value stored in this node.
     * 
     * @return the node value
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the next node in the stack.
     * 
     * @return next StackNode
     */
    public StackNode<T> getNext() {
        return next;
    }
}