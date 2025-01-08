package org.ricky;

import org.ricky.config.OpenFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/2
 * @className JudgeApplication
 * @desc
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "org.ricky.client", defaultConfiguration = OpenFeignConfiguration.class)
public class JudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeApplication.class, args);
    }

}
