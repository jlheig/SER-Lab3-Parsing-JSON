import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("countries.geojson")){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray countries = (JSONArray) jsonObject.get("features");

            //System.out.println(countries);



            ArrayList<Pays> pays = generateCountries(countries);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Pays> generateCountries(JSONArray countries){

        int size = countries.size();
        ArrayList<Pays> pays = new ArrayList<Pays>();

        for(Object country : countries){

            //get the name and abrevation of the countries from the JSON
            Object properties = ((JSONObject) country).get("properties");
            String abrevation = (String) ((JSONObject) properties).get("ISO_A3");
            String nom = (String) ((JSONObject) properties).get("ADMIN");

            //get the coordinates from the JSON
            Object geometry = ((JSONObject) country).get("geometry");
            Object coordinates = ((JSONObject) geometry).get("coordinates");
            System.out.println(coordinates);
        }

        return pays;
    }

}
