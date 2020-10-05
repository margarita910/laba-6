package laba.com.company;

/**
 * The exception that occurs when reading a file.json.
 */

public class JSONParserException extends RuntimeException{
    public JSONParserException(String message){
        super(message);
    }

    public JSONParserException(){
        super();
    }
}
