package top.cutexingluo.tools.common.valid.num;

import top.cutexingluo.tools.common.valid.StatusValidator;
import top.cutexingluo.tools.utils.se.array.XTArrayUtil;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/19 16:12
 */
public class IntStatusValidator extends StatusValidator<IntStatus, Integer> {

    protected IntStatusConfig statusConfig;

    @Override
    public void initialize(IntStatus constraintAnnotation) {
        statusConfig = new IntStatusConfig(
                constraintAnnotation.notNull(),
                constraintAnnotation.matchNum(),
                constraintAnnotation.limit(),
                constraintAnnotation.min(),
                constraintAnnotation.max()
        );
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (!statusConfig.notNull && value == null) {
            return true;
        }
        if (value != null) {
            List<Integer> nums = XTArrayUtil.toList(statusConfig.matchNum);
            if (statusConfig.matchNum.length > 0 && nums.contains(value)) {
                return true;
            }
            if (statusConfig.limit) {
                if (value < statusConfig.min) {
                    return false;
                }
                return value <= statusConfig.max;
            }
        }
        return false;
    }
}
