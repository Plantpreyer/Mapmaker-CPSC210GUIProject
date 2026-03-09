package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.exceptions.InvalidInputException;
import model.feature.Building;
import model.feature.MapPoint;
import model.feature.Route;
import model.feature.TreeFeature;

// import model.feature.MapObject;


import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestCustomMap {
    CustomMap m1;
    Route r1;
    Route r2;
    Building b1;
    TreeFeature t1;
    List<MapPoint> pointsList;

    @BeforeEach
    void runBefore() {
        m1 = new CustomMap("Test map");
        r1 = new Route("Route 1");

        pointsList = new ArrayList<>();
        pointsList.add(new MapPoint("p1", 1, 1));
        pointsList.add(new MapPoint("p2", 3, 2));
        pointsList.add(new MapPoint("p3", 5, 7));
        r2 = new Route("Route 2", pointsList);
        m1.addRoute(r1);
        m1.addRoute(r2);
        b1 = new Building("Buildinisg");
        t1 = new TreeFeature("Treep");
        m1.addObject(b1);
        m1.addObject(t1);
    }

    @Test
    void testFind() {
        try {
            assertEquals(r1, m1.findRoute("route 1"));
            assertEquals(r2, m1.findRoute("route 2"));

            assertEquals(b1, m1.findObject("buildinisg"));
            assertEquals(t1, m1.findObject("treep"));
        } catch (InvalidInputException e) {
            fail();
        }

        try {
            m1.findRoute("route 1 ");
            fail();
        } catch (InvalidInputException e) {
            // expected
        }

        try {
            m1.findObject("tre ep");
            fail();
        } catch (InvalidInputException e) {
            // expected
        }
    }

    @Test
    void testFindIndex() {
        try {
            m1.findRouteIndex("route 1");
        } catch (InvalidInputException e) {
            fail();
        }

        try {
            m1.findRouteIndex("route 3");
            fail();
        } catch (InvalidInputException e) {
            // expected
        }
    }

    @Test
    void testDelete() {
        m1.deleteObject(b1);
        assertEquals(t1, m1.getObject(0));

        runBefore();

        m1.deleteObject(t1);
        assertEquals(b1, m1.getObject(0));
        assertEquals(1, m1.getObjects().size());

        runBefore();

        m1.deleteRoute(r1);
        assertEquals(r2, m1.getRoutes().get(0));

        runBefore();

        m1.deleteRoute(r2);
        assertEquals(r1, m1.getRoutes().get(0));
        assertEquals(1, m1.getRoutes().size());
    }

    @Test
    void testCons() {
        assertEquals("Test map", m1.getName());

        assertEquals(r1, m1.getRoutes().get(0));
        assertEquals(r2, m1.getRoutes().get(1));

        assertEquals(b1, m1.getObject(0));
        assertEquals(t1, m1.getObjects().get(1));
    }

    @Test
    void testMapInfo() {
        List<String> l1 = m1.mapInfo();
        List<String> l2 = new ArrayList<>();
        l2.add("Objects: 2");
        for (String b : m1.objectsInfo()) {
            l2.add(b);
        }

        l2.add("Routes: 2");
        for (String b : m1.routesInfo()) {
            l2.add(b);
        }

        assertEquals(l2, l1);
    }

    @Test
    void testInfos() {
        List<String> l1 = m1.objectsInfo();
        List<String> l2 = new ArrayList<>();
        l2.add("1: \'Buildinisg\', Type: building");
        for (String b : b1.getInfo()) {
            l2.add("\t" + b);
        }

        l2.add("2: \'Treep\', Type: tree");
        for (String b : t1.getInfo()) {
            l2.add("\t" + b);
        }

        assertEquals(l2, l1);

        List<String> l3 = m1.routesInfo();
        List<String> l4 = new ArrayList<>();
        l4.add("1: \'Route 1\'");
        for (String b : r1.getInfo()) {
            l4.add("\t" + b);
        }

        l4.add("2: \'Route 2\'");
        for (String b : r2.getInfo()) {
            l4.add("\t" + b);
        }

        assertEquals(l4, l3);
    }

    @Test
    void testEditRoute() {
        Route r3 = new Route("Route 333");

        try {
            m1.editRoute(r1, r3);
        } catch (InvalidInputException e) {
            fail();
        }

        List<String> l1 = m1.routesInfo();
        List<String> l2 = new ArrayList<>();
        l2.add("1: \'Route 333\'");
        for (String b : r3.getInfo()) {
            l2.add("\t" + b);
        }

        l2.add("2: \'Route 2\'");
        for (String b : r2.getInfo()) {
            l2.add("\t" + b);
        }

        assertEquals(l2, l1);

    }
}
