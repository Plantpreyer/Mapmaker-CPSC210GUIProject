package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.Feature;
import model.feature.Route;
import model.feature.TreeFeature;


public class TestFeature {
    Feature tree;
    Feature route;

    @BeforeEach
    void runBefore() {
        tree = new TreeFeature("TREE", 6, 2, 5, 2);
        route = new Route("ROUTE");
    }

    @Test
    public void testSetters() {
        tree.setName("chargers");
        assertEquals("chargers", tree.getName());
        route.setName("cappo");
        assertEquals("cappo", route.getName());

        tree.setXpos(2);
        assertEquals(2, tree.getXpos());
        tree.setYpos(1);
         assertEquals(1, tree.getYpos());

        tree.setRadius(3);
        assertEquals(3, tree.getRadius());
        tree.setRadius(4);
        assertEquals(4, tree.getRadius());
    }
}
