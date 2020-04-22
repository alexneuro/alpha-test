package ru.alexneuro.alfatest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.entity.Item;
import ru.alexneuro.alfatest.service.BoxService;
import ru.alexneuro.alfatest.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {
    private ItemService itemService;
    private BoxService boxService;

    @Autowired
    public MyController(ItemService itemService, BoxService boxService) {
        this.itemService = itemService;
        this.boxService = boxService;
    }

    @GetMapping("/test")
    public String get() {
        return "info";
    }

    @PostMapping("/test")
    public ResponseEntity<?> getItemsByColorAndBox(@RequestBody ItemsRequest request) {
        if (request == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(getList(request.getBox(), request.getColor()), HttpStatus.OK);
    }

    public int[] getList(int id, String color) {
        Box box = this.boxService.getBoxById(id);
        List<Box> childBoxes = new ArrayList<>();
        childBoxes.add(box);
        this.boxService.getAllChildBoxes(box, childBoxes);

        return this.itemService.getItemsByBoxesAndColor(childBoxes, color).stream().mapToInt(Item::getId).toArray();
    }


    public static class ItemsRequest {
        private int box;
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getBox() {
            return box;
        }

        public void setBox(int box) {
            this.box = box;
        }
    }
}
