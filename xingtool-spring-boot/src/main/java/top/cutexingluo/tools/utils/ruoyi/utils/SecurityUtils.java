package top.cutexingluo.tools.utils.ruoyi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.cutexingluo.tools.exception.ServiceException;
import top.cutexingluo.tools.utils.ruoyi.constraint.HttpStatus;

/**
 * 安全服务工具类
 *
 * @author ruoyi
 *
 * updated By @author XingTian
 * @update 1.0.2
 * @since 1.0.0
 */
public class SecurityUtils {


    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }


    /**
     * 获取用户账户
     *
     * @since 1.0.2
     **/
    public static String getUsername() {
        try {
            UserDetails loginUser = getLoginUser();
            return loginUser != null ? loginUser.getUsername() : null;
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户账户异常");
        }
    }

    /**
     * 获取用户,限定类型
     *
     * @since 1.0.2
     **/
    public static <T extends UserDetails> T getLoginUser(Class<T> loginUserClass) {
        try {
            Object principal = getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                return (T) principal;
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }

    /**
     * 获取用户, 自动判断类型
     *
     * @since 1.0.2
     **/
    public static <T extends UserDetails> T getLoginUser() {
        try {
            Object principal = getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                return (T) principal;
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }
}
