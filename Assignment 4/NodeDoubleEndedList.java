import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Creates an instance of the DoubleEndedList.java interface.
 *
 * @author Daniel Thomason
 * @version 2019-10-17
 */
public class NodeDoubleEndedList<T> implements DoubleEndedList<T> {

    private Node front;
    private Node rear;
    private int size;

    /**
     * Constructs a NodeDoubleEndedList object.
     */
    public NodeDoubleEndedList() {
        front = null;
        size = 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Determines if the list is empty.
     *
     * @return true if the list is empty
     * @return false if the list is not empty
     */
    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Creates an iterator over the list.
     *
     * @return an iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new DoubleEndedIterator();
    }

    /**
     * Adds an element to the front of the list.
     *
     * @param element - the element to be added
     */
    @Override
    public void addFirst(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(element);
        if (this.isEmpty()) {
            front = n;
            rear = n;
            size++;
        }
        else {
            n.next = front;
            front = n;
            size++;
        }
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element - the element to be added
     */
    @Override
    public void addLast(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(element);
        n.element = element;
        if (this.isEmpty()) {
            front = n;
            rear = n;
            size++;
        }
        else {
            rear.next = n;
            rear = n;
            size++;
        }
    }

    /**
     * Removes the element at the front of the list.
     *
     * @return the element that was removed from the list
     */
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T removed = front.element;
        front = front.next;
        size--;
        return removed;
    }

    /**
     * Removes the element at the end of the list.
     *
     * @return the element that was removed from the list
     */
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        else if (size == 1) {
            T removed = front.element;
            front = null;
            rear = null;
            size--;
            return removed;
        }
        else {
            Node n = front;
                while (n.next.next != null) {
                    n = n.next;
                }
            T removed = n.next.element;
            n.next = null;
            rear = null;
            size--;
            return removed;
        }
    }

    /**
     * Creates a linked structure to represent elements in the list.
     *
     * @author Daniel Thomason
     * @version 2019-10-17
     */
    private class Node {
        private T element;
        private Node next;

        /**
         * Constructs a node object.
         *
         * @param t - reference to the element
         */
        public Node(T t) {
            element = t;
        }

        /**
         * Constructs a node object that also references the next node in the
         * list.
         *
         * @param t - reference to the element
         * @param n - reference to the next element in the list
         */
        public Node(T t, Node n) {
            element = t;
            next = n;
        }
    }


    private class DoubleEndedIterator implements Iterator<T> {

        private Node current = front;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.element;
        }
    }
}
