package ru.ncedu.java.tasks;

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SimpleXMLImpl implements SimpleXML {

    @Override
    public String createXML(String tagName, String textNode) {
        try {
            DocumentBuilder documentBuilder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
        
            Document doc = documentBuilder.newDocument();
            Element root = doc.createElement(tagName);
            root.appendChild(doc.createTextNode(textNode));
            doc.appendChild(root);
        
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(doc),
                    new StreamResult(stringWriter));

            return stringWriter.toString();
        }
        catch (Exception exception) {
            return null;
        }
    }
    
    private class RootElementHandler extends DefaultHandler {
        private String rootName;
        
        @Override
        public void startElement(String uri, String localName,
                String qName, Attributes attributes) throws SAXException{
            if (rootName == null) {
                rootName = qName;
            }
        }
        
        public String getRoot() {
          return rootName;
        }
    }

    @Override
    public String parseRootElement(InputStream xmlStream) throws SAXException {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setValidating(true);
            parserFactory.setNamespaceAware(false);
            
            SAXParser parser = parserFactory.newSAXParser();
            RootElementHandler handler = new RootElementHandler();
            parser.parse(xmlStream, handler);
            
            return handler.getRoot();
        } catch (Exception exception) {
            throw new SAXException();
        }
    }
}
