import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileWriter;
import java.io.IOException;

public class DrawIOParser {
    private static final String rounded0 = "rounded=0";
    private static final String rounded1 = "rounded=1";
    private static final String edgeStyle = "edgeStyle";
    private static final String rhombus = "rhombus";

    public static void main(String[] args) throws DocumentException {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read("src/main/resources/t1.xml");

            Element root = document.getRootElement();

            Element diagramElement = root.element("diagram");
            if (diagramElement == null) {
                System.out.println("Invalid draw.io file. 'diagram' element not found.");
                return;
            }

            Element mxGraphModelElement = diagramElement.element("mxGraphModel");
            if (mxGraphModelElement == null) {
                System.out.println("Invalid draw.io file. 'mxGraphModel' element not found.");
                return;
            }

            Element rootElement = mxGraphModelElement.element("root");
            if (rootElement == null) {
                System.out.println("Invalid draw.io file. 'root' element not found.");
                return;
            }

            FileWriter writer = new FileWriter("src/main/resources/output.txt");

            for (Object shapeObj : rootElement.elements("mxCell")) {
                Element shapeElement = (Element) shapeObj;

                String shapeId = shapeElement.attributeValue("id");
                String shapeValue = shapeElement.attributeValue("value");
                String style = shapeElement.attributeValue("style");

                writer.write("Shape ID: " + shapeId + "\n");
                writer.write("Shape Value: " + shapeValue + "\n");

                if (style != null) {
                    String nString = null;

                    if (style.contains(rounded0)) {
                        nString = "Arrow down";
                    } else if (style.contains(rounded1)) {
                        nString = "Bloc";
                    } else if (style.contains(edgeStyle)) {
                        nString = "Inscription";
                    } else if (style.contains(rhombus)) {
                        nString = "Rhomb";
                    } else {
                        nString = "           ";
                    }

                    if (nString != null) {
                        writer.write("Shape Style: " + nString + "\n");
                    }
                }
            }
            writer.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
