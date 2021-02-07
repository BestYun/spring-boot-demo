package com.readchen.springbootmybatisplus.controller;

import com.readchen.springbootmybatisplus.entity.Post;
import com.readchen.springbootmybatisplus.mapper.PostMapper;
import com.readchen.springbootmybatisplus.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostMapper postMapper;

    @GetMapping("/posts")
    JsonResult getAllPosts(){
        List<Post> postList =
                postMapper.selectList(null);
        return JsonResult.success(postList);
    }

    @PostMapping("/post")
    JsonResult save(){

        Post post = new Post();
        post.setUserID(1L);
        post.setTitle("test title");
        post.setContent("test content");
        postMapper.insert(post);

        return JsonResult.success(post);
    }
}
