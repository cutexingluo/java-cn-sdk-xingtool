package top.cutexingluo.tools.utils.se.office.Excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/2 22:01
 */
public class ExcelUtil {
    /**
     * @param alias    别名,输出的表格头,最好使用LinkedHashMap,按插入顺序排序
     *                 Map< entityString , excelString  >  entityString  =>  excelString
     * @param list     实体的列表数据
     * @param <Entity> 实体
     * @return
     * @Author XingTian
     */
    //需导入 hutool 依赖 必须与export连用
    public static <Entity> ExcelWriter getExcelWriter(@Nullable Map<String, String> alias, @Nullable List<Entity> list) {
        // 通过工具类创建writer，写到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter("d:/writeBeanTest.xlsx");
        // 内存操作，写出到浏览器
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);

        if (alias != null) { // 提高性能，写入别名
            Iterator<Map.Entry<String, String>> iterator = alias.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                writer.addHeaderAlias(entry.getKey(), entry.getValue());
            }
        }
        //一次性写出list内容对象到excel,使用默认样式，强制输出标题
        writer.write(list, true);
//        // 合并单元格后的标题行，使用默认标题样式
//        writer.merge(4, "一班成绩单");
        // 关闭writer，释放内存
        return writer;
    }

    /**
     * @param response       HttpServletResponse对象
     * @param getExcelWriter 需使用XTUtils.getExcelWriter 获取 ExcelWriter
     * @param fileName       文件名
     * @throws IOException
     * @author XingTian
     */
    //导出命令
    public static void export(@NonNull HttpServletResponse response,
                              @NonNull ExcelWriter getExcelWriter,
                              @DefaultValue("导出文档") String fileName) throws IOException {
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;" +
                "charset=utf-8");
        String filename = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        getExcelWriter.flush(out, true);
        out.close();
        getExcelWriter.close();
    }

    /**
     * @param file MultipartFile类型，通过导入
     *             cotroller MultipartFile对象名必须与post请求名一致
     * @return ExcelReader 返回reader对象 用法如下
     * List < User> list = reader.readAll(User.class); //获取数据列表
     * @throws IOException
     * @author XingTian
     */
    public static ExcelReader getExcelReader(@Nullable Map<String, String> alias,
                                             @NonNull MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(inputStream);
        if (alias != null) { // 提高性能，写入别名
            Iterator<Map.Entry<String, String>> iterator = alias.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                reader.addHeaderAlias(entry.getValue(), entry.getKey());
            }
        }
        /*
        List<User> list = reader.readAll(User.class); //获取数据列表
         */
        return reader;
    }

    /**
     * getExcelReader重载方法
     *
     * @param alias     如果是英语，这个可为空
     * @param file      输入文件
     * @param isEnglish 如果表头是英语可以不用映射
     * @return 返回reader对象
     */
    public static ExcelReader getExcelReader(@Nullable Map<String, String> alias,
                                             @NonNull MultipartFile file,
                                             boolean isEnglish) throws IOException {
        if (isEnglish) {
            return getExcelReader(null, file);
        } else {
            return getExcelReader(alias, file);
        }
    }
}
