package com.mohamednasser.sadaqa.repository;

import com.mohamednasser.sadaqa.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByHandle(String handle);
}
