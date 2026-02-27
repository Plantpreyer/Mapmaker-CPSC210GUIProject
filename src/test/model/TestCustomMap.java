package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.feature.MapObject;

public class TestCustomMap {
    CustomMap m1;
    
    @BeforeEach
    void runBefore() {
        m1 = new CustomMap("Test map");
    }

    @Test
    void testAddObject() throws Exception {
        assertTrue(m1.objects.isEmpty());
        m1.addObject("1", 0, 0);
        assertEquals(m1.objects.size(), 1);
        MapObject b = m1.getObject(0);
        assertEquals(b.getName(), "1");
        assertEquals(b.getXpos(), b.getYpos());
        assertEquals(b.getXpos(), 0);
    }

    @Test
    void testSelectFeature() throws Exception {
        m1.addObject("1", 0, 0);
        assertEquals(m1.objects.size(), 1);
        MapObject b = m1.getObject(0);
        m1.selectFeature(b);
        assertEquals(b, m1.getSelectedFeature());
    }
}
