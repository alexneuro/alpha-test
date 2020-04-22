package ru.alexneuro.alfatest.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.alexneuro.alfatest.entity.Box;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class XMLParser {
    private Map<String, XmlMapper> xmlMappers = new HashMap<>();

    @Autowired
    public XMLParser(@Qualifier("boxXmlMapper") XmlMapper mapper1, @Qualifier("itemXmlMapper") XmlMapper mapper2) {
        xmlMappers.put(mapper1.getTag(), mapper1);
        xmlMappers.put(mapper2.getTag(), mapper2);
    }

    public void parse(File xmlFile) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            Element storage = document.getDocumentElement();
            if (storage.getChildNodes().getLength() > 0)
                readNode(storage.getChildNodes(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readNode(NodeList childNodes, Box parentBox) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            XmlMapper mapper = xmlMappers.get(element.getTagName());
            if (mapper == null)
                continue;

            Object entity = mapper.create(element, parentBox);

            if (mapper.isBoxed() && element.getChildNodes().getLength() > 0)
                readNode(element.getChildNodes(), (Box) entity);
        }
    }


}
