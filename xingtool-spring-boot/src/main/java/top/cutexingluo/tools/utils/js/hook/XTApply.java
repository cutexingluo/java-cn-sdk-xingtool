package top.cutexingluo.tools.utils.js.hook;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.List;


/**
 * <p>
 * 仿 js 的Apply类，反射调用方法 <br>
 * XTApply拥有XTCall方法
 * </p>
 * <br>
 * <h3>使用方法</h3>
 * <ul>
 *     <li>
 *         new XTApply(PrintClass.class, "print", String.class).apply(target,"hello world");
 *     </li>
 * </ul>
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/10 22:39
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class XTApply {
    private Method method;

    public XTApply(Class<?> clazz, String methodName, @NonNull Class<?>... args) throws NoSuchMethodException {
        this.method = clazz.getMethod(methodName, args);
    }

    public XTApply(Class<?> clazz, String methodName, @NonNull List<Class<?>> args) throws NoSuchMethodException {
        Class<?>[] clazzArray = XTApply.getClassesFromList(args, true);
        this.method = clazz.getMethod(methodName, clazzArray);
    }

    public static <T> Class<?>[] getClassesFromList(List<T> args, boolean isClass) throws ClassCastException {
//        List<? extends Class<?>> classList = null;
        Class<?>[] classArray = null;
        if (!isClass) classArray = (Class<?>[]) args.stream().map(Object::getClass).toArray();
        return classArray != null ? classArray : (Class<?>[]) args.toArray();
    }

    // apply
    public static <T, R> R apply(Method method, T target, List<Object> args) {
        if (method == null || target == null) return null;
        try {
            return (R) method.invoke(target, args.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T, R> R apply(String methodName, T target, List<Object> args) throws NoSuchMethodException {
        Class<?>[] classArray = XTApply.getClassesFromList(args, false);
        return XTApply.apply(target.getClass().getMethod(methodName, classArray), target, args);
    }

    public <T, R> R apply(T target, List<Object> args) {
        return XTApply.apply(method, target, args);
    }
}
