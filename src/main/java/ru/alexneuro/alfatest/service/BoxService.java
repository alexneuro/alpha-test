package ru.alexneuro.alfatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.repository.BoxRepository;

import java.util.List;

@Service
public class BoxService {
    private final BoxRepository boxRepository;

    @Autowired
    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @Transactional
    public void save(Box box) {
        this.boxRepository.save(box);
    }

    @Transactional
    public List<Box> getChildBoxes(Box box) {
        return this.boxRepository.findAllByParentEquals(box.getId());
    }

    @Transactional
    public void getAllChildBoxes(Box box, List<Box> list) {
        for (Box child : getChildBoxes(box)) {
            list.add(child);
            getAllChildBoxes(child, list);
        }
    }

    @Transactional
    public Box getBoxById(int id) {
        return this.boxRepository.findById(id).get();
    }

}

