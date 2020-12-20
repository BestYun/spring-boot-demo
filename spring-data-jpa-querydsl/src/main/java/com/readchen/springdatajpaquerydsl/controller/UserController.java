package com.readchen.springdatajpaquerydsl.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.readchen.springdatajpaquerydsl.entity.QArticle;
import com.readchen.springdatajpaquerydsl.entity.QUser;
import com.readchen.springdatajpaquerydsl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    EntityManager entityManager;

    @GetMapping("/user/{userID}")
    List<User> getUserById(@PathVariable Integer userID){
        QUser qUser = QUser.user;
        QArticle qArticle = QArticle.article;
        List<User> data = jpaQueryFactory.selectFrom(qUser)
                .leftJoin(qArticle)
                .on(qArticle.userID.eq(qUser.id))
                .where(qUser.id.eq(userID))
                .fetch();
        return data;
    }
}
