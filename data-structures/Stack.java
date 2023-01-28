import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stack is a class where you can push and pop items. It is a LIFO structure.
 * Stack implements Iterable interface.
 *
 * @author hzhu20@georgefox.edu
 * @version 1.0
 * @since 2021-10-29
 */
public class Stack<E> implements Iterable<E> {

    private StackNode _top;     // top of the stack
    private int _size;          // number of items in the stack


    /**
     * Constructor for objects of class Stack.
     */
    public Stack() {
        this._top = null;
        this._size = 0;
    }


    /**
     * Pushes an item onto the stack.
     *
     * @param element the item to be pushed onto the stack
     * @return whether the push was successful
     */
    public boolean push(E element) {

        boolean pushed = false;

        if (!isEmpty()) {

            // Create a new node to hold the item.
            StackNode newNode = new StackNode(element);

            // Set the new node's next pointer to the top of the stack.
            newNode.setNext(_top);

            // Set the top of the stack to the new node.
            _top = newNode;

            // Increment the size of the stack.
            _size++;

            // The push was successful.
            pushed = true;

        } else {

            this._top = new StackNode(element);

            // Increment the size of the stack.
            this._size = 1;

            // Set the pushed flag to true.
            pushed = true;
        }

        return pushed;
    }


    /**
     * Push all items in the given stack onto this stack.
     *
     * @param elements the stack to be pushed onto this stack
     */
    public void pushAll(Iterable<E> elements) {

        // Iterate through the given stack.
        for (E element : elements) {

            // Push the item onto this stack.
            push(element);
        }
    }


    /**
     * Pops an item off the stack.
     *
     * @return the item that was popped off the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() {

        E popped = null;

        if (!isEmpty()) {

            // Set the popped item to the top of the stack.
            popped = this._top.getItem();

            // Set the top of the stack to the next node.
            this._top = this._top.getNext();

            // Decrement the size of the stack.
            this._size--;

        } else {

            // Throw an exception.
            throw new EmptyStackException();
        }

        return popped;
    }


    /**
     * Peep at the top item on the stack.
     *
     * @return the item at the top of the stack.
     */
    public E peek() {

        // Checks if the stack is empty
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        // Returns the item at the top of the stack
        return this._top.getItem();
    }


    /**
     * Returns the number of items in the stack.
     *
     * @return The number of items in the stack.
     */
    public int depth() {
        return this._size;
    }


    /**
     * Clear the stack. This method does not return any items.
     */
    public void clear() {
        this._top = null;
        this._size = 0;
    }


    /**
     * Returns true if the stack is empty, false otherwise.
     *
     * @return true if the stack is empty, false otherwise.
     */
    public boolean isEmpty(){
        return this._top == null;
    }


    /**
     * Returns an iterator for the stack.
     *
     * @return An iterator for the stack.
     */
    public Iterator<E> iterator() {
        return new StackIterator();
    }


    /**
     * StackNode is a class that holds an item and a pointer to the next node.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     */
    private class StackNode {

        private E _item;             // item stored in the node
        private StackNode _next;     // reference to the next node


        /**
         * Constructor for objects of class StackNode.
         *
         * @param item the item to be stored in the node
         */
        public StackNode(E item) {
            this._item = item;
        }


        /**
         * Returns the item stored in the node.
         *
         * @return The item stored in the node.
         */
        public E getItem() {
            return this._item;
        }


        /**
         * Returns the next node in the stack.
         *
         * @return The next node in the stack.
         */
        public StackNode getNext() {
            return this._next;
        }


        /**
         * Sets the next node in the stack.
         *
         * @param next The next node in the stack.
         */
        public void setNext(StackNode next) {
            this._next = next;
        }
    }


    /**
     * StackIterator is an iterator for the Stack class.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     * @since 2021-10-29
     */
    private class StackIterator implements Iterator<E> {

        /**
         * Returns true if there is another item in the stack.
         *
         * @return True if there is another item in the stack.
         *
         */
        @Override
        public boolean hasNext() {
            return !isEmpty();
        }


        /**
         * Returns the next item in the stack.
         *
         * @return The next item in the stack.
         *
         * @throws NoSuchElementException {@inheritDoc}
         */
        @Override
        public E next() {

            // Checks if the stack is empty
            if (_top == null) {
                throw new NoSuchElementException();
            }

            E item = null;

            if (hasNext()) {

                // gets the item at the current node
                item = _top.getItem();

                // pops the item off the stack to get the next item on top
                pop();
            }

            return item;
        }
    }
}
