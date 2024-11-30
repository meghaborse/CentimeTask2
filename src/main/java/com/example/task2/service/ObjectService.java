package com.example.task2.service;

import com.example.task2.model.ObjectEntity;
import com.example.task2.repository.ObjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ObjectService {
    private final ObjectRepository repository;

    public ObjectService(ObjectRepository repository) {
        this.repository = repository;
    }
    public List<Map<String, Object>> getNestedObjects() {
        List<ObjectEntity> objectEntities = repository.findAll();

        List<Map<String, Object>> outputList = new ArrayList<>();
        Map<Integer, List<ObjectEntity>> childeObjectEntities = new HashMap<>();
        List<ObjectEntity> parentObjectEntities = new ArrayList<>();
        
        //get childeObjectEntities & parentObjectEntities separately
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

        for (ObjectEntity parent : parentObjectEntities) {  //iterate through parent one by one
            int paremntid = parent.getId();
            if (childeObjectEntities.containsKey(paremntid)) {  //  is parentId belong to any child?
                List<ObjectEntity> childEntities = childeObjectEntities.get(paremntid); // get the childes of given arent
                List<String> childEntitiesName= childEntities.stream()
                        .map (s->s.getName()).collect(Collectors.toList());// get names of child
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("Name", parent.getName());   //update parent name
                innerMap.put("Sub Class", childEntitiesName);  // update childs
                outputList.add(innerMap);
            }

        }
        return outputList;

    }
}
