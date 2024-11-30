package com.example.task2.controller;

import com.example.task2.service.ObjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/objects")
public class ObjectController {
    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }
@GetMapping("/nested")
    public List<Map<String,Object>> getNestedObjects(){
        return objectService.getNestedObjects();

    }
}
