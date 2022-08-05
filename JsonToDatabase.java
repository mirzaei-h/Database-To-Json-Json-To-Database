import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class JsonToDatabase {

    public static Connection ConnectToDB() throws Exception {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost:3306/world";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "hajar122333");
        System.out.println("Connection established......");
        return con;
    }
    public static void main(String args[]) {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/city.json"));
            //Retrieving the array
            JSONArray jsonArray = (JSONArray) jsonObject.get("city");
            Connection con = ConnectToDB();
            //Insert a row into the MyPlayers table
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO city values (?, ?, ?, ?)");
            for(Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                int id = Integer.parseInt((String) record.get("id"));
                String name = (String) record.get("name");
                String countryCode = (String) record.get("countryCode");
                int population = Integer.parseInt((String) record.get("population"));

                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, countryCode);
                pstmt.setInt(4, population);
                pstmt.executeUpdate();
            }
            System.out.println("Records inserted.....");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();}
    }
}

