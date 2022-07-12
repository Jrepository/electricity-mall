package com.indi.electricity.mall.utils;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: url转成MultipartFile
 * @author: admin
 */
public class MultipartFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(MultipartFileUtil.class);

    /**
     * inputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        //System.getProperty("java.io.tmpdir")临时目录+File.separator目录中间的间隔符+文件名
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
//        if (file.exists()) {
//            return file;
//        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * file转multipartFile
     */
    public static MultipartFile fileToMultipartFile(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(file.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonsMultipartFile(item);
    }

    /**
     * url转MultipartFile
     */
    public static MultipartFile urlToMultipartFile(String url) {
        File file = null;
        MultipartFile multipartFile = null;
        try {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            file = MultipartFileUtil.inputStreamToFile(httpUrl.getInputStream(), httpUrl.getURL().getFile());
            multipartFile = MultipartFileUtil.fileToMultipartFile(file);
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile;
    }


    public static Map<String, List<String[]>> readExcelBySheetName(String httpUrl, int startRow, int startCell) throws Exception {
       //把远程文件地址转换成URL格式
        URL url = new URL(httpUrl);
        URLConnection connection = url.openConnection();
        InputStream fin = connection.getInputStream();
        //文件后缀为xlsx，所以用XSSF
        XSSFWorkbook workbook = new XSSFWorkbook(fin);
        Map<String, List<String[]>> list = new HashMap();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); ++sheetNum) {
            XSSFSheet sheet = workbook.getSheetAt(sheetNum);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            for (int rowNum = firstRowNum + startRow; rowNum <= lastRowNum; ++rowNum) {
                Row row = sheet.getRow(rowNum);
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                String[] cells = new String[lastCellNum];
                for (int cellNum = firstCellNum + startCell; cellNum < lastCellNum; ++cellNum) {
                    Cell cell = row.getCell(cellNum);
                    cells[cellNum] = ExcelUtil.getCellValue(cell);
                }

                if (list.containsKey(sheet.getSheetName())) {
                    ((List) list.get(sheet.getSheetName())).add(cells);
                } else {
                    List<String[]> tmpSheet = new ArrayList();
                    tmpSheet.add(cells);
                    list.put(sheet.getSheetName(), tmpSheet);
                }
            }
        }
        return list;
    }


    /**
     * 根据地址获得数据的输入流
     *
     * @param strUrl 网络连接地址
     * @return url的输入流
     */
    public static InputStream getInputStreamByUrl(String strUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }


    /**
     * 获取封装得MultipartFile
     *
     * @param fileUrl
     * @return
     * @throws Exception
     */
    public static MultipartFile getMultipartFile(String fileUrl) throws Exception {
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(fileUrl).openConnection();
        FileItem fileItem = createFileItem(httpUrl.getInputStream(), httpUrl.getURL().getFile());
        return new CommonsMultipartFile(fileItem);
    }


    /**
     * FileItem类对象创建
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return FileItem
     */
    public static FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fileName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        OutputStream os = null;
        //使用输出流输出输入流的字节
        try {
            os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("文件上传失败");
        } finally {
        }

        return item;
    }

    public static MultipartFile createFileItem(String url) throws Exception {
        FileItem item = null;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(30000);
        conn.setConnectTimeout(30000);
        // 设置应用程序要从网络连接读取数据
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        String fileName = "";
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            //获取文件名称
            String newUrl = conn.getURL().getFile();
            if (newUrl != null || newUrl.length() <= 0) {
                newUrl = URLDecoder.decode(newUrl, "UTF-8");
                int pos = newUrl.indexOf('?');
                if (pos > 0) {
                    newUrl = newUrl.substring(0, pos);
                }
                pos = newUrl.lastIndexOf('/');
                fileName = newUrl.substring(pos + 1);
            }
            FileItemFactory factory = new DiskFileItemFactory(16, null);
            logger.info("文件名为：" + fileName + "  大小" + (conn.getContentLength() / 1024) + "KB");
            item = factory.createItem(fileName, MediaType.MULTIPART_FORM_DATA_VALUE, false, fileName);
            OutputStream os = item.getOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024 * conn.getContentLength()];
            while ((bytesRead = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
        }

        if (item != null) {
            return new CommonsMultipartFile(item);
        }
        return null;
    }

    /**
     * url转变为 MultipartFile对象
     *
     * @param url
     * @param fileName
     * @return
     * @throws Exception
     */
    private static MultipartFile createFileItem(String url, String fileName) throws Exception {
        FileItem item = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            //设置应用程序要从网络连接读取数据
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                FileItemFactory factory = new DiskFileItemFactory(16, null);
                item = factory.createItem(fileName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                OutputStream os = item.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败", e);
        }
        return new CommonsMultipartFile(item);
    }


    public String getFileName(String urlStr) {
        String fileName = null;
        try {
            URL url = new URL(urlStr);
            URLConnection uc = url.openConnection();
            fileName = uc.getHeaderField("Content-Disposition");
            fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
            fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
            logger.info("文件名为：" + fileName + "  大小" + (uc.getContentLength() / 1024) + "KB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}

