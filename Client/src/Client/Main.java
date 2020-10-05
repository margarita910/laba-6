package Client;

public class Main {
    final static int PORT = 9945;
    final static String ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        new Client().startClient(ADDRESS, PORT);
    }
}
