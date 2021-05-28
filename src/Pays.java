/**
 *  Cette classe permet d'implémenter un objet "Pays".
 *  Ce dernier permet de stocker le nom, l'abrévation ainsi qu'une liste de polygon qui forment les frontières d'un pays.
 *
 *  Auteurs : Blanc Jean-Luc & Janssens Emmmanuel
 *  Date : 28.05.2021
 */

import java.util.ArrayList;

public class Pays {

    String abrevation;
    String nom;
    ArrayList<Polygon> polygons;

    public Pays(String abrevation, String nom){
        this.abrevation = abrevation;
        this.nom = nom;
        this.polygons = new ArrayList<>();
    }

    public String getAbrevation(){ return abrevation; }
    public String getNom(){ return nom; }
    public ArrayList<Polygon> getPolygons(){ return polygons; }
    public void addPolygon(Polygon polygon){ this.polygons.add(polygon); }

    /**
     * Cette méthode permet d'afficher les informations d'un pays
     * @return une chaine de caractères contenant les informations d'un pays
     */
    @Override
    public String toString(){
        String res = "("+abrevation+") "+ nom + "\r\n";
        for(Polygon p : polygons){
            res += "\t - " + p.getCoordinates().size() + " Coordinates\r\n";
        }
        return res;
    }
}
