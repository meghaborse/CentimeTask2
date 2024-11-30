package com.example.task2.service;

import com.example.task2.model.ObjectEntity;
import com.example.task2.repository.ObjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObjectService {
    private final ObjectRepository repository;

    public ObjectService(ObjectRepository repository) {
        this.repository = repository;
    }
    public List<Map<String, Object>> getNestedObjects() {
        List<ObjectEntity> inputeData = repository.findAll();
        Map<Integer, List<String>> childrenMap = new HashMap<>();
        List<ObjectEntity> result = new ArrayList<>();
        for (ObjectEntity obj : inputeData) {
            int parentId = obj.getParentId();
            if (parentId != 0) {
                List<String> children = childrenMap.get(parentId);
                if (children == null) {
                    children = new ArrayList<>();
                    childrenMap.put(parentId, children);
                }
                children.add(obj.getName());

            } else {
                result.add(obj);
            }
        }

        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> listIn = new ArrayList<>();
        Map<String, Object> mapIn = new HashMap<>();
        for (ObjectEntity parent : result) {
            int paremntid = parent.getId();
            if (childrenMap.containsKey(paremntid)) {
                List<String> children = childrenMap.get(paremntid);
                mapIn.put("SubClass", children);
                mapIn.put("Name", parent.getName());
                list1.add(mapIn);
            }

        }
        return list1;

    }
}
