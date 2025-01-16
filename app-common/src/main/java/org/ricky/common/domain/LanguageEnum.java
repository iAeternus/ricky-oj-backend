package org.ricky.common.domain;

import lombok.Getter;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/12/26
 * @className LanguageEnum
 * @desc 编程语言枚举
 */
@Getter
public enum LanguageEnum {

    C("C", "gcc"),
    C_WITH_O2("C With O2", "gcc"),
    CPP("C++", "g++"),
    CPP_WITH_O2("C++ With O2", "g++"),
    CPP_17("C++ 17", "g++"),
    CPP_17_WITH_O2("C++ 17 With O2", "g++"),
    CPP_20("C++ 20", "g++"),
    CPP_20_WITH_O2("C++ 20 With O2", "g++"),
    CPP_23("C++ 23", "g++"),
    CPP_23_WITH_O2("C++ 23 With O2", "g++"),
    JAVA("Java", "javac"),
    PYTHON_2("Python2", "python"),
    PYTHON_3("Python3", "python3.7"),
    PYPY2("PyPy2", "pypy"),
    PYPY3("PyPy3", "pypy3"),
    GOLANG("Golang", "go"),
    C_SHARP("C#", "mcs"),
    PHP("PHP", "php"),
    JAVASCRIPT_V8("JavaScript V8", ""),
    RUBY("Ruby", "ruby"),
    RUST("Rust", "rustc"),

    // 以下为特殊评测或交互评测使用的语言
    SPJ_C("SPJ-C", "gcc"),
    SPJ_CPP("SPJ-C++", "g++"),
    INTERACTIVE_C("INTERACTIVE-C", "gcc"),
    INTERACTIVE_CPP("INTERACTIVE-C++", "g++"),
    ;

    final String name;
    final String compiler;

    LanguageEnum(String name, String compiler) {
        this.name = name;
        this.compiler = compiler;
    }
}
