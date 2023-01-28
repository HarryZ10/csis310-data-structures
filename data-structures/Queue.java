import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue class which is a data structure that implements the Iterable
 * interface. It is a first-in, first-out data structure.
 *
 * @author hzhu20@georgefox.edu
 * @version 1.0
 */
public class Queue<E> implements Iterable<E> {

    private QueueNode _front;  // the front of the queue
    private QueueNode _rear;   // the tail of the queue
    private int _size;         // the size of the queue


    /**
     * Constructor for objects of class Queue
     */
    public Queue() {
        this._front = null;
        this._rear = null;
        this._size = 0;
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

        // create a new node
        QueueNode newNode = new QueueNode(element);

        // if the queue is empty
        if (this._front == null) {

            // set the front and rear to the new node
            this._front = newNode;
            this._rear = newNode;

            // successfully added
            added = true;

            // update size
            this._size++;

        } else {

            // set the next of the rear to the new node
            this._rear.setNext(newNode);

            // update the rear
            this._rear = newNode;

            // successfully added
            added = true;

            // update size
            this._size++;
        }

        // return whether the element is added
        return added;
    }

    
    /**
     * Create an iterator for the queue.
     *
     * @return an iterator for the queue
     */
    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }


    /**
     * Enqueue all elements of an array into the queue.
     *
     * @param elements the array of elements to be enqueued
     */
    public void enqueueAll(Iterable<E> elements) {

        // iterate through the array
        for (E element : elements) {

            // add the element to the queue
            enqueue(element);
        }
    }


    /**
     * Dequeue an element from the queue
     *
     * @return the element dequeued
     */
    public E dequeue() {

        // result if dequeued or not
        E dequeued = null;

        // if the queue is not empty
        if (this._front != null) {

            // set the result to the front element
            dequeued = this._front.getElement();

            // set the front to the next element
            this._front = this._front.getNext();

            // decrease the size of the queue
            this._size--;
        }

        return dequeued;
    }


    /**
     * Method to check if the queue is empty
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return this._front == null;
    }


    /**
     * Method to get the size of the queue
     *
     * @return the size of the queue
     */
    public int depth() {
        return this._size;
    }


    /**
     * Returns the head of the queue.
     * 
     * @return the head element of the queue
     * 
     * @throws NoSuchElementException {@inheritDoc}
     */
    public E head() {

        // if the queue is empty
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // return the front element
        return this._front.getElement();
    }


    /**
     * Clears the queue
     */
    public void clear() {
        this._front = null;
        this._rear = null;
        this._size = 0;
    }


    /**
     * QueueIterator class which is an iterator for the queue.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     */
    private class QueueIterator implements Iterator<E> {


        /**
         * Method to check if the iterator has a next element.
         *
         * @return true if the iterator has a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return !isEmpty();
        }


        /**
         * Method to get the next element in the queue.
         *
         * @return the next element in the queue
         *
         * @throws NoSuchElementException {@inheritDoc}
         */
        @Override
        public E next() {

            // if the queue is empty
            if (_front == null) {
                throw new NoSuchElementException();
            }

            // the next element in the queue
            E next = null;

            // if the iterator has a next element
            if (hasNext()) {

                // set the next element to the current element
                next = _front.getElement();

                // Dequeue the current node from the queue to get next node
                dequeue();
            }

            return next;
        }
    }


    /**
     * QueueNode class which is a node for the queue.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     */
    private class QueueNode {

        private E _element;       // the element in the node
        private QueueNode _next;  // the next node in the queue


        /**
         * Constructor for objects of class QueueNode
         */
        public QueueNode(E element) {
            this._element = element;
            this._next = null;
        }


        /**
         * Method to get the element in the node.
         *
         * @return the element in the node
         */
        public E getElement() {
            return this._element;
        }


        /**
         * Method to get the next node in the queue.
         *
         * @return the next node in the queue
         */
        public QueueNode getNext() {
            return this._next;
        }


        /**
         * Method to set the next node in the queue.
         *
         * @param next the next node in the queue
         */
        public void setNext(QueueNode next) {
            this._next = next;
        }
    }
}
