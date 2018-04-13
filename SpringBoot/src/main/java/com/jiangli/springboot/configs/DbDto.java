package com.jiangli.springboot.configs;

import lombok.Data;

/**
 * @author Jiangli
 * @date 2018/4/13 17:56
 */
@Data
public class DbDto {
    private String url;
    private String username;
    private String password;
    private String max_active;
    private String maxActive;
    private String initial_size;
    private String initialSize;
    private String min_idle;
    private String minIdle;
}
