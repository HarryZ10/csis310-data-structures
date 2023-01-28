import java.util.*;


/**
 * A ListGraph is a graph that uses a HashMap to store the edges and vertices.
 *
 * @author hzhu20@georgefox.edu
 */
public class ListGraph<V ,E> extends DirectedGraph<V, E> {


    private HashMap<V, Vertex<V>> _vertices;            // the vertices
    private HashMap<V, HashMap<V, Edge<V, E>>> _edges;  // map of edges
    private int _size;                                  // number of vertices


    /**
     * Constructs an empty graph.
     */
    public ListGraph() {
        _vertices = new HashMap<>();
        _edges = new HashMap<>();
        _size = 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void add(V v) {

        // check if v is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex already exists
        if (this._vertices.containsKey(v)) {
            throw new DuplicateVertexException();
        }

        // add vertex to graph
        Vertex<V> vertex = new Vertex<>(v);

        this._vertices.put(v, vertex);
        this._edges.put(v, new HashMap<>());

        this._size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(V v) {

        // check if v is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }

        return this._vertices.containsKey(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Vertex<V> get(V v) {

        // check if v is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex exists, otherwise throw
        // NoSuchVertexException
        checkVertex(v);

        return this._vertices.get(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(V v) {

        // check if vertex is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }
        
        // check if vertex exists in graph, throw NoSuchVertexException
        checkVertex(v);

        // remove v from _vertices
        Vertex<V> removed = this._vertices.remove(v);

        // remove v as a key from _edges
        this._edges.remove(v);

        // for each innerMap, remove v as a dest vertex from _edges
        for (HashMap<V, Edge<V, E>> innerMap : this._edges.values()) {
            innerMap.remove(v);
        }

        // decrement size
        this._size--;

        return removed.getLabel();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(V u, V v, E label) {

        // check if u and v are null, throw IllegalArgumentException
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        // check if u and v does not exist in the vertices map
        checkVertices(u, v);

        // check if label is null, otherwise throw IllegalArgumentException
        if (label == null) {
            throw new IllegalArgumentException();
        }

        HashMap<V, Edge<V, E>> innerMap = this._edges.get(u);

        if (innerMap == null) {
            innerMap = new HashMap<>();
        }

        Edge<V, E> edge = null;

        // check if edge already exists, otherwise throw DuplicateEdgeException
        if (innerMap.containsKey(v)) {
            throw new DuplicateEdgeException();
        }

        edge = new Edge<V, E>(u, v, label);
        innerMap.put(v, edge);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsEdge(V u, V v) {
        
        // check if u and v are null, throw IllegalArgumentException
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertices exist, otherwise throw
        // NoSuchVertexException
        checkVertices(u, v);

        return this._edges.get(u).containsKey(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Edge<V, E> getEdge(V u, V v) {

        // check if null
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertices exist, otherwise throw
        // NoSuchVertexException
        checkVertices(u, v);

        // check if edge exists, otherwise throw
        // NoSuchEdgeException
        if (!this._edges.get(u).containsKey(v)) {
            throw new NoSuchEdgeException();
        }

        return this._edges.get(u).get(v);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public E removeEdge(V u, V v) {

        // check if null
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }
        
        // innermap = map of edges from u
        HashMap<V, Edge<V, E>> innermap = this._edges.get(u);

        // check if vertices exist, otherwise throw NoSuchVertexException
        checkVertices(u, v);

        // check if edge exists, otherwise throw NoSuchEdgeException
        if (!innermap.containsKey(v) || innermap == null) {
            throw new NoSuchEdgeException();
        }

        Edge<V,E> edge = innermap.get(v);

        // check if edge exists, otherwise throw NoSuchEdgeException
        if (edge == null) {
            throw new NoSuchEdgeException();
        } else {
            // remove edge from graph
            innermap.remove(v);

            return edge.getLabel();
        }
        
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

        // check if v is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex exists, otherwise throw NoSuchVertexException
        checkVertex(v);

        // compute degree of v
        int degree = 0;

        for (Edge<V, E> edge : this._edges.get(v).values()) {
            degree++;
        }

        return degree;
        
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int edgeCount() {
        
        // count number of edges
        int count = 0;

        for (V v : this._vertices.keySet()) {
            count += this._edges.get(v).size();
        }

        return count;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Vertex<V>> vertices() {
        // return iterator of vertices
        return this._vertices.values().iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Vertex<V>> adjacent(V v) {

        // check if v is null, throw IllegalArgumentException
        if (v == null) {
            throw new IllegalArgumentException();
        }

        // check if vertex exists, otherwise throw NoSuchVertexException
        checkVertex(v);

        // given a vertex label, where can i go to from the given src vertex?
        // grab the inner map of edges from v
        HashMap<V, Edge<V, E>> innermap = this._edges.get(v);

        // grab the set of keys from the inner map
        ArrayList<V> keys = new ArrayList<>(innermap.keySet());
        
        // create adjacency list
        ArrayList<Vertex<V>> adj = new ArrayList<>();

        // for each key in keys, add the vertex to x
        for (V key : keys) {

            // grab the vertex from the key
            Vertex<V> vertex = this._vertices.get(key);

            // add vertex to adjacency list
            adj.add(vertex);
        }

        // return iterator of x
        return adj.iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Edge<V, E>> edges() {

        // create list of edges
        List<Edge<V, E>> edges = new ArrayList<>();

        // for each innermap, add each edge to list
        for (HashMap<V, Edge<V, E>> innerMap : this._edges.values()) {
            for (Edge<V, E> edge : innerMap.values()) {
                edges.add(edge);
            }
        }

        // return iterator of edges
        return edges.iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        // clear vertices
        this._vertices.clear();

        // clear edges
        this._edges.clear();

        // reset size
        this._size = 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Check if the vertex is in the graph
     * @param v the vertex to check
     */
    private void checkVertex(V v) {
        if (!this._vertices.containsKey(v)) {
            throw new NoSuchVertexException();
        }
    }


    /**
     * Check if the edge is in the graph
     * @param u the source vertex
     * @param v the destination vertex
     */
    private void checkVertices(V u, V v) {
        if (!this._vertices.containsKey(u) || !this._vertices.containsKey(v)) {
            throw new NoSuchVertexException();
        }
    }
}
