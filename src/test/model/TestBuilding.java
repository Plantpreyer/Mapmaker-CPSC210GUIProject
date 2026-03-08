package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.Building;

public class TestBuilding {
    Building b1;

    @BeforeEach
    void runBefore() {
        b1 = new Building("1", 0, 0, 0);
    }

    @Test
    void testCons() {
        b1 = new Building();
        assertEquals("" , b1.getName());
        assertEquals(0 , b1.getXpos());
        assertEquals(0 , b1.getYpos());
        assertEquals(0 , b1.getHeight());

        b1 = new Building("ggggg");
        assertEquals("ggggg" , b1.getName());
        assertEquals(0 , b1.getXpos());
        assertEquals(0 , b1.getYpos());
        assertEquals(0 , b1.getHeight());

        b1 = new Building("1", 1, 1, 1);
        assertEquals("1" , b1.getName());
        assertEquals(1 , b1.getXpos());
        assertEquals(1 , b1.getYpos());
        assertEquals(1 , b1.getHeight());

        b1 = new Building("1", 5, 5, 5, 5, 5);
        assertEquals("1" , b1.getName());
        assertEquals(5 , b1.getXpos());
        assertEquals(5 , b1.getYpos());
        assertEquals(5 , b1.getXdim());
        assertEquals(5 , b1.getYdim());
        assertEquals(5 , b1.getHeight());
    }

    @Test
    void testGetInfo() {
        List<String> l1 = b1.getInfo();
        List<String> l2 = b1.getPosList();

        l2.add("height: 0");
        l2.add("width: 0 length: 0");

        assertEquals(l2, l1);
    }

    @Test
    void testGetType() {
        assertEquals("building", b1.getType());
    }
}