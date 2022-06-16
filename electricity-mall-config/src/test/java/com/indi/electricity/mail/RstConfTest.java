package com.indi.electricity.mail;

import com.indi.electricity.mall.ConfigApplication;
import com.indi.electricity.mall.config.processor.annotation.Config;
import com.indi.electricity.mall.config.processor.RstConf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(classes = ConfigApplication.class)
//@AutoConfigureMockMvc
//@DisplayName("Junit5单元测试")
@ExtendWith(SpringExtension.class)
public class RstConfTest {

    @Config(name = "int-val")
    RstConf<Integer> intVal;

    @Config(name = "long-val")
    RstConf<Long> longVal;

    @Config(name = "double-val")
    RstConf<Double> doubleVal;

    @Config(name = "string-val")
    RstConf<String> stringVal;

    @Config(name = "boolean-val")
    RstConf<Boolean> booleanVal;

    @Config(name = "list-val")
    RstConf<List<Integer>> listVal;

    @Config(name = "set-val")
    RstConf<Set<String>> setVal;

    @Config(name = "map-val")
    RstConf<Map<String, String>> mapVal;

    @Config(name = "map-val", findChild = true)
    RstConf<Map<String, String>> children;

    @Test
    @DisplayName("Long")
    void intTest() {
        System.out.println(intVal.getData());
    }

    @Test
    @DisplayName("Integer")
    void longTest() {
        System.out.println(longVal.getData());
    }

    @Test
    @DisplayName("Double")
    void doubleTest() {
        System.out.println(doubleVal.getData());
    }

    @Test
    @DisplayName("String")
    void stringTest() {
        System.out.println(stringVal.getData());
    }


    @Test
    @DisplayName("Boolean")
    void booleanTest() {
        System.out.println(booleanVal.getData());
    }

    @Test
    @DisplayName("List<Integer>")
    void listTest() {
        System.out.println(listVal.getData());
    }

    @Test
    @DisplayName("Set<String>")
    void setTest() {
        System.out.println(setVal.getData());
    }

    @Test
    @DisplayName("Map<String,String>")
    void mapTest() {
        System.out.println(mapVal.getData());
    }

    @Test
    @DisplayName("Map<String,String> children")
    void childrenTest() {
        System.out.println(children.getData());
    }
}

