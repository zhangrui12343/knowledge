package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 文件处理工具
 *
 * @author huang_kangjie
 * @date 2020/3/19 0019 20:40
 */
@Slf4j
public class FileUtil {

     /**
      * 本地文件转换成base64字符串
      * @param filePath	文件全路径 （注意：带文件名）
      *  (将文件转化为字节数组字符串，并对其进行Base64编码处理)
      * @return base64字符串
      */
     public static String fileToBase64(String filePath) throws IOException {
          byte[] data;
          // 读取字节数组
          InputStream in = new FileInputStream(filePath);
          data = new byte[in.available()];
          in.read(data);
          in.close();
          // 返回Base64编码过的字节数组字符串
          return Base64Utils.encodeToString(data);
     }


     public static boolean isEmpty(MultipartFile file){
          return file==null||file.isEmpty();
     }

     /**
      * base64字符串转换成 (对字节数组字符串进行Base64解码并生成)
      * @param base64Str base64字符串
      * @param filePath	 指定存放路径  （注意：带文件名）
      * @return base64字符串转文件
      */
     public static boolean base64ToFile(String base64Str,String filePath) {
          // 图像数据为空
          if (StringUtils.isEmpty(base64Str)) {
               return false;
          }
          try {
               // Base64解码
               byte[] b = Base64Utils.decodeFromString(base64Str);
               for (int i = 0; i < b.length; ++i) {
                    // 调整异常数据
                    if (b[i] < 0) {
                         b[i] += 256;
                    }
               }

               OutputStream out = new FileOutputStream(filePath);
               out.write(b);
               out.flush();
               out.close();

               return true;
          } catch (Exception e) {
               log.error("文件base64字符串转文件出错：" + e.getMessage(), e);
               return false;
          }

     }
     public static String getBase64FilePath(String filePath) {
          try {
               filePath = URLEncoder.encode(
                       Base64Utils.encodeToString(
                               filePath.getBytes(StandardCharsets.UTF_8)), "utf-8");
          } catch (Exception e) {
               log.error("导出文件地址base64出错:" + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_KEY_PARAM_ERR);
          }
          return filePath;
     }
     public static String getFilePath(String base64FilePath) {
          try {
               base64FilePath = URLDecoder.decode(new String(Base64Utils.decodeFromString(base64FilePath), StandardCharsets.UTF_8), "utf-8");
          } catch (Exception e) {
               log.error("导出文件地址base64出错:" + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_KEY_PARAM_ERR);
          }
          return base64FilePath;
     }
     /**
      * 根据base64的编码 解码成字符串
      * @param base64Str base64Str
      * @return 字符串
      */
     public static String base64ToString(String base64Str) {
          // Base64解码
          byte[] b = Base64Utils.decodeFromString(base64Str);
          return new String(b);
     }

     /**
      * 往文件里面写内容
      * @param content 文本内容
      * @param destFile 目标文件地址
      */
     public static void write(String content, String destFile) throws Exception {
          File desFile = new File(destFile);
          String fileNewName;
          File newFile = null;
          if(desFile.exists()) {
               fileNewName = destFile + "-副本-" + getTime() + getSuffix(destFile);
               newFile = new File(fileNewName);
               boolean flag = desFile.renameTo(newFile);
               log.info("文件备份 fileNewName = {}, flag = {}", fileNewName, flag);
          }
          try {
               FileWriter desWriter = new FileWriter(desFile);
               desWriter.write(content);
               desWriter.close();
          } catch (IOException e) {
               //删除失败则还原原文件
               try {
                    desFile.renameTo(desFile);
               } catch (Exception ignore){

               }
               throw new IOException("更新文件出错：" + e.getMessage());
          }
          try {
               if(newFile != null) {
                   boolean flag = newFile.delete();
                   log.info("副本文件删除状态 flag = {}", flag);
               }
          } catch (Exception e) {
               log.error("副本文件删除失败: " + e.getMessage(), e);
          }
     }

     public static String getTime(){
          return TimeUtil.getTime().replace("-","")
                  .replace(" ", "")
                  .replace(":", "");
     }

     /**
      * 获取文件字符串
      * @param path 目标文件所在地址
      * @return     返回字符串
      */
     public static String getResource(String path) {
          File file = new File(path);
          FileInputStream fin;
          InputStream is;
          try {
               fin = new FileInputStream(file);
               is = fin;
               Reader reader = new InputStreamReader(is);
               int tempchar;
               StringBuilder sb = new StringBuilder();
               //去掉换行和回车
               while ((tempchar = reader.read()) != -1) {
                    if (((char) tempchar) != '\r' && ((char) tempchar) != '\n') {
                         sb.append((char) tempchar);
                    }
               }
               reader.close();
               return sb.toString();
          } catch (Exception e) {
               log.error("读取文件出错：" + e.getMessage(), e);
          }
          return null;
     }

     /**
      * 获取文件字符串
      * @param file 目标文件
      * @return     返回字符串
      */
     public static String getResource(File file) {
          FileInputStream fin;
          InputStream is;
          try {
               fin = new FileInputStream(file);
               is = fin;
               Reader reader = new InputStreamReader(is);
               int tempchar;
               StringBuilder sb = new StringBuilder();
               //去掉换行和回车
               while ((tempchar = reader.read()) != -1) {
                    if (((char) tempchar) != '\r' && ((char) tempchar) != '\n') {
                         sb.append((char) tempchar);
                    }
               }
               reader.close();
               return sb.toString();
          } catch (Exception e) {
               log.error("读取文件出错：" + e.getMessage(), e);
          }
          return null;
     }

     /**
      * 获取文件的后缀名
      * @param fileName 文件名
      * @return     后缀名包含点 .xml .doc
      */
     public static String getSuffix(String fileName) {
          return fileName.substring(fileName.lastIndexOf("."));
     }

     /**
      * 获取项目的根路径
      * @return 返回当前项目所在路径
      */
     public static String getRoot() {
          try {
               File directory = new File("");
               return directory.getCanonicalPath().replace("\\","/");
          } catch (Exception e) {
               log.error("获取项目根路径出错：" + e.getMessage(), e);
          }

          return "C:/";
     }

     /**
      * 获取当前系统所在盘符
      * @return C:
      */
     public static String getCurrentDiskDriver(){
          return getRoot().split(":")[0] + ":";
     }

     /**
      * 获取项目的根路径
      * @return 返回当前项目所在路径
      */
     public static String getRootParent() {
          try {
               File directory = new File(getRoot());
               return directory.getParent();
          } catch (Exception e) {
               log.error("获取项目根路径出错：" + e.getMessage(), e);
          }

          return "C:/";
     }

     /**
      * 校验资源文件是否存在
      * @param path 资源文件绝对地址
      */
     public static File checkFileExist(String path){
          File file = new File(path);
          if(!file.exists()) {
               throw new CustomException(ErrorCode.SYS_FILE_NOT_EXIST, "'" + path + "', file is not found !!!");
          }
          return file;
     }

     /**
      * 获取文件的下载地址
      * @return 下载地址
      */
     public static String getDownloadPath(){
          return getRootParent() + "/download";
     }

     /**
      * 删除文件夹
      * @param dir 文件夹地址
      * @return 删除结果
      */
     public static boolean deleteDir(File dir) {
          if (dir.isDirectory()) {
               String[] children = dir.list();
              if(children != null && children.length != 0) {
                   //递归删除目录中的子目录下
                   for (int i=0; i<children.length; i++) {
                        boolean success = deleteDir(new File(dir, children[i]));
                        if (!success) {
                             return false;
                        }
                   }
              }
          }
          // 目录此时为空，可以删除
          return dir.delete();
     }

     /**
      * 下载文件
      * @param response response
      * @param path 文件绝对地址 如：D:/test/test.txt
      */
     public static void download(HttpServletResponse response, String path) {
          try {
               // path是指欲下载的文件的路径。
               File file = new File(path);
               if(!file.exists()) {
                    throw new CustomException(ErrorCode.SYS_FILE_NOT_EXIST, path + "not exsit!");
               }
               // 取得文件名。
               String filename = file.getName();
               // 取得文件的后缀名。
               String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

               // 以流的形式下载文件。
               InputStream fis = new BufferedInputStream(new FileInputStream(path));
               byte[] buffer = new byte[fis.available()];
               //sonar校验读取了多少字节
               int read = fis.read(buffer);
               log.info("download file read byte read = {}", read);
               fis.close();
               // 清空response
               response.reset();
               //判断文件名是否是中文
               if(StringUtil.isChinese(filename)) {
                    filename = new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
               } else {
                    filename = new String(filename.getBytes(), StandardCharsets.UTF_8);
               }
               // 设置response的Header
               response.addHeader("Content-Disposition", "attachment;filename=" + filename);
               response.addHeader("Content-Length", "" + file.length());
               OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
               response.setContentType("application/octet-stream");
               toClient.write(buffer);
               toClient.flush();
               toClient.close();
          } catch (Exception ex) {
               log.error("download error ：" + ex.getMessage(), ex);
          }
     }

     /**
      * 获取wcms4的项目路径
      * @return wcsm4.0的路径
      */
     public static String getWcms4Root(){
          return getCMSRoot() + "/WCMS4.0";
     }

     /**
      * C:/Program Files (x86)/CMS Server/TransmitServer/BaseDataServer/dist_lib
      * @return
      */
     @SuppressWarnings({"all"})
     public static String getCMSRoot(){
          //获取项目的运行路径
          String root = getRoot();
          //获取路径的数组
          String[] roots = root.split("/");
          List<String> rootList = Arrays.asList(roots);
          //从dist_lib往前退3节目录，获取出来C:/Program Files (x86)/CMS Server
          if(roots.length >=4) {
               rootList = rootList.subList(0, rootList.size() - 3);
               StringBuilder sb = new StringBuilder();
               for(int i = 0; i < rootList.size(); i++) {
                    sb.append(rootList.get(i));
                    if(i != rootList.size() - 1) {
                         sb.append("/");
                    }
               }
               return sb.toString();
          }
          return "";
     }

     /**
      * 获取mysql的路径
      * 本地 mysql C:\Program Files\MySQL\MySQL Server 5.6
      * @return C:\Program Files (x86)\CMS Server\MySql5.5
      */
     @SuppressWarnings({"all"})
     public static String getMysqlRoot(){
          //return "C:\\Program Files\\MySQL\\MySQL Server 5.6";
          String cmsRoot = getCMSRoot();
          File cms = new File(cmsRoot);
          File[] files = cms.listFiles();
          if(files == null) {
               return cmsRoot + "/MySql";
          }
          for(File file : files) {
               if(file.getName().toLowerCase().startsWith("mysql")){
                    return cmsRoot + "/" + file.getName();
               }
          }
          return cmsRoot + "/MySql";
     }

     /**
      * 根据文件路径创建文件所在文件夹，并返回文件夹地址
      * @param filePath 文件的绝对路径，比如： d:/c/test/a.txt
      * @return d:/c/test
      */
     public static String mkdir(String filePath){
          File file = new File(filePath);
          File parent = new File(file.getParent());
          if(!parent.exists()) {
               parent.mkdirs();
          }
          return file.getParent();
     }

     /**
      * 创建文件，并填充0
      * @param filePath 文件的绝对路径，比如： d:/c/test/a.txt
      * @return d:/c/test
      */
     public static void createAndFill(String filePath, long fileSize) {
          mkdir(filePath);
          File file = new File(filePath);
          try {
               if(!file.exists()) {
                    boolean flag = file.createNewFile();
                    if(!flag) {
                         throw new CustomException(ErrorCode.SYS_CREATE_FIFLE_ERR);
                    }
               }
          } catch (Exception e) {
               log.error("createAndFill error: " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_CREATE_FIFLE_ERR);
          }
     }

     /**
      * 获取文件的字节数组
      * @param file 文件
      * @return byte[]
      */
     public static byte[] getFileByteArray(File file) {
          ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
          BufferedInputStream in = null;
          try {
               in = new BufferedInputStream(new FileInputStream(file));
               int bufSize = 1024;
               byte[] buffer = new byte[bufSize];
               int len = 0;
               while (-1 != (len = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len);
               }
               return bos.toByteArray();
          } catch (IOException e) {
               log.error("getFileByteArray error : " + e.getMessage(), e);
               return new byte[0];
          } finally {
               try {
                    if(in != null) {
                         in.close();
                    }
               } catch (IOException ignore) {
               }
               try {
                    bos.close();
               } catch (IOException ignore) {
               }
          }
     }

     /**
      * 获取文件的md5
      * @param path
      * @return
      */
     public static String md5(String path){
          return Md5Util.getMD5(getFileByteArray(new File(path)));
     }

     /**
      * 判断某个文件是否存在
      * @param filePath 文件的绝对地址
      * @return true:存在 false:不存在
      */
     public static boolean isExist(String filePath){
          return new File(filePath).exists();
     }

}



