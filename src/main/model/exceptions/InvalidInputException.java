package model.exceptions;

public class InvalidInputException extends MapMakerException {
    private static final String exceptionMsg = "Input couldn't be interpreted.";

    public InvalidInputException() {
        super(exceptionMsg);
    }

    public InvalidInputException(String msg) {
        super(msg);
    }
}