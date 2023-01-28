import java.util.*;

/**
 * A MatrixGraph implementation is a graph that uses a matrix to store the
 * edges and vertices of a graph.
 *
 * @author hzhu20@georgefox.edu
 */
@SuppressWarnings("unchecked")
public class MatrixGraph<V, E> extends DirectedGraph<V, E> {

    // Default Capacity:
    // '1' means 1 row and 1 column,
    // '10' would be 10 rows and 10 columns

    // '10' would be too big a graph to begin with... no matter how fast it
    // could be. We shouldn't assume that the end user will want to pre-allocate
    // a graph with 10 rows and 10 columns unless specifically told to do so,
    // using the constructor that takes an initialCapacity parameter.

    private static final int DEFAULT_CAPACITY = 1;

    private Vertex<V>[] _vertices;       // The vertices of the graph.
    private Edge<V, E>[][] _edges;       // The edges of the graph.
    private int _size;                   // The number of vertices in the graph.
    private HashMap<V,Vertex<V>> _dict;  // labels -> vertices


    /**
     * Constructs a new, empty graph.
     */
    public MatrixGraph() {
        _vertices = (Vertex<V>[]) new Vertex[DEFAULT_CAPACITY];
        _edges = (Edge<V, E>[][]) new Edge[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        _size = 0;
        _dict = new HashMap<>();
    }


    /**
     * Constructs a new MatrixGraph with the given vertices and edges.
     *
     * Time complexity: O(|V| + |V|^2) where |V| is the number of vertices.
     *      = O(|V|^2)
     *
     * @param initialCapacity the initial capacity of the graph
     */
    public MatrixGraph(int initialCapacity) {
        _vertices = (Vertex<V>[]) new Vertex[initialCapacity];
        _edges = (Edge<V, E>[][]) new Edge[initialCapacity][initialCapacity];
        _size = 0;
        _dict = new HashMap<>();
    }


    /**
     * {@inheritDoc}
     * pre: label is a non-null label for vertex
     * post: a vertex with label is added to graph;
     */
    @Override
    public void add(V v) {

        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex already exists
        for (int i = 0; i < this.size(); i++) {
            if (this._vertices[i].getLabel().equals(v)) {
                throw new DuplicateVertexException();
            }
        }

        /// if size is vertices.length, then we need to resize
        if (this.size() == this._vertices.length) {
            expand();
        }

        // create new vertex
        Vertex<V> vertex = new Vertex<>(v);

        // add vertex to vertices
        this._vertices[this.size()] = vertex;

        // add vertex to dictionary
        this._dict.put(v, vertex);

        // update size
        this._size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(V v) {

        if (v == null) {
            throw new IllegalArgumentException();
        }

        return this._dict.containsKey(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Vertex<V> get(V v) {

        // a vertex label of null is not allowed
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex exists
        if (!this.contains(v)) {
            throw new NoSuchVertexException();
        }

        return this._dict.get(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(V v) {

        // a vertex label of null is not allowed
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex exists
        if (!this.contains(v)) {
            throw new NoSuchVertexException();
        }

        // get index of v using Arrays
        int index = Arrays.asList(this._vertices).indexOf(this._dict.get(v));

        for (int i = 0; i < this.size(); i++) {
            if (this._edges[index][i] != null) {

                // remove label from edge
                this._edges[index][i].setLabel(null);
                this._edges[index][i] = null;
            }
            if (this._edges[i][index] != null) {

                // remove label from edge
                this._edges[i][index].setLabel(null);
                this._edges[i][index] = null;
            }
        }

        // remove vertex at index
        this._vertices[index] = null;

        // remove vertex from dictionary
        Vertex<V> removed = this._dict.remove(v);

        // update size to reflect vertex removal
        this._size--;

        return removed.getLabel();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(V u, V v, E label) {

        // pre: u and v are labels of existing vertices
        // post: an edge is inserted between u and v;
        // if edge is new, it is labeled with label (can be null)

        // check if label is null
        if (label == null) {
            throw new IllegalArgumentException();
        }

        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        // if u and v are not in the graph, throw exception
        // don't use dictionary to check if u and v are in the graph
        if (!this.contains(u) || !this.contains(v)) {
            throw new NoSuchVertexException();
        }

        // check if edge already exists
        if (this.containsEdge(u, v)) {
            throw new DuplicateEdgeException();
        }

        // get vertices
        Vertex<V> vertexU = this.get(u);
        Vertex<V> vertexV = this.get(v);

        // get indices
        int indexU = Arrays.asList(this._vertices).indexOf(vertexU);
        int indexV = Arrays.asList(this._vertices).indexOf(vertexV);

        // create edge
        Edge<V, E> edge = new Edge<V, E>(vertexU.getLabel(), vertexV.getLabel(), label);

        // add edge to graph
        this._edges[indexU][indexV] = edge;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsEdge(V u, V v) {

            // pre: u and v are labels of existing vertices
            // post: returns true if an edge exists between u and v;
            // otherwise, returns false

            if (u == null || v == null) {
                throw new IllegalArgumentException();
            }

            // if u and v are not in the graph, throw exception
            // don't use dictionary to check if u and v are in the graph
            if (!this.contains(u) || !this.contains(v)) {
                throw new NoSuchVertexException();
            }

            // get indices
            int indexU = Arrays.asList(this._vertices).indexOf(this._dict.get(u));
            int indexV = Arrays.asList(this._vertices).indexOf(this._dict.get(v));

            // check if edge exists
            if (this._edges[indexU][indexV] != null) {
                return true;
            }

            return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Edge<V, E> getEdge(V u, V v) {

        // pre: u and v are labels of existing vertices
        // post: returns edge between u and v;
        // if edge does not exist, returns null

        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        // check if edge exists, otherwise throw NoSuchEdgeException
        if (!this._dict.containsKey(u) || !this._dict.containsKey(v)) {
            throw new NoSuchVertexException();
        }

        // get indices
        int indexU = Arrays.asList(this._vertices).indexOf(this._dict.get(u));
        int indexV = Arrays.asList(this._vertices).indexOf(this._dict.get(v));

        Edge<V, E> edge = this._edges[indexU][indexV];

        if (edge == null) {
            throw new NoSuchEdgeException();
        }

        return edge;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public E removeEdge(V u, V v) {
        // pre: u and v are labels of existing vertices
        // post: edge is removed, its label is returned

        // remove edge
        Edge<V, E> edge = this.getEdge(u, v);

        // remove edge from graph
        this._edges[Arrays.asList(this._vertices)
            .indexOf(this._dict.get(u))][Arrays.asList(this._vertices)
            .indexOf(this._dict.get(v))] = null;

        return edge.getLabel();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this._size;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int degree(V v) {

        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if v is in the graph
        if (!this.contains(v)) {
            throw new NoSuchVertexException();
        }

        // get index of v
        int index = Arrays.asList(this._vertices).indexOf(this._dict.get(v));

        // count number of edges
        int degree = 0;

        for (int i = 0; i < this._vertices.length; i++) {
            if (this._edges[index][i] != null) {
                degree++;
            }
        }

        return degree;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int edgeCount() {

        int count = 0;

        for (int i = 0; i < this._vertices.length; i++) {
            for (int j = 0; j < this._vertices.length; j++) {
                if (this._edges[i][j] != null) {
                    count++;
                }
            }
        }

        return count;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Vertex<V>> vertices() {
        // post: returns traversal across all vertices of graph
        return this._dict.values().iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Vertex<V>> adjacent(V v) {
        // pre: label is label of vertex in graph
        // post: returns traversal over vertices adj. to vertex
        // each edge beginning at label visited exactly once

        if (v == null) {
            throw new IllegalArgumentException();
        }

        if (!this._dict.containsKey(v)) {
            throw new NoSuchVertexException();
        }

        // get vertex
        Vertex<V> vertex = this._dict.get(v);

        // get index
        int index = Arrays.asList(this._vertices).indexOf(vertex);

        // create queue using LinkedList
        Queue<Vertex<V>> queue = new LinkedList<Vertex<V>>();

        // iterate through the rows of the adjacency matrix
        for (int row = this.size() - 1; row >= 0; row--) {

            // create the edge using vertex's index and row
            Edge<V, E> edge = (Edge<V, E>) this._edges[index][row];

            // edge needs to be non-null
            if (edge != null) {

                // if edge's label is equal to vertex's label, add vertex to queue
                if (edge.getU().equals(vertex.getLabel())) {

                    // Add the dest vertex at the other end of the edge
                    queue.add(this._dict.get(edge.getV()));
                } else {

                    // Add the src vertex at the other end of the edge
                    queue.add(this._dict.get(edge.getU()));
                }
            }
        }

        return queue.iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Edge<V, E>> edges() {
        // post: returns traversal across all edges of graph (returns Edges)

        // create queue using LinkedList
        Queue<Edge<V, E>> queue = new LinkedList<Edge<V, E>>();

        // iterate through the rows of the adjacency matrix
        for (int row = this.size() - 1; row >= 0; row--) {

            // iterate through the columns of the adjacency matrix
            for (int col = this.size() - 1; col >= 0; col--) {

                // create the edge using row and column
                Edge<V, E> edge = (Edge<V, E>) this._edges[row][col];

                // edge needs to be non-null
                if (edge != null) {

                    // add edge to queue
                    queue.add(edge);
                }
            }
        }

        return queue.iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        // post: graph is empty
        this._dict.clear();
        this._vertices = (Vertex<V>[]) new Vertex[this._vertices.length];
        this._edges = (Edge<V, E>[][]) new Edge[this._edges.length][this._edges.length];
        this._size = 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }


    /**
     * Expands the adjacency matrix by +1 row and +1 column.
     */
    private void expand() {
        // pre: none
        // post: adjacency matrix is expanded by +1 row and +1 column

        // create new array of vertices
        Vertex<V>[] newVertices = (Vertex<V>[]) new Vertex[this._vertices.length + 1];

        // create new array of edges
        Edge<V, E>[][] newEdges = (Edge<V, E>[][]) new Edge[this._edges.length + 1][this._edges.length + 1];

        // copy vertices
        for (int i = 0; i < this._vertices.length; i++) {
            newVertices[i] = this._vertices[i];
        }

        // copy edges
        for (int i = 0; i < this._edges.length; i++) {
            for (int j = 0; j < this._edges.length; j++) {
                newEdges[i][j] = this._edges[i][j];
            }
        }

        // update vertices and edges
        this._vertices = newVertices;
        this._edges = newEdges;
    }
}
