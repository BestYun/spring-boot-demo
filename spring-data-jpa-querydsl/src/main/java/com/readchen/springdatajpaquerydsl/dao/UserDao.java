package com.readchen.springdatajpaquerydsl.dao;

import com.readchen.springdatajpaquerydsl.dto.UserDTO;
import com.readchen.springdatajpaquerydsl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserDao extends JpaRepository<User,Long> {
}
