package top.cutexingluo.tools.common;


//public interface Constants { // 用枚举类也行
//    String CODE_200 = "200"; //成功
//    String CODE_500 = "500"; //系统错误
//    String CODE_401 = "401"; //权限不足
//    String CODE_400 = "400"; //参数错误
//    String CODE_600 = "600"; //其他业务异常
//}


import top.cutexingluo.tools.common.base.IResultData;
import top.cutexingluo.tools.common.base.XTIntCode;

/**
 * HttpStatus Constants枚举类
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/2/1 22:48
 */


public enum Constants implements IResultData<String>, XTIntCode {
    /**
     * 200 成功
     */
    CODE_200("200", "SUCCESS", "成功"),
    /**
     * 500 失败
     */
    CODE_500("500", "SYSTEM_ERROR", "系统错误"),
    CODE_401("401", "UNAUTHORIZED ", "权限不足"),
    CODE_400("400", "BAD_REQUEST ", "参数错误（缺少，格式不匹配）"),
    CODE_403("403", "FORBIDDEN ", "访问受限，授权过期"),
    CODE_404("404", "NOT_FOUND ", "资源，服务未找到"),
    CODE_600("600", "OTHER_ERROR", "其他业务异常"),

    // 下面不常用

    CODE_201("201", "CREATED", "成功, 对象已创建"),
    CODE_202("202", "ACCEPTED", "成功, 请求已经被接受"),
    CODE_204("204", "NO_CONTENT", "成功, 但是没有返回数据"),

    CODE_301("301", "MOVED_PERM", "资源已被移除"),
    CODE_303("303", "SEE_OTHER", "重定向"),
    CODE_304("304", "NOT_MODIFIED", "资源没有被修改"),

    CODE_405("405", "BAD_METHOD", "不允许的http方法"),
    CODE_409("409", "CONFLICT", "资源冲突，或者资源被锁"),
    CODE_415("415", "UNSUPPORTED_TYPE", "不支持的数据，媒体类型"),
    CODE_501("501", "NOT_IMPLEMENTED", "接口未实现"),
    CODE_601("601", "WARN", "系统警告消息");

    private String code;
    private String name;
    private String msg;

    Constants(String code, String name, String msg) {
        this.code = code;
        this.name = name;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int intCode() {
        return Integer.parseInt(code);
    }
}
