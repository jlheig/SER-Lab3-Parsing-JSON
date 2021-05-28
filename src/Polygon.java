/**
 *  Cette classe permet d'implémenter un objet "Polygon".
 *  Ce dernier permet de stocker une liste de coordonnées permettant de construire un polygon
 *
 *  Auteurs : Blanc Jean-Luc & Janssens Emmmanuel
 *  Date : 28.05.2021
 */

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Coordinate> coordinates;

    public Polygon(){
        this.coordinates = new ArrayList<Coordinate>();
    }

    public Polygon(ArrayList<Coordinate> coordinates){
        this.coordinates = coordinates;
    }

    public void addCoordinate(Coordinate coordinate){
        this.coordinates.add(coordinate);
    }

    public ArrayList<Coordinate> getCoordinates(){
        return coordinates;
    }
}
