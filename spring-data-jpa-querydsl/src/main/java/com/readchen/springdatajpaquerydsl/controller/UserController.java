package com.readchen.springdatajpaquerydsl.controller;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.readchen.springdatajpaquerydsl.dao.UserDao;
import com.readchen.springdatajpaquerydsl.dto.ArticleDTO;
import com.readchen.springdatajpaquerydsl.dto.UserArticleDTO;
import com.readchen.springdatajpaquerydsl.dto.UserDTO;
import com.readchen.springdatajpaquerydsl.entity.QArticle;
import com.readchen.springdatajpaquerydsl.entity.QUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    EntityManager entityManager;

//    @Autowired
//    private UserDao userDao;

    @GetMapping("/user/{userID}")
   UserDTO getUserById(@PathVariable Integer userID){
        QUser qUser = QUser.user;
        QArticle qArticle = QArticle.article;
//        List<UserDTO> data = jpaQueryFactory.select(Projections.bean(UserDTO.class))
//                .from(qUser)
//                .leftJoin(qArticle)
//                .on(qArticle.userID.eq(qUser.id))
//                .where(qUser.uid.eq(userID))
//                .fetch();

//        userDao.findAllById(userID);

        List<UserArticleDTO> data = jpaQueryFactory.select(
                Projections.bean(UserArticleDTO.class,qUser.uid,qUser.name,qUser.phone,qUser.age,
                        qArticle.id,qArticle.content,qArticle.title)
        ).from(qUser).leftJoin(qArticle).on(qArticle.userID.eq(qUser.uid))
                .where(qUser.uid.eq(userID)).groupBy(qArticle.id).fetch();

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (UserArticleDTO item : data){
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setContent(item.getContent());
            articleDTO.setTitle(item.getTitle());
            articleDTO.setId(item.getId());
            articleDTOList.add(articleDTO);
        }
        UserArticleDTO userArticleDTO = data.get(0);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userArticleDTO,userDTO);
        userDTO.setArticles(articleDTOList);

        return userDTO;
    }
}
