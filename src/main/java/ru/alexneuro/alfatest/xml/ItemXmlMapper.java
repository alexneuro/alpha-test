package ru.alexneuro.alfatest.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;
import ru.alexneuro.alfatest.service.ItemService;

import java.util.HashSet;
import java.util.Set;

@Component
public class ItemXmlMapper implements XmlMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemXmlMapper.class);
    private static Set<Integer> _uniqueIdSet = new HashSet<>();

    @Value("${xmlparser.not-unique-id-message}")
    private String MESSAGE_FOR_NOT_UNIQUE_ID;

    private ItemService itemService;

    @Autowired
    public ItemXmlMapper(ItemService itemService) {
        this.itemService = itemService;
    }

    public Item create(Element element, Box parentBox) {
        Item item = new Item();
        int id = Integer.parseInt(element.getAttribute("id"));
        item.setId(id);
        item.setColor(element.getAttribute("color"));
        if (parentBox != null)
            item.setParent(parentBox);

        if (_uniqueIdSet.contains(id)) {
            LOGGER.warn(String.format(MESSAGE_FOR_NOT_UNIQUE_ID, item));
            return null;
        }
        _uniqueIdSet.add(id);

        itemService.save(item);
        return item;
    }

    @Override
    public String getTag() {
        return "Item";
    }

    @Override
    public boolean isBoxed() {
        return false;
    }

}
