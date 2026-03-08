package model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.MapPoint;
import model.feature.Route;

public class TestRoute {
    Route r1;
    List<MapPoint> pointsList;

    @BeforeEach
    void runBefore() {
        r1 = new Route("1");
        pointsList = new ArrayList<>();
        pointsList.add(new MapPoint("p1", 1, 1));
        pointsList.add(new MapPoint("p2", 3, 2));
        pointsList.add(new MapPoint("p3", 5, 7));
    }

    @Test
    void testCons() {
        r1 = new Route();
        assertEquals("", r1.getName());
        assertEquals(0, r1.getXpos());
        assertEquals(0, r1.getYpos());

        r1 = new Route("1", pointsList);
        assertEquals("1", r1.getName());
        assertEquals(pointsList, r1.getPoints());
    }

    @Test
    void testGetInfo() {
        List<String> l1 = r1.getInfo();
        List<String> l2 = new ArrayList<>();

        assertEquals(l2, l1);

        r1 = new Route("1", pointsList);

        l2.add("Point 1: \'p1\'");
        l2.add("\tPosition: 1, 1");

        l2.add("Point 2: \'p2\'");
        l2.add("\tPosition: 3, 2");

        l2.add("Point 3: \'p3\'");
        l2.add("\tPosition: 5, 7");

        l1 = r1.getInfo();

        assertEquals(l2, l1);
    }

    @Test
    void testSetPoints() {
        r1.setPoints(pointsList);
        assertEquals(pointsList, r1.getPoints());
    }
}