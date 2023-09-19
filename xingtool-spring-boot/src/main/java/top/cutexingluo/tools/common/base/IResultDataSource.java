package top.cutexingluo.tools.common.base;

/**
 * IResultDataSource 提供 set方法
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/14 20:00
 */
public interface IResultDataSource<C> extends IResultData<C> {
    IResultDataSource<C> setCode(C code);

    IResultDataSource<C> setMsg(String msg);

}
