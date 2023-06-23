
public class Main {
    public static void main(String[] args) {
        TxtParser txtParser = new TxtParser();
        txtParser.connect();
        String filepath = ("src/main/resources/input.txt");
        txtParser.parseTextFile(filepath);
    }
}
