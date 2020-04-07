package com.nautestech.www.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class PropertyConfiguration{
    private static String confHome = System.getProperty("conf.home"); // c드라이브 기준

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        FileSystemResource[] list = Files.list(Paths.get(confHome))
                .filter(path -> {
                    File file = path.toFile();
                    return file.exists() && file.isFile();
                             // file이 yml / yaml 확장자인지 검사
                })
                .map(path -> new FileSystemResource(path.toFile()))
                .toArray(size -> new FileSystemResource[size]);
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(list);
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }
}
