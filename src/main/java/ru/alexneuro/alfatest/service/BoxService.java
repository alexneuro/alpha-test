package ru.alexneuro.alfatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexneuro.alfatest.entity.Box;
import ru.alexneuro.alfatest.repository.BoxRepository;

import java.util.List;

@Service
@Transactional
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
    public List<Box> getAllHead() {
        return this.boxRepository.findAllByParentEquals(0);
    }

    public List<Box> getChildBoxes(Box box) {
        return this.boxRepository.findAllByParentEquals(box.getId());
    }

    public void getAllChildBoxes(Box box, List<Box> list) {
        for (Box child : getChildBoxes(box)) {
            list.add(child);
            getAllChildBoxes(child, list);
        }
    }

    public Box getBoxById(int id) {
        return this.boxRepository.findById(id).get();
    }

    public List<Integer> getChildIds(Box box) {
        return boxRepository.childBoxIdList(box.getId());
    }
}

