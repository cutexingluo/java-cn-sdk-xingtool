package top.cutexingluo.tools.exception;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;

import javax.annotation.PostConstruct;

/**
 * 业务拦截返回异常并封装返回
 *
 * @author XingTian
 * <p>
 * 版本v1.0.0
 */
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "global-exception", havingValue = "true", matchIfMissing = false)
@Slf4j
@RestControllerAdvice
public class XTGlobalExceptionHandler {
    public static final String TAG = " XTGlobalExceptionHandler" + ":";

    @PostConstruct
    public void init() {
        log.info("XingTool GlobalExceptionHandler is enabled ---> {}", "全局异常拦截，已开启");
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result duplicateKeyException(DuplicateKeyException e) {
        log.error(TAG + "数据添加错误", e);
        return Result.error(Constants.CODE_500, "数据重复");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg;
        try {
            msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } catch (Exception be) {
            msg = "";
        }
        log.warn(TAG + "参数校验错误 -> {}", msg);
        return Result.error("参数校验错误");
    }

    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionError(ServiceException e) {
        String code = e.getCode();
        if (StrUtil.isNotBlank(code)) {
            return Result.error(e.intCode(), e.getMessage());
        }
        log.warn(TAG + "服务错误: " + e.getMessage());
//        log.warn("服务错误", e); // 不用打印在控制台
        return Result.error(e.getMsg());
    }


    @ExceptionHandler(value = Exception.class)
    public Result exceptionError(Exception e) {
        log.error(TAG + "未知错误 ", e);
        return Result.error("内部未知错误");
    }
    /**
     *  too many ServiceException.class will be error
     */
//    /**
//     * @param se 业务异常
//     * @return Result
//     */
//    @Deprecated
//    @ExceptionHandler({ServiceException.class})
////    @ResponseBody
//    public Result handle(ServiceException se) {
//        return Result.error(se.getCode(), se.getMessage());
//    }
}
