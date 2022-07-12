package com.indi.electricity.mall.utils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 邮件对象
 *
 * @author Administrator
 */
public class MailUtil {
    private static Logger log = LoggerFactory.getLogger(MailUtil.class);
    //特殊账户
    private String host = "";
    private int port = 25;
    private String auth = "";
    private String password = "";

    private boolean enable = true;


    private RestTemplate restTemplate;

    private MailInfo mailInfo;

    private List<AttachmentObject> attachmentList = new ArrayList<>();//附件列表
    private boolean dispositionNotification = false;//设置是否需要回执

    public MailUtil(String host, String auth, String password) {
        this.host = host;
        this.auth = auth;
        this.password = password;
    }

    public MailUtil(String host, int port, String auth, String password) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.password = password;
    }

    public boolean send() {
        return this.send(false);
    }

    public boolean sendMail(String tplPath, String tplName, Map paramMap) throws Exception {
        return this.sendMail(tplPath, tplName, paramMap, false);
    }

    /**
     * @param tplPath
     * @param tplName
     * @param paramMap
     * @param async
     * @return
     * @throws Exception
     */
    public boolean sendMail(String tplPath, String tplName, Map paramMap, boolean async) throws Exception {
        if (StringUtils.isEmpty(tplPath)) {
            tplPath = String.format("%s%s", tplName, this.mailInfo.suffix);
        } else {
            tplPath = String.format("/%s/%s%s", tplPath, tplName, this.mailInfo.suffix);
        }
        String htmlStr = HtmlUtil.generate(tplPath, paramMap);

        this.mailInfo.setBody(htmlStr);
        if (async) {
            (new Thread() {
                public void run() {
                    sendMail();
                }
            }).start();
        } else {
            return sendMail();
        }
        return true;
    }

    /**
     * 发送邮件重载方法：支持异步或者同步发送
     *
     * @param async 是否异步发送
     * @return
     */
    public boolean send(boolean async) {
        if (async) {
            (new Thread() {
                public void run() {
                    sendMail();
                }
            }).start();
        } else {
            return sendMail();
        }
        return true;
    }

    public boolean sendMail() {
        long start, end;
        start = System.currentTimeMillis();
        if (!this.enable) {
            log.info("This Email actually NOT sent since enable=false was set");
            return true;
        }
        boolean ret;
        ret = this.sendBySmtp();
        end = System.currentTimeMillis();
        log.info("sendMail cost is:{}", (end - start));
        return ret;
    }

    /**
     * 增加附件的钩子
     *
     * @param multipart
     */
    public void addDataSource(Multipart multipart) {

    }


    /**
     * 发送邮件的实际方法
     *
     * @return
     */
    private boolean sendBySmtp() {

        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", this.host);
            prop.put("mail.smtp.auth", true);

            Session mailSession = Session.getDefaultInstance(prop);
            MimeMessage message = new MimeMessage(mailSession);
            if (isDispositionNotification()) {
                message.addHeader("Disposition-Notification-To", "1");
            }

            message.setSubject(MimeUtility.encodeText(this.mailInfo.title, "UTF-8", "B"));

            // 发件人
            if (this.mailInfo.fromName != null) {
                message.setFrom(new InternetAddress(this.auth, this.mailInfo.fromName));
            } else {
                message.setFrom(new InternetAddress(this.auth, "系统"));
            }


            HashMap<String, String> hm = new HashMap<>();
            InternetAddress[] address = getInternetAddresses(hm, mailInfo.toList);
            InternetAddress[] ccListAddress = getInternetAddresses(hm, mailInfo.ccList);
            InternetAddress[] bccListAddress = getInternetAddresses(mailInfo.bccList);
            InternetAddress[] rtAddress = getInternetAddresses(mailInfo.rtList);

            message.addRecipients(Message.RecipientType.TO, address);
            message.addRecipients(Message.RecipientType.CC, ccListAddress);
            message.addRecipients(Message.RecipientType.BCC, bccListAddress);
            message.setReplyTo(rtAddress);


            //邮件内容
            MimeBodyPart contextPart = new MimeBodyPart();
            if ("txt".equalsIgnoreCase(this.mailInfo.mode)) {
                contextPart.setText(this.mailInfo.body, "utf-8");
            } else {
                contextPart.setContent(this.mailInfo.body, "text/html;charset=UTF-8");
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(contextPart);

            // 附件
            if (!CollectionUtils.isEmpty(attachmentList)) {
                for (AttachmentObject attachmentObject : attachmentList) {
                    contextPart = new MimeBodyPart();
                    DataSource source = null;
                    if (attachmentObject.getAttachmentData() != null) {
                        source = new ByteDataSource(attachmentObject.getAttachmentData(), attachmentObject.getAttachmentName());
//                        source = new ByteArrayDataSource(attachmentObject.getAttachmentData(),"application/octet-stream");
                    } else if (attachmentObject.getAttachmentFile() != null) {
                        source = new FileDataSource(attachmentObject.getAttachmentFile());
                    } else {
                        continue;
                    }
                    contextPart.setDataHandler(new DataHandler(source));
                    contextPart.setFileName(attachmentObject.getAttachmentName());
                    contextPart.setHeader("Content-ID", attachmentObject.getAttachmentName());
                    multipart.addBodyPart(contextPart);
                }
            }

            // 增加附件的钩子 被子类覆盖MimeBodyPart
            this.addDataSource(multipart);
            message.setContent(multipart);
            message.saveChanges();
            Transport transport = mailSession.getTransport("smtp");
            //transport.connect(this.host, this.auth, this.password);
            transport.connect(this.host, this.port, this.auth, this.password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            log.error("send mail error:", e);
            return false;
        }

        return true;
    }

    private InternetAddress[] getInternetAddresses(List<String> addressList) {
        if (CollectionUtils.isEmpty(addressList)) {
            return new InternetAddress[0];
        }
        return addressList.toArray(new InternetAddress[addressList.size()]);
    }

    private InternetAddress[] getInternetAddresses(HashMap<String, String> hm, List<String> mailList) throws AddressException {
        List<InternetAddress> addressList = new ArrayList<InternetAddress>();
        for (String mail : mailList) {
            if (mail != null && !hm.containsKey(mail.toLowerCase())) {
                addressList.add(new InternetAddress(mail));
                hm.put(mail.toLowerCase(), "");
            }
        }
        return addressList.toArray(new InternetAddress[addressList.size()]);
    }

    @Data
    private class MailInfo {
        private String title;    // 邮件标题
        private String fromName = null;  // 发信人姓名
        private String from;  // 发信人email
        private String body = "";     // 邮件内容

        private String mode = "html";     // 邮件类型，html或txt   默认为html

        private String suffix = ".html";

        private List<String> toList = new ArrayList<>(); //收信人邮箱
        private List<String> ccList = new ArrayList<>(); //收信人邮箱
        private List<String> bccList = new ArrayList<>(); //收信人邮箱
        private List<String> rtList = new ArrayList<>();//回复人邮箱

        public MailInfo(String title, String from, String fromName, List<String> toList, String body) {
            this.title = title;
            this.from = from;
            this.fromName = fromName;
            this.toList = toList;
            this.body = body;
        }

        public MailInfo(String title, String from, String fromName, List<String> toList, String body, String suffix) {
            this.title = title;
            this.from = from;
            this.fromName = fromName;
            this.toList = toList;
            this.body = body;
            this.suffix = suffix;
        }
    }

    private class AttachmentObject {
        private String attachmentName;
        private byte[] attachmentData;
        private File attachmentFile;

        public File getAttachmentFile() {
            return attachmentFile;
        }

        public void setAttachmentFile(File attachmentFile) {
            this.attachmentFile = attachmentFile;
        }

        public String getAttachmentName() {
            return attachmentName;
        }

        public void setAttachmentName(String attachmentName) {
            this.attachmentName = attachmentName;
        }

        public byte[] getAttachmentData() {
            return attachmentData;
        }

        public void setAttachmentData(byte[] attachmentData) {
            this.attachmentData = attachmentData;
        }
    }

    /**
     * 添加附件，二进制数据
     *
     * @param attachmentName
     * @param attachmentData
     */
    public void addAttachment(String attachmentName, byte[] attachmentData) {
        AttachmentObject obj = new AttachmentObject();
        obj.setAttachmentData(attachmentData);
        obj.setAttachmentFile(null);
        try {
            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

            //MimeUtility.encodeWord可以避免文件名乱码
//            obj.setAttachmentName(MimeUtility.encodeWord(attachmentName));
            obj.setAttachmentName(MimeUtility.encodeText(attachmentName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        attachmentList.add(obj);
    }

    /**
     * 添加附件，文件形式
     *
     * @param attachmentName
     * @param attachmentFile
     */
    public void addAttachment(String attachmentName, File attachmentFile) {
        AttachmentObject obj = new AttachmentObject();
        obj.setAttachmentData(null);
        obj.setAttachmentFile(attachmentFile);
        try {
            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

            //MimeUtility.encodeWord可以避免文件名乱码
//            obj.setAttachmentName(MimeUtility.encodeWord(attachmentName));
            obj.setAttachmentName(MimeUtility.encodeText(attachmentName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        attachmentList.add(obj);
    }


    /**
     * 设置邮件信息
     *
     * @param toList String[]
     */
    public void setMailInfo(String title, String fromName, String from, List<String> toList, String body) {
        List<String> emailList = toList.stream().map(oneData -> {
            String to = oneData;
            if (to.indexOf('@') == -1) {
                to += "@gmail.com";
            }
            return to;
        }).collect(Collectors.toList());
        this.mailInfo = new MailInfo(title, from, fromName, emailList, body);
    }

    public void setMailInfo(String title, String fromName, String from, List<String> toList, String body, String suffix) {
        List<String> emailList = toList.stream().map(oneData -> {
            String to = oneData;
            if (to.indexOf('@') == -1) {
                to += "@gmail.com";
            }
            return to;
        }).collect(Collectors.toList());
        this.mailInfo = new MailInfo(title, from, fromName, emailList, body, suffix);
    }

    public boolean isDispositionNotification() {
        return dispositionNotification;
    }


    public class ByteDataSource implements DataSource {
        private byte[] filebyte = null;
        private String filetype = "application/octet-stream";
        private String filename = "";
        private OutputStream outputstream = null;
        private InputStream inputstream = null;

        public ByteDataSource() {
        }

        public ByteDataSource(String FileName) {
            File f = new File(FileName);
            filename = f.getName();
            try {
                inputstream = new FileInputStream(f);
                inputstream.read(filebyte);

            } catch (Exception e) {
            }
        }

        public ByteDataSource(byte[] filebyte, String displayfilename) {
            this.filebyte = filebyte;
            this.filename = displayfilename;
        }

        public String getContentType() {
            return filetype;
        }

        public InputStream getInputStream() throws IOException {
            InputStream input = new ByteArrayInputStream(filebyte);
            return input;
        }

        public String getName() {
            return filename;
        }

        public OutputStream getOutputStream() throws IOException {
            outputstream.write(filebyte);
            return outputstream;
        }
    }

}
