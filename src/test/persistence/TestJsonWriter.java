package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CustomMap;
import model.feature.Building;
import model.feature.MapPoint;
import model.feature.Route;
import model.feature.TreeFeature;

// modelled from sample project

@ExcludeFromJacocoGeneratedReport
public class TestJsonWriter extends JsonTest {
    JsonWriter jsonWriter;

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

    // This test was based off the sample project
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/::.\\.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyList() {
        try {
            jsonWriter = new JsonWriter("./data/test/WriterEmptyMapList.json");
            jsonWriter.open();
            jsonWriter.write(new ArrayList<>());
            jsonWriter.close();

            jsonReader = new JsonReader("./data/test/WriterEmptyMapList.json");
            List<CustomMap> mapList = jsonReader.read();
            assertTrue(mapList.isEmpty());
        } catch (Exception e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    void testSingleMapList() {
        try {
            map1.addObject(building1);
            List<CustomMap> mapList = new ArrayList<>();
            mapList.add(map1);

            jsonWriter = new JsonWriter("./data/test/WriterSingleMapList.json");
            jsonWriter.open();
            jsonWriter.write(mapList);
            jsonWriter.close();

            jsonReader = new JsonReader("./data/test/WriterSingleMapList.json");
            List<CustomMap> newMapList = jsonReader.read();

            assertEquals(mapList.size(), newMapList.size());
            for (int i = 0; i < mapList.size(); i++) {
                checkMap(mapList.get(i), newMapList.get(i));
            }
        } catch (Exception e) {
            fail("Should not have thrown exception");
        }
    }

    List<CustomMap> setUpComplex() {
        map1.addObject(building1);
        map2.addObject(building2);
        map2.addObject(tree1);

        List<MapPoint> pointsList = new ArrayList<>();
        pointsList.add(new MapPoint("p1", 1, 1));
        pointsList.add(new MapPoint("p2", 3, 2));
        pointsList.add(new MapPoint("p3", 5, 7));

        route1.setPoints(pointsList);

        map2.addRoute(route1);
        List<CustomMap> mapList = new ArrayList<>();
        mapList.add(map1);
        mapList.add(map2);

        return mapList;
    }

    @Test
    void testComplexMapList() {
        try {
            List<CustomMap> mapList = setUpComplex();

            jsonWriter = new JsonWriter("./data/test/WriterComplexMapList.json");
            jsonWriter.open();
            jsonWriter.write(mapList);
            jsonWriter.close();

            jsonReader = new JsonReader("./data/test/WriterComplexMapList.json");
            List<CustomMap> newMapList = jsonReader.read();

            assertEquals(mapList.size(), newMapList.size());
            for (int i = 0; i < mapList.size(); i++) {
                checkMap(mapList.get(i), newMapList.get(i));
            }
        } catch (Exception e) {
            fail("Should not have thrown exception");
        }
    }

}
