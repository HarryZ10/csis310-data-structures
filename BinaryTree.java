import java.util.*;


/**
 * <p>ClassBinaryTree of type E is a recursive data structure,
 * where each tree node has an optional left and right childnode.</p>
 *
 * <p>Each node allows setting the left or right child,
 * which has the side effect of detaching any pre-existing child
 * subtree rooted at that node from itself and returning it.</p>
 *
 * <p>Moreover, setting a child has the additionalside effect of also detaching
 * the new child subtree node from any pre-existing parent node it was
 * connected to, thereby maintaining the binary tree nature of
 * all relevant trees.</p>
 *
 * @author hzhu20@georgefox.edu
 * @since 17.0
 * @version 0.0.1
 */
public class BinaryTree<E> {

    private BinaryTree<E> _parent;  // parent node
    private BinaryTree<E> _left;    // left child's subtree node
    private BinaryTree<E> _right;   // right child's subtree node
    private E _data;                // data stored at this node


    /**
     * Constructor for class BinaryTree that creates a new BinaryTree object
     * with a root node.
     *
     * @param element the element to be stored in the root node of the tree
     */
    public BinaryTree(E element) {
        _data = element;
        _parent = null;
        _left = null;
        _right = null;
    }


    /**
     * Gets the data stored in the root node of the tree.
     *
     * @return the data stored in the root node of the tree
     */
    public E getElement() {
        return this._data;
    }


    /**
     * Sets the data stored in the root node of the tree.
     *
     * @param element the data to be stored in the root node of the tree
     */
    public void setElement(E element) {
        this._data = element;
    }


    /**
     * Checks for a left child node.
     *
     * @return true if the root node has a left child tree
     */
    public boolean hasLeftChild() {
        return this.getLeftChild() != null;
    }


    /**
     * Get left child tree.
     *
     * @return the left child tree
     */
    public BinaryTree<E> getLeftChild() {
        return this._left;
    }


    /**
     * Set left child tree.
     *
     * @param child the new child tree to set as the left child tree
     * @return the left child tree that was detached
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public BinaryTree<E> setLeftChild(BinaryTree<E> child) {

        // make a copy for the old child
        BinaryTree<E> oldChild = null;

        // Cannot set an ancestor as a child
        if (child != null && child.hasAncestor(this)) {
            throw new IllegalArgumentException();
        }

        if (this.hasLeftChild()) {
            // detach old child
            oldChild = this.getLeftChild();

            // detach old child
            oldChild._parent = null;
        }

        // if the right child is the same as "child", then set left child to right
        // and set right child to null
        if (this.hasRightChild() && this.getRightChild() == child) {
            this._left = this.getRightChild();
            this._right = null;
        }

        // if child is not null
        if (child != null) {
            // set child's parent to this

            if (child.getParent() != null) {

                // null the opposite of child of parent
                if (child.getParent().getRightChild() == child) {
                    child.getParent()._right = null;
                } else {
                    child.getParent()._left = null;
                }

                child._parent = null;
            }

            child._parent = this;
        }

        // if this has a right child
        if (child == null) {

            // save the old child
            oldChild._parent = null;
        }

        // set the new child
        this._left = child;

        return oldChild;
    }


    /**
     * Checks if the child node has an ancestor.
     *
     * @param binaryTree the node to check against
     * @return true if the child node has an ancestor
     */
    private boolean hasAncestor(BinaryTree<E> binaryTree) {
        if (binaryTree == null) {
            return false;
        }

        if (binaryTree == this) {
            return true;
        }

        return hasAncestor(binaryTree.getParent());
    }


    /**
     * Checks for a right child tree.
     *
     * @return true if the root node has a right child tree
     */
    public boolean hasRightChild() {
        return this.getRightChild() != null;
    }


    /**
     * Get right child tree.
     *
     * @return the right child tree
     */
    public BinaryTree<E> getRightChild() {
        return this._right;
    }


    /**
     * Set right child tree.
     *
     * @param child the new child tree to set as the right child tree
     * @return the right child tree that was detached
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public BinaryTree<E> setRightChild(BinaryTree<E> child) {

        // make a copy for the old child
        BinaryTree<E> oldChild = null;

        // Cannot set an ancestor as a child
        if (child != null && child.hasAncestor(this)) {
            throw new IllegalArgumentException();
        }

        if (this.hasRightChild()) {
            oldChild = this.getRightChild();

            // detach old child
            oldChild._parent = null;
        }

        // if the left child is the same as "child"
        if (this.hasLeftChild() && this.getLeftChild() == child) {

            this._right = this.getLeftChild();

            this._left = null;
        }

        // if child is not null
        if (child != null) {

            // set child's parent to this

            if (child.getParent() != null) {

                // null the opposite of child of parent
                if (child.getParent().getLeftChild() == child) {
                    child.getParent()._left = null;
                } else {
                    child.getParent()._right = null;
                }

                child._parent = null;
            }

            child._parent = this;
        }

        // if this has a right child
        if (child == null) {

            // save the old child
            oldChild._parent = null;
        }

        // set the new child
        this._right = child;

        return oldChild;
    }


    /**
     * Returns the root tree.
     *
     * @return the root tree
     */
    public BinaryTree<E> getRoot() {

        // traverse up the tree until the root is reached
        if (this.isRoot()) {
            // root is reached
            return this;

        } else {
            // traverse up the tree until the root is reached
            return this.getParent().getRoot();
        }
    }


    /**
     * Returns the parent tree.
     *
     * @return the parent tree of the current tree
     */
    public BinaryTree<E> getParent() {
        return this._parent;
    }


    /**
     * Recursively calculates the size of the tree.
     *
     * @return the size of the tree
     */
    public int size() {
        // if the tree is empty
        if (this.isEmpty()) {
            return 0;
        }

        int leftSize = 0;
        int rightSize = 0;

        // if the left child is not null
        if (this.hasLeftChild()) {
            leftSize = this.getLeftChild().size();
        }

        // if the right child is not null
        if (this.hasRightChild()) {
            rightSize = this.getRightChild().size();
        }

        return 1 + leftSize + rightSize;
    }


    /**
     * Returns the height of the tree which is the longest path from the root
     *
     * The height of a subtree rooted at some node is the length of the
     * longest path to any leaf descendant of that node.
     *
     * @return the height of the tree
     */
    public int height() {
        // if the current node is null then return 0
        if (this.isEmpty() || this.isLeaf()) {
            return 0;
        }

        if (this.hasLeftChild() && this.hasRightChild()) {
            return 1 + Math.max(this.getLeftChild().height(), this.getRightChild().height());
        } else if (this.hasLeftChild()) {
            return 1 + this.getLeftChild().height();
        } else if (this.hasRightChild()) {
            return 1 + this.getRightChild().height();
        } else {
            return 0;
        }
    }


    /**
     * Returns the level of the tree that the current tree is on.
     *
     * @return the level (integer) of the tree
     */
    public int level() {

        int level = 0;

        if (!this.isRoot()) {
            level = this.getParent().level() + 1;
        }

        return level;
    }


    /**
     * Compute the the degree of a node which is the count of its children.
     *
     * @return the number of children the tree has (which must be 0, 1, or 2)
     */
    public int degree() {

        int degree = 0;

        // if the left child tree exists, add 1 to the degree of the current tree
        if (this.hasLeftChild()) {
            degree++;
        }

        // if the right child tree exists, add 1 to the degree of the current tree
        if (this.hasRightChild()) {
            degree++;
        }

        return degree;
    }


    /**
     * Checks if tree is a root.
     *
     * @return true if the tree is a root, false otherwise
     */
    public boolean isRoot() {
        return this.getParent() == null;
    }


    /**
     * Checks if tree is a parent.
     *
     * @return true if the tree is a parent, false otherwise
     */
    public boolean isParent() {
        return this.hasLeftChild() || this.hasRightChild();
    }


    /**
     * Checks if tree is a child.
     *
     * @return true if the tree is a child, false otherwise
     */
    public boolean isChild() {
        return this.getParent() != null;
    }


    /**
     * Checks if tree is a leaf. A leaf is a node with no children.
     *
     * @return true if the tree is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return this.getLeftChild() == null && this.getRightChild() == null;
    }


    /**
     * Checks if tree is full.
     *
     * @return true if the tree is full, false otherwise
     */
    public boolean isFull() {

        if (this.isEmpty()) {
            return true;
        }

        if (this.hasLeftChild() && this.hasRightChild()) {
            if (this.getLeftChild().height() != this.getRightChild().height()) {
                return false;
            }
        } else if (this.hasLeftChild() || this.hasRightChild()) {
            return false;
        } else {
            return true;
        }

        return this.getLeftChild().isFull() && this.getRightChild().isFull();


        // if (isEmpty()) return true;
        // if (left().height() != right().height()) return false;
        // return left().isFull() && right().isFull();
    }


    /**
     * Returns true if tree is empty.
     *
     * Precondition: tree is not null
     * Postcondition: returns true iff tree rooted at node is empty
     *
     * @return true if the tree is empty, false otherwise
     */
    private boolean isEmpty() {
        return this.getRoot() == null;
    }


    /**
     * Return whether tree is complete. A complete tree has minimal height
     * and any holes in tree would appear in last level to right.
     *
     * @return true if the tree is complete, false otherwise
     */
    public boolean isComplete() {

        if (this == null|| this.isLeaf()) {
            return true;
        }

        // check if node has only 1 child
        if (this.getLeftChild() == null && this.getRightChild() != null
            || this.getRightChild() == null && this.getLeftChild() != null)  {

            return false;
        }

        int leftHeight = 0;
        int rightHeight = 0;

        if (this.hasLeftChild()) {
            leftHeight = this.getLeftChild().height();
        }

        if (this.hasRightChild()) {
            rightHeight = this.getRightChild().height();
        }

        if (rightHeight > leftHeight) {

            return false;

        } else if (leftHeight - rightHeight > 1) {

            return false;

        } else if (rightHeight == leftHeight) {

            return this.getLeftChild().isFull()
                && this.getRightChild().isFull()
                && this.getRightChild().isComplete();

        } else if (leftHeight - rightHeight == 1) {

            return this.getLeftChild().isFull()
                && this.getRightChild().isFull()
                && this.getRightChild().isComplete()
                && this.getLeftChild().isComplete();

        } else {
            return false;
        }
    }


    /**
     * Checks if tree is degenerate.
     * Degenerate trees are trees that have no leaves.
     *
     * @return the root tree
     */
    public boolean isDegenerate() {

        if (this.hasLeftChild()) {
            if (hasRightChild()) {
                return false; // not degenerate, has two children
            } else {
                return this.getLeftChild().isDegenerate();
            }
        } else {
            if (this.hasRightChild()) {
                return this.getRightChild().isDegenerate();
            } else {
                return true; // arriving here without any node with two children
            }
        }
    }


    /**
     * Checks if the tree is an ancestor of the specified tree.
     *
     * @param descendant the tree to check
     * @return true if the tree is an ancestor of the specified tree, false
     *         otherwise
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public boolean isAncestorOf(BinaryTree<E> descendant) {

        if (this == null || descendant == null) {
            throw new IllegalArgumentException();
        }

        if (descendant == this) {
            return false;
        }

        while (descendant != null) {

            // base case: descendant is the same as the current tree
            // base case 2: descendant is the root of the current tree

            if (descendant == this) {
                return true;
            }

            if (descendant.isRoot()) {
                return false;
            }

            descendant = descendant.getParent();
        }

        return false;
    }


    /**
     * Checks if the tree is a parent of the specified tree.
     *
     * @param child the tree to check
     * @return true if the tree is a parent of the specified tree, false
     *         otherwise
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public boolean isParentOf(BinaryTree<E> child) {

        // both pointers are non-null
        if (this != null && child != null) {

            if (child.getParent() == this) {
                return true;
            }

        } else {
            throw new IllegalArgumentException();
        }

        return false;
    }


    /**
     * Checks if the current tree is a sibling of the specified tree.
     *
     * @param sibling the tree to check
     * @return true if the current tree is a sibling of the specified tree,
     *         false otherwise
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public boolean isSiblingOf(BinaryTree<E> sibling) {



        if (sibling != null) {
            return sibling.getParent() == this.getParent()
                && sibling != this
                && this.getParent() != null;
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Checks if the current tree is a child of the specified tree.
     *
     * @param parent the tree to check
     * @return true if the tree is a child of the specified tree, and false
     *         otherwise
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public boolean isChildOf(BinaryTree<E> parent) {

        boolean result = false;

        if (parent != null) {
            result = parent.isParentOf(this);
        } else {
            throw new IllegalArgumentException();
        }

        return result;
    }


    /**
     * Checks if the current tree is a descendant of the specified tree.
     *
     * @param ancestor the tree to check
     * @return true if the tree is a descendant of the specified tree, false
     *         otherwise
     *
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public boolean isDescendantOf(BinaryTree<E> ancestor) {

        if (ancestor != null) {
            if (ancestor.isAncestorOf(this)) {
                return true;
            }

        } else {
            throw new IllegalArgumentException();
        }

        return false;
    }


    /**
     * Returns new iterator for the tree.
     *
     * @return new iterator for the tree
     */
    public Iterator<E> iterator() {
        return new InOrderIterator(this);
    }


    /**
     * Pre-order Iterator for the tree (root, left, right) that
     * traverses the tree in a pre-order manner.
     *
     * @return Iterator of type E for the tree in a pre-order manner
     */
    public Iterator<E> preOrderIterator() {
        return new PreOrderIterator(this);
    }


    /**
     * In-order Iterator for the tree (left, root, right) that
     * traverses the tree in a in-order manner.
     *
     * @return Iterator of type E for the tree in a in-order manner
     */
    public Iterator<E> inOrderIterator() {
        return new InOrderIterator(this);
    }


    /**
     * Post-order Iterator for the tree (left, root, right) that
     * traverses the tree in a post-order manner.
     *
     * @return Iterator of type E for the tree in a post-order manner
     */
    public Iterator<E> postOrderIterator() {
        return new PostOrderIterator(this);
    }


    /**
     * Level-order Iterator for the tree (root, left, right) that
     * traverses the tree in a level-order manner.
     *
     * @return Iterator of type E for the tree in a level-order manner
     */
    public Iterator<E> levelOrderIterator() {
        return new LevelOrderIterator(this);
    }


    /**
     * Returns a string representation of the tree.
     *
     * @return a string representation of the tree
     */
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        // iterate using in-order traversal
        Iterator<E> iter = this.inOrderIterator();

        // append each element to the string
        while (iter.hasNext()) {
            result.append(iter.next().toString());
            result.append(", ");
        }

        // remove the last ", "
        if (result.length() > 0) {
            result.delete(result.length() - 2, result.length());
        }

        return result.toString();
    }


    /**
     * In order traversal of the tree.
     *
     * @author hzhu20@georgefox.edu
     */
    private class InOrderIterator implements Iterator<E> {

        private Queue<E> _queue;        // queue of nodes
        private Iterator<E> _iterator;  // iterator of the queue


        /**
         * Constructs an iterator for the tree.
         *
         * @param root the root node
         */
        public InOrderIterator(BinaryTree<E> root) {
            _queue = new LinkedList<E>();
            inOrder(root);
            _iterator = _queue.iterator();
        }


        /**
         * Checks if there is a next node.
         *
         * @return true if there is a next node, false otherwise
         */
        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }


        /**
         * Returns the next node.
         *
         * @return the next node
         * @throws NoSuchElementException {@inheritDoc}
         */
        @Override
        public E next() {
            return _iterator.next();
        }


        /**
         * Recursively orders the tree.
         *
         * @param root the root node
         */
        private void inOrder(BinaryTree<E> root) {
            if (root != null) {
                inOrder(root.getLeftChild());
                this._queue.add(root.getElement());
                inOrder(root.getRightChild());
            }
        }
    }


    /**
     * Pre-order traversal of the tree.
     *
     * Visit current node, then visit left child, then visit right child
     *
     * @author hzhu20@georgefox.edu
     */
    private class PreOrderIterator implements Iterator<E> {

        private Queue<E> _queue;        // queue of nodes
        private Iterator<E> _iterator;  // iterator of the queue


        /**
         * Constructs an iterator for the tree.
         *
         * @param root the root node
         */
        public PreOrderIterator(BinaryTree<E> root) {
            _queue = new LinkedList<E>();
            preOrder(root);
            _iterator = _queue.iterator();
        }


        /**
         * Checks if there is a next node.
         *
         * @return true if there is a next node, false otherwise
         */
        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }


        /**
         * Returns the next node.
         *
         * @return the next node
         */
        @Override
        public E next() {
            return _iterator.next();
        }


        /**
         * Recursively orders the tree.
         *
         * @param root the root node
         */
        private void preOrder(BinaryTree<E> root) {
            if (root != null) {
                this._queue.add(root.getElement());
                preOrder(root.getLeftChild());
                preOrder(root.getRightChild());
            }
        }
    }


    /**
     * Post order traversal of the tree.
     *
     * @author hzhu20@georgefox.edu
     */
    private class PostOrderIterator implements Iterator<E> {

        private Queue<E> _queue;        // queue of nodes
        private Iterator<E> _iterator;  // iterator of the queue


        /**
         * Constructs an iterator for the tree.
         *
         * @param root the root node
         */
        public PostOrderIterator(BinaryTree<E> root) {
            _queue = new LinkedList<E>();
            postOrder(root);
            _iterator = _queue.iterator();
        }


        /**
         * Checks if there is a next node.
         *
         * @return true if there is a next node, false otherwise
         */
        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }


        /**
         * Returns the next node.
         *
         * @return the next node
         */
        @Override
        public E next() {
            return _iterator.next();
        }


        /**
         * Recursively orders the tree.
         *
         * @param root the root node
         */
        private void postOrder(BinaryTree<E> root) {
            if (root != null) {
                postOrder(root.getLeftChild());
                postOrder(root.getRightChild());
                this._queue.add(root.getElement());
            }
        }
    }


    /**
     * Level order traversal of the tree.
     *
     * @author hzhu20@georgefox.edu
     */
    private class LevelOrderIterator implements Iterator<E> {

        private Queue<E> _queue;        // queue of nodes
        private Iterator<E> _iterator;  // iterator of the queue


        /**
         * Constructs an iterator for the tree.
         *
         * @param root the root node
         */
        public LevelOrderIterator(BinaryTree<E> root) {
            _queue = new LinkedList<E>();

            for (int i = 0; i <= height(); i++) {
                levelOrder(root, i);
            }

            _iterator = _queue.iterator();
        }


        /**
         * Checks if there is a next node.
         *
         * @return true if there is a next node, false otherwise
         */
        @Override
        public boolean hasNext() {
            return _iterator.hasNext();
        }


        /**
         * Returns the next node.
         *
         * @return the next node
         */
        @Override
        public E next() {
            return _iterator.next();
        }


        /**
         * Recursively orders the tree.
         *
         * @param root the root node
         * @param level the current level
         */
        private void levelOrder(BinaryTree<E> root, int level) {

            if (root == null) {
                return;
            } else if (level == 0) {
                _queue.add(root.getElement());
            } else if (level > 0) {
                levelOrder(root.getLeftChild(), level - 1);
                levelOrder(root.getRightChild(), level - 1);
            }
        }
    }
}
