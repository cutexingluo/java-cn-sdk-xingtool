package top.cutexingluo.tools.common.opt;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author XingTian
 * @version 1.0.0
 * @date 2024/3/22 17:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OptData<T, Meta> extends OptRes<T> {

    protected Meta meta;

    public OptData(T value) {
        super(value);
    }

    public OptData() {
    }

    public OptData(T value, Class<T> clazz) {
        super(value, clazz);
    }

    public OptData(@NotNull OptRes<T> optRes) {
        this(optRes.value, optRes.clazz, null);
    }

    public OptData(@NotNull OptRes<T> optRes, Meta meta) {
        this(optRes.value, optRes.clazz, meta);
    }

    public OptData(T value, Class<T> clazz, Meta meta) {
        super(value, clazz);
        this.meta = meta;
    }


    @NotNull
    @Contract("_ -> new")
    public static <T> OptData<T, Object> of(OptRes<T> optRes) {
        return new OptData<>(optRes);
    }


}
