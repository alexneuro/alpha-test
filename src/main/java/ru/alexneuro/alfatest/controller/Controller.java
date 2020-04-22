package ru.alexneuro.alfatest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;
import ru.alexneuro.alfatest.service.BoxService;
import ru.alexneuro.alfatest.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private ItemService itemService;
    private BoxService boxService;

    @Autowired
    public Controller(ItemService itemService, BoxService boxService) {
        this.itemService = itemService;
        this.boxService = boxService;
    }

    @PostMapping("/test")
    public ResponseEntity<?> getItemsByColorAndBox(@RequestBody ItemsRequest request) {
        System.out.println(request.getBox() + " " + request.getColor());

        return new ResponseEntity<>(getList(request.getBox(), request.getColor()), HttpStatus.OK);
    }

    public int[] getList(int id, String color) {
        Box box = this.boxService.getBoxById(id);
        List<Box> childBoxes = new ArrayList<>();
        childBoxes.add(box);
        this.boxService.getAllChildBoxes(box, childBoxes);

        return this.itemService.getItemsByBoxesAndColor(childBoxes, color).stream().mapToInt(Item::getId).toArray();
    }
}
