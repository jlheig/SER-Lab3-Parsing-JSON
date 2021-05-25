import java.awt.*;
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
}
