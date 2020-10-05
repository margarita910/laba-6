package laba.com.company;

/**
 * Exception occurring when reading a Script File.
 */

public class InvalidScriptException extends Exception {
    public InvalidScriptException(String message){
        super(message);
    }

    public InvalidScriptException(){
        super();
    }
}