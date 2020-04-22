package ru.alexneuro.alfatest.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.service.BoxService;

import java.util.HashSet;
import java.util.Set;

@Component
public class BoxXmlMapper implements XmlMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoxXmlMapper.class);
    private static Set<Integer> _uniqueIdSet = new HashSet<>();

    @Value("${xmlparser.not-unique-id-message}")
    private String MESSAGE_FOR_NOT_UNIQUE_ID;
    @Value("${xmlparser.null-id-message}")
    private String MESSAGE_FOR_NULL_ID;
    private BoxService boxService;

    @Autowired
    public BoxXmlMapper(BoxService boxService) {
        this.boxService = boxService;
    }

    public Box create(Element element, Box parentBox) {
        Box box = new Box();
        int id;
        try {
            id = Integer.parseInt(element.getAttribute("id"));
        } catch (NumberFormatException e) {
            LOGGER.warn(String.format(MESSAGE_FOR_NULL_ID, box));
            return null;
        }
        box.setId(id);
        if (parentBox != null)
            box.setParent(parentBox.getId());

        if (_uniqueIdSet.contains(id)) {
            LOGGER.warn(String.format(MESSAGE_FOR_NOT_UNIQUE_ID, box));
            return null;
        }
        _uniqueIdSet.add(id);

        boxService.save(box);
        return box;
    }

    @Override
    public String getTag() {
        return "Box";
    }

    @Override
    public boolean isBoxed() {
        return true;
    }

}
