package top.cutexingluo.tools.utils.se.file.pkg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * XTFile 捆绑包
 * <p>含原文件</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2024/3/2 18:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XTFileBundle {

    protected MultipartFile multipartFile;

    protected XTFile xtFile;

    public XTFileBundle(MultipartFile multipartFile) throws IOException {
        this.multipartFile = multipartFile;
        this.xtFile = new XTFile(multipartFile);
    }
}
