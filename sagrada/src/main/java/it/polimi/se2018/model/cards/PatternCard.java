package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Pattern;
import it.polimi.se2018.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model representation of a window pattern card
 * @author Alì El Wahsh
 */
public class PatternCard extends CardModel {

    private Logger logger = Logger.getGlobal();
    private Pattern frontSide; /*first pattern*/
    private Pattern backSide; /*second pattern*/

    /**
     * PatternCard's constructor
     * Builds frontSide and backSide from a XML file
     * @param path file's path
     */
    public PatternCard(String path)
    {
        Pair[][] schema;
        int height;
        int width;
        int value;
        ColorModel color;
        String name;
        int favourPoints;

        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList lList = doc.getElementsByTagName("pattern");
            for (int j = 0; j < 2; j++) {
                schema  = new Pair[Pattern.HEIGHT][Pattern.WIDTH];
                Node lNode = lList.item(j);

                Element xElement = (Element) lNode;
                favourPoints = Integer.parseInt(xElement.getAttribute("difficulty"));
                name = xElement.getAttribute("name");

                NodeList nList = lNode.getChildNodes();

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        height = Integer.parseInt(eElement.getAttribute("height"));
                        width = Integer.parseInt(eElement.getAttribute("width"));
                        color = ColorModel.valueOf(eElement.getElementsByTagName("color").item(0).getTextContent());
                        value = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());

                        schema[height][width] = new Pair<>(color,value);
                    }

                    if(j == 0)
                        frontSide = new Pattern(schema.clone(),name,favourPoints);
                    else
                        backSide = new Pattern(schema.clone(),name,favourPoints);
                }
            }
            } catch(Exception e){
                logger.log(Level.SEVERE, e.toString());

        }
    }

    /**
     * Getter for the backside
     * @return the backside pattern
     */
    public Pattern getBackSide() {
        return backSide;
    }

    /**
     * Getter for the front side
     * @return the front side pattern
     */
    public Pattern getFrontSide() {
        return frontSide;
    }

    /**
     * The player choose one of the two sides as his/hers
     * own pattern for the game
     * @param front true if the front side is the chosen one, false otherwise
     * @return the chosen pattern
     */
    public Pattern chooseSide(boolean front)
    {
        if(front)
            return frontSide;
        else
            return backSide;

    }

    /**
     * Simple toString method for CLI and log
     * @return the two sides of the card in their string format
     */
    @Override
    public String toString() {
       return  frontSide.toString() + "\n" + backSide.toString();
    }
}
