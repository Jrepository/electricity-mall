package com.indi.electricity.mall.utils;


import freemarker.template.Template;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author: admin
 */
@Log4j2
public class HtmlUtil {

    public static String generate(String tplName, Map paramMap) {
        BufferedWriter writer = null;
        try {
            Template template = FreemarkerUtil.getFreemarkerTemplate(tplName);
            StringWriter stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            template.process(paramMap, writer);
            return stringWriter.toString();
        } catch (Exception e) {
            log.error("generate template for ({}) error:", tplName, e);
        } finally {
            if (writer != null) {
                flush(writer);
                close(writer);
            }
        }
        return null;
    }

    private static void flush(BufferedWriter writer) {
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void close(BufferedWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
