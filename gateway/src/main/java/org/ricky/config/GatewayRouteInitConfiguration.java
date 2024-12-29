package org.ricky.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ricky.properties.GatewayRouteConfigProperties;
import org.ricky.routers.RouteService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

import static java.util.Objects.requireNonNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className GatewayRouteInitConfiguration
 * @desc
 */
@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class GatewayRouteInitConfiguration {

    private final GatewayRouteConfigProperties configProperties;
    private final NacosConfigProperties nacosConfigProperties;
    private final RouteService routeService;
    private final ObjectMapper objectMapper;

    /**
     * nacos 配置服务
     */
    private final ConfigService configService;

    @PostConstruct
    public void init() {
        log.info("开始网关动态路由初始化...");
        try {
            // getConfigAndSignListener()方法 发起长轮询和对dataId数据变更注册监听的操作
            // getConfig 只是发送普通的HTTP请求
            String initConfigInfo = configService.getConfigAndSignListener(configProperties.getDataId(), configProperties.getGroup(), nacosConfigProperties.getTimeout(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    if (StringUtils.isBlank(configInfo)) {
                        log.warn("当前网关无动态路由相关配置");
                        return;
                    }

                    log.info("接收到网关路由更新配置：\r\n{}", configInfo);
                    List<RouteDefinition> routeDefinitions = null;
                    try {
                        routeDefinitions = objectMapper.readValue(configInfo, new TypeReference<>() {
                        });

                    } catch (Exception e) {
                        log.error("解析路由配置出错，" + e.getMessage(), e);
                    }
                    requireNonNull(routeDefinitions).forEach(routeService::update);
                }
            });
            log.info("获取网关当前动态路由配置:\r\n{}", initConfigInfo);
            if (StringUtils.isNotBlank(initConfigInfo)) {
                List<RouteDefinition> routeDefinitions = objectMapper.readValue(initConfigInfo, new TypeReference<>() {
                });
                routeDefinitions.forEach(routeService::add);
            } else {
                log.warn("当前网关无动态路由相关配置");
            }
            log.info("结束网关动态路由初始化...");
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
        }
    }

}
