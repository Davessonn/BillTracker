package com.davezone.billtracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.Console;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BillTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillTrackerApplication.class, args);
    }


}
