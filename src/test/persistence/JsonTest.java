package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CustomMap;
import model.feature.Building;
import model.feature.MapObject;
import model.feature.Route;
import model.feature.TreeFeature;

// modelled from sample project

@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    protected void checkMap(CustomMap expected, CustomMap actual) {
        assertEquals(expected.getName(), actual.getName());
        
        checkObjects(expected, actual);
        checkRoutes(expected, actual);
    }

    protected void checkObjects(CustomMap expected, CustomMap actual) {
        List<MapObject> objectsExpected = expected.getObjects();
        List<MapObject> objectsActual = actual.getObjects();

        for(int i = 0; i < objectsExpected.size(); i++) {
            MapObject moExpected = objectsExpected.get(i);
            MapObject moActual = objectsActual.get(i);

            assertEquals(moExpected.getName(), moActual.getName());
            assertEquals(moExpected.getXpos(), moActual.getXpos());
            assertEquals(moExpected.getYpos(), moActual.getYpos());
            assertEquals(moExpected.getHeight(), moActual.getHeight());

            switch (moExpected.getType()) {
                case "building":
                    checkBuilding((Building) moExpected, (Building) moActual);
                    break;
                case "tree":
                    checkTree((TreeFeature) moExpected, (TreeFeature) moActual);
                    break;
                default:
                    fail("Does not have valid type");
            }
        }
    }

    protected void checkBuilding(Building expected, Building actual) {
        assertEquals(expected.getXdim(), actual.getXdim());
        assertEquals(expected.getYdim(), actual.getYdim());
    }

    protected void checkTree(TreeFeature expected, TreeFeature actual) {
        assertEquals(expected.getRadius(), actual.getRadius());
    }

    protected void checkRoutes(CustomMap expected, CustomMap actual) {
        List<Route> routesExpected = expected.getRoutes();
        List<Route> routesActual = actual.getRoutes();

        for(int i = 0; i < routesExpected.size(); i++) {
            Route routeExpected = routesExpected.get(i);
            Route routeActual = routesActual.get(i);

            checkRoute(routeExpected, routeActual);
        }
    }

    protected void checkRoute(Route expected, Route actual) {
        assertEquals(expected.getInfo(), actual.getInfo());

        checkInfo(expected.getInfo(), actual.getInfo());
    }

    protected void checkInfo(List<String> expected, List<String> actual) {
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}