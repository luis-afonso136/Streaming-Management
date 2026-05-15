package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom generic LIFO (Last In, First Out) Stack using linked StackNodes.
 * Does not use java.util.Stack.
 * 
 * @param <T> the type of elements stored in the stack
 */
public class Stack<T> {

    private StackNode<T> top;
    private int size;

    /**
     * Constructs an empty Stack.
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes a new element onto the top of the stack.
     * 
     * @param value the element to push
     */
    public void push(T value) {
        StackNode<T> newNode = new StackNode<>(value);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top element of the stack.
     * 
     * @return the top element
     * @throws RuntimeException if the stack is empty
     */
    public T pop() {
        if (isEmpty())
            throw new RuntimeException("Stack is empty.");
        T value = top.value;
        top = top.next;
        size--;
        return value;
    }

    /**
     * Returns (without removing) the top element of the stack.
     * 
     * @return the top element
     * @throws RuntimeException if the stack is empty
     */
    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Stack is empty.");
        return top.value;
    }

    /**
     * Checks if the stack is empty.
     * 
     * @return true if no elements are present
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the number of elements in the stack.
     * 
     * @return stack size
     */
    public int size() {
        return size;
    }

    /**
     * Returns all elements in LIFO order (top to bottom).
     * 
     * @return list from top to bottom
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        StackNode<T> current = top;
        while (current != null) {
            list.add(current.value);
            current = current.next;
        }
        return list;
    }

    /**
     * Returns a string representation of the stack from top to bottom.
     * 
     * @return formatted string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack [top->bottom]: ");
        StackNode<T> current = top;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null)
                sb.append(" -> ");
            current = current.next;
        }
        return sb.toString();
    }
}