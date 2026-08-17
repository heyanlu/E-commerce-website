package com.qiuzhitech.onlineshopping_06.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTest {
    private Integer id;
    private String name;
    private String email;

    public UserTest(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
