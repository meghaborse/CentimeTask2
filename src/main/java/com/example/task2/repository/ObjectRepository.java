package com.example.task2.repository;

import com.example.task2.model.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectEntity, Integer> {
}
