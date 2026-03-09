package persistence;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CustomMap;
import model.exceptions.ObjectClassificationException;
import model.feature.Building;
import model.feature.MapPoint;
import model.feature.Route;
import model.feature.TreeFeature;

// modelled from sample project

@ExcludeFromJacocoGeneratedReport
public class TestJsonReader extends JsonTest {
    JsonReader jsonReader;
    CustomMap map1;
    CustomMap map2;
    Building building1;
    Building building2;
    Route route1;
    TreeFeature tree1;

    @BeforeEach
    void runBefore() {
        map1 = new CustomMap("goo");
        map2 = new CustomMap("map 2");
        building1 = new Building("buildingoo", 3, 2, 6, 9, 10);
        building2 = new Building("b2", 1, 1, 1, 1, 1);
        route1 = new Route("router");
        tree1 = new TreeFeature("Groot", 0, 0, 4, 4);
    }

    @Test
    void testNoSuchFile() {
        jsonReader = new JsonReader("./data/thisdoesntexist.json");
        try {
            jsonReader.read();
            fail("should have thrown exception");
        } catch (IOException e) {
            // expected
        } catch (Exception e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testEmptyList() {
        jsonReader = new JsonReader("./data/test/ReaderEmptyMapList.json");
        try {
            List<CustomMap> mapList = jsonReader.read();
            assertTrue(mapList.isEmpty());
        } catch (Exception e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testSingleMapList() {
        jsonReader = new JsonReader("./data/test/ReaderSingleMapList.json");
        map1.addObject(building1);

        try {
            List<CustomMap> mapList = jsonReader.read();
            assertEquals(1, mapList.size());
            checkMap(map1, mapList.get(0));
        } catch (Exception e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testComplexMapList() {
        jsonReader = new JsonReader("./data/test/ReaderComplexMapList.json");
        map1.addObject(building1);
        map2.addObject(building2);
        map2.addObject(tree1);

        List<MapPoint> pointsList = new ArrayList<>();
        pointsList.add(new MapPoint("p1", 1, 1));
        pointsList.add(new MapPoint("p2", 3, 2));
        pointsList.add(new MapPoint("p3", 5, 7));

        route1.setPoints(pointsList);

        map2.addRoute(route1);
        
        try {
            List<CustomMap> mapList = jsonReader.read();
            assertEquals(2, mapList.size());
            checkMap(map1, mapList.get(0));
            checkMap(map2, mapList.get(1));
        } catch (Exception e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testBuggyMap() {
        jsonReader = new JsonReader("./data/test/ReaderBuggyMapList.json");

        try {
            jsonReader.read();
            fail();
        } catch (IOException e) {
            fail("should not have thrown exception");
        } catch (ObjectClassificationException e) {
            // expected;
        }
    }
}
