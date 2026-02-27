package model.exceptions;

public class InvalidInputException extends MapMakerException {
    public InvalidInputException() {
        super("Input couldn't be interpreted.");
    }

    public InvalidInputException(String msg) {
        super(msg);
    }
}