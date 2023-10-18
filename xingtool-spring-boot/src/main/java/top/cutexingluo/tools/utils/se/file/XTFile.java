package top.cutexingluo.tools.utils.se.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * 一个 File Data 封装类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 15:17
 */
// DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XTFile implements XTFileSource {
    /**
     * 原始名
     */
    private String originalFilename; // 原始名
    /**
     * 唯一 id
     */
    private String uuid; // 唯一 id
    /**
     * 扩展名
     */
    private String type; // 扩展名
    /**
     * 大小, 默认Byte
     */
    private Long size; // 大小
    /**
     * md5码
     */
    private String md5; //md5码

    /**
     * 新文件全称
     */
    private String fileUUID;//新文件全称

    public XTFile(String originalFilename, String uuid, String type, Long size, String md5) {
        this(originalFilename, uuid, type, size, md5, null);
    }

    /**
     * 得到file文件
     *
     * @param file 文件
     * @throws IOException ioexception
     */
    public XTFile(MultipartFile file) throws IOException {
        //获取原文件数据
        this.originalFilename = file.getOriginalFilename();
        //file.getContentType()
        this.type = FileUtil.extName(originalFilename);
        this.uuid = IdUtil.fastSimpleUUID(); // 定义文件唯一标识
        //获取md5码
        this.md5 = SecureUtil.md5(file.getInputStream());
        this.size = file.getSize(); //获取大小
        this.fileUUID = getFileUUID();
    }

    /**
     * 得到file文件
     *
     * @param file 文件
     * @throws IOException ioexception
     */
    public XTFile(File file) throws IOException {
        //获取原文件数据
        this.originalFilename = file.getName();
        //file.getContentType()
        this.type = FileUtil.extName(originalFilename);
        this.uuid = IdUtil.fastSimpleUUID(); // 定义文件唯一标识
        //获取md5码
        this.md5 = SecureUtil.md5(file);
        this.size = file.length();
        this.fileUUID = getFileUUID();
    }

    //替代方法
    @Override
    @PostConstruct //重写 方法
    public String getFileUUID() {
        if (StrUtil.isBlank(fileUUID)) fileUUID = uuid + StrUtil.DOT + type;
        return fileUUID;
    }

    public String getURL(String urlPrefix) {
        return urlPrefix + getFileUUID();
    }


    public File toFile() {
        return new File(this.getFileUUID());
    }
}
