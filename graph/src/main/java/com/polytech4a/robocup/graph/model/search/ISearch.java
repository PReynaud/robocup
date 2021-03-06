package com.polytech4a.robocup.graph.model.search;

import com.polytech4a.robocup.graph.enums.EdgeType;
import com.polytech4a.robocup.graph.enums.NodeType;
import com.polytech4a.robocup.graph.model.Graph;
import com.polytech4a.robocup.graph.model.Node;
import com.polytech4a.robocup.graph.model.exceptions.SearchException;

import java.util.ArrayList;

/**
 * Created by Dimitri on 19/05/2015.
 * @version 1.0
 *
 * Path finding interface
 */
public interface ISearch {
    /**
     * Search the way from the node begin to the node end matching the parameters
     *
     * @param graph     graph of the search
     * @param begin     start node
     * @param end       objective node
     * @param nodeTypes parameters for the nodes
     * @param edgeTypes parameters for the edges
     * @return the way found or empty way
     */
    Way wayToNodeWithParam(Graph graph, Node begin, Node end, ArrayList<NodeType> nodeTypes, ArrayList<EdgeType> edgeTypes) throws SearchException;

    /**
     * Search the way from the node begin to the node end not matching any parameters
     *
     * @param graph     graph of the search
     * @param begin     start node
     * @param end       objective node
     * @param nodeTypes parameters for the nodes
     * @param edgeTypes parameters for the edges
     * @return the way found or empty way
     */
    Way wayToNodeWithoutParam(Graph graph, Node begin, Node end, ArrayList<NodeType> nodeTypes, ArrayList<EdgeType> edgeTypes) throws SearchException;
}
