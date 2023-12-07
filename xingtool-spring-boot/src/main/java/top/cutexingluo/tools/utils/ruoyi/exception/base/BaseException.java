package top.cutexingluo.tools.utils.ruoyi.exception.base;

import top.cutexingluo.tools.exception.ServiceException;
import top.cutexingluo.tools.utils.ruoyi.utils.MessageUtils;
import top.cutexingluo.tools.utils.ruoyi.utils.StringUtils;


/**
 * 基础异常
 *
 * @author ruoyi
 *
 * updated by @author XingTian
 */
public class BaseException extends ServiceException {
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;


    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        super(code, defaultMessage);
        this.defaultMessage = defaultMessage;
        this.module = module;
        this.code = code;
        this.args = args;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule() {
        return module;
    }

    @Override
    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
