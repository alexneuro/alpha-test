package ru.alexneuro.alfatest.xml;

import org.w3c.dom.Element;
import ru.alexneuro.alfatest.entity.Box;

public interface XmlMapper {
    Object create(Element element, Box parentBox);

    String getTag();

    boolean isBoxed();
}
