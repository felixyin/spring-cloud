package com.qtrj.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResult<T> {

    //    http 状态码
    private Integer code;
    //    错误消息
    private String message;
    //    数据
    private T data;

    // 增加一个没有数据的构造方法
    public CommentResult(Integer code,String message){
        this(code,message,null);
    }
}
