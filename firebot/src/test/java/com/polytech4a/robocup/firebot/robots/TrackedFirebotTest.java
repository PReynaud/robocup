package com.polytech4a.robocup.firebot.robots;

import com.polytech4a.robocup.graph.model.search.AStar;
import com.polytech4a.robocup.graph.utils.MalformGraphException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Adrien CHAUSSENDE on 11/05/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class TrackedFirebotTest extends FirebotTest {

    @Override
    public void setUp() throws ParserConfigurationException, MalformGraphException, SAXException, IOException {
        super.setUp();
        setFirebot(new TrackedFirebot(1, getGraph(), 100, new AStar()));
    }

    @Override
    public void testComputeTime() {
        assertEquals(((long)100 * 1000 / 100), getFirebot().computeTime());
    }

    @Override
    public void testComputeDistance() {
        getFirebot().setCurrentNode(getGraph().getNode(5));
        assertEquals(13.99, getFirebot().computeDistance(getGraph().getNode(6)), 0.001);
    }

    @Override
    public void testRun() {
        super.testRun();
        getFirebot().setCurrentNode(getCurrentNode());
        getFirebot().setDestinationNode(getDestinationNode());
        getFirebot().computeDistance(getDestinationNode());
        //Test movement
        new Timer("Schedule shutdown").schedule(new TimerTask() {
            @Override
            public void run() {
                getFirebot().setShutdown(true);
            }
        }, 5000);
        getFirebot().run();
        assertEquals(getGraph().getNode(2), getFirebot().getCurrentNode());
    }
}
