package com.mohamednasser.sadaqa.repository;

import com.mohamednasser.sadaqa.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

     Role findByName(String name);
}
