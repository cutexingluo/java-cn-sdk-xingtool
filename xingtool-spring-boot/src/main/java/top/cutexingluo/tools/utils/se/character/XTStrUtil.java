package top.cutexingluo.tools.utils.se.character;

import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/7/16 15:24
 */
public class XTStrUtil {

    public static final RegexPool regexPool = null;

    /**
     * 得到所有字符串最小长度
     * <p>若为空则返回-1</p>
     *
     * @param sequences str
     * @return int 最小字符串长度
     */
    public static int getMinLength(Collection<CharSequence> sequences) {
        if (sequences == null) return -1;
        int min = Integer.MAX_VALUE;
        for (CharSequence s : sequences) {
            min = Math.min(min, s.length());
        }
        return min;
    }

    /**
     * 得到所有字符串最小长度
     * <p>若为空则返回-1</p>
     *
     * @param sequences str
     * @return int 最小字符串长度
     */
    public static int getMinLength(CharSequence[] sequences) {
        if (sequences == null) return -1;
        int min = Integer.MAX_VALUE;
        for (CharSequence s : sequences) {
            min = Math.min(min, s.length());
        }
        return min;
    }

    /**
     * 得到所有字符串最大长度
     * <p>若为空则返回-1</p>
     *
     * @param sequences str
     * @return int 最小字符串长度
     */
    public static int getMaxLength(Collection<CharSequence> sequences) {
        if (sequences == null) return -1;
        int max = 0;
        for (CharSequence s : sequences) {
            max = Math.max(max, s.length());
        }
        return max;
    }

    /**
     * 得到所有字符串最大长度
     * <p>若为空则返回-1</p>
     *
     * @param sequences str
     * @return int 最小字符串长度
     */
    public static int getMaxLength(CharSequence[] sequences) {
        if (sequences == null) return -1;
        int max = 0;
        for (CharSequence s : sequences) {
            max = Math.max(max, s.length());
        }
        return max;
    }


    /**
     * 向右填充空格，不足则填充
     *
     * @param inputString 输入字符串
     * @param length      最大长度
     * @return {@link String}
     */
    public static String padRight(String inputString, int length) {
        return String.format("%-" + length + "s", inputString);
    }


    /**
     * 检测邮箱是否合法
     *
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String username) {
        // RegexPool.EMAIL
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    /**
     * 检测手机号是否合法
     *
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkPhone(String username) {
//        RegexPool.MOBILE
        //验证手机号是否合法
        String rule = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    /**
     * 获取括号内容
     *
     * @param str str
     * @return {@link String} 括号内容
     */
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }


    /**
     * 将所有字符串设置为null
     *
     * @param str str
     * @return {@link String}
     */
    public static void setNull(String... str) {
        Arrays.fill(str, null);
    }

    /**
     * 将所有字符串设置为空字符串""
     *
     * @param str str
     * @return {@link String}
     */
    public static void setBlank(String... str) {
        Arrays.fill(str, "");
    }

    /**
     * 正则匹配
     *
     * @param reg     正则表达式
     * @param content 内容
     * @return boolean
     */
    public static boolean matches(String reg, String content) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);
        return m.matches();
    }
    //---------------------find------------------------

    // 获取 next 数组

    /**
     * 获取next数组, 获取最长前后缀
     *
     * @since 1.0.2
     */
    public static int[] getNext(String sub) {
        int[] next = new int[sub.length() + 1];
        int i = 0, j = -1;
        next[0] = -1;
        while (i < sub.length()) {
            if (j == -1 || sub.charAt(i) == sub.charAt(j)) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    private static int checkBounds(String origin, int startIndex, String substring) {
        if (StrUtil.isBlank(origin) || StrUtil.isBlank(substring)) {
            return -2;
        }
        if (startIndex < 0 || startIndex >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndex must be  greater than  or equals 0 and less than origin string length");
        } else if (startIndex + substring.length() > origin.length()) {
            return -1;
        }
        return 0;
    }

    /**
     * KMP查找字符串, 返回下标
     *
     * @return -1 means not found , -2 means input is empty
     * @since 1.0.2
     */
    public static int find(String origin, int startIndex, String substring) {
        int ret = checkBounds(origin, startIndex, substring);
        if (ret < 0) return ret;
        int i = startIndex, j = 0;
        int[] next = getNext(substring);
        while (i < origin.length() && j < substring.length()) {
            if (j == -1 || origin.charAt(i) == substring.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == substring.length())
            return i - j;
        else
            return -1;
    }

    /**
     * KMP查找字符串, 返回下标
     *
     * @return -1 means not found , -2 means input is empty
     * @since 1.0.2
     */
    public static int find(String origin, String substring) {
        return find(origin, 0, substring);
    }

    /**
     * KMP逆向查找字符串, 返回下标
     * <p>反向查找，正向匹配</p>
     * <p>包含这个位置及其后面的字符串, 例如 rFind(“ababa” , 1 ,  "ba") = 1</p>
     *
     * @param startIndexToPre 从指定位置往前查找
     * @return -1 means not found , -2 means input is empty
     * @since 1.0.2
     */
    public static int rFind(String origin, int startIndexToPre, String substring) {
        if (StrUtil.isBlank(origin) || StrUtil.isBlank(substring)) {
            return -2;
        }
        if (startIndexToPre < 0 || startIndexToPre >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndexToPre must be  greater than  or equals 0 and less than origin string length");
        }
        int rpLen = origin.length() - substring.length();  //反转变量
        int startIndex = Math.max(rpLen - startIndexToPre, 0);
        String s = new StringBuilder(origin).reverse().toString();
        String ns = new StringBuilder(substring).reverse().toString();
        int revPos = find(s, startIndex, ns);
        if (revPos == -1) return -1;
        return rpLen - revPos; //反转回来
    }

    /**
     * KMP逆向查找字符串, 返回下标
     * <p>反向查找，正向匹配</p>
     *
     * @return -1 means not found , -2 means input is empty
     * @since 1.0.2
     */
    public static int rFind(String origin, String substring) {
        return rFind(origin, origin.length() - 1, substring);
    }


    private static int findFirstOfCollection(String origin, int startIndex, String characterCollection, boolean isInCollection, boolean ignoreCase) {
        if (ignoreCase) origin = origin.toLowerCase();
        // chars[]数组转为列表
        HashSet<Character> characters = characterCollection
                .chars()
                .mapToObj(i -> {
                    if (ignoreCase) {
                        return Character.toLowerCase((char) i);
                    } else {
                        return (char) i;
                    }
                }).collect(Collectors.toCollection(HashSet::new));
        if (!isInCollection) {
            for (int i = startIndex; i < origin.length(); i++) {
                if (!characters.contains(origin.charAt(i))) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < origin.length(); i++) {
                if (characters.contains(origin.charAt(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 找到字符串第一个出现指定字符的位置
     *
     * @param characterCollection 字符集合组成的字符串
     * @return -1 means not found , -2 means origin is empty
     * @since 1.0.2
     */
    public static int findFirstOf(String origin, int startIndex, String characterCollection, boolean ignoreCase) {
        if (origin == null) return -2;
        if (startIndex < 0 || startIndex >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndex must be  greater than  or equals 0 and less than origin string length");
        }
        if (StrUtil.isBlank(characterCollection)) {
            return -1;
        }
        return findFirstOfCollection(origin, startIndex, characterCollection, true, ignoreCase);
    }

    /**
     * 找到字符串第一个不出现指定字符的位置
     *
     * @param characterCollection 字符集合组成的字符串
     * @return -1 means not found , -2 means origin is empty
     * @since 1.0.2
     */
    public static int findFirstNotOf(String origin, int startIndex, String characterCollection, boolean ignoreCase) {
        if (origin == null) return -2;
        if (startIndex < 0 || startIndex >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndex must be  greater than  or equals 0 and less than origin string length");
        }
        if (StrUtil.isBlank(characterCollection)) {
            return 0;
        }
        return findFirstOfCollection(origin, startIndex, characterCollection, false, ignoreCase);
    }

    /**
     * 找到字符串最后一个出现指定字符的位置
     *
     * @param characterCollection 字符集合组成的字符串
     * @param startIndex          从指定位置往前查找
     * @return -1 means not found , -2 means origin is empty
     * @since 1.0.2
     */
    public static int findLastOf(String origin, int startIndex, String characterCollection, boolean ignoreCase) {
        if (origin == null) return -2;
        if (startIndex < 0 || startIndex >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndex must be  greater than  or equals 0 and less than origin string length");
        }
        if (StrUtil.isBlank(characterCollection)) {
            return -1;
        }
        String s = new StringBuilder(origin).reverse().toString();
        int revPos = findFirstOfCollection(s, startIndex, characterCollection, true, ignoreCase);
        if (revPos == -1) return -1;
        return s.length() - 1 - revPos;
    }

    /**
     * 找到字符串最后一个不出现指定字符的位置
     *
     * @param characterCollection 字符集合组成的字符串
     * @return -1 means not found , -2 means origin is empty
     * @since 1.0.2
     */
    public static int findLastNotOf(String origin, int startIndex, String characterCollection, boolean ignoreCase) {
        if (origin == null) return -2;
        if (startIndex < 0 || startIndex >= origin.length()) {
            throw new ArrayIndexOutOfBoundsException("startIndex must be  greater than  or equals 0 and less than origin string length");
        }
        if (StrUtil.isBlank(characterCollection)) {
            return origin.length() - 1;
        }
        String s = new StringBuilder(origin).reverse().toString();
        int revPos = findFirstOfCollection(s, startIndex, characterCollection, false, ignoreCase);
        if (revPos == -1) return -1;
        return s.length() - 1 - revPos;
    }
}
