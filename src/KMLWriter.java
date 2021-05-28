/**
 *  Cette classe permet d'implémenter un objet "KMWLWriter".
 *  Ce dernier permet de créer un fichier .kml à partir d'une liste de pays.
 *
 *  Auteurs : Blanc Jean-Luc & Janssens Emmmanuel
 *  Date : 28.05.2021
 */

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;


public class KMLWriter {

    public void write(List<Pays> pays)
    {
        try{

            //balise principale
            Element kml = new Element("kml");
            Document root = new Document(kml);

            Element document = new Element("Document");


            //<name></name>
            Element name = new Element("name");
            name.addContent("countries.kml");
            document.addContent(name);

            //<Style></Style>Définis un style commun a toute les formes
            Element style = new Element("Style");
            style.setAttribute("id","white-1px");
            //remplissage
            Element PolyStyle = new Element("PolyStyle");
            Element polyColor = new Element("color");
            polyColor.addContent("40e87649");
            PolyStyle.addContent(polyColor);

            //Bordures
            Element LineStyle = new Element("LineStyle");
            Element color = new Element("color");
            color.addContent("ffffffff");

            Element width = new Element("width");
            width.addContent("1");

            LineStyle.addContent(color);
            LineStyle.addContent(width);

            style.addContent(LineStyle);
            style.addContent(PolyStyle);

            document.addContent(style);

            for(Pays c : pays){
                Element country = new Element("Placemark");
                Element countryName = new Element("name");
                countryName.addContent(c.getNom());

                Element styleUrl = new Element("styleUrl");
                styleUrl.addContent("#white-1px");
                country.addContent(countryName);
                country.addContent(styleUrl);
                Element MultiGeometry = new Element("MultiGeometry");

                for(Polygon shapes : c.getPolygons()){
                    Element polygon = new Element("Polygon");
                    Element outerBoundary = new Element("outerBoundaryIs");
                    Element linearRing = new Element("LinearRing");
                    String strcoord = "";
                    Element coordinates = new Element("coordinates");

                    for(Coordinate coord:shapes.getCoordinates()){
                        strcoord += coord.getX()+","+coord.getY()+" ";
                    }
                    //imbriquer les elements
                    /**<Polygon>
                     *  <OuterBoundaryIs>
                     *      <LinearRing>
                     *          <coordinates>
                     *              x y
                     *          </coordinates>
                     *      </LinearRing>
                     *  </OuterBoundaryIs>
                     * </Polygon>
                     */
                    coordinates.addContent(strcoord);
                    linearRing.addContent(coordinates);
                    outerBoundary.addContent(linearRing);
                    polygon.addContent(outerBoundary);
                    MultiGeometry.addContent(polygon);
                }
                country.addContent(MultiGeometry);
                document.addContent(country);
            }
            root.getRootElement().addContent(document);


            //écriture du fichier KML dans un objet d'affichage XML
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat()); //mise en forme du fichier
            StringWriter str = new StringWriter();
            out.output(root,str);


            //write to file
            File file = new File("countries.kml");
            FileWriter writer = new FileWriter(file,false);
            writer.write(str.toString()); //écriture dans le fichier
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
