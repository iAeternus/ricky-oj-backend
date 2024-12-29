package org.ricky.routers;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/29
 * @className RouteService
 * @desc 路由配置服务
 */
public interface RouteService {

    /**
     * 更新路由配置
     */
    void update(RouteDefinition routeDefinition);

    /**
     * 添加路由配置
     */
    void add(RouteDefinition routeDefinition);

}
