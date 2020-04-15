package com.example.demo.open.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 *  接口版本标识注解
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    /**
     * 版本号
     * @return
     */
    int value();
}
