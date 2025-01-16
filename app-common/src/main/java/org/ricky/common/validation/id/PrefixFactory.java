package org.ricky.common.validation.id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static org.ricky.common.utils.ValidationUtils.isNull;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/28
 * @className PrefixFactory
 * @desc ID前缀享元工厂
 */
public class PrefixFactory {

    private final Map<String, Pattern> patternMap = new ConcurrentHashMap<>();
    private volatile static PrefixFactory instance;

    private PrefixFactory() {
    }

    public static PrefixFactory getInstance() {
        if (isNull(instance)) {
            synchronized (PrefixFactory.class) {
                if (isNull(instance)) {
                    instance = new PrefixFactory();
                }
            }
        }
        return instance;
    }

    public Pattern getPattern(String prefix) {
        return patternMap.computeIfAbsent(prefix, key -> Pattern.compile("^" + key + "[0-9]{17,19}$"));
    }

    public boolean matches(String id, String prefix) {
        return getPattern(prefix).matcher(id).matches();
    }

}
