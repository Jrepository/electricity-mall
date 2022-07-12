package com.indi.electricity.mall.utils;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author: admin
 */
public class FileUtil {

    public static byte[] getByte(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.flush();
        return bytes;
    }

}
