package ru.alexneuro.alfatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;
import ru.alexneuro.alfatest.service.BoxService;
import ru.alexneuro.alfatest.service.ItemService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class XMLParser {
    private BoxService boxService;
    private ItemService itemService;

    @Autowired
    public XMLParser(BoxService boxService, ItemService itemService) {
        this.boxService = boxService;
        this.itemService = itemService;
    }

    private static Set<Integer> _cashBoxIds = new HashSet<>();
    private static Set<Integer> _cashItemsIds = new HashSet<>();

    public void parse(File xmlFile) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

//            document.getDocumentElement().normalize();

            Element storage = document.getDocumentElement();
            if (storage.getChildNodes().getLength() > 0)
                readNode(storage.getChildNodes(), null, 0);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void readNode(NodeList childNodes, Box parentBox, int o) throws Exception {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            if (element.getTagName().equals("Box")) {
                Box box = new Box();
                int id = Integer.parseInt(element.getAttribute("id"));
                if (_cashBoxIds.contains(id)) {
                    System.out.println(String.format("Box id %s is not unique", id));
                    continue;
                }
                _cashBoxIds.add(id);

                box.setId(id);
                if (parentBox != null)
                    box.setParent(parentBox.getId());

                for (int p = 0; p < o; p++)
                    System.out.print("\t");
                System.out.println(box);
                boxService.save(box);

                if (element.getChildNodes().getLength() > 0)
                    readNode(element.getChildNodes(), box, o + 1);
            } else if (element.getTagName().equals("Item")) {
                Item item = new Item();
                int id = Integer.parseInt(element.getAttribute("id"));
                if (_cashItemsIds.contains(id)) {
                    System.out.println(String.format("Item id %s is not unique", id));
                    continue;
                }
                _cashItemsIds.add(id);

                item.setId(id);
                item.setColor(element.getAttribute("color"));
                if (parentBox != null)
                    item.setParent(parentBox);
                for (int p = 0; p < o; p++)
                    System.out.print("\t");
                System.out.println(item);
                itemService.save(item);
            }
        }
    }


}
