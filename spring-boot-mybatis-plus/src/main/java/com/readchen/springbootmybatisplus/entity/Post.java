package com.readchen.springbootmybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Post {
    @TableId
    private int pid;
    private String title;
    private String content;
    @TableField("user_id")
    private Long userID;

}
