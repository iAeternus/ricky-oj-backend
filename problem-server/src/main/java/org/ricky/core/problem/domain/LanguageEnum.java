package org.ricky.core.problem.domain;

import lombok.Getter;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className LanguageEnum
 * @desc
 */
@Getter
public enum LanguageEnum {

    C_11("C11", "GNU GCC", "5.1.0"),
    CPP_17("C++17", "GNU G++", "7.3.0"),
    CPP_20("C++20", "GNU G++", "13.2 (64bit, winlibs)"),
    CPP_23("C++23", "GNU G++", "14.2 (64bit, winlibs)"),
    GOLANG("GoLang", "", "1.22.2"),
    JAVA_8("Java8", "", ""),
    JAVA_21("Java21", "", ""),
    PYTHON_2("python", "", "2.7.18"),
    PYTHON_3("python", "", "3.8.10"),
    RUST("rust", "", "1.75.0 (2021)"),
    ;

    final String name;
    final String compiler;
    final String version;

    LanguageEnum(String name, String compiler, String version) {
        this.name = name;
        this.compiler = compiler;
        this.version = version;
    }

    @Override
    public String toString() {
        return compiler + version;
    }
}
