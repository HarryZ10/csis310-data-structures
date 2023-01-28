import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class LinkedList of type E is a doubly-linkedlist of LinkedListNode of E,
 * such that a node contains a reference to some object of type E.
 *
 * The class should internally maintain head and tail, size, and
 * modification count and support forward and reverse iteration by
 * implementing the Iterable interface. Each node should internally maintain
 * previous and next nodes.
 *
 * @author hzhu20@georgefox.edu
 * @version 1.0
 * @since 2021-10-29
 */
public class LinkedList<E> implements Iterable<E> {

    private LinkedListNode<E> _head;  // first node of first element
    private LinkedListNode<E> _tail;  // last node of last element
    private int _size;                // number of nodes
    private int _modCount;            // modification count


    /**
     * Constructs an empty list.
     */
    public LinkedList() {
        _head = null;
        _tail = null;
        _size = 0;
    }


    /**
     * Append a new node at the tail of the list.
     *
     * @param element the element to be appended
     */
    public boolean add(E element) {

        boolean added = false;

        // create new node and add to end of list
        final LinkedListNode<E> newNode = new LinkedListNode<E>(element);

        // if list is empty, set head & tail to new node, otherwise add to end
        if (isEmpty()) {

            // set head and tail to new node
            _head = newNode;
            _tail = newNode;

            // update next node of head to _tail
            _head.setNext(_tail);

        } else {

            // set new node's next to tail
            _tail.setNext(newNode);

            // update _tail's previous node to old tail
            _tail.getNext().setPrevious(_tail);

            // set tail to new node
            _tail = newNode;
        }

        // increment the size
        _size++;

        // increment the modification count
        _modCount++;

        added = true;

        // return true if added
        return added;
    }


    /**
     * Insert a new node into the list at the specified index.
     *
     * @param index the index at which the new node is to be inserted
     * @param element the element to be inserted
     * @return true if node is added to the list, false otherwise
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {

        // Create a new node with given data
        LinkedListNode<E> newNode = new LinkedListNode<E>(element);

        // If the Linked List is empty, then set new node as head
        if (isEmpty()) {

            // check if index is less than 0 or greater than size
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }

            // set head and tail to new node
            _head = newNode;
            _tail = newNode;

            // increment the size, and increment the modification count
            _size++;
            _modCount++;

        } else {

            // if index is out of range, throw exception
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException();
            }

            if (index == 0) {

                // temporary node to store head
                LinkedListNode<E> headCopy = _head;

                // set head to new node
                _head = newNode;

                // set new node's next to headCopy
                _head.setNext(headCopy);

                // set headCopy's previous to new node
                headCopy.setPrevious(_head);

            } else if (index == this.size()) {

                // temporary node to store tail
                LinkedListNode<E> tailCopy = _tail;

                // set tail to new node
                _tail = newNode;

                // set new node's previous to temp
                _tail.setPrevious(tailCopy);

                // set temp's next to new node
                tailCopy.setNext(_tail);

            } else {

                // temporary nodes to store previous and current nodes
                LinkedListNode<E> current = _head;
                LinkedListNode<E> previous = _head;

                // declare a counter
                int counter = 0;

                // loop through list until index is reached
                while (counter < index) {

                    // set previous to current
                    previous = current;

                    // set current to next
                    current = current.getNext();
                    
                    // increment to propogaate through list
                    counter++;
                }

                // set new node to previous's next
                previous.setNext(newNode);

                // set new node's next to current
                newNode.setNext(current);

                // set current's previous to new node
                current.setPrevious(newNode);

                // set new node's previous to previous
                newNode.setPrevious(previous);
            }

            // update the size, modification count, and added to true
            _size++;
            _modCount++;
        }
    }


    /**
     * Remove the node at the specified index.
     *
     * @param index the index of the node to be removed
     * @return the element of the removed node
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {

        // temporary variable to store removed element
        E element = null;

        // check if list is empty
        if (isEmpty()) {

            // throw exception if index is out of range
            throw new IndexOutOfBoundsException();

        } else {

            if (index == 0) {

                // store head's element
                element = _head.getValue();

                // set head to next node
                _head = _head.getNext();

                // checks if removing the head was the last node
                if (_head != null) {
                    _head.setPrevious(null);
                }

            } else if (index == this.size() - 1) {

                // store tail's element
                element = _tail.getValue();

                // set tail to previous node
                _tail = _tail.getPrevious();

                // set next of previous node to null
                _tail.setNext(null);

                // update tail's previous to 2nd to last node
                _tail.setPrevious(_tail.getPrevious().getPrevious());

            } else {

                // if index is out of bounds, throw exception
                checkRange(index);

                // find the previous node of the node to be removed
                LinkedListNode<E> prev = SetIterateCurrents(index);

                // get the element to be removed
                element = prev.getNext().getValue();

                // set the next of previous node to next of next node
                prev.setNext(prev.getNext().getNext());

                // update the previous node when removed a node
                prev.getNext().setPrevious(prev);
            }

            // decrement the size and the modification count
            _size--;
            _modCount++;
        }

        return element;
    }


    /**
     * Get the element at the specified index.
     *
     * @param index the index of the element to be returned
     * @return the element at the specified index
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {

        // temporary node to store element to be returned
        E element = null;
        
        // checks for empty list
        if (isEmpty()) {

            // throw exception if list is empty
            throw new IndexOutOfBoundsException();
        }

        // if index is at head of list
        if (index == 0) {

            // get the element at head
            element = _head.getValue();

        } else if (index == this.size() - 1) {

            // get the element at tail
            element = _tail.getValue();

        } else {
            // if index is out of bounds, throw exception
            checkRange(index);

            // find the previous node of the node to be returned
            LinkedListNode<E> current = SetIterateCurrentsForGet(index);

            // get the element at the current node
            element = current.getValue();
        }

        // return the element of the node

        return element;
    }


    /**
     * Set the element at the specified index.
     *
     * @param index the index of the element to be set
     * @param element the element to be set
     * @return the element that was replaced
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {

        // temporary variable to store the element to be replaced
        E oldElement = null;

        // if list is empty, throw exception
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        // affirm head and tail constant time
        if (index == 0) {

            // get old element that's being replaced
            oldElement = _head.getValue();

            // set new element to head
            _head.setValue(element);

        } else if (index == this.size() - 1) {

            // get the element to be replaced
            oldElement = _tail.getValue();

            // set tail to the new element
            _tail.setValue(element);

        } else {

            // if index is out of bounds, throw exception
            checkRange(index);

            // find the node at the specified index
            LinkedListNode<E> current = SetIterateCurrentsForGet(index);

            // store the element to be replaced
            oldElement = current.getValue();

            // set the element of the node
            current.setValue(element);

            // update previous node of the next node
            current.getNext().setPrevious(current);
        }

        return oldElement;
    }


    /**
     * Clears the list of all elements, ensuring head and tail are null,
     * reseting the size to 0.
     *
     * @return the size of the list after clearing
     */
    public void clear() {

        // set head and tail to null
        _head = null;
        _tail = null;

        // reset the size
        _size = 0;

        // update the modification count
        _modCount++;
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return this._size;
    }


    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Returns the index of the first occurrence of the node with the
     * element, or -1 if this list does not contain the element.
     *
     * @param element the element to be searched for
     * @return the index of the first occurrence of the specified node
     *         in this list, or -1 if this list does not contain the element
     */
    public int indexOf(E element) {

        // control variable for loop
        boolean found = false;

        // index is negative if element is not found
        int index = -1;

        // if list is empty, return -1
        if (!isEmpty()) {

            // set current to head
            LinkedListNode<E> current = _head;

            // checks if element is valid or not
            if (element == null) {

                // iterate through the list
                for (int i = 0; i < this.size(); i++) {

                    // checks if current node's value is null and not found
                    if (current.getValue() == null && !found) {

                        // end loop
                        found = true;

                        // update index to return
                        index = i;
                    }

                    // set current to next node
                    current = current.getNext();
                }

            } else {

                for (int i = 0; i < this.size(); i++) {

                    if (current.getValue() != null
                        && element.equals(current.getValue())
                        && !found) {

                        // end loop
                        found = true;

                        // update index to return
                        index = i;
                    }

                    // set current to next node
                    current = current.getNext();
                }
            }

        }

        return index;
    }


    /**
     * Creates a new LinkedListIterator that iterates over this linked list.
     *
     * @return a new LinkedListIterator that iterates over this list
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator(false);
    }


    /**
     * Creates a new LinkedListIterator that iterates over this
     * list in reverse.
     *
     * @return a new LinkedListIterator that iterates over this
     *         list in reverse
     */
    public Iterator<E> reverseIterator() {
        return new LinkedListIterator(true);
    }


    /**
     * Checks the index to see if index is less than 0 or greater than
     * or equal to the size.
     *
     * @param index the index to be checked
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    private void checkRange(int index) {

        // checks the bounds of the index
        if (index < 0 || index >= this.size()) {

            // if index is out of bounds, throw exception
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Point current to the head, and iterate through current using
     * getNext() until i is at index.
     *
     * @param index the index to be found
     * @return the node at the index
     *
     * @see java.util.ListIterator#getNext()
     */
    private LinkedListNode<E> SetIterateCurrents(int index) {

        // store the head node to current node
        LinkedListNode<E> current = _head;

        // loop through the list until the node at the index is found
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }

        return current;
    }


    /**
     * Point current to the head, and iterate through current using
     * getNext() until i is at index.
     *
     * @param index the index to be found
     * @return the node at the index
     */
    private LinkedListNode<E> SetIterateCurrentsForGet(int index) {

        // store the head node to current node
        LinkedListNode<E> current = _head;

        // loop through the list until the node at the index is found
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current;
    }


    /**
     * LinkedListNode class that stores the value of the node,
     * the next node, and the previous node.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     * @since 2021-10-29
     */
    private class LinkedListNode<T> {

        private E _value;                 // the value of the node
        private LinkedListNode<E> _next;  // the next node in the list
        private LinkedListNode<E> _prev;  // the previous node in the list


        /**
         * Constructs a node with the given value and next node.
         *
         * @param value the value to be stored in this node
         * @param prev the prev node in the list
         * @param next the next node in the list
         */
        public LinkedListNode(E value,
                              LinkedListNode<E> prev,
                              LinkedListNode<E> next) {
            _value = value;
            _next = next;
            _prev = prev;
        }


        /**
         * Constructs a node with the given value and next node.
         *
         * @param value the value to be stored in this node
         */

        public LinkedListNode(E value) {
            this(value, null, null);
        }


        /**
         * Constructs a node with no specified value.
         */
        public LinkedListNode() {
            this(null, null, null);
        }


        /**
         * Returns the value stored in this node.
         *
         * @return the value stored in this node
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the next node in the list.
         *
         * @return the next node in the list
         */
        public LinkedListNode<E> getNext() {
            return _next;
        }


        /**
         * Returns the previous node in the list.
         *
         * @return the previous node in the list
         */
        public LinkedListNode<E> getPrevious() {
            return _prev;
        }


        /**
         * Sets the next node in the list.
         *
         * @param value the next node in the list
         */
        public void setValue(E value) {
            _value = value;
        }


        /**
         * Sets the next node in the list.
         *
         * @param next the next node in the list
         */
        public void setNext(LinkedListNode<E> next) {
            _next = next;
        }


        /**
         * Sets the previous node in the list.
         *
         * @param prev the previous node in the list
         */
        public void setPrevious(LinkedListNode<E> prev) {
            _prev = prev;
        }
    }


    /**
     * LinkedListIterator class implements the Iterator interface.
     *
     * @author hzhu20@georgefox.edu
     * @version 1.0
     * @since 2021-10-29
     */
    private class LinkedListIterator implements Iterator<E> {

        private int _currIndex;                  // tracking the index
        private int _expectedModCount;           // tracking list changes
        private boolean _reverse;                // reverse iteration order
        private LinkedListNode<E> _currentNode;  // pointer node in the list


        /**
         * Constructs a new LinkedListIterator that iterates over this
         * list and starts at the head of the list or at the tail of the
         * list if reverse is true.
         *
         * @param reverse true if the iterator should start at the tail of the
         *        {@link java.util.List}
         */
        public LinkedListIterator(boolean reverse) {

            if (reverse) {

                // set current index to the size of the list
                _currIndex = _size - 1;

                // set the current node to the tail of the list
                _currentNode = _tail;

                // set reverse to true to indicate reverse iteration
                _reverse = reverse;

            } else {

                // set current index to 0
                _currIndex = 0;

                // set the current node to the head of the list
                _currentNode = _head;

                // set reverse to false to indicate forward iteration
                _reverse = reverse;

            }

            // set the expected mod count to the current mod count
            _expectedModCount = _modCount;
        }


        /**
         * Returns true if this iterator has at least one more element
         * to deliver in the iteration.
         *
         * @return true if this iterator has at least one more element
         *         to deliver in the iteration
         *
         * @throws ConcurrentModificationException {@inheritDoc}
         */
        public boolean hasNext() {

            // check if the list has been modified
            if (_expectedModCount != _modCount) {

                // throw an exception if the list has been modified
                throw new ConcurrentModificationException();
            }

            return _currIndex < _size && _currentNode != null;
        }


        /**
         * Returns the next element in the iteration. Functionality of next
         * iteration is dependent on the type of iterator.
         *
         * If the iterator is a forward iterator, the next element is the next
         * element in the list. If the iterator is a reverse iterator, the next
         * element is the previous element in the list.
         *
         * @return the next element in the iteration
         *
         * @throws NoSuchElementException {@inheritDoc}
         * @throws ConcurrentModificationException {@inheritDoc}
         */
        public E next() {

            // check if the list has been modified
            if (_expectedModCount != _modCount) {

                // throw an exception if the list has been modified
                throw new ConcurrentModificationException();
            }

            // check if there is a next element
            if (!hasNext()) {

                // throw an exception if there is no next element
                throw new NoSuchElementException();
            }

            // value is null indicate that the value has not been retrieved
            E value = null;

            // if current node is not null
            if (_currentNode != null) {

                // if in reverse get previous
                if (_reverse) {

                    // store the value of the current node
                    value = _currentNode.getValue();

                    // move to the previous node
                    _currentNode = _currentNode.getPrevious();

                    // point to the previous node in the list
                    _currIndex--;

                } else {

                    // store the current node's value
                    value = _currentNode.getValue();

                    // move to the next node
                    _currentNode = _currentNode.getNext();

                    // point to the next node in the list
                    _currIndex++;
                }
            }

            return value;
        }
    }
}
