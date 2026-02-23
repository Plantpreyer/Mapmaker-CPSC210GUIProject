package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.Building;
import model.feature.FeatureSection;

public class TestBuilding {
    Building b1;
    
    @BeforeEach
    void runBefore() {
       b1 = new Building("1", 0, 0);
    }

    @Test
    void testGetInfo() {
        List<String> l1 = b1.getInfo();
        assertEquals(l1.get(0), "name: 1");
        assertEquals(l1.get(0), "max height: none");
        assertEquals(l1.get(0), "coords: 0, 0");
        b1.addSection(0, 0, 5, 20);
        assertEquals(l1.get(0), "name: 1");
        assertEquals(l1.get(0), "max height: 20");
        assertEquals(l1.get(0), "coords: 0, 0");
    }

    @Test
    void testAddSection() {
        assertTrue(b1.getBody().isEmpty());
        b1.addSection(0, 0, 1, 1, 5);
        b1.addSection(1, 1, 2, 7);
        assertEquals(b1.getBody().size(), 2);
        FeatureSection s1 = b1.getSection(0);

        assertEquals(s1.getHeight(), 5);
        assertEquals(s1.getHeight(), 7);
    }

    @Test
    void testDeleteSection() {
        b1.addSection(0, 0, 1, 1, 5);
        b1.addSection(1, 1, 2, 7);
        FeatureSection s1 = b1.getSection(0);

        b1.deleteSection(1);
        assertEquals(b1.getBody().size(), 1);
        assertEquals(b1.getSection(0), s1);

        b1.deleteSection(0);
        assertEquals(b1.getBody().size(), 0);
    }
}
