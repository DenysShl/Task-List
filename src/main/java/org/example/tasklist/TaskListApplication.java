package org.example.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class TaskListApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TaskListApplication.class)
                .properties("optional:file:./local-config/")
                .build()
                .run(args);
//        SpringApplication.run(TaskListApplication.class, args);
    }

}
