package top.cutexingluo.tools.utils.ee.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.cutexingluo.tools.auto.server.XingToolsAutoConfiguration;
import top.cutexingluo.tools.designtools.protocol.serializer.impl.json.JacksonSerializer;
import top.cutexingluo.tools.utils.ee.fastjson.FastJsonRedisSerializer;

import java.nio.charset.StandardCharsets;


/**
 * redis配置
 *
 * @author XingTian
 */
@AutoConfigureAfter(RedisConnectionConfig.class)
@ConditionalOnClass(GenericJackson2JsonRedisSerializer.class)
@ConditionalOnBean(XingToolsAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xingtools.enabled", name = "redisconfig", havingValue = "true", matchIfMissing = false)
@Configuration(proxyBeanMethods = false)
@Import({RedisConnectionConfig.class})
@Slf4j
public class RedisConfig {


    @ConditionalOnProperty(prefix = "xingtools.enabled", name = "redisconfig-setting", havingValue = "jackson", matchIfMissing = true)
    @ConditionalOnBean(RedisConnectionFactory.class)
//    @ConditionalOnMissingBean(name = {"xtRedisTemplate"})  // 有一个 stringRedisTemplate
    @ConditionalOnMissingBean(name = {"xtRedisTemplate"})
    @Bean("xtRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = initTemplate(redisConnectionFactory);
        log.info("RedisTemplate ----> 已发现RedisConnectionFactory  , jackson序列化  {}", "自动配置成功");
        return template;
    }

    @ConditionalOnProperty(prefix = "xingtools.enabled", name = "redisconfig-setting", havingValue = "fastjson", matchIfMissing = false)
    @ConditionalOnBean(RedisConnectionFactory.class)
    @ConditionalOnMissingBean(name = {"xtRedisTemplate"})
    @Bean("xtRedisTemplate")
    public RedisTemplate<String, Object> fastJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = initTemplateByFastJson(redisConnectionFactory);
        log.info("RedisTemplate ----> 已发现RedisConnectionFactory  , fastjson序列化 {}", "自动配置成功");
        return template;
    }

//    @ConditionalOnProperty(prefix = "xingtools.enabled", name = "redisconfig-util",
//            havingValue = "true", matchIfMissing = false)
//    @Bean
////    @Autowired
//    public RedisUtilAutoConfigure redisUtilAutoConfigure(RedisTemplate<String, Object> redisTemplate) {
//        return new RedisUtilAutoConfigure(redisTemplate);
//    }


    /**
     * 加工模板 使用Jackson
     *
     * @param template 模板
     */
    public static <T, V> void initTemplate(RedisTemplate<T, V> template) {
        //配置序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);

        // 1.0.5 封装进入
        ObjectMapper obm = new JacksonSerializer().initRedis().getObjectMapper();


        jackson2JsonRedisSerializer.setObjectMapper(obm);
        //String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key 采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value 采用 JSON
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash 采用String序列化
        template.setHashKeySerializer(stringRedisSerializer);
        // hash value 采用 JSON
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
    }

    @ConditionalOnClass(SerializerFeature.class)
    public static class XTGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {

        @Override
        public byte[] serialize(Object object) throws SerializationException {
            if (object == null) return new byte[0];
            if (object instanceof Long || object instanceof Double)
                return object.toString().getBytes(StandardCharsets.UTF_8);
            try {
                return JSON.toJSONBytes(object, SerializerFeature.WriteClassName);
//                return JSONUtil.toJsonStr(object).getBytes(StandardCharsets.UTF_8);
            } catch (Exception exception) {
                throw new SerializationException("Could not serialize : " + exception.getMessage(), exception);
            }
        }

    }

    /**
     * 返回封装模板
     *
     * @param redisConnectionFactory 连接工厂
     */
    public static RedisTemplate<String, Object> initTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        initTemplate(template);
        return template;
    }

    /**
     * 加工模板 使用 FastJson 序列化
     *
     * @param template 模板
     */
    public static <T, V> RedisTemplate<T, V> initTemplateByFastJson(RedisTemplate<T, V> template) {

        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 返回封装模板 FastJson 序列化
     *
     * @param redisConnectionFactory 连接工厂
     */
    public static RedisTemplate<String, Object> initTemplateByFastJson(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        initTemplateByFastJson(template);
        return template;
    }
}