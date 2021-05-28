/**
 *  Cette classe permet d'implémenter un objet "Coordinate".
 *  Ce dernier permet de stocker les coordonnées X et Y d'un point d'un polygone
 *
 *  Auteurs : Blanc Jean-Luc & Janssens Emmmanuel
 *  Date : 28.05.2021
 */

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
