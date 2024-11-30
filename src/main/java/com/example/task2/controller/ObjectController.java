package com.example.task2.controller;

import com.example.task2.model.ObjectEntity;
import com.example.task2.service.ObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/objects")
public class ObjectController {
    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ObjectEntity> getObjectById(@PathVariable int id){
        Optional<ObjectEntity> object = objectService.getObjectById(id);
        return object.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nested")
    public ResponseEntity<List<Map<String, Object>>> getNestedObjects() {
        return ResponseEntity.ok(objectService.getNestedObjects());
    }
}
