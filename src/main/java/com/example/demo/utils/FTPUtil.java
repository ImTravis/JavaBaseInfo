package com.example.demo.utils;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FTPUtil {
    public static FTPClient ftp;
    public ArrayList<String> arFiles;
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * 重载构造函数
     *
     * @param isPrintCommmand 是否打印与FTPServer的交互命令
     */
    public FTPUtil(boolean isPrintCommmand) {
        ftp = new FTPClient();
        arFiles = new ArrayList<String>();
        if (isPrintCommmand) {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
    }

    /**
     * 登陆FTP服务器
     * @param host     FTPServer IP地址
     * @param port     FTPServer 端口
     * @param username FTPServer 登陆用户名
     * @param password FTPServer 登陆密码
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login(String host, int port, String username, String password) throws IOException {
        this.ftp.connect(host, port);
        if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
            if (this.ftp.login(username, password)) {
                /**
                 需要注意这句代码，如果调用List()方法出现，文件的无线递归，与真实目录结构不一致的时候，可能就是因为转码后，读出来的文件夹与正式文件夹字符编码不一致所导致。
                 则需去掉转码，尽管递归是出现乱码，但读出的文件就是真实的文件，不会死掉。等读完之后再根据情况进行转码。
                 如果ftp部署在windows下，则：*/
//                for (String arFile : f.arFiles) {
//                    arFile = new String(arFile.getBytes("iso-8859-1"), "GBK");
//                    logger.info(arFile);
//                }
                this.ftp.setControlEncoding("UTF-8");
                return true;
            }
        }
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
        return false;
    }

    /**
     * 关闭数据链接
     *
     * @throws IOException
     */
    public void disConnection() throws IOException {
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
    }

    /**
     * 递归遍历出目录下面所有文件
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public void List(String pathName) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(pathName);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    arFiles.add(pathName + file.getName());
                } else if (file.isDirectory()) {
                    // 需要加此判断。否则，ftp默认将‘项目文件所在目录之下的目录（./）’与‘项目文件所在目录向上一级目录下的目录（../）’都纳入递归，这样下去就陷入一个死循环了。需将其过滤掉。
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(pathName + file.getName() + "/");
                    }
                }
            }
        }
    }

    /**
     * 删除文件
     * @param pathname FTP服务器保存目录
     * @param filename 要删除的文件名称
     * @return
     */
    public boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            //切换FTP目录
            ftp.changeWorkingDirectory(pathname);
            ftp.dele(filename);
            ftp.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        }
//        finally {
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return flag;
    }

    /**
     * 递归遍历目录下面指定的文件名
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ext      文件的扩展名
     * @throws IOException
     */
    public void List(String pathName, String ext) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(pathName);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(ext)) {
                        arFiles.add(ROOT_PATH + pathName + file.getName());
                    }
                } else if (file.isDirectory()) {
                    if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        List(pathName + file.getName() + "/", ext);
                    }
                }
            }
        }
    }

    // 协议 - ftp
    public static final String PROTOCOL = "ftp://";
    // 主机IP
    public static final String HOST = "192.168.0.86";
    // 端口
    public static final int PORT = 21;
    // 登录用户
    public static final String USERNAME = "ftpuser";
    // 登录密码
    public static final String PASSWORD = "ftpuser";
    // 目录名称
    public static final String PATH_NAME = "/192.168.0.150/";
    // 根路径
    public static final String ROOT_PATH = PROTOCOL + HOST;

    public static final Base64.Decoder decoder = Base64.getDecoder();

    public static void main(String[] args) throws IOException {
//        downFile();

        List<String> models = new ArrayList<String>();
        models.add("/192.168.0.150/");
        models.add("/192.168.0.148/");

        for(String str : models){
            FTPUtil f = new FTPUtil(true);
            if (f.login(HOST, PORT, USERNAME, PASSWORD)) {
                f.List(str, "jpg");
            }
            for (String arFile : f.arFiles) {
                int size = arFile.length();

                arFile = arFile.substring(arFile.lastIndexOf("/")+1,size);
                InputStream inputStream = ftp.retrieveFileStream(arFile);
                byte[] bytes =read(inputStream);


                System.out.println(System.currentTimeMillis());
                inputStream.close();
                ftp.completePendingCommand();

            }
            f.disConnection();
        }



    }

    public static final String DEFAULT_CHARSET ="UTF-8";
    public static byte[] urlToBytes(String urlStr) {

        InputStream is = null;
        ByteArrayOutputStream os = null;
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            URL url = new URL(UriUtils.encodePath(urlStr, DEFAULT_CHARSET));
//            URL url= new URL(null, urlStr, new sun.net.www.protocol.https.Handler());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "plain/text;charset=" + DEFAULT_CHARSET);
            conn.setRequestProperty("charset", DEFAULT_CHARSET);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(50000);
            conn.connect();
            is = conn.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            logger.error("发起请求出现异常:", e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("【关闭流异常】");
                    return null;
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("【关闭流异常】");
                    return null;
                }
            }
        }

    }

    public static void downFile() {
        // ftp登录用户名
        String userName = "ftpuser";
        // ftp登录密码
        String userPassword = "ftpuser";
        // ftp地址:直接IP地址
        String server = HOST;
        // 创建的文件
        String fileName = "311.jpg";
        // 指定写入的目录
        String path = PATH_NAME;


        FTPClient ftp = new FTPClient();
        try {
            int reply;
            //1.连接服务器
            ftp.connect(server);
            //2.登录服务器 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(userName, userPassword);
            //3.判断登陆是否成功
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            //4.指定要下载的目录
            ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
            //5.遍历下载的目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                //解决中文乱码问题，两次解码
                byte[] bytes=ff.getName().getBytes("iso-8859-1");
                String fn=new String(bytes,"utf8");
                if (fn.equals(fileName)) {
                    InputStream inputStream = ftp.retrieveFileStream(ff.getName());
                    byte[] a = read(inputStream);
                    System.out.print(a);
                }
            }
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    public static byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
