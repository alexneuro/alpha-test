package ru.alexneuro.alfatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;
import ru.alexneuro.alfatest.repository.ItemRepository;

import java.util.List;

@Service
@Transactional
public class ItemService {

    public final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public List<Item> getItems(Box box, String color) {
        return itemRepository.findAllByParentAndColor(box, color);
    }

    public List<Item> getItemsByBoxesAndColor(List<Box> boxes, String color) {
        return itemRepository.findAllByParentInAndAndColorEquals(boxes, color);
    }

}
