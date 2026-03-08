package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.TreeFeature;

public class TestTree {
    TreeFeature t1;

    @BeforeEach
    void runBefore() {
        t1 = new TreeFeature("1", 0, 0, 1, 1);
    }

    @Test
    void testCons() {
        t1 = new TreeFeature();
        assertEquals("", t1.getName());
        assertEquals(0, t1.getXpos());
        assertEquals(0, t1.getYpos());
        assertEquals(0, t1.getHeight());

        t1 = new TreeFeature("foop");
        assertEquals("foop", t1.getName());
        assertEquals(0, t1.getXpos());
        assertEquals(0, t1.getYpos());
        assertEquals(0, t1.getHeight());

        t1 = new TreeFeature("1", 1, 1, 2, 4);
        assertEquals("1", t1.getName());
        assertEquals(1, t1.getXpos());
        assertEquals(1, t1.getYpos());
        assertEquals(2, t1.getRadius());
        assertEquals(4, t1.getHeight());
    }

    @Test
    void testGetInfo() {
        List<String> l1 = t1.getInfo();
        List<String> l2 = t1.getPosList();

        l2.add("Height: 1");
        l2.add("Radius: 1");

        assertEquals(l2, l1);
    }

    @Test
    void testGetType() {
        assertEquals("tree", t1.getType());
    }

    @Test
    void testSetBody() {
        t1.setBody(5, 6);

        assertEquals(5, t1.getRadius());
        assertEquals(6, t1.getHeight());
    }
}