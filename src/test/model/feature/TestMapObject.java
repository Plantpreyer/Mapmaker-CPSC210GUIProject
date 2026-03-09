package model.feature;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestMapObject {
    MapObject mo1;
    MapObject mo11;
    MapObject mo2;

    @BeforeEach
    void runBefore() {
        mo1 = new Building("1", 0, 0, 2, 3, 1);
        mo11 = new Building("1", 0, 0, 4, 5, 1);
        mo2 = new TreeFeature("2", 0, 0, 1, 2);
    }

    @Test
    public void testGetPosList() {
        List<String> l1 = mo1.getPosList();
        List<String> l2 = new ArrayList<>();
        l2.add("Position: 0, 0");

        assertEquals(l2, l1);
    }

    @Test
    public void testGetSet() {
        assertEquals(1, mo1.getHeight());
        assertEquals(2, mo2.getHeight());

        assertEquals(2, mo1.getXdim());
        assertEquals(3, mo1.getYdim());

        assertEquals(4, mo11.getXdim());
        assertEquals(5, mo11.getYdim());

        mo1.setHeight(5);
        mo1.setXdim(4);
        mo1.setYdim(4);

        assertEquals(5, mo1.getHeight());

        assertEquals(4, mo1.getXdim());
        assertEquals(4, mo1.getYdim());
    }

}
