package com.davezone.billtracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Console;

//TODO security ki van kapcsolva!!!
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing
public class BillTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillTrackerApplication.class, args);
    }


}
