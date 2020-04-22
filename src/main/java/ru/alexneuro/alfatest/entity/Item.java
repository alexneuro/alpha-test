package ru.alexneuro.alfatest.entity;

import javax.persistence.*;

@Entity
public class Item {
    @Id
//    @SequenceGenerator(name="jpaSeq1", sequenceName = "item_id_seq", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSeq1")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(length = 100)
    private String color;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "contained_in", referencedColumnName = "id")
    private Box parent;

    public Box getParent() {
        return parent;
    }

    public void setParent(Box parent) {
        this.parent = parent;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String toString() {
        if (parent == null)
            return String.format("%s {id=%s, color='%s'}", "Item", id, color);
        return String.format("%s {id=%s, color='%s', contained_in=%s}", "Item", id, color, parent.getId());
    }
}
