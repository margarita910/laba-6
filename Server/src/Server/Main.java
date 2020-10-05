package Server;

import laba.com.company.InvalidScriptException;

import java.io.IOException;

public class Main {
    final static int PORT = 9945;
    final static String path = "file.json";

    public static void main(String[] args) throws IOException, InvalidScriptException {
        new Server().startServer(path, PORT);
    }
}

