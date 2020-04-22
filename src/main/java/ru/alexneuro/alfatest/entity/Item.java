package ru.alexneuro.alfatest.entity;

import javax.persistence.*;

@Entity
public class Item {
    @Id
    private int id;

    @Column(length = 100)
    private String color;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "contained_in", referencedColumnName = "id")
    private Box parent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Box getParent() {
        return parent;
    }

    public void setParent(Box parent) {
        this.parent = parent;
    }


    public String toString() {
        if (parent == null)
            return String.format("%s {id=%s, color='%s'}", "Item", id, color);
        return String.format("%s {id=%s, color='%s', contained_in=%s}", "Item", id, color, parent.getId());
    }
}
