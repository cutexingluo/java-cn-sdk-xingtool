package top.cutexingluo.tools.utils.se.file;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/6/25 12:01
 */
@FunctionalInterface
public interface XTFileSource {

    /**
     * 得到新文件全称
     *
     * @return {@link String}
     */
    String getFileUUID();
}
