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
    //    集群环境下具体哪个端口绑定的生产者
    private String serverPort;
    //    数据
    private T data;

    // 增加一个没有数据的构造方法
    public CommentResult(Integer code, String message, String serverPort) {
        this(code, message, serverPort, null);
    }

}
