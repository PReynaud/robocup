package com.polytech4a.robocup.firebot;

/**
 * Created by Adrien CHAUSSENDE on 11/05/2015.
 *
 * @author Adrien CHAUSSENDE
 * @version 1.0
 */
public class TrackedFirebotTest extends FirebotTest {

    @Override
    public void setUp() {
        super.setUp();
        setFirebot(new TrackedFirebot(getGraph(), getCurrentNode(), getDestinationNode(), 150));
    }

    @Override
    public void testComputeTime() {

    }

}
