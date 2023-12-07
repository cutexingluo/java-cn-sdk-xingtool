package top.cutexingluo.tools.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 继承自 {@link MSResult}, 根据常用作为替代品
 * <p>兼容 int 类型的 code</p>
 * <p>支持泛型</p>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/13 23:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> extends MSResult<T> {
    public R(MSResult<T> result) {
        super(result.getCode(), result.getMsg(), result.getData());
    }

    public R(int code, String msg, T data) {
        super(code, msg, data);
    }

    public R(Result result) {
        super(result);
    }

    public R(StrResult result) {
        super(result);
    }

    public R(StrMSResult<T> result) {
        super(result);
    }

    /**
     * 得到R, 兼容MSResult
     *
     * @param result 结果
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> getR(MSResult<T> result) {
        return (R<T>) result;
    }
}
