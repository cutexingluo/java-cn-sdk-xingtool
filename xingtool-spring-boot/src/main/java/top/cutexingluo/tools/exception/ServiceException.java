package top.cutexingluo.tools.exception;

import lombok.Getter;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.base.IResultData;
import top.cutexingluo.tools.common.base.XTIntCode;

/**
 * <p>
 * 版本v1.0.0
 * <p>
 * 自定义异常，抛出对象，会被拦截封装
 *
 * @author XingTian
 */
@Getter
public class ServiceException extends RuntimeException implements IResultData<String>, XTIntCode {
    protected static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    protected String code;

    /**
     * 服务异常
     *
     * @param code 代码
     * @param msg  消息
     */
    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 服务异常
     *
     * @param constantsCode 错误常量
     * @param msg           味精
     */
    public ServiceException(Constants constantsCode, String msg) {
        super(msg);
        this.code = constantsCode.getCode();
    }

    /**
     * 服务异常
     *
     * @param resultData 数据接口
     */
    public <T> ServiceException(IResultData<T> resultData) {
        super(resultData.getMsg());
        T code = resultData.getCode();
        if (code instanceof Integer) {
            this.code = String.valueOf(code);
        } else {
            this.code = code.toString();
        }
    }


    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    @Override
    public String getMsg() {
        return super.getMessage();
    }

    @Override
    public int intCode() {
        return Integer.parseInt(code);
    }
}
