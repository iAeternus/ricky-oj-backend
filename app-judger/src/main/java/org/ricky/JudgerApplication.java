package org.ricky;

import org.ricky.config.OpenFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgerApplication
 * @desc
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.ricky.client", defaultConfiguration = OpenFeignConfiguration.class)
public class JudgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgerApplication.class, args);
    }

}
