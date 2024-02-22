package com.example.schedulegroup.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.schedulegroup.entity.ListGroupEntity;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ListGroupRepository extends CrudRepository<ListGroupEntity, Integer> {

}


