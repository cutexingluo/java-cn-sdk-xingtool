package top.cutexingluo.tools.common.valid.str;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import top.cutexingluo.tools.common.valid.StatusValidator;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/19 16:12
 */
public class StrStatusValidator extends StatusValidator<StrStatus, String> {

    protected StrStatusConfig statusConfig;

    @Override
    public void initialize(StrStatus constraintAnnotation) {
        statusConfig = new StrStatusConfig(
                constraintAnnotation.notNull(),
                constraintAnnotation.notBlankIfPresent(),
                constraintAnnotation.lenLimit(),
                constraintAnnotation.minLength(),
                constraintAnnotation.maxLength(),
                constraintAnnotation.anyStr(),
                constraintAnnotation.anyReg()
        );
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!statusConfig.notNull && s == null) {
            return true;
        }
        if (!statusConfig.notBlankIfPresent && s != null && StrUtil.isBlank(s)) {
            return true;
        }
        if (s != null) {
            if (statusConfig.lenLimit) {
                if (statusConfig.minLength >= 0 && s.length() < statusConfig.minLength) {
                    return false;
                }
                if (statusConfig.maxLength >= 0 && s.length() > statusConfig.maxLength) {
                    return false;
                }
            }
            // 先进行绝对匹配
            if (statusConfig.anyStr.length > 0 && Arrays.asList(statusConfig.anyStr).contains(s)) {
                return true;
            }
            // 再进行正则匹配
            if (statusConfig.anyReg.length > 0) {
                for (String str : statusConfig.anyReg) {
                    if (ReUtil.isMatch(str, s)) {
                        return true;
                    }
                }
            }
        }
        // 直接拒绝
        return false;
    }
}
