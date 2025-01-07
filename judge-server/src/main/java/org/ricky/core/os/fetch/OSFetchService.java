package org.ricky.core.os.fetch;

import org.ricky.core.os.fetch.response.FetchOSConfigResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static cn.hutool.system.oshi.OshiUtil.getCpuInfo;
import static cn.hutool.system.oshi.OshiUtil.getMemory;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/7
 * @className OSFetchService
 * @desc
 */
@Service
public class OSFetchService {

    public FetchOSConfigResponse fetchOSConfig() {
        Map<String, Object> info = new HashMap<>();

        int cpuCores = Runtime.getRuntime().availableProcessors(); // cpu核数
        double cpuLoad = 100 - getCpuInfo().getFree();
        String percentCpuLoad = String.format("%.2f", cpuLoad) + "%"; // cpu使用率

        double totalVirtualMemory = (double) getMemory().getTotal(); // 总内存
        double freePhysicalMemorySize = (double) getMemory().getAvailable(); // 空闲内存
        double value = freePhysicalMemorySize / totalVirtualMemory;
        String percentMemoryLoad = String.format("%.2f", (1 - value) * 100) + "%"; // 内存使用率

        info.put("cpuCores", cpuCores);
        info.put("percentCpuLoad", percentCpuLoad);
        info.put("percentMemoryLoad", percentMemoryLoad);

        return FetchOSConfigResponse.builder()
                .info(info)
                .build();
    }
}
