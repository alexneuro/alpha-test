package ru.alexneuro.alfatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alexneuro.alfatest.entity.Box;

import java.util.List;

public interface BoxRepository extends JpaRepository<Box, Integer> {
    List<Box> findAllByParentEquals(int parent);

    @Query(value = "SELECT id FROM box WHERE contained_in = :parentId", nativeQuery = true)
    List<Integer> childBoxIdList(@Param("parentId") int parentId);
}
