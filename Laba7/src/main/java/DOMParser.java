import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import models.*;

public class DOMParser {
    public static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("Line " +e.getLineNumber() + ":");
            System.out.println(e.getMessage());
        }
    }

    public static Dealer parse(String path) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new SimpleErrorHandler());
        Document doc = builder.parse(new File(path));
        doc.getDocumentElement().normalize();

        Dealer dealer = new Dealer();
        NodeList nodes = doc.getElementsByTagName("Manufacturers");

        for(int i = 0; i < nodes.getLength(); ++i) {
            Element n = (Element)nodes.item(i);
            Manufacturers manufacturers = new Manufacturers();
            manufacturers.setManufacturersId(n.getAttribute("id"));
            manufacturers.setName(n.getAttribute("name"));
            dealer.addManufacturers(manufacturers);
        }

        nodes = doc.getElementsByTagName("Brands");
        for(int j =0; j < nodes.getLength(); ++j) {
            Element e = (Element) nodes.item(j);
            Brands brands = new Brands();
            brands.setBrandsId(e.getAttribute("id"));
            brands.setManufacturersId(e.getAttribute("manufacturersId"));
            brands.setName(e.getAttribute("name"));
            brands.setReleaseYear(Integer.parseInt(e.getAttribute("releaseYear")));
            dealer.addBrands(brands, e.getAttribute("manufacturersId"));
        }

        return dealer;
    }

    public static void write(Dealer dealer, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("Dealer");
        doc.appendChild(root);

        Map<String, Manufacturers> authors = dealer.getManufacturers();
        for(Map.Entry<String, Manufacturers> entry : authors.entrySet()) {
            Element gnr = doc.createElement("Brands");
            gnr.setAttribute("id", entry.getValue().getManufacturersId());
            gnr.setAttribute("name", entry.getValue().getName());
            root.appendChild(gnr);

            for(Brands brands : entry.getValue().getBrands()) {
                Element mv = doc.createElement("Brands");
                mv.setAttribute("id", brands.getBrandsId());
                mv.setAttribute("manufacturersId", brands.getManufacturersId());
                mv.setAttribute("name", brands.getName());
                mv.setAttribute("releaseYear", String.valueOf(brands.getReleaseYear()));
                gnr.appendChild(mv);
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dealer.dtd");
        transformer.transform(domSource, fileResult);
    }
}