package model.exceptions;

public class ObjectClassificationException extends MapMakerException {
    private static final String exceptionMsg = "Object type couldn't be interpreted";

    public ObjectClassificationException() {
        super(exceptionMsg);
    }

    // public ObjectClassificationException(String msg) {
    //     super(msg);
    // }
}