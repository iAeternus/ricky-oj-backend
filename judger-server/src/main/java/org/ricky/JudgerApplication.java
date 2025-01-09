package org.ricky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/9
 * @className JudgerApplication
 * @desc
 */
@SpringBootApplication
@EnableDiscoveryClient
public class JudgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgerApplication.class, args);
    }

}
