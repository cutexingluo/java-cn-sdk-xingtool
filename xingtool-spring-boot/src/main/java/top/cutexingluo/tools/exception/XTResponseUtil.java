package top.cutexingluo.tools.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.cutexingluo.tools.common.Constants;
import top.cutexingluo.tools.common.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 数据返回处理工具类 <br>
 * 类似 WebUtils
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/5/17 21:48
 */
public class XTResponseUtil {

    /**
     * 返回请求封装
     *
     * @param rsp           返回请求
     * @param result        返回数据
     * @param rspStatusCode 返回码
     */
    public static void response(HttpServletResponse rsp, Result result, int rspStatusCode) throws IOException {
        byte[] responseBytes = new ObjectMapper().writeValueAsBytes(result);
        rsp.addHeader("Content-Type", "application/json;charset=UTF-8");
        rsp.setStatus(rspStatusCode);
        rsp.getWriter().write(new String(responseBytes));
    }

    /**
     * 返回请求封装<br>
     * 返回码 200 SC_OK
     *
     * @param rsp    返回请求
     * @param result 返回数据
     */
    public static void success(HttpServletResponse rsp, Result result) throws IOException {
        response(rsp, result, HttpServletResponse.SC_OK);
    }

    /**
     * 返回请求封装<br>
     * 返回码 400 SC_BAD_REQUEST
     *
     * @param rsp    返回请求
     * @param result 返回数据
     */
    public static void badRequest(HttpServletResponse rsp, Result result) throws IOException {
        response(rsp, result, HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * 返回请求封装<br>
     * 返回码 500 SC_INTERNAL_SERVER_ERROR
     *
     * @param rsp    返回请求
     * @param result 返回数据
     */
    public static void serverError(HttpServletResponse rsp, Result result) throws IOException {
        response(rsp, result, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 未验证请求 401
     *
     * @param rsp    返回请求
     * @param result 返回数据
     */
    public static void unauthorized(HttpServletResponse rsp, Result result) throws IOException {
        response(rsp, result, HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * 未验证请求 401
     *
     * @param rsp 返回请求
     */
    public static void unauthorized(HttpServletResponse rsp) throws IOException {
        response(rsp, Result.error(Constants.CODE_401, "Authentication failed, Insufficient permissions"), HttpServletResponse.SC_UNAUTHORIZED);
    }


    /**
     * 未经授权
     *
     * @param httpServletResponse http servlet响应
     * @param msg                 消息
     * @throws IOException ioexception
     */
    public static void unauthorized(HttpServletResponse httpServletResponse, String msg) throws IOException {
        Result error = Result.error(Constants.CODE_401, msg);
        unauthorized(httpServletResponse, error);
    }

    /**
     * 未验证请求 401
     *
     * @param rsp 返回请求
     */
    public static void unauthorizedCN(HttpServletResponse rsp) throws IOException {
        response(rsp, Result.error(Constants.CODE_401, "认证失败, 权限不足"), HttpServletResponse.SC_UNAUTHORIZED);
    }
}
