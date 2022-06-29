package com.indi.electricity.mall.utils;

import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FreemarkerUtilTest extends BaseTest {

    @Test
    void getPathTest() {
        output( this.getClass().getResource("/templates/mail.ftl"));
    }

    @Test
    void getFreemarkerTemplateTest() throws IOException {
        Template template = FreemarkerUtil.getFreemarkerTemplate("mail.ftl");
        output(template);
    }

}
