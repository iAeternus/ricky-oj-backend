package org.ricky.core.common.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/13
 * @className YamlUtils
 * @desc
 */
@Slf4j
public class YamlUtils {

    /**
     * 读取classpath下指定yaml文件名的所有内容
     *
     * @param filename yaml文件名，需要带扩展名
     * @return 可迭代的对象集合
     */
    public static Iterable<Object> loadYaml(String filename) {
        try {
            Yaml yaml = new Yaml();
            String ymlContent = ResourceUtil.readUtf8Str(filename);
            return yaml.loadAll(ymlContent);
        } catch (Exception e) {
            log.error("load language yaml error:", e);
            throw new RuntimeException(e);
        }
    }

}
