import java.util.Iterator;
import java.util.*;

/**
 * Class Deque (commonly pronounced “deck”) is adouble-ended queuebased on a
 * linked list that supports first in, first out (FIFO) and last in,
 * first out (LIFO) semantics simultaneously.  A deque should maintain some
 * notion of its head and tail elements, and allow read and write access to
 * both ends of the queue.
 *
 * @author hzhu20@georgefox.edu
 * @version 1.0
 */
public class Deque<E> implements Iterable<E> {

    private DequeNode _head;  // head of the deque
    private DequeNode _tail;  // tail of the deque
    private int _size;        // number of elements in the deque


    /**
     * Constructs an empty deque.
     */
    public Deque() {
        this._head = null;
        this._tail = null;
        this._size = 0;
    }


    /**
     * Returns the number of elements in the deque.
     *
     * @return the number of elements in the deque
     */
    public int depth() {
        return this._size;
    }


    /**
     * Returns true if the deque is empty.
     *
     * @return true if the deque is empty
     */
    public boolean isEmpty() {
        return this._size == 0;
    }


    /**
     * Method to add an item to the queue
     *
     * @param element the element to be added
     *
     * @return true if the element is added, false otherwise
     */
    public boolean enqueue(E element) {

        // whether the element is added
        boolean added = false;

        // creates a new node
        DequeNode newNode = new DequeNode(element);

        // checks if the deque is empty
        if (this._head == null) {

            // sets the head and tail to the new node
            this._head = newNode;
            this._tail = newNode;

        } else {

            // sets the next node of the tail to the new node
            this._tail.setNext(newNode);

            // sets the new node as the tail
            newNode.setPrev(this._tail);

            // sets the tail to the new node
            this._tail = newNode;
        }

        // updates size to current state
        this._size++;

        // successfully added element to the queue
        added = true;

        // returns whether the element is added
        return added;

    }


    /**
     * Enqueue all elements of an array into the queue.
     *
     * @param elements the array of elements to be enqueued
     */
    public void enqueueAll(Iterable<E> elements) {

        // iterates through the elements
        for (E element : elements) {

            // adds the element to the queue
            enqueue(element);
        }
    }


    /**
     * Enqueue at head of the deque.
     *
     * @param element the element to be added to the head of the deque
     *
     * @return true if the element is added, false otherwise
     */
    public boolean enqueueHead(E element) {

        // whether the element is added
        boolean added = false;

        // creates a new node
        DequeNode newNode = new DequeNode(element);

        // checks if the deque is empty
        if (this._head == null) {

            // sets the head and tail to the new node
            this._head = newNode;
            this._tail = newNode;

        } else {

            // sets the new node as the head
            newNode.setNext(this._head);

            // sets the head to the new node
            this._head = newNode;

            // sets the next node of the head to null
            this._head.getNext().setPrev(this._head);
        }

        // updates size to current state
        this._size++;

        // successfully added element to the queue
        added = true;

        // returns whether the element is added
        return added;
    }


    /**
     * Returns the head of the deque.
     *
     * @return the head of the deque element
     *
     * @throws NoSuchElementException {@inheritDoc}
     */
    public E head() {

        // checks if the deque is empty
        if (this._head == null) {

            // throws exception if the deque is empty
            throw new NoSuchElementException();
        }

        // returns the head of the deque
        return this._head.getValue();
    }


     /**
     * Returns the tail of the deque.
     *
     * @return the tail of the deque element
     *
     * @throws NoSuchElementException {@inheritDoc}
     */
    public E tail() {

        // checks if the deque is empty
        if (this._tail == null) {

            // throws exception if the deque is empty
            throw new NoSuchElementException();
        }

        // returns the head of the deque
        return this._tail.getValue();
    }


    /**
     * Method to remove an item from the queue
     *
     * @return the element removed from the queue
     */
    public E dequeue() {

        // the element removed from the queue
        E element = null;

        // sets the element to the head of the deque
        element = this._head.getValue();

        // removes the head of the deque
        this._head = this._head.getNext();

        // checks if the new head is null
        if (this._head == null) {

            // sets the tail to null
            this._tail = null;

        } else {

            // sets the previous node of the head to null
            this._head.setPrev(null);
        }

        // updates size to current state
        this._size--;

        // returns the element removed from the queue
        return element;
    }


    /**
     * Method to remove an item from the tail of the queue
     *
     * @return the element removed from the tail of the queue
     */
    public E dequeueTail() {

        // the element removed from the queue
        E element = null;

        // sets the element to the tail of the deque
        element = this._tail.getValue();

        // removes the tail of the deque
        this._tail = this._tail.getPrev();

        // checks if the new tail is null
        if (this._tail == null) {

            // sets the head to null
            this._head = null;

        } else {

            // sets the next node of the tail to null
            this._tail.setNext(null);
        }

        // updates size to current state
        this._size--;

        // returns the element removed from the queue
        return element;
    }


    /**
     * Clears the deque.
     */
    public void clear() {

        // sets the head and tail to null
        this._head = null;
        this._tail = null;

        // updates size to current state
        this._size = 0;
    }


    /**
     * Create an iterator over the elements in the deque.
     *
     * @return an iterator over the elements in the deque
     */
    @Override
    public Iterator<E> iterator() {
        return new DequeIterator(false);
    }


    /**
     * Create a reverse iterator over the elements in the deque.
     *
     * @return a reverse iterator over the elements in the deque
     */
    public Iterator<E> reverseIterator() {
        return new DequeIterator(true);
    }


    /**
     * DequeNode is a class that represents a node in a deque.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     */
    private class DequeNode {

        private E _value;         // value of the node
        private DequeNode _next;  // next node in the deque
        private DequeNode _prev;  // previous node in the deque


        /**
         * Constructs a node with the given value.
         *
         * @param value the value of the node
         */
        public DequeNode(E value) {
            this._value = value;
            this._next = null;
            this._prev = null;
        }


        /**
         * Returns the value of the node.
         *
         * @return the value of the node
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the next node in the deque.
         *
         * @return the next node in the deque
         */
        public DequeNode getNext() {
            return _next;
        }


        /**
         * Sets the next node in the deque.
         *
         * @param next the new next node in the deque
         */
        public void setNext(DequeNode next) {
            _next = next;
        }

        
        /**
         * Returns the previous node in the deque.
         *
         * @return the previous node in the deque
         */
        public DequeNode getPrev() {
            return _prev;
        }


        /**
         * Sets the previous node in the deque.
         *
         * @param prev the new previous node in the deque
         */
        public void setPrev(DequeNode prev) {
            _prev = prev;
        }
    }


    /**
     * DequeNode is a class that represents a node in a deque.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     */
    private class DequeIterator implements Iterator<E> {

        private DequeNode _current;  // current node in the deque
        private boolean _reverse;    // tracks if the iterator is reversed


        /**
         * Constructs an iterator for the given deque.
         */
        public DequeIterator(boolean reverse) {

            if (reverse) {
                this._current = _tail;
                this._reverse = true;

            } else {
                this._current = _head;
                this._reverse = false;
            }
        }


        /**
         * Checks if there is a next element in the deque.
         *
         * @return true if there is a next element in the deque, false otherwise
         */
        @Override
        public boolean hasNext() {
            return !isEmpty();
        }


        /**
         * Returns the next element in the deque.
         *
         * @return the next element in the deque
         *
         * @throws NoSuchElementException {@inheritDoc}
         */
        @Override
        public E next() {

            // checks if there is a next element in the deque
            if (this._current == null) {

                // throws exception if there is no next element in the deque
                throw new NoSuchElementException();
            }

            // the next element in the deque
            E element = null;

            if (hasNext()) {
                if (this._reverse) {

                    // sets the element to the current node
                    element = _tail.getValue();

                    // dequeue the current node
                    dequeue();

                } else {

                    // sets the element to the current node
                    element = _head.getValue();

                    // dequeue the current node
                    dequeueTail();
                }
            }

            return element;
        }
    }
}
