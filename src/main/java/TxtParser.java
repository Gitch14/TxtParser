import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TxtParser {

    private Connection connection;

    public void connect() {
        connection = org.example.ManagerDb.getInstance().getConnection();
    }

    private void insertToDb(String text, int numberOfLine) throws IOException {
        String query = "INSERT INTO txtfile (text, numberOfLine) VALUES (?, ?)";


        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, numberOfLine);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void parseTextFile(String filePath) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            int numberOfLine = 0;

            while ((line = reader.readLine()) != null) {
                insertToDb(line,numberOfLine++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
