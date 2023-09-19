package top.cutexingluo.tools.aop.log;

import lombok.extern.slf4j.Slf4j;
import top.cutexingluo.tools.basepackage.baseimpl.XTRunnable;

/**
 * 日志输出Handler
 *
 * @author XingTian
 * @version 1.0.0
 * @date 2023/4/6 19:04
 */
@Slf4j
public class LogHandler {
    LogType type;

    public LogHandler(LogType type) {
        this.type = type;
    }

    public XTRunnable getTask(String msg) {
        return new XTRunnable(() -> {
            switch (type) {
                case Info:
                    log.info(msg);
                    break;
                case Warn:
                    log.warn(msg);
                    break;
                case Error:
                    log.error(msg);
                    break;
                case Debug:
                    log.debug(msg);
                    break;
                default:
                    System.out.println(msg);
                    break;
            }
        });
    }

    public void send(String msg) {
        getTask(msg).run();
    }
}
