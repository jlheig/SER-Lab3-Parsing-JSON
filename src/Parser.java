/**
 *  Cette classe permet d'implémenter un objet "Parser".
 *  Ce dernier permet de lire les informations contenues dans un fichier geojson.
 *
 *  Auteurs : Blanc Jean-Luc & Janssens Emmmanuel
 *  Date : 28.05.2021
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    static String filename = "countries.geojson";

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();

        /**
         * On tente d'abord de lire le fichier geojson
         * Puis on génère une liste de pays grâce aux informations contenues dans le fichier geojson
         * Finalement, on créé un fichier KML à l'aide de la liste de pays générée
         */
        try (FileReader reader = new FileReader(filename)){
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray countries = (JSONArray) jsonObject.get("features");
            ArrayList<Pays> pays = generateCountries(countries);

            KMLWriter writer = new KMLWriter();
            writer.write(pays);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Pays> generateCountries(JSONArray countries){

        ArrayList<Pays> paysListe = new ArrayList<Pays>();

        for(Object country : countries){

            //On récupère le nom et l'abrévation du pays
            Object properties = ((JSONObject) country).get("properties");
            String abrevation = (String) ((JSONObject) properties).get("ISO_A3");
            String nom = (String) ((JSONObject) properties).get("ADMIN");

            //on initialise un pays
            Pays pays = new Pays(abrevation, nom);

            //on récupère les coordonnées
            Object geometry = ((JSONObject) country).get("geometry");
            Object geoType = ((JSONObject) geometry).get("type");


            /**
             * Le code suivant est assez lourd, cela est du à 2 choses :
             * 1. Il faut souvent cast les objets que l'on récupère dans différents types, en effet ceux-ci sont des
             *  "Objects" et pour pouvoir accéder aux informations à l'intérieur il faut les casts en JSONObject ou JSONArray
             *
             * 2. Les coordonnées sont imbriquées dans une multitude de chainons, il faut donc tout "déméler" afin d'accéder
             *   à une information précise
             */
            //POUR UN POLYGON SIMPLE
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
            }// POUR UN MULTI-POLYGONE
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
            System.out.println(pays);
            paysListe.add(pays);
        }
        return paysListe;
    }

}
