package model;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.exceptions.InvalidInputException;
import model.exceptions.ObjectClassificationException;

@ExcludeFromJacocoGeneratedReport
public class TestExceptions {
    InvalidInputException e1;
    ObjectClassificationException e2;

    @BeforeEach
    void runBefore() {
        e1 = new InvalidInputException();
        e2 = new ObjectClassificationException();
    }

    @Test
    void testCons() throws InvalidInputException {
        try {
            throw e1;
        } catch (InvalidInputException e) {
            // expected
        } catch (Exception e) {
            fail();
        }

        try {
            throw e2;
        } catch (ObjectClassificationException e) {
            // expected
        } catch (Exception e) {
            fail();
        }
    }
    
}
