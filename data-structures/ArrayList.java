import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Resizable array implementation of the List interface. Permits all elements,
 * including null. Allows for manipulation the size that is used to store
 * the respective values.
 * 
 * @author hzhu20@georgefox.edu
 */
public class ArrayList<E> implements Iterable<E>
{
    // Constants

    private static final int MAX_ELEMENTS = Integer.MAX_VALUE - 8; // max capacity
    private static final int DEFAULT_LENGTH_ARRAY = 10; // default capacity

    // Internal State

    private E[] _values; // backing array
    private int _size; // size of claimed space in the array


    /**
     * Constructor that creates an ArrayList with the given initial capacity.
     * 
     * @param initialCapacity the initial capacity of the ArrayList
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity)
    {
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException("Capacity needs to be at least 1.");
        }

        this._values = (E[]) new Object[initialCapacity];
        this._size = 0;
    }


    /**
     * Constructor that creates an empty ArrayList with default capacity of 10.
     */
    public ArrayList()
    {
        this(DEFAULT_LENGTH_ARRAY);
    }


    /**
     * Returns the size of the ArrayList.
     * 
     * @return the size of the ArrayList
     */
    public int size()
    {
        return this._size;
    }


    /**
     * Returns true if the ArrayList is empty, false otherwise.
     * 
     * @return true if the ArrayList is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return this.size() == 0;
    }


    /**
     * Returns element at the specified index.
     * 
     * @param index the index of the element to return
     * @return the element at the specified index
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index)
    {
        checkRangeForGet(index);
        return getValues()[index];
    }


    /**
     * Returns the index of the specified element
     * 
     * @param element the element to check for
     * @return the index of the element or -1 if the element isn't found
     */
    public int indexOf(E element)
    {
        int index = -1;

        if (element == null)
        {
            for (int i = 0; i < this.size(); i++){
                if (getValues()[i] == null)
                {
                    index = i;
                }
            }
        } 
        else
        {
            for (int i = 0; i < this.size(); i++)
            {
                if (element.equals(getValues()[i]))
                {
                    index = i;
                }
            }
        }

        return index;
    }


    /**
     * Returns true if the ArrayList contains the specified element,
     * false otherwise.
     * 
     * @param element the element to check for
     * @return true if the ArrayList contains the specified element,
     *         false otherwise
     */
    public boolean add(E element)
    {
        boolean added = false;
        int oldSize = size();

        checkCapacityNeeded(this._size + 1);

        getValues()[this._size++] = element;

        if (size() > oldSize)
        {
            added = true;
        }

        return added;
    }


    /**
     * Adds the specified element at the specified index.
     * 
     * @param index the index to add the element at
     * @param element the element to add
     * 
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element)
    {
        checkIndex(index);
        checkCapacityNeeded(this._size + 1);

        System.arraycopy(getValues(), index, getValues(), index + 1,
                         size() - index);

        getValues()[index] = element;
        this._size++;
    }


    /**
     * Removes the element at the specified index.
     * 
     * @param index the index of the element to remove     * 
     * @return the element that was removed
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index)
    {
        checkRange(index);

        E remElement = get(index);

        for (int i = index; i < this.size() - 1; i++)
        {
            getValues()[i] = getValues()[i + 1];
        }

        getValues()[this.size() - 1] = null;

        this._size--;

        return remElement;
    }


    /**
     * Clears the backing array and sets the size to 0
     */
    public void clear()
    {
        Arrays.setAll(getValues(), i -> null);
        this._size = 0;
    }


    /**
     * Returns the old element at the specified index and replaces it with the
     * new specified element.
     * 
     * @param index the index to find the position to set the specified element
     * @param element the element to set at the specified index
     * 
     * @return the previous element at the specified index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element)
    {
        E prevElement = getValues()[index];

        checkRange(index);
        getValues()[index] = element;

        return prevElement;
    }


    /**
     * Returns the backing array of the ArrayList.
     * 
     * @return the backing array of the ArrayList
     */
    private E[] getValues()
    {
        return this._values;
    }


    /**
     * Returns the capacity of the ArrayList.
     * 
     * @return the capacity of the ArrayList
     */
    private int currCapacity()
    {
        return getValues().length;
    }


    /**
     * Checks the index is greater than or equal to the
     * size of the backing array's length or less than 0
     * 
     * @param index the index in question
     */
    private void checkIndex(int index)
    {
        if (index < 0 || index > this.size())
        {
            throw new IndexOutOfBoundsException(
                String.format("Invalid Index: %d", index));
        }
    }


    /**
     * Checks the index is greater than or equal to the size of the
     * backing array's length or less than 0
     * 
     * @param index the index in question
     */
    private void checkRange(int index)
    {
        if (index < 0 || index >= this.size())
        {
            throw new IndexOutOfBoundsException(
                String.format("Invalid index"));
        }
    }


    /**
     * Checks the index is greater than or equal to the size of the
     * backing array's length
     * 
     * @param index the index in question
     */
    private void checkRangeForGet(int index)
    {
        if (index >= this.size())
        {
            throw new IndexOutOfBoundsException(
                String.format("Invalid index"));
        }
    }


    /**
     * Grows capacity if necessary for the array
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity)
    {
        int currCapacity = currCapacity();
        int newCapacity = currCapacity * 2;

        if (newCapacity - minCapacity < 0)
        {
            newCapacity = minCapacity;
        }
        
        if (newCapacity - MAX_ELEMENTS > 0)
        {
            newCapacity = overloadedArray(minCapacity);
        }

        this._values = Arrays.copyOf(getValues(), newCapacity);
    }


    /**
     * Returns max element size
     * 
     * @param minCapacity the minimum capacity to make the array bigger
     * @return the maximum element size for the array
     */
    private int overloadedArray(int minCapacity)
    {
        if (minCapacity < 0) // overflow
        {
            throw new OutOfMemoryError("Overflow");
        }

        return (minCapacity > MAX_ELEMENTS) ? Integer.MAX_VALUE : MAX_ELEMENTS;
    }


    /**
     * Ensures the array has enough capacity
     * 
     * @param minCapacity the desired minimum capacity
     */
    private void checkCapacityNeeded(int minCapacity)
    {
        if (size() == 0)
        {
            minCapacity = Math.max(DEFAULT_LENGTH_ARRAY, minCapacity);
        }

        checkCurrentCapacity(minCapacity);
    }


    /**
     * Checks current capacity if large enough, otherwise grows
     * 
     * @param minCapacity the desired minimum capacity
     */
    private void checkCurrentCapacity(int minCapacity)
    {
        if (minCapacity - currCapacity() > 0)
        {
            grow(minCapacity);
        }
    }


    /**
     * Returns new ArrayListIterator
     * 
     * @return new ArrayListIterator
     * @see ArrayListIterator
     * @see Iterator
     */
    @Override
    public Iterator<E> iterator()
    {
        return new ArrayListIterator();
    }


    /**
     * ArrayListIterator implementing Iterator interface
     * 
     * @param _index the index of the element to iterate
     * @author hzhu20@georgefox.edu
     * 
     * @see Iterator
     * @see ArrayListIterator
     * @see ArrayList
     */
    private class ArrayListIterator implements Iterator<E>
    {
        // current index points to the next element to be returned
        private int _index;


        /**
         * Constructs an ArrayListIterator
         * 
         * @param _index the index of the element to iterate
         */
        public ArrayListIterator()
        {
            this._index = 0;
        }

        
        /**
         * Returns true if the ArrayListIterator has a next element, false
         * otherwise.
         * 
         * @return true if the ArrayListIterator has a next element, false
         */
        @Override
        public boolean hasNext()
        {
            return _index < size();
        }


        /**
         * Returns the next element in the ArrayListIterator.
         * 
         * @return the next element in the ArrayListIterator
         * @throws NoSuchElementException {@inheritDoc}
         * 
         * @see ArrayListIterator
         */
        @Override
        public E next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            return _values[_index++];
        }
    }
}
