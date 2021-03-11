import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;


/**
 * Creates an instance of the RandomizedList.java interface.
 *
 * @author Daniel Thomason
 * @version 2019-10-17
 */
public class RandomArrays <T> implements RandomizedList<T> {

    private T[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 5;

    /** Constructs a RandomArrays object with a capacity of 5. */
    public RandomArrays() {
        this(DEFAULT_CAPACITY);
    }

    /** Constructs a RandomArrays object with a capacity specified by
     * the paramater.
     *
     * @param cap - capacity of the RandomArrays object.
     */
    @SuppressWarnings("unchecked")
    public RandomArrays(int cap) {
        elements = (T[]) new Object[cap];
        size = 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return size
     */
    @Override
    public int size(){
        return size;
    }

    /**
     * Determines if the list contains any elements.
     *
     * @return true if size() is equal to 0
     * @return false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator over the list.
     *
     * @return new ArrayIterator
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<T>(elements, size);
    }

    /**
     * Adds the speciified element to the list. If the element
     * is null, this method throws an IllegalArgumentException. If the
     * list is full, it is resized.
     *
     * @throws IllegalArgumentException if element is null
     * @param element - the element to be added
     */
    @Override
    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            resize(elements.length * 2);
        }
        elements[size] = element;
        size++;
    }

    /**
     * Removes a random element from the list. Returns null if
     * list is null.
     *
     * @return the element that was removed
     */
    @Override
    public T remove() {
        if (this.isEmpty()) {
            return null;
        }
        Random r = new Random();
        int index = r.nextInt(size);
        T removed = elements[index];
        elements[index] = elements[size() - 1];
        elements[size() - 1] = null;
        size--;
        return removed;
    }

    /**
     * Selects an element from the list at random.
     *
     * @return the selected element
     */
    @Override
    public T sample() {
        if (this.isEmpty()) {
            return null;
        }
        Random r = new Random();
        int index = r.nextInt(size);
        return elements[index];
    }

    /**
     * Resizes the array to the size indicated by the parameter.
     *
     * @param cap - the new capacity of the array
     */
    @SuppressWarnings("unchecked")
    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        for (int i = 0; i < size(); i++) {
            a[i] = elements[i];
        }
        elements = a;
    }

    /**
     * Determines whether the list is full or not.
     *
     * @return true if size is equal to array length, false if not
     */
    private boolean isFull() {
        return (size == elements.length);
    }

    /**
     * Creates an iterator that can iterate over a RandomizedList.
     *
     * @author Daniel Thomason
     * @version 2019-10-17
     */
    public class ArrayIterator<T> implements Iterator<T> {
        private T[] items;
        private int count;
        private int current;

        /**
         * Constructs an ArrayIterator object.
         *
         * @param elements - the array that needs to be iterated over.
         * @param size - the size of the array.
         */
        public ArrayIterator(T[] elements, int size) {
            items = elements;
            count = size;
            current = 0;
        }

        /**
         * Determines if the list has any more elements to iterate over.
         *
         * @return true if there is another element, false if not
         */
        @Override
        public boolean hasNext() {
            return (current < count);
        }

        /**
         * Optional method in the Iterator interface that throws an
         * UnsupportedOperationException in this class.
         *
         * @throws UnsupportedOperationException regardless
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Returns the next item in the list.
         *
         * @return the next element.
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Random r = new Random();
            current = r.nextInt(count);
            count--;
            return items[current];
        }
    }
}
