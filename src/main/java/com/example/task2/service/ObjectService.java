package com.example.task2.service;

import com.example.task2.aop.LogMethodParam;
import com.example.task2.model.ObjectEntity;
import com.example.task2.repository.ObjectRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObjectService {
    private final ObjectRepository repository;

    public ObjectService(ObjectRepository repository) {
        this.repository = repository;
    }
    @LogMethodParam
    public Optional<ObjectEntity> getObjectById(int id){
        return repository.findById(id);
    }
    public List<Map<String, Object>> getNestedObjects() {
        List<ObjectEntity> objectEntities = repository.findAll();

        List<Map<String, Object>> outputList = new ArrayList<>();
        Map<Integer, List<ObjectEntity>> childeObjectEntities = new HashMap<>();
        List<ObjectEntity> parentObjectEntities = new ArrayList<>();
        
        //get childeObjectEntities & parentObjectEntities separately
        for (ObjectEntity obj : objectEntities) {
            int parentId = obj.getParentId();  //get parent id
            if (parentId != 0) { //if ObjectEntity have parent entity other than 0 then its child entity
                List<ObjectEntity> children = childeObjectEntities.get(parentId);
                if (children == null) {
                    children = new ArrayList<>();
                    //update childEntityList if parent != 0
                    childeObjectEntities.put(parentId, children);
                }
                children.add(obj);

            } else {
                //if parent id is 0 then its parent entity
                parentObjectEntities.add(obj);  //update parententityList if parent id =0
            }
        }

        for (ObjectEntity parent : parentObjectEntities) {  //iterate through parent one by one
            int paremntid = parent.getId();
            List<Map<String, Object>> subClassList = new ArrayList<>();
            if (childeObjectEntities.containsKey(paremntid)) {  //  is parentId belong to any child?

                // get the childes of given parent
                List<ObjectEntity> childEntities = childeObjectEntities.get(paremntid);

                // get names of child in list
                List<String> childEntitiesName= childEntities.stream()
                        .map (s->s.getName()).collect(Collectors.toList());

               //subclass-> names will in map
                for(String subClassName:  childEntitiesName) {
                    Map<String, Object> subClassNameMap = new HashMap<>();
                    subClassNameMap.put("Name", subClassName);
                    subClassList.add(subClassNameMap);
                }


                    Map<String, Object> outperMap = new HashMap<>();
                    outperMap.put("Name", parent.getName());   //update parent name
                    outperMap.put("Sub Class", subClassList);  // update childs
                    outputList.add(outperMap);

            }

        }
        return outputList;

    }
}
