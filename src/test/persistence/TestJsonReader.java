package persistence;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CustomMap;
import model.feature.Building;
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
        }
    }

    @Test
    void testEmptyList() {
        jsonReader = new JsonReader("./data/testReaderEmptyMapList.json");
        map1 = new CustomMap("goo");
        map1.addObject(null);
        try {
            List<CustomMap> mapList = jsonReader.read();
            assertTrue(mapList.isEmpty());
        } catch (IOException e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testSingleMapList() {
        jsonReader = new JsonReader("./data/testReaderSingleMapList.json");
        map1.addObject(building1);

        try {
            List<CustomMap> mapList = jsonReader.read();
            assertEquals(1, mapList.size());
            checkMap(map1, mapList.get(0));
        } catch (IOException e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    void testComplexMapList() {
        jsonReader = new JsonReader("./data/testReaderSingleMapList.json");
        map1.addObject(building1);
        map2.addObject(building2);
        map2.addObject(tree1);
        map2.addRoute(route1);
        
        try {
            List<CustomMap> mapList = jsonReader.read();
            assertEquals(2, mapList.size());
            checkMap(map1, mapList.get(0));
            checkMap(map2, mapList.get(1));
        } catch (IOException e) {
            fail("should not have thrown exception");
        }
    }
}
