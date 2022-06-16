package com.indi.electricity.mall.utils;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuplicateElementUtilTest extends BaseTest {

    public static List<String> strList;
    public static List<Integer> intList;
    public static List list;

    List<String> strDuplicateElement;
    List<Integer> intDuplicateElement;
    List duplicateElement;

    @BeforeEach
    public void initList() {
        strList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "a", "d"));
        intList = new ArrayList<>(Arrays.asList(1, 2, 3, 1, 3));
        list = new ArrayList<>(Arrays.asList(1.1, 2.1, 3.1, 1.1, 3));
//        list = new ArrayList<>(Arrays.asList(1, 2, 3, 1,3));
    }

    @AfterEach
    public void println() {
        output(strDuplicateElement);
        output(intDuplicateElement);
        output(duplicateElement);
    }


    @Test
    public void getDuplicateElementsTest() {
        strDuplicateElement = DuplicateElementUtil.getDuplicateElements(strList);
        intDuplicateElement = DuplicateElementUtil.getDuplicateElements(intList);
        duplicateElement = DuplicateElementUtil.getDuplicateElements(list);

    }

    @Test
    public void getDuplicateElementsGuavaTest() {
        strDuplicateElement = DuplicateElementUtil.getDuplicateElementsGuava(strList);
    }

    @Test
    public void getDuplicateElements1Test() {
        strDuplicateElement = DuplicateElementUtil.getDuplicateElements(strList);
    }

}
