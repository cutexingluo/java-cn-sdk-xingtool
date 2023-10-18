package top.cutexingluo.tools.utils.se.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.multipart.MultipartFile;
import top.cutexingluo.tools.designtools.method.XTObjUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件IO工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 15:21
 */
public class XTFileIO {
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    //需要设置请求地址
//    @Value("http://${server.ip}:9090/file/")
//    private static String RequestUrl;        //= "http://localhost:9090/file/"

//    public static String getRequestUrl() {
//        return RequestUrl;
//    }
//
//    public static void setRequestUrl(String nowRequestUrl) {
//        RequestUrl = nowRequestUrl;
//    }

    public static String addNamePreSlash(String s) { //前面加斜线
        if (s.charAt(0) != '/') s = "/" + s;
        return s;
    }

    public static String addUrlSlash(String s) {//后面加斜线
        if (s.charAt(s.length() - 1) != '/') s += "/";
        return s;
    }

    //*******************

    /**
     * @param file 文件
     * @return XTFile 得到文件的信息包
     * @throws IOException 读写异常
     */
    public static XTFile getXTFile(MultipartFile file) throws IOException {
        //获取原文件数据
        String originalFilename = file.getOriginalFilename();
        //file.getContentType()
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize() / 1024;//KB
        String uuid = IdUtil.fastSimpleUUID(); // 定义文件唯一标识
        //获取md5码
        String md5 = SecureUtil.md5(file.getInputStream());
        return new XTFile(originalFilename, uuid, type, size, md5, null);
    }

    /**
     * 需要提前判定md5码是否冲突
     *
     * @param file               上传的文件
     * @param xtFile             获取的xtFile整合包
     * @param fileUploadDiskPath 要上传的磁盘路径
     * @throws IOException 读写异常
     */
    //存到磁盘
    public static void uploadToDisk(MultipartFile file, XTFileSource xtFile, String fileUploadDiskPath) throws IOException {
        String urlSlash = addUrlSlash(fileUploadDiskPath);
        File uploadFile = new File(urlSlash + xtFile.getFileUUID());//含路径文件
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {//创建文件夹
            parentFile.mkdirs();
        }
        file.transferTo(uploadFile); // 存储到磁盘中
    }

    /**
     * @param filePath 文件路径
     * @param fileUUID 文件名
     * @param response 请求
     * @throws IOException 读写异常
     */
    //下载，输入 下载路径和下载文件名 以及 请求对象
    public static void download(String filePath, String fileUUID, HttpServletResponse response) throws IOException {
        File file = new File(filePath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileUUID, StandardCharsets.UTF_8.name()));
        response.setContentType("application/octet-stream");
        //读取文件字节流
        try {
            os.write(FileUtil.readBytes(file));
        } finally {
            os.flush();
            os.close();
        }
    }


    //获得md5码反射备用版
    public static <T, TM> T getFileByMd5(Class<T> fileType, TM fileMapper, String md5ColName, String md5) throws InvocationTargetException, IllegalAccessException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(md5ColName, md5);
        Method method = ReflectUtil.getMethod(fileMapper.getClass(), "selectList", QueryWrapper.class);
        List<T> filesList = (List<T>) method.invoke(fileMapper, queryWrapper);
//        List filesList = filesMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    public static <T, TM> T getFileByMd5(Class<T> fileType, TM fileMapper, String md5) throws InvocationTargetException, IllegalAccessException {
        return getFileByMd5(fileType, fileMapper, "md5", md5);
    }

    /**
     * @param DBFile     数据库的实体类
     * @param fileMapper 数据库的DAO层
     * @param fieldName  字段名数组,默认原始名，扩展名，大小, md5码
     * @param xtFile     XTFile信息包
     * @param url        需要存入的url
     * @param <T>        DBFile的类型
     * @param <TM>       数据库的DAO层Mapper类型
     */
    //存到数据库反射备用版
    public static <T, TM> void saveToDB(T DBFile, TM fileMapper, String[] fieldName, XTFile xtFile, String url) {
        ReflectUtil.setFieldValue(DBFile, "url", url);
        ReflectUtil.setFieldValue(DBFile, fieldName[0], xtFile.getOriginalFilename());
        ReflectUtil.setFieldValue(DBFile, fieldName[1], xtFile.getType());
        ReflectUtil.setFieldValue(DBFile, fieldName[2], xtFile.getSize()); //KB
        ReflectUtil.setFieldValue(DBFile, fieldName[3], xtFile.getMd5());
        ReflectUtil.invoke(fileMapper, "insert", DBFile);
//        fileMapper.insert(saveFIle);
    }

    /**
     * 字段名数组,默认原始名，扩展名，大小, md5码
     * 默认 "name","type","size","md5"
     */
    public static <T, TM> void saveToDB(T DBFile, TM fileMapper, XTFile xtFile, String url) {
        String[] fieldName = {"name", "type", "size", "md5"};
        saveToDB(DBFile, fileMapper, fieldName, xtFile, url);
    }

    /**
     * @param file           上传文件
     * @param fileType       DB文件类型
     * @param fileMapper     DBMapper对象
     * @param urlPrefix      url前缀
     * @param fileUploadPath 磁盘父路径
     * @return url
     * <p>
     * 参数为 上传文件，DB文件类型，DBMapper对象,url前缀，磁盘父路径
     * 返回值为 url
     * <p>
     * 全部默认值 上传
     * 实体类关键字如下
     * "name","type","size","md5","url",
     * DB字段
     * "insert" ,"selectList"
     */
    //上传反射备用版
    public static <T, TM> String upload(MultipartFile file, Class<T> fileType, TM fileMapper, String urlPrefix, String fileUploadPath)
            throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        XTFile xtFile = getXTFile(file);
//        return xtFile.getFileUUID();
        T files = getFileByMd5(fileType, fileMapper, "md5", xtFile.getMd5());
//        System.out.println(xtFile.getFileUUID());
        String url;
        if (files == null) {//如果没有就存到磁盘
            url = urlPrefix + xtFile.getFileUUID();
            uploadToDisk(file, xtFile, fileUploadPath);
        } else {
//            url = files.getUrl();
//            url= (String) ReflectUtil.getFieldValue(files,"url"); //获取字段
            url = (String) XTObjUtil.getProperty(files, "getUrl");//获取方法
        }
        //存储到数据库
        T newFiles = fileType.getConstructor().newInstance();
        saveToDB(newFiles, fileMapper, xtFile, url);
        return url;
    }
}
