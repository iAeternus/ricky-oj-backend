package org.ricky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className GatewayRouteConfigProperties
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "my.gateway.routes.config")
public class GatewayRouteConfigProperties {

    private String dataId;
    private String group;
    private String namespace;

}
