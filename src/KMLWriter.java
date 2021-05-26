import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.sound.sampled.Line;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
public class KMLWriter {
    String xmlVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    String kmlHeader = "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n";

    public void write(List<Pays> pays)
    {
        try{
            Element kml = new Element("kml");
            Document root = new Document(kml);

            Element document = new Element("Document");

            Element name = new Element("name");
            name.addContent("countries.kml");
            document.addContent(name);

            Element style = new Element("Style");
            style.setAttribute("id","white-2px");
            Element PolyStyle = new Element("PolyStyle");
            Element polyColor = new Element("color");
            polyColor.addContent("40e87649");
            PolyStyle.addContent(polyColor);
            Element LineStyle = new Element("LineStyle");
            Element color = new Element("color");
            color.addContent("ffffffff");
            Element width = new Element("width");
            width.addContent("2");

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
                styleUrl.addContent("#white-2px");
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

            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat());
            StringWriter str = new StringWriter();
            out.output(root,str);


            //write to file
            File file = new File("countries.kml");
            FileWriter writer = new FileWriter(file,false);
            writer.write(str.toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
