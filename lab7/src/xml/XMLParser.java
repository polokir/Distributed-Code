package xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import models.AirCompany;
import models.Airport;
import models.Flight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser {
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

    public static Airport parse(String path) throws ParserConfigurationException, SAXException, IOException {
        SchemaFactory sf =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema s = sf.newSchema(new File("src/xml/input.xsd"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setSchema(s);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new SimpleErrorHandler());
        Document doc = builder.parse(new File(path));
        doc.getDocumentElement().normalize();

        Airport airport = new Airport();
        NodeList nodes = doc.getElementsByTagName("AirCompany");
        for(int i = 0; i < nodes.getLength(); ++i) {
            Element n = (Element)nodes.item(i);
            AirCompany company = new AirCompany();
            company.setId(Long.parseLong(n.getAttribute("id")));
            company.setName(n.getAttribute("name"));
            airport.addAirCompany(company);

        }

        nodes = doc.getElementsByTagName("Flight");
        for(int j =0; j < nodes.getLength(); ++j) {
            Element e = (Element) nodes.item(j);
            Flight flight = new Flight();
            flight.setId(Long.parseLong(e.getAttribute("id")));
            flight.setCompanyID(Long.parseLong(e.getAttribute("companyID")));
            flight.setDeparture(e.getAttribute("departure"));
            flight.setDestination(e.getAttribute("destination"));
            flight.setPassangers(Integer.parseInt(e.getAttribute("passengers")));
            airport.addFlight(flight);
        }

        return airport;
    }

    public static void write(Airport airport, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("Airport");
        doc.appendChild(root);

        List<AirCompany> companies = airport.getCompanies();
        for(AirCompany company :  companies) {
            Element cmp = doc.createElement("AirCompany");
            cmp.setAttribute("id", Long.toString(company.getId()));
            cmp.setAttribute("name", company.getName());
            root.appendChild(cmp);

            for(Flight flight: company.getFlights()) {
                Element f = doc.createElement("Flight");
                f.setAttribute("id", Long.toString(flight.getId()));
                f.setAttribute("companyID", Long.toString(flight.getCompanyID()));
                f.setAttribute("departure", flight.getDeparture());
                f.setAttribute("destination", flight.getDestination());
                f.setAttribute("passengers", Integer.toString(flight.getPassangers()));
                cmp.appendChild(f);
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transformer = tfactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Airport airport = parse("src/xml/input.xml");
        write(airport,"src/xml/output.xml");
    }
}