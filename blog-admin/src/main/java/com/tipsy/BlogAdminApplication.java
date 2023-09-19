package com.tipsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.prefs.BackingStoreException;

/**
 * @author lys
 * @Date 2023/9/15
 **/
@SpringBootApplication
@MapperScan("com.tipsy.repository")
public class BlogAdminApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlogAdminApplication.class,args);
    }
}
