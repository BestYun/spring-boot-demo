package com.readchen.springbootjpa.repository;

import com.readchen.springbootjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
