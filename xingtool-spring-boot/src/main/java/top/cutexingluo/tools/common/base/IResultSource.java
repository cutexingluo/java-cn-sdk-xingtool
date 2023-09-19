package top.cutexingluo.tools.common.base;

/**
 * IResultSource 提供set方法
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/14 19:58
 */
public interface IResultSource<C, T> extends IResult<C, T>, IResultDataSource<C> {

    IResultSource<C, T> setData(T data);
}
