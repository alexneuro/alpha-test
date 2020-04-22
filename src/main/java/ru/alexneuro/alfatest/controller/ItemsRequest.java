package ru.alexneuro.alfatest.controller;

import org.springframework.stereotype.Service;

@Service
public class ItemsRequest {
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
