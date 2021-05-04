import java.util.ArrayList;

public class Pays {

    String abrevation;
    String nom;
    ArrayList<ArrayList<String>> coordinates;

    public Pays(String abrevation, String nom, ArrayList<ArrayList<String>> coordinates){
        this.abrevation = abrevation;
        this.nom = nom;
        this.coordinates = coordinates;
    }

    public String getAbrevation(){ return abrevation; }
    public String getNom(){ return nom; }
    public ArrayList<ArrayList<String>> getCoordinates(){ return coordinates; }
}
