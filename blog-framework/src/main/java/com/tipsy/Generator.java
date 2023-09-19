package com.tipsy;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Scanner;

/**
 * @author lys
 * @Date 2023/9/5
 **/
public class Generator {

    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        String encryptorPassword = System.getProperty("jasypt.encryptor.password");
        encryptor.setPassword(encryptorPassword);
        String databasePassword = encryptor.decrypt("OmqQbOji72uWuWZIdqL0aU+Npq0U2Kv/");
        DataSourceConfig dsc = new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/blog_database?useUnicode=true&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true"
                ,"root",databasePassword).build();
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

        // 全局配置
        String projectPath = System.getProperty("user.dir");

        GlobalConfig gc = new GlobalConfig.Builder().outputDir(projectPath + "/src/main/java").disableOpenDir().author("lys").build();
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.global(gc);


        // 包配置
        PackageConfig pc = new PackageConfig.Builder("com.tipsy","")
                .mapper("repository")
                .xml("repository.dao")
                .build();
        mpg.packageInfo(pc);

        //模板配置
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .build();
        templateConfig.disable(TemplateType.ENTITY,TemplateType.MAPPER,TemplateType.XML);
        mpg.template(templateConfig);


        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude("sys_user_role")
                .build();
        mpg.strategy(strategyConfig);
        mpg.execute(new FreemarkerTemplateEngine());
    }
}
