package com.polytech4a.robocup.firebot.controller;

import com.polytech4a.robocup.firebot.robots.RobotManager;
import com.polytech4a.robocup.firebot.ui.GraphicViewPanel;
import com.polytech4a.robocup.firebot.ui.MainForm;
import com.polytech4a.robocup.firebot.ui.graphic.models.NodeView;
import com.polytech4a.robocup.graph.enums.EdgeType;
import com.polytech4a.robocup.graph.model.Edge;
import com.polytech4a.robocup.graph.model.Graph;
import com.polytech4a.robocup.graph.model.Node;
import com.polytech4a.robocup.graph.model.exceptions.MissingParameterException;
import com.polytech4a.robocup.graph.model.exceptions.NotFoundTypeException;
import org.apache.log4j.Logger;

import java.util.Optional;

/**
 * Created by Pierre on 11/05/2015.
 */
public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class);

    private MainForm view;
    private RobotManager model;

    private FileController fileController;
    private GraphicControlController graphicControlController;
    private SimulationController simulationController;
    private MouseController mouseController;

    private EnumSelection selectionMode;
    private NodeView lastClickedNode;

    public  MainController(MainForm mainForm, RobotManager model){
        this.view = mainForm;
        this.model = model;

        this.fileController = new FileController(this);
        this.graphicControlController = new GraphicControlController(this);
        this.simulationController = new SimulationController(this);
        this.mouseController = new MouseController(this);
        this.view.getGraphicViewPanel().addMouseListener(this.mouseController);
        this.view.getGraphicViewPanel().addMouseMotionListener(this.mouseController);
        this.selectionMode = EnumSelection.NOTHING;
    }

    public MainForm getView() {
        return view;
    }
    public EnumSelection getSelectionMode() {return selectionMode;}
    public NodeView getLastClickedNode() {
        return lastClickedNode;
    }
    public RobotManager getModel() {
        return model;
    }
    public Graph getGraph() {
        return model.getGraph();
    }
    public void setGraph(Graph graph) {
        this.model.setGraph(graph);
    }
    public void setModel(RobotManager model) {
        this.model = model;
    }
    public void setLastClickedNode(NodeView lastClickedNode) {
        this.lastClickedNode = lastClickedNode;
    }
    public void setSelectionMode(EnumSelection newMode){this.selectionMode = newMode;}


    /**
     * Transform the model in objects that can be used in the view
     * Call when we have to load a graph from a file
     */
    public void transformModelGraphToView(){
        GraphicViewPanel graphicViewPanel = (GraphicViewPanel) view.getGraphicViewPanel();
        for(Node node: this.model.getGraph().getNodes()){
            try {
                graphicViewPanel.getGraph().addNode((int)node.getX(), (int)node.getY(), node.getId());
            } catch (MissingParameterException e) {
                logger.error("Error for getting the model");
            }
        }
        for(Edge edge: this.model.getGraph().getEdges()){
            Optional<NodeView> n1 = graphicViewPanel.getGraph().getNodes().stream().filter(o -> o.getId() == edge.getNode1()).findFirst();
            Optional<NodeView> n2 = graphicViewPanel.getGraph().getNodes().stream().filter(o -> o.getId() == edge.getNode2()).findFirst();
            if(n1.isPresent() && n2.isPresent()){
                try {
                    if(edge.getType() == EdgeType.PLAT){
                        graphicViewPanel.getGraph().addEdge(n1.get(), n2.get());
                    }
                    if(edge.getType() == EdgeType.ESCARPE){
                        graphicViewPanel.getGraph().addSteepEdge(n1.get(), n2.get());
                    }
                } catch (NotFoundTypeException e) {
                    logger.error("The type of the edge does not exist");
                }
            }
            else{
                logger.error("Error for getting the model");
            }
        }
    }
}

