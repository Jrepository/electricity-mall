package com.indi.electricity.mall.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @author: admin
 */
public class FreemarkerUtil {

    public static Configuration configuration;
    public static final String TEMPLATE_PATH="/templates/";
    public static final String ENCOD_UTF8="utf-8";


    static {
        //创建一个Configuration对象
        configuration = new Configuration();
        // 告诉config对象模板文件存放的路径。
//        configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
        configuration.setClassForTemplateLoading(FreemarkerUtil.class,TEMPLATE_PATH);
        // 设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding(ENCOD_UTF8);
    }

    public static Template getFreemarkerTemplate(String name) throws IOException {
        //从config对象中获得模板对象。需要制定一个模板文件的名字。
        return configuration.getTemplate(name);
    }
}
