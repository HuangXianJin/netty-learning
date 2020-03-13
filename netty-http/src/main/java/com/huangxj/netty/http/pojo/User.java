package com.huangxj.netty.http.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author: huangxj
 * @Create: 2020-03-11 15:28
 * @Version V1.0
 **/
@Data
public class User {
    private String userName;

    private String method;

    private Date date;
}
