package top.cutexingluo.tools.common.base;

/**
 * IResult 接口
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/13 20:34
 */
public interface IResult<C, T> extends IResultData<C> {
    T getData();
}
