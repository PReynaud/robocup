package com.polytech4a.robocup.firebot.robots;

import com.polytech4a.robocup.graph.enums.EdgeType;
import com.polytech4a.robocup.graph.enums.NodeType;
import com.polytech4a.robocup.graph.model.Graph;

import java.util.ArrayList;

/**
 * Created by Adrien CHAUSSENDE on 06/05/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 *          <p/>
 *          Class representing a cross country firebot.
 */
public class CrossCountryFirebot extends Firebot {

    public CrossCountryFirebot(Graph graph, int capacity) {
        super(graph, capacity, constructEdgeConstraints(), new ArrayList<NodeType>());
    }

    /**
     * Method to construct the array list containing the type of edges where the robot can't go by.
     *
     * @return ArrayList of Edge Type
     */
    private static ArrayList<EdgeType> constructEdgeConstraints() {
        return null;
    }

}
