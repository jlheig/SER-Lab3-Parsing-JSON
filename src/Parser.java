import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("countries.geojson")){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray countries = (JSONArray) jsonObject.get("features");

            //System.out.println(countries);



            ArrayList<Pays> pays = generateCountries(countries);

            KMLWriter writer = new KMLWriter();

            writer.write(pays);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Pays> generateCountries(JSONArray countries){

        int size = countries.size();
        ArrayList<Pays> paysListe = new ArrayList<Pays>();

        for(Object country : countries){

            //get the name and abrevation of the countries from the JSON
            Object properties = ((JSONObject) country).get("properties");
            String abrevation = (String) ((JSONObject) properties).get("ISO_A3");
            String nom = (String) ((JSONObject) properties).get("ADMIN");

            Pays pays = new Pays(abrevation, nom);

            //get the coordinates from the JSON
            Object geometry = ((JSONObject) country).get("geometry");
            Object geoType = ((JSONObject) geometry).get("type");

            if(geoType.equals("Polygon")){
                JSONArray geoCoordinates = (JSONArray) ((JSONObject) geometry).get("coordinates");
                for(Object coordinatesObj : geoCoordinates){
                    Polygon polygon = new Polygon();
                    JSONArray coordinates = (JSONArray) coordinatesObj;
                    for(Object coordinate : coordinates){
                        Coordinate coord = new Coordinate((Double) ((JSONArray) coordinate).get(0),(Double) ((JSONArray) coordinate).get(1));
                        polygon.addCoordinate(coord);
                    }
                    pays.addPolygon(polygon);
                }
            }
            else if(geoType.equals("MultiPolygon")){
                JSONArray geoCoordinates = (JSONArray) ((JSONObject) geometry).get("coordinates");
                for(Object coordinatesGreaterObj : geoCoordinates) {
                    JSONArray geoGreaterCoordinates = (JSONArray) coordinatesGreaterObj;
                    for (Object coordinatesObj : geoGreaterCoordinates) {
                        Polygon polygon = new Polygon();
                        JSONArray coordinates = (JSONArray) coordinatesObj;
                        for (Object coordinate : coordinates) {
                            Coordinate coord = new Coordinate((Double) ((JSONArray) coordinate).get(0), (Double) ((JSONArray) coordinate).get(1));
                            polygon.addCoordinate(coord);
                        }
                        pays.addPolygon(polygon);
                    }
                }
            }


            paysListe.add(pays);
        }
        
        return paysListe;
    }

}
