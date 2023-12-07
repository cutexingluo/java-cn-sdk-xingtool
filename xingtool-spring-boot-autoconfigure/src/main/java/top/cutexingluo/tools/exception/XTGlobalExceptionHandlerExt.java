package top.cutexingluo.tools.exception;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;

import javax.annotation.PostConstruct;

/**
 * Sa-token 异常拦截
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/26 17:54
 */
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "global-exception-ext", havingValue = "true", matchIfMissing = false)
@ConditionalOnClass({NotPermissionException.class, SaTokenException.class})
@Slf4j
@RestControllerAdvice
public class XTGlobalExceptionHandlerExt {
    public static final String TAG = " XTGlobalExceptionHandlerExt" + ":";

    @PostConstruct
    public void init() {
        log.info("XingTool GlobalExceptionHandlerExt is enabled ---> {}", "全局异常扩展拦截，已开启，支持sa-token");
    }

    @ConditionalOnClass(NotPermissionException.class)
    @ExceptionHandler(value = NotPermissionException.class)
    public Result notPermissionException(NotPermissionException e) {
        log.error(TAG + "权限验证错误", e);
        return Result.error(Constants.CODE_401, "无权限");
    }

    @ConditionalOnClass(SaTokenException.class)
    @ExceptionHandler(value = SaTokenException.class)
    public Result notLoginException(SaTokenException e) {
        log.error(TAG + "权限验证错误", e);
        return Result.error(Constants.CODE_401, "请登录");
    }
}
