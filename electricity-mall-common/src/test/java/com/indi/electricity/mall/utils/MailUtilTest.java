package com.indi.electricity.mall.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MailUtilTest extends BaseTest {

    public static Map<String, Object> paramMap = null;
    LinkedHashMap<String, String> title;
    List<Map<String, Object>> data;

    @BeforeEach
    void initData() {
        List dataList = new ArrayList() {{
            add(new HashMap<String, Object>() {{
                put("name", "名称1");
                put("languageScore", 10);
                put("mathScore", 11);
                put("englishScore", 12);
                put("physicsScore", 13);
                put("chemicalScore", 14);
                put("biologyScore", 15);
                put("politicsScore", 16);
                put("historyScore", 17);
                put("geographyScore", 18);
            }});
            add(new HashMap<String, Object>() {{
                put("name", "名称3");
                put("languageScore", 30);
                put("mathScore", 31);
                put("englishScore", 32);
                put("physicsScore", 33);
                put("chemicalScore", 34);
                put("biologyScore", 35);
                put("politicsScore", 36);
                put("historyScore", 37);
                put("geographyScore", 38);
            }});
            add(new HashMap<String, Object>() {{
                put("name", "名称2");
                put("languageScore", 20);
                put("mathScore", 21);
                put("englishScore", 22);
                put("physicsScore", 23);
                put("chemicalScore", 24);
                put("biologyScore", 25);
                put("politicsScore", 26);
                put("historyScore", 27);
                put("geographyScore", 28);
            }});
        }};
        paramMap = new HashMap<String, Object>() {{
            put("title", "测试模版");
            put("num", "num-001");
            put("createTime", LocalDate.now());
            put("updateTime", LocalDateTime.now());
            put("timeDiff", 1000);
            put("userName", "ph");
            put("flag", "true");
            put("dataList", dataList);
        }};


        title = new LinkedHashMap<String, String>() {
            {
                put("col1", "rc1");
                put("col2", "rc2");
            }
        };
        data = Arrays.asList(
                new HashMap<String, Object>() {
                    {
                        put("col1", "11");
                        put("col2", "12");
                    }
                },
                new HashMap<String, Object>() {
                    {
                        put("col1", "21");
                        put("col2", "12");
                    }
                });
    }

    @Test
    void sendMailTest() throws Exception {
        MailUtil mailUtil = new MailUtil("xxx",
                587,
                "auth",
                "123456");
        mailUtil.setMailInfo("邮件标题", null, null,
                Arrays.asList("邮箱地址"), "正文", ".ftl");
        mailUtil.addAttachment("test-1.xlsx", FileUtil.getByte(ExcelUtil.getWorkBook(title, ExcelUtil.getExcelData(title, data))));
        mailUtil.addAttachment("test-2.xlsx", FileUtil.getByte(ExcelUtil.getWorkBook(title, ExcelUtil.getExcelData(title, data))));
//        mailUtil.sendMail();
        mailUtil.sendMail(null, "mail", paramMap);
    }
}
