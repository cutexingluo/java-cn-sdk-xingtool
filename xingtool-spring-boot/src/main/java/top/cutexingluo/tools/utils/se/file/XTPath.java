package top.cutexingluo.tools.utils.se.file;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Path 封装工具类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/3 15:10
 */
public class XTPath {

    /**
     * 环境类型
     */
    public enum EnvType {
        /**
         * 开发环境，即直接运行Application项目
         */
        DEV("dev"),
        /**
         * 测试环境，即ApplicationTests中测试
         */
        TEST("test"),
        /**
         * 发布环境，即打包成jar包环境,
         */
        PROD("prod");

        final String code;

        EnvType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 自动选择环境
     *
     * @param code 编码
     * @return {@link EnvType}
     */
    public static EnvType selectEnv(String code) {
        if (EnvType.DEV.getCode().equalsIgnoreCase(code)) {
            return EnvType.DEV;
        }
        if (EnvType.TEST.getCode().equalsIgnoreCase(code)) {
            return EnvType.TEST;
        }
        return EnvType.PROD;
    }

    public static final String SEPARATOR = File.separator;
    public static final String CLASSPATH = "classpath:/";
    public static final String MAIN_PATH = SEPARATOR + "src" + SEPARATOR + "main";

    /**
     * 获取项目地址（ idea 打开的地址）
     *
     * @return 项目地址
     */
    @Deprecated //由于只能获取 当前用户idea打开项目的根目录，已弃用
    public static String getProjectPath() { // 获取项目绝对路径
        return System.getProperty("user.dir");
    }

    //极力推荐用，后面发布会有用
    //由于class文件根目录是classes

    /**
     * 利用Spring获取项目绝对路径
     *
     * @param clazz 目标类
     * @return 项目地址
     */
    public static String getProjectPath(Class<?> clazz) {
        return getProjectPath(clazz, EnvType.PROD);
    }

    /**
     * 利用Spring获取项目绝对路径
     * <p>PathType 代表环境</p>
     *
     * @param clazz 目标类
     * @return 项目地址
     */
    public static String getProjectPath(Class<?> clazz, EnvType envType) {
        ApplicationHome applicationHome = new ApplicationHome(clazz);
        //classes -> target  ->  project path
        //getParentFile().getParentFile().
        File homeDir = applicationHome.getDir();
        if (envType == EnvType.DEV) {
            homeDir = homeDir.getParentFile().getParentFile();
        }
        return homeDir.getAbsolutePath();
    }

    /**
     * 从字节码位置推测 ,
     * 利用Spring获取源项目地址中java文件夹路径
     * <ul>
     *     <li>在 test , dev 环境下运行</li>
     *     <li>不要 prod 下使用该方法, 除非jar包在根目录下 </li>
     * </ul>
     *
     * @param clazz   目标类
     * @param envType 环境类型
     * @return 项目地址
     */
    public static String getProjectJavaPath(Class<?> clazz, EnvType envType) {
        return getProjectPath(clazz, envType) + MAIN_PATH + SEPARATOR + "java";
    }

    /**
     * 从字节码位置推测 ,
     * 利用Spring获取源项目地址中resource文件夹路径
     * <ul>
     *     <li>在 test , dev 环境下运行</li>
     *     <li>不要 prod 下使用该方法 </li>
     * </ul>
     *
     * @param clazz   目标类
     * @param envType 环境类型
     * @return 项目地址
     */
    public static String getProjectResPath(Class<?> clazz, EnvType envType) {
        return getProjectPath(clazz, envType) + MAIN_PATH + SEPARATOR + "resources";
    }

    /**
     * 从字节码位置推测 java源文件的路径 （只是推测，慎用）
     * <ul>
     *     <li>在 test , dev 环境下运行</li>
     *     <li>不要 prod 下使用该方法 </li>
     * </ul>
     *
     * @param clazz 目标类
     * @return 目标类的 java 绝对路径
     */
    public static String getTryJavaAbsolutePath(Class<?> clazz, EnvType envType) {
        return getProjectJavaPath(clazz, envType) + SEPARATOR + packageToPath(clazz);
    }

    /**
     * 从字节码位置推测 java源文件的路径 （包含文件名） （只是推测，慎用）
     * <ul>
     *     <li>在 test , dev 环境下运行</li>
     *     <li>不要 prod 下使用该方法 </li>
     * </ul>
     *
     * @param clazz 目标类
     * @return 目标类的 java 绝对路径
     */
    public static String getTryJavaAbsolutePathWithName(Class<?> clazz, EnvType envType) {
        return getTryJavaAbsolutePath(clazz, envType) + SEPARATOR + clazz.getSimpleName() + ".java";
    }


    //********************************* 以下适用于Dev状态，class字节码用上面的

    /**
     * @param clazz        目标类
     * @param relativePath 相对位置
     * @return 目标类的 class 路径的相对位置
     */
    public static String getPath(@NotNull Class<?> clazz, String relativePath) {
        return new File(Objects.requireNonNull(clazz.getResource(relativePath)).getPath()).getAbsolutePath();
    }

    /**
     * @param thisObj      目标对象
     * @param relativePath 相对位置
     * @return 目标对象的 class 路径的相对位置
     */
    public static <T> String getRelativeToPath(T thisObj, String relativePath) {
        return new File(Objects.requireNonNull(thisObj.getClass().getResource(relativePath)).getPath()).getAbsolutePath();
    }

    /**
     * @param clazz 目标类
     * @return 目标类的 class 绝对路径
     */
    public static String getAbsolutePath(Class<?> clazz) {
        return getPath(clazz, "./");
    }


    /**
     * @param thisObj 目标对象
     * @return 目标对象 class 的绝对路径
     */
    public static <T> String getCurrentAbsolutePath(T thisObj) {
        return getAbsolutePath(thisObj.getClass());
    }

    // 包名

    /**
     * @param clazz 类名
     * @return 类所在的包名（不包含类名）
     */
    public static String getPackageName(Class<?> clazz) {
        return clazz.getPackage().getName();
    }

    // 包名包含本身

    /**
     * @param clazz 类名
     * @return 类所在的包名（包含类名）
     */
    public static String getPackageNameWIthClass(Class<?> clazz) {
        return clazz.getCanonicalName();
    }

    // 包名转路径

    /**
     * 包名转相对路径(com.example.common  =>   com/example/common)
     *
     * @param packageName 包名
     * @return 路径
     */
    public static String packageNameToSrcPath(String packageName) {
        return packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
    }

    // 类路径

    /**
     * 将类所在的包名转为相对路径(com.example.common  =>   com/example/common)
     *
     * @param clazz 类名
     * @return 路径
     */
    public static String packageToPath(Class<?> clazz) {
        return packageNameToSrcPath(getPackageName(clazz));
    }

    /**
     * 合并路径
     *
     * @param parentPath 父路径 (D://hello ,D://hello/)
     * @param sonPath    子路径 (world ,/world)
     * @return 合成的路径 (D://hello/world)
     */
    public static String combinePath(@NotNull String parentPath, @NotNull String sonPath) {
        parentPath = parentPath.trim();
        sonPath = sonPath.trim();
        if (!parentPath.endsWith(SEPARATOR) && !sonPath.startsWith(SEPARATOR)) parentPath += SEPARATOR;
        else if (parentPath.endsWith(SEPARATOR) && sonPath.startsWith(SEPARATOR)) sonPath = sonPath.substring(1);
        return parentPath + sonPath;
    }
}
