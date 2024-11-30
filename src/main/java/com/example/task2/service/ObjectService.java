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
        List<ObjectEntity> objectEntities = repository.findAll();
        
        Map<Integer, List<ObjectEntity>> childeObjectEntities = new HashMap<>();
        List<ObjectEntity> parentObjectEntities = new ArrayList<>();
        
        //get childeObjectEntities & parentObjectEntities
        for (ObjectEntity obj : objectEntities) {
            int parentId = obj.getParentId();
            if (parentId != 0) {
                List<ObjectEntity> children = childeObjectEntities.get(parentId);
                if (children == null) {
                    children = new ArrayList<>();
                    childeObjectEntities.put(parentId, children);
                }

                children.add(obj);

            } else {
                parentObjectEntities.add(obj);
            }
        }
        List<Map<String, Object>> outputList = new ArrayList<>();

        for (ObjectEntity parent : parentObjectEntities) {
            int paremntid = parent.getId();
            if (childeObjectEntities.containsKey(paremntid)) {
                List<ObjectEntity> childEntities = childeObjectEntities.get(paremntid);
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Sub Class", childEntities);  //
                innerMap.put("Name", parent.getName());
                outputList.add(innerMap);
            }

        }
        return outputList;

    }
}
