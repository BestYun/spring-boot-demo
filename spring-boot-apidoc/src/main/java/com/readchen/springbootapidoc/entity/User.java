package com.readchen.springbootapidoc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    @ApiComment("用户id")
    private Long id;
    @ApiComment("用户名")
    private String name;
    @ApiComment("年龄")
    private Integer age;
    @ApiComment("邮箱")
    private String email;
    @ApiComment("上级id")
    private Long managerId;
    @ApiComment("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime;
}