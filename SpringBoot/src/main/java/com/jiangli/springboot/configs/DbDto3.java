package com.jiangli.springboot.configs;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/4/13 17:56
 */
@Data
@Component
public class DbDto3 {
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
