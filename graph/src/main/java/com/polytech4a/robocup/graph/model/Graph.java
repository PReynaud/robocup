package com.polytech4a.robocup.graph.model;

import com.polytech4a.robocup.graph.enums.EdgeType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * Created by Antoine CARON on 06/05/2015.
 *
 * @author Antoine CARON
 * @version 1.0
 *          <p/>
 *          Simple Graph Model.
 */
public class Graph {

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(Graph.class);

    /**
     * Nodes of the graph.
     */
    private ArrayList<Node> nodes;

    /**
     * Edge of the graph.
     */
    private ArrayList<Edge> edges;

    /**
     * Constructor for the Graph class
     */
    public Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    /**
     * Constructor for the Graph class
     */
    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.edges = edges;
        this.nodes = nodes;
    }

    /**
     * Add node to the graph if the node doesn't exists
     *
     * @param node Node to add
     */
    public void addNode(Node node) {
        try {
            getNode(node.getId());
        } catch (NoSuchElementException e) {
            nodes.add(node);
        }
    }

    /**
     * Remove node from the graph
     *
     * @param node Node to remove
     */
    public void removeNode(Node node) {
        try {
            ArrayList<Edge> edgesToRemove = getEdgesFromNode(node);
            for (Edge edge : edgesToRemove) {
                removeEdge(edge);
            }
        } catch (NoSuchElementException e) {
            //List of edges empty
        }
        nodes.remove(node);
    }

    /**
     * Add edge to the graph if the edge doesn't exist yet
     *
     * @param edge Edge to add
     */
    public void addEdge(Edge edge) {
        try {
            getEdge(getNode(edge.getNode1()), getNode(edge.getNode2()));
        } catch (NoSuchElementException e) {
            edges.add(edge);
        }
    }

    /**
     * Add edge to the graph between the two nodes if the
     * edge doesn't exist yet
     *
     * @param node1 First node of the edge
     * @param node2 Second node of the edge
     * @param type  type of the edge
     */
    public void addEdge(Node node1, Node node2, EdgeType type) {
        try {
            getEdge(node1, node2);
        } catch (NoSuchElementException e) {
            edges.add(new Edge(node1.getId(), node2.getId(), type));
        }
    }

    /**
     * Remove edge from the graph
     *
     * @param edge Edge to remove
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    /**
     * Remove the edge between the two nodes if exists
     *
     * @param node1 First node of the edge
     * @param node2 Second node of the edge
     */
    public void removeEdge(Node node1, Node node2) {
        edges.remove(getEdge(node1, node2));
    }

    /**
     * Get the idNode node of the graph
     *
     * @param idNode id of the Node
     * @return idNode Node
     */
    public Node getNode(int idNode) {
        return nodes.parallelStream().filter(n -> n.getId() == idNode).findAny().get();
    }

    /**
     * Get the edge between the two nodes
     *
     * @param node1 First node of the edge
     * @param node2 Second node of the edge
     * @return Edge if exists
     */
    public Edge getEdge(Node node1, Node node2) {
        int idN1 = node1.getId(), idN2 = node2.getId();
        return edges.parallelStream()
                .filter(e ->
                        (e.getNode1() == idN1 && e.getNode2() == idN2) ||
                                (e.getNode1() == idN2 && e.getNode2() == idN1))
                .findAny()
                .get();
    }

    /**
     * Get the nodes of the graph
     *
     * @return List of nodes
     */
    public ArrayList<Node> getNodes() {
        ArrayList<Node> clonedNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            clonedNodes.add(node.clone());
        }
        return clonedNodes;
    }

    /**
     * Get the edges of the graph
     *
     * @return list of edges
     */
    public ArrayList<Edge> getEdges() {
        return (ArrayList<Edge>) edges.stream().map(Edge::clone).collect(Collectors.toList());
    }

    /**
     * Get the List of Nodes in the closed area of an input node
     *
     * @param node center of the linked nodes
     * @return Linked Nodes
     */
    public ArrayList<Node> getNeighboursFromNode(Node node) {
        ArrayList<Node> result = new ArrayList<>();
        ArrayList<Edge> linkedEdges = getEdgesFromNode(node);
        for (Edge edge : linkedEdges) {
            int i = edge.getSecondNode(node.getId());
            if (i >= 0) {
                result.add(getNode(i));
            }
        }
        return result;
    }

    /**
     * Get the Edges linked to a Node
     *
     * @param node Center of the Edge
     * @return List of the linked Edges
     */
    public ArrayList<Edge> getEdgesFromNode(Node node) {
        return (ArrayList<Edge>) edges.parallelStream().filter(e -> e.hasNode(getNode(node.getId()).getId())).collect(Collectors.toList());
    }

    /**
     * Get the list of nodes linked to an edge
     *
     * @param edge link between the nodes
     * @return List of the linked nodes
     */
    public ArrayList<Node> getNodesFromEdge(Edge edge) {
        ArrayList<Node> result = new ArrayList<>();
        result.add(getNode(edge.getNode1()));
        result.add(getNode(edge.getNode2()));
        return result;
    }

    /**
     * Get the number of nodes of the graph
     *
     * @return
     */
    public int getNumberOfNodes() {
        return nodes.size();
    }

    public boolean equals(Graph obj) {
        if (obj.getEdges().size() == getEdges().size() && obj.getNodes().size() == getNodes().size()) {
            for (Edge edge : edges) {
                try {
                    if (!edge.equals(obj.getEdge(obj.getNode(edge.getNode1()), obj.getNode(edge.getNode2())))) {
                        return false;
                    }
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
            for (Node node : nodes) {
                try {
                    if (!node.equals(obj.getNode(node.getId()))) {
                        return false;
                    }
                } catch (NoSuchElementException e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected Graph clone() {
        ArrayList<Node> clonedNodes = new ArrayList<>();
        ArrayList<Edge> clonedEdges = new ArrayList<>();

        for (Node node : nodes) {
            clonedNodes.add(node.clone());
        }
        for (Edge edge : edges) {
            clonedEdges.add(edge.clone());
        }
        return new Graph(clonedNodes, clonedEdges);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Graph [").append("\n");
        for (Node node : nodes) {
            result.append(node.toString()).append("\n");
        }
        result.append("\n");
        for (Edge edge : edges) {
            result.append(edge.toString()).append("\n");
        }
        return result.append("]").toString();
    }
}
