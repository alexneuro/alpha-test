package ru.alexneuro.alfatest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Box {
    @Id
//    @SequenceGenerator(name="jpaSeq", sequenceName = "box_id_seq", allocationSize = 1, initialValue = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSeq")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "contained_in")
    private int parent;

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        if (parent == 0)
            return String.format("%s {id=%s}", "Box", id);
        return String.format("%s {id=%s, contained_in=%s}", "Box", id, parent);
    }
}
