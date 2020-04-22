package ru.alexneuro.alfatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByParentAndColor(Box box, String color);

    List<Item> findAllByParentInAndAndColorEquals(List<Box> boxes, String color);
}
