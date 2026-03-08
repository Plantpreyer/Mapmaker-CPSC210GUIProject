package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.MapPoint;

public class TestMapPoint {
    MapPoint mp1;

    @BeforeEach
    void runBefore() {
        mp1 = new MapPoint("1", 0, 0);
    }

    @Test
    void testCons() {
        mp1 = new MapPoint();
        assertEquals("", mp1.getName());
        assertEquals(0, mp1.getXpos());
        assertEquals(0, mp1.getYpos());

        mp1 = new MapPoint("1", 4, 6);
        assertEquals("1", mp1.getName());
        assertEquals(4, mp1.getXpos());
        assertEquals(6, mp1.getYpos());
    }

    @Test
    void testGetInfo() {
        List<String> l1 = mp1.getInfo();
        List<String> l2 = mp1.getPosList();

        assertEquals(l2, l1);

        mp1 = new MapPoint("1", 4, 6);

        l1 = mp1.getInfo();
        l2 = mp1.getPosList();

        assertEquals(l2, l1);
    }
}
