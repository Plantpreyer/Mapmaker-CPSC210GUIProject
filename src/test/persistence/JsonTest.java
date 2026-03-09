package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.CustomMap;

// modelled from sample project

@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    protected void checkMap(CustomMap expected, CustomMap actual) {
        assertEquals(expected, actual);
    }
}
