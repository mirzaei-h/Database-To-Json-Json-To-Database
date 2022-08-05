package Jsonproject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class DataBaseToJson {

    public static ResultSet RetrieveData() throws Exception {
        
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        //Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost:3306/world";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "password");
        System.out.println("Connection established......");

        //Creating the Statement
        Statement stmt = con.createStatement();

        //Retrieving the records
        ResultSet rs = stmt.executeQuery("Select * from city");
        return rs;
    }

    public static void main(String args[]) throws Exception {
        //Creating a JSONObject object
        
        JSONObject jsonObject = new JSONObject();
        //Creating a json array
        JSONArray array = new JSONArray();
        ResultSet rs = RetrieveData();

        //Inserting ResutlSet data into the json object
        //Finally, add the JSON object to the array created in the previous step.
        while(rs.next()) {
            JSONObject record = new JSONObject();
            //Inserting key-value pairs into the json object
            record.put("id", rs.getInt("id"));
            record.put("name", rs.getString("name"));
            record.put("countryCode", rs.getString("countryCode"));
            record.put("population", rs.getInt("population"));
            array.add(record);
        }
        jsonObject.put("Cities ", array);
        try {
            FileWriter file = new FileWriter("E:/output1.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created......");
    }
}
