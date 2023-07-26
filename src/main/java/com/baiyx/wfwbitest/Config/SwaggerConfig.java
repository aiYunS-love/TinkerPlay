package com.baiyx.wfwbitest.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author: baiyx
 * @Date: 2022-11-21 11:02
 * @Description: Swagger配置类
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                // 为当前包下controller生成API文档
//                .apis(RequestHandlerSelectors.basePackage("com.baiyx.wfwbitest.Controller"))
//                // 为有@Api注解的Controller生成API文档
////                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                // 为有@ApiOperation注解的方法生成API文档
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build();
//    }

    private ApiInfo apiInfo() {
        // 作者信息
        Contact contact = new Contact("baiyx","https://gitee.com/Mr_Baiyx", "784724280@qq.com");
        return new ApiInfoBuilder()
                .title("小白学习")
                .description("baiyx-study")
                .contact(contact)
                .version("1.0.1")
                .build();
//        return new ApiInfo(
//                "小白学习",
//                "baiyx",
//                "1.0.0",
//                "https://gitee.com/Mr_Baiyx",
//                contact,
//                "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList());
    }

    @Bean
    public Docket docket(Environment environment) {
        // 获取项目的环境 需要创建多个application-xx环境配置文件
        String[] profiles = environment.getActiveProfiles();
        System.out.println("当前环境");
        for (String temp : profiles) {
            System.out.println(temp);
        }
        Boolean enableSwagger = false;
        // if (profiles.length > 0 && profiles[0].equals("windows")) {
        if (profiles.length > 0) {
            enableSwagger = true;
        }
        System.out.println("swagger是否开启：" + enableSwagger);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Test")
                .apiInfo(apiInfo())
                // 是否启用swagger, false: 不能在浏览器访问
                .enable(enableSwagger)
                .select()
                // RequestHandlerSelectors, 配置要扫描接口的方式
                // basePackage: 指定要扫描的包
                // any(): 扫描全部
                // none(): 不扫描
                // withClassAnnotation: 扫描类上的注解, 参数是一个注解的反射对象 withClassAnnotation(RestController.class)
                // withMethodAnnotation: 扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.baiyx.wfwbitest.Controller"))
                // paths 过滤什么路径 不显示到swagger文档中
                // .paths(PathSelectors.ant("/loong/**"))
                .build();
    }

//    @Bean
//    public Docket docket(Environment environment) {
//        // 获取项目的环境
//        String[] profiles = environment.getActiveProfiles();
//        System.out.println("当前环境");
//        for (String temp : profiles) {
//            System.out.println(temp);
//        }
//        Boolean enableSwagger = false;
//        if (profiles.length > 0 && profiles[0].equals("dev")) {
//            enableSwagger = true;
//        }
//        System.out.println("swagger是否开启：" + enableSwagger);
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .groupName("loong")
//                .enable(enableSwagger)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.loong.controller"))
//                .build();
//    }

    // 配置多个分组
//    @Bean
//    public Docket docket1() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("loong-A");
//    }

    // 配置多个分组
//    @Bean
//    public Docket docket2() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("loong-B");
//    }

    // 配置多个分组
//    @Bean
//    public Docket docket3() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("loong-C");
//    }

}
