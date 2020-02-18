package com.readchen.springbootHikariCP.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerId;
    private LocalDateTime createTime;
//    排除非表字段
    @TableField(exist = false)
//    排除json输出
    @JsonIgnore
    private String info;
}
